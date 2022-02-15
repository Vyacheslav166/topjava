package ru.javawebinar.topjava.repository;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface UserRepository {
    // null if not found, when updated
    User save(User user);

    // false if not found
    boolean delete(AtomicInteger id);

    // null if not found
    User get(AtomicInteger id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}