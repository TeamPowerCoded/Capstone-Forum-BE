package gamingforum.gamingforum.exception;

import gamingforum.gamingforum.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeUtil dateTimeUtil;

    @ExceptionHandler(EntryAlreadyExist.class)
    public ResponseEntity<Object> handlerUserAlreadyExist(EntryAlreadyExist exception) {
        return new ResponseEntity<Object>(new ApiError(exception.getMessage(), HttpStatus.BAD_REQUEST, dateTimeUtil.currentDate()), HttpStatus.BAD_REQUEST);
    }
}