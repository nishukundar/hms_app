package com.hms.controller;

import com.hms.dto.LoginDTO;
import com.hms.dto.TokenDTO;
import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody AppUser user){
        Optional<AppUser> byUsername = appUserRepository.findByUsername(user.getUsername());

        if(byUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> byEmail = appUserRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }



       // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();//optional
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);

        String enpwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(enpwd);

        AppUser savedUser = appUserRepository.save(user);
        return  new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String msg(){
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginDTO loginDTO){
        String token = userService.verifyLogin(loginDTO);

        if(token!=null){
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);
            tokenDTO.setType("JWT");
            return new ResponseEntity<>(tokenDTO,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username/password",HttpStatus.FORBIDDEN);
        }

    }
}
