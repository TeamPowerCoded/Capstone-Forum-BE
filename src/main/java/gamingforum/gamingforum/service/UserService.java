package gamingforum.gamingforum.service;

import gamingforum.gamingforum.dto.UserDTO;
import gamingforum.gamingforum.entity.UserEntity;
import gamingforum.gamingforum.exception.EntryAlreadyExist;
import gamingforum.gamingforum.model.UserRequest;
import gamingforum.gamingforum.repository.UserRepository;
import gamingforum.gamingforum.util.DateTimeUtil;
import gamingforum.gamingforum.util.S3StorageUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DateTimeUtil dateTimeUtil;
    private final ModelMapper modelMapper;
    private final S3StorageUtil s3StorageUtil;

    public List<UserDTO> getAllUsers() {

        // Get all data from database
        List<UserEntity> allUsers = userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        // Initialize dto
        List<UserDTO> allUsersDto = new ArrayList<>();

        allUsers.forEach(user -> {
            allUsersDto.add(modelMapper.map(user, UserDTO.class));
        });

        return allUsersDto;
    }
    public UserDTO saveUser(@NonNull UserRequest newUser){


        //Check if email is existing
        if(userRepository.findByEmail(newUser.getEmail()) != null){
            throw new EntryAlreadyExist("Email is already in used");
        }

        //Initialize user
        UserEntity user = UserEntity
                .builder()
                .userId(UUID.randomUUID())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .imageLink(null)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Save to Database
        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO loginUser(@NonNull UserRequest activeUser){
        //Initialize User
        UserEntity user = userRepository.findByEmail(activeUser.getEmail());

        //check if user is existing
        if(user == null) throw new EntryAlreadyExist("User does not exist");

        //check if email is existing
        if(!Objects.equals(user.getPassword(), activeUser.getPassword())) throw new EntryAlreadyExist("Invalid Password");
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO getCurrentUser(String email){
        UserEntity user = userRepository.findByEmail(email);
        if(user == null) throw new EntryAlreadyExist("User does not exist");

        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> uploadUserImage(UUID userId, MultipartFile file){
        //Initialize user
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null) throw new IllegalStateException("product does not exist");

        //Check if file validity
        s3StorageUtil.checkFile(file);

        // Grab some meta data
        Map<String, String> metadata = s3StorageUtil.getMetaData(file);

        // Store the image to S3 bucket
        String path = String.format("%s/%s", "062289/user-profile", userId);

        String fileName = String.format("%s-%s", "user", file.getOriginalFilename());

        try{
            s3StorageUtil.save(path, fileName, Optional.of(metadata), file.getInputStream());
            userRepository.save(UserEntity
                    .builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .imageLink(fileName)
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(dateTimeUtil.currentDate())
                    .build());

        } catch(IOException e){
            throw new IllegalStateException(e);
        }

        return getAllUsers();
    }

    public byte[] downloadUserImage(UUID userId){
        //Initialize product
        UserEntity user = userRepository.findByUserId(userId);
        if(user == null) throw new IllegalStateException("this user does not exist");

        String path = String.format("%s/%s", "062289/user-profile", userId);

        return s3StorageUtil.download(path, user.getImageLink());

    }

}
