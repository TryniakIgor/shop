package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    private User expectedUser;
    private UserDTO expectedUserDTO;

    @BeforeEach
    void setUp() {
        expectedUserDTO =
                UserDTO.builder()
                        .id(1L)
                        .name("Luk")
                        .userName("luk111")
                        .age(22)
                        .build();

        expectedUser = User.builder()
                .id(1L)
                .name("Luk")
                .userName("luk111")
                .password("1111")
                .age(22)
                .roles(new ArrayList<>())
                .build();
    }

    @Test
    void getUser() {
        List<User> listExpected = new ArrayList<User>();
        listExpected.add(expectedUser);

        when(userRepo.findByUserName("luk111")).thenReturn(expectedUser);

        UserDTO actualUser = (userService.getUser("luk111"));

        assertEquals(actualUser, UserMapper.toDTO(expectedUser));
    }

    @Test
    void getUsers() {
        List<User> listExpected = new ArrayList<User>();
        listExpected.add(expectedUser);
        List<UserDTO> listToDtolistExpected = listExpected.stream().map(UserMapper::toDTO).collect(Collectors.toList());

        when(userRepo.findAll()).thenReturn(listExpected);

        List<UserDTO> listActual = userService.getUsers();

        assertEquals(listActual, listToDtolistExpected);
    }

    @Test
    void findUserByLocation() {
        List<User> users = Arrays.asList(expectedUser);

        when(userRepo.findByLocation(any())).thenReturn(users);
        List<UserDTO> userDTOList = userService.findUserByLocation("lviv");

        assertEquals(userDTOList.get(0).getName(), expectedUserDTO.getName());
        assertEquals(userDTOList.get(0).getAge(), expectedUserDTO.getAge());
    }
}