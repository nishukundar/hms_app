package com.hms.service;

import com.auth0.jwt.JWT;
import com.hms.dto.LoginDTO;
import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public String verifyLogin( LoginDTO logindto){
        Optional<AppUser> byUsername = appUserRepository.findByUsername(logindto.getUsername());
        //System.out.println(byUsername);
        if(byUsername.isPresent()){
            AppUser appUser = byUsername.get();

            if(BCrypt.checkpw(logindto.getPassword(), appUser.getPassword())){
                //generate token ComputerEngineerIsUnemployed

                System.out.println("Before generateToken()");
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }

        }else{
            return null;
        }
        return  null;

    }
}
