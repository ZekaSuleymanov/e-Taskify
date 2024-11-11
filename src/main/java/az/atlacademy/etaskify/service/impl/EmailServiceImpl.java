package az.atlacademy.etaskify.service.impl;

import org.springframework.scheduling.annotation.Async;

public interface EmailServiceImpl {

    @Async
    void sendTo(String email, Object subject);

}
