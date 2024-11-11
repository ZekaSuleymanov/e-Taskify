package az.atlacademy.etaskify.handler;



import az.atlacademy.etaskify.enums.Exceptions;
import az.atlacademy.etaskify.exception.ApplicationException;
import az.atlacademy.etaskify.exception.NotFoundException;
import az.atlacademy.etaskify.exception.UserNotFoundException;
import az.atlacademy.etaskify.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHandler {
    private final MessageUtil messageUtil;
    private final ErrorAttributes errorAttributes;


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handle(ApplicationException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(NotFoundException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(UsernameNotFoundException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handle(BadCredentialsException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handle(DataIntegrityViolationException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handle(Exception ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }

    private Map<String, Object> buildExceptionResponse(String message,
                                                       WebRequest webRequest,
                                                       HttpStatus http) {

        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        errorAttributes.put("error", message);
        errorAttributes.put("status", http.value());
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("path", ((ServletRequestAttributes) webRequest).getRequest().getServletPath());

        return errorAttributes;
    }
    /////
    private Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions defaults) {
        return (Map<String, Object>) errorAttributes;
    }

    private Map<String, Object> buildExceptionResponse(MethodArgumentNotValidException ex,
                                                       WebRequest webRequest) {

        Map<String,Object> invalidFields = new HashMap<>();
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());


        ex.getFieldErrors().forEach(fieldError -> invalidFields.put(fieldError.getField(),messageUtil.getMessage(fieldError.getDefaultMessage())));

        errorAttributes.put("error", invalidFields);
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("path", ((ServletRequestAttributes) webRequest).getRequest().getServletPath());

        return errorAttributes;
    }

    private ResponseEntity<Map<String,Object>> of(ApplicationException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(ex.getResponse().getMessage()),
                        webRequest,
                        ex.getResponse().getStatus()),
                ex.getResponse().getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(MethodArgumentNotValidException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(ex,
                        webRequest
                ),
                HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Map<String,Object>> of(Exception ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(ex.getMessage(),
                        webRequest,
                        HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Map<String,Object>> of(DataIntegrityViolationException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getMessage()),
                        webRequest,
                        Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getStatus()),
                Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getStatus());
    }

    private ResponseEntity<Map<String,Object>> of(BadCredentialsException ex,
                                                  WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(Exceptions.BAD_CREDENTIALS_EXCEPTION.getMessage()),
                        webRequest,
                        Exceptions.BAD_CREDENTIALS_EXCEPTION.getStatus()),
                Exceptions.BAD_CREDENTIALS_EXCEPTION.getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(NotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(buildExceptionResponse(
                messageUtil.getMessage(ex.getResponse().getMessage(),ex.getDynamicKey()),
                webRequest,
                ex.getResponse().getStatus()),ex.getResponse().getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(UserNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(buildExceptionResponse(
                messageUtil.getMessage(ex.getResponse().getMessage()),
                webRequest,
                ex.getResponse().getStatus()),ex.getResponse().getStatus());
    }
}


