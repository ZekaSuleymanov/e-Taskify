package az.atlacademy.etaskify.repository;

import az.atlacademy.etaskify.entity.TokenEntity;
import az.atlacademy.etaskify.entity.UserEntity;
import az.atlacademy.etaskify.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByTokenTypeAndNameAndAvailable(TokenType tokenType, String name, boolean available);

    Optional<TokenEntity> findByUserAndTokenType(UserEntity userEntity, TokenType tokenType);

}
