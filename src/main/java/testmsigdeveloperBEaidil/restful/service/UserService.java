package testmsigdeveloperBEaidil.restful.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.ValidationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testmsigdeveloperBEaidil.restful.entity.User;
import testmsigdeveloperBEaidil.restful.model.RegisterUserRequest;

import testmsigdeveloperBEaidil.restful.model.UpdateUserRequest;
import testmsigdeveloperBEaidil.restful.model.UserResponse;
import testmsigdeveloperBEaidil.restful.repository.UserRepository;
import testmsigdeveloperBEaidil.restful.security.BCrypt;


import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request) {
        validationService.validate(request);
        if (userRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request) {
        validationService.validate(request);
        // kita coba tambahkan lognya, apakah bener datanya masuk atau tidak
        log.info("REQUEST : {}", request);

        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);
        // kita log lagi disini
        log.info("USER : {}", user.getName());

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}
