package az.atlacademy.etaskify.contant;

import lombok.Value;

public class TokenTime {

    @Value("${app.time.token.refresh}")
    public static long REFRESH_TOKEN_TIME;
    @Value("${app.time.token.confirmation}")
    public static long CONFIRMATION_TOKEN_TIME;

}
