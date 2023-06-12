package com.vkorba.commands;


import com.vkorba.persistance.UserRepository;
import com.vkorba.persistance.UserRepositoryImpl;

public class DeleteAllUsersCommand implements Command {
    private final UserRepository userRepository;

    public DeleteAllUsersCommand() {
        this.userRepository = UserRepositoryImpl.getInstance();
    }

    public void execute() {
        userRepository.deleteAll();
        System.out.println("All Users deleted.");
    }
}
