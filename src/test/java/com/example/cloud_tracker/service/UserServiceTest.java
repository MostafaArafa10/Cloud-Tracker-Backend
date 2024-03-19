package com.example.cloud_tracker.service;


import com.example.cloud_tracker.dto.UserDTO;
import com.example.cloud_tracker.model.User;
import com.example.cloud_tracker.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void registerUserSuccessTest(){
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.com")
                .password("password")
                .name("test")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(new User(userDTO));

        Optional<User> user = Optional.of(userRepository.save(new User(userDTO)));
        Assertions.assertEquals(user.get().getEmail(), userDTO.getEmail());
    }

    @Test
    public void loginSuccessTest(){
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = new User(userDTO);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        Assertions.assertEquals(user.getEmail(), userDTO.getEmail());
        Assertions.assertNotNull(jwtService.generateToken(user));
    }

    @Test
    public void getUserByEmailSuccessTest(){
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = new User(userDTO);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);

        User found = userService.findUserByEmail(userDTO.getEmail());
        Assertions.assertEquals(found.getEmail(), userDTO.getEmail());
    }

    @Test
    public void saveProfileImageSuccessTest(){
        UserDTO userDTO = UserDTO.builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = new User(userDTO);
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.saveProfileImage(userDTO.getEmail(), "image");
        Assertions.assertEquals(user.getImage(), "image");
    }
}
