package org.semennikov.service;

import org.semennikov.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Runner implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public Runner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== Создание пользователей ===");
        User user1 = userService.createUser("Михаил");
        User user2 = userService.createUser("Иван");
        User user3 = userService.createUser("Андрей");

        System.out.println("Создан: " + user1);
        System.out.println("Создан: " + user2);
        System.out.println("Создан: " + user3);

        System.out.println("\n=== Получение всех пользователей ===");
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("\n=== Получение пользователя по ID ===");
        userService.getUserById(user2.getId())
                .ifPresentOrElse(
                        user -> System.out.println("Найден пользователь: " + user),
                        () -> System.out.println("Пользователь не найден")
                );

        System.out.println("\n=== Обновление пользователя ===");
        Optional<User> updatedUser = userService.updateUser(user1.getId(), "МИХАИЛ");
        updatedUser.ifPresentOrElse(
                user -> System.out.println("Обновленный пользователь: " + user),
                () -> System.out.println("Не найден пользователь для обновления")
        );

        System.out.println("\n=== Получение всех пользователей ===");
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("\n=== Удаление пользователя ===");
        userService.deleteUser(user3.getId());

        System.out.println("\n=== Попытка получить удаленного пользователя ===");
        userService.getUserById(user3.getId())
                .ifPresentOrElse(
                        user -> System.out.println("Проблема!!!: Найден удаленный пользователь: " + user),
                        () -> System.out.println("Пользователь успешно удален")
                );
    }
}
