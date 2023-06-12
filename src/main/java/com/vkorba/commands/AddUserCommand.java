package com.vkorba.commands;


import com.vkorba.persistance.UserRepository;
import com.vkorba.persistance.UserRepositoryImpl;

public class AddUserCommand implements Command {
    private final UserRepository userRepository;
    private final String userName;

    public AddUserCommand(String userName) {
        this.userRepository = UserRepositoryImpl.getInstance();
        this.userName = userName;
    }

    public void execute() {
        userRepository.save(userName);
        System.out.printf("User added: %s%n", userName);
    }

}