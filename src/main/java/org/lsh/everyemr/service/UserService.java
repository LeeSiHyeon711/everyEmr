package org.lsh.everyemr.service;

import org.lsh.everyemr.entity.User;
import org.lsh.everyemr.model.Role;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findByUsername(String username);
    void changeRole(Role newRole, String username);
    List<User> findAllUsers();
}
