package com.kuklin.webrisetest.services;

import com.kuklin.webrisetest.components.exceptions.ErrorResponseException;
import com.kuklin.webrisetest.components.exceptions.ErrorStatus;
import com.kuklin.webrisetest.entities.User;
import com.kuklin.webrisetest.models.UserDto;
import com.kuklin.webrisetest.models.mappers.UserMapper;
import com.kuklin.webrisetest.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    //- Создание пользователя.
    //- Получение информации о пользователе.
    //- Обновление данных пользователя.
    //- Удаление пользователя.

    public UserDto createUserAndGetDto(UserDto userDto) {
        return userMapper.toDto(createUser(userDto));
    }
    public User createUser(UserDto userDto) {
        if (!isValidEmail(userDto.getEmail())) {
            throw new ErrorResponseException(ErrorStatus.NOT_VALID_EMAIL);
        }
        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            throw new ErrorResponseException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }
        return userRepository.save(userMapper.toEntity(userDto));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+.-]+@A-Za-z0-9.-+$";
        return email != null && email.matches(emailRegex);
    }

    public UserDto getUserDto(Long id) {
        return userMapper.toDto(getUserById(id));
    }
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ErrorResponseException(ErrorStatus.USER_NOT_FOUND));
    }

    public UserDto updateUserByIdAndGetDto(Long id, UserDto userDto) {
        return userMapper.toDto(updateUserById(id, userDto));
    }
    public User updateUserById(Long id, UserDto userDto) {
        User user = getUserById(id);
        if (!user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsUserByEmail(userDto.getEmail())) {
                throw new ErrorResponseException(ErrorStatus.EMAIL_ALREADY_EXISTS);
            }
        }

        return userRepository.save(userMapper.toEntity(userDto).setId(id));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ErrorResponseException(ErrorStatus.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
