package com.vkorba.persistance;


import com.vkorba.dtos.User;

import java.util.List;

public interface UserRepository {
    void save(String userName);

    List<User> getAll();

    void deleteAll();
}
