package com.apiforge.backend.service;

import com.apiforge.backend.dto.UserRequestDTO;
import com.apiforge.backend.dto.UserResponseDTO;
import com.apiforge.backend.model.Users;
import com.apiforge.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToResponseDTO(users);
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        Users users = new Users();
        users.setUsername(requestDTO.getUsername());
        users.setEmail(requestDTO.getEmail());
        users.setPassword(passwordEncoder.encode(requestDTO.getPassword())); // Encodes the password

        Users savedUser = userRepository.save(users);
        return mapToResponseDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        users.setUsername(requestDTO.getUsername());
        users.setEmail(requestDTO.getEmail());
        users.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        Users updatedUser = userRepository.save(users);
        return mapToResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    private UserResponseDTO mapToResponseDTO(Users users) {
        return new UserResponseDTO(users.getId(), users.getUsername(), users.getEmail());
    }
}