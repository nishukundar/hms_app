package com.hms.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    //3rd part -- signature
    @Value("${jwt.algorithm.key}") // @Value ann will go to .properties file and search "jwt.algorithm.key" key and take its value store it in algorithmKey var
    private String algorithmKey;  //algorithmKey = ncskdjuvbvhfshvgsfjvhbjshvb

    //2nd part of jwt -- payload
    @Value("${jwt.issuer}")
    private String issuer;  //issuer =nischitha

    //2nd part of jwt -- payload
    @Value("${jwt.expire.duration}")
    private int expiryTime; //  expiryTime = 5650090

    //1st part of jwt -- algorithm
    private Algorithm algorithm;

    @PostConstruct // this ann used to call method automatically without user called
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm =  Algorithm.HMAC256(algorithmKey);
    }

    //generate token -- Computer Engineer Is Unemployee
    public String generateToken(String username){


        String genToken = JWT.create()
                .withClaim("name", username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);

        System.out.println(genToken);
        return genToken;
    }

    //which is responsible for extracting the username from a JWT (JSON Web Token):
//    The getUserName method effectively does the following:
//
//    It takes a JWT token as input.
//    It verifies the token using the specified signing algorithm and issuer.
//    If valid, it extracts the username (or relevant claim) from the token and returns it as a String.

   public String getUserName(String token){//JunioR With Boxer Vicky
        DecodedJWT decodedJWT =
                JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        return decodedJWT.getClaim("name").asString();
    }
}
