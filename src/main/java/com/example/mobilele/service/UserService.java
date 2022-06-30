package com.example.mobilele.service;

import com.example.mobilele.dto.UserLoginDTO;
import com.example.mobilele.dto.UserRegisterDTO;
import com.example.mobilele.entity.UserEntity;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.user.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private CurrentUser currentUser;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       CurrentUser currentUser,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(UserLoginDTO userLoginDTO){
        Optional<UserEntity> userEntityOptional = userRepository.
                findByEmail(userLoginDTO.getUsername());

        if (userEntityOptional.isEmpty()){
            LOGGER.debug("User with name [{}] not found.",userLoginDTO.getUsername());
            return false;
        }
        var rawPassword  =  userLoginDTO.getPassword();
        var hashedPassword = userEntityOptional.get().getPassword();

        boolean successfulLogin = passwordEncoder.matches(rawPassword,hashedPassword);

        if (successfulLogin){
            login(userEntityOptional.get());
        }else {
            logout();
        }

        return successfulLogin;
    }

    private void login(UserEntity userEntity){
        currentUser.setLogged(true);
        currentUser.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    public void logout() {
        currentUser.clear();
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO){
        UserEntity newUser =
                new UserEntity().setActive(true)
                        .setEmail(userRegisterDTO.getEmail())
                        .setFirstName(userRegisterDTO.getFirstName())
                        .setLastName(userRegisterDTO.getLastName())
                        .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(newUser);
        login(newUser);
    }

}
