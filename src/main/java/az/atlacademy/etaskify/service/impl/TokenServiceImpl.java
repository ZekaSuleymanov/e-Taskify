package az.atlacademy.etaskify.service.impl;

import az.atlacademy.etaskify.entity.UserEntity;

public interface TokenServiceImpl {

    String generateAndSaveRefreshToken(UserEntity userEntity);
    String generateAndSaveConfirmationToken(UserEntity userEntity);

}
