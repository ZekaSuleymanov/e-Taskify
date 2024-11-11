package az.atlacademy.etaskify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private  String accessToken;
    private  String refreshToken;
    private LocalDateTime responseTime;


    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.responseTime = LocalDateTime.now();
    }
    public static TokenResponse of(String accessToken,String refreshToken){
        return new TokenResponse(accessToken,refreshToken);
    }

}
