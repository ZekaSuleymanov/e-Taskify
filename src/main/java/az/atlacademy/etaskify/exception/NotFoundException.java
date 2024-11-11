package az.atlacademy.etaskify.exception;


import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final ExceptionResponse response;
    private final Object dynamicKey;

    public NotFoundException(ExceptionResponse response, Object dynamicKey) {
        super(response.getMessage());
        this.response = response;
        this.dynamicKey = dynamicKey;
    }

    public static NotFoundException of(ExceptionResponse response, Object dynamicKey) {
        return new NotFoundException(response, dynamicKey);
    }


}
