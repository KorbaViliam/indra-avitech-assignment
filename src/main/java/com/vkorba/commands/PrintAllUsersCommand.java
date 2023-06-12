package com.vkorba.commands;


import com.vkorba.persistance.UserRepository;
import com.vkorba.persistance.UserRepositoryImpl;

public class PrintAllUsersCommand implements Command {
    private final UserRepository userRepository;

    public PrintAllUsersCommand() {
        this.userRepository = UserRepositoryImpl.getInstance();
    }

    public void execute() {
        userRepository.getAll().forEach(System.out::println);
    }
}
