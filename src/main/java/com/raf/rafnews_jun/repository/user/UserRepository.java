package com.raf.rafnews_jun.repository.user;

import com.raf.rafnews_jun.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> allUsers();
    User addUser(User user);
    User findUser(String email);
    void deleteUser(String email);
    void changeUserActivity(String email);
    User updateUser(User user, String email);
    User findUserById(Integer id);
    User updateUserById(User user, Integer id);
}
