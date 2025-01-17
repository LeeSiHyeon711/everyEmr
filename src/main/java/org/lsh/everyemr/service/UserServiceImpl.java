package org.lsh.everyemr.service;

import lombok.RequiredArgsConstructor;
import org.lsh.everyemr.entity.User;
import org.lsh.everyemr.model.Role;
import org.lsh.everyemr.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

//    @Override
//    public void changeRole(Role newRole, String username) {
//        userRepository.updateUserRole(username, newRole);
//    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean changeUserRole(String username, String newRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found")); // User 타입으로 안전하게 반환

        // 권한 변경 로직
        user.setRole(Role.valueOf(newRole)); // Role이 Enum 타입일 경우
        user.setIsLoading(Boolean.FALSE); // is_loading 을 false로 설정
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean requestRoleChange() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getIsLoading() == true) {
            return false; // 이미 신청한 상태
        }

        user.setIsLoading(true); // 변경 신청 상태로 설정
        userRepository.save(user);
        return true;
    }

}
