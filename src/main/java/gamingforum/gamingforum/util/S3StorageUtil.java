package gamingforum.gamingforum.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class S3StorageUtil {
    private final AmazonS3 amazonS3;

    public void save(String path, String filename, Optional<Map<String, String>> optionalMetadata, InputStream inputStream){
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()){
                map.forEach(metadata::addUserMetadata);
            }
        });
        try{
            amazonS3.putObject(path, filename, inputStream, metadata);
        } catch (AmazonS3Exception e){
            throw new IllegalStateException("Failed to store file to S3", e);
        }
    }

    public byte[] download(String path, String key){
        try{
            S3Object object = amazonS3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e){
            throw new IllegalStateException("Failed to download file from S3", e);
        }
    }

    public void checkFile(MultipartFile file){
        //check if file is empty

        if(file.isEmpty()) throw new IllegalStateException("Cannot upload empty file [" + file.getSize()+ "]");

        //check if file is image
        if(Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF).contains(file.getContentType()))

            throw new IllegalStateException("File must be an image ["+ file.getContentType() +"]");

    }

    public Map<String, String> getMetaData(MultipartFile file){
        //Grab some meta data

        Map<String, String> metadata = new HashMap<>();

        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        return metadata;
    }
}
