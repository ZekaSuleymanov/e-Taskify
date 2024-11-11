package az.atlacademy.etaskify.provider;



import az.atlacademy.etaskify.config.AuthenticationDetails;
import az.atlacademy.etaskify.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class DaoAuthenticationProviderDetails extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken successAuthentication =
                (UsernamePasswordAuthenticationToken) super.createSuccessAuthentication(principal, authentication, userDetails);
        UserEntity userEntity = (UserEntity) userDetails;
        successAuthentication.setDetails(new AuthenticationDetails(null,
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getOrganizationEntity().getId()));
        return successAuthentication;
    }

}
