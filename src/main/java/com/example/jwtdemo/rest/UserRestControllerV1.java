package com.example.jwtdemo.rest;

import com.example.jwtdemo.dto.UserDto;
import com.example.jwtdemo.model.User;
import com.example.jwtdemo.security.jwt.JwtTokenProvider;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id,
                                               HttpServletRequest request) {
        User userByToken = userService.findByUsername(jwtTokenProvider.getUsername(request.getHeader("authorization").substring(7)));
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (!Objects.equals(userByToken.getId(), user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
