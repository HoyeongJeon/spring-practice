package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequestDTO;
import com.group.libraryapp.dto.user.request.UserUpdateRequestDTO;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserCreateRequestDTO request) {
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequestDTO request) {
        User user = userRepository.findById(request.getId()).orElseThrow(IllegalAccessError::new);
        user.setName(request.getName());
        // Optional을 사용하면 user가 null 인 경우 빈 Optional 객체를 반환한다.
//        Optional<User> user = userRepository.findById(request.getId());
//
//        if(user.isEmpty()) {
//            throw new IllegalArgumentException("해당 id의 유저가 없습니다.");
//        }
//        user.get().setName(request.getName());
//        userRepository.save(user.get());
    }

    @Transactional
    public void deleteUser(String name) {
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }
}
