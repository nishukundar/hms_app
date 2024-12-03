package com.hms.controller;

import com.hms.dto.LoginDTO;
import com.hms.dto.PropertyDto;
import com.hms.dto.TokenDTO;
import com.hms.dto.UserDto;
import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.modelmapper.ModelMapper;
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

    //This is for those who signup using /signup url are User
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
        user.setRole("ROLE_USER");

        AppUser savedUser = appUserRepository.save(user);
        return  new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //This is for those who signup using /signup-property-owner url are Property Owner
    //different role= different url Dont create url for ADMIN (HardCode)
    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwner(@RequestBody AppUser user){
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
        user.setRole("ROLE_OWNER");

        AppUser savedUser = appUserRepository.save(user);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(savedUser, UserDto.class);
        return  new ResponseEntity<>(userDto, HttpStatus.CREATED);
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
