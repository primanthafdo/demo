package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    public final static String INFO_MESSAGE_HELLO_ALICE = "Hello Alice";
    public final static String ERROR_MESSAGE_INVALID_INPUT = "Invalid Input";
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

//    public UserDTO saveUser(UserDTO userDTO) {
//        User savedUser = userRepo.save(modelMapper.map(userDTO, User.class));
//        return userDTO;
//    }

    public UserDTO saveUser(UserDTO userDTO) {
        User savedUser = userRepo.save(modelMapper.map(userDTO, User.class));
        return modelMapper.map(savedUser, new TypeToken<UserDTO>() {
        }.getType());
    }

    public UserDTO updateUser(UserDTO userDTO) {
        userRepo.save(modelMapper.map(userDTO, User.class));
        return userDTO;
    }

    public String deleteUser(Integer userId) {
//        userRepo.delete(modelMapper.map(userDTO, User.class));
        userRepo.deleteById(userId);
        return "User Deleted";
    }

    public UserDTO getUserById(Integer userId) {
        User user = userRepo.findById(userId).get();
        return modelMapper.map(user, new TypeToken<UserDTO>() {
        }.getType());
    }

    public String getName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_INPUT);
        }

        char firstLetter = name.trim().charAt(0);

        if ((firstLetter >= 'A' && firstLetter <= 'M') || (firstLetter >= 'a' && firstLetter <= 'm')) {
            return INFO_MESSAGE_HELLO_ALICE;
        }

        throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_INPUT);
    }
}
