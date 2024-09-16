package com.vitiwari.response;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse {
    private String userName;
    private String msg;
    private String token;
    private String refreshToken;
}
