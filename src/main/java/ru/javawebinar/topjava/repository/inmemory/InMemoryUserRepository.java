package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> repositiry = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repositiry.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repositiry.put(user.getId(), user);
            return user;
        }
        return repositiry.computeIfPresent(user.getId(), ((integer, userOld) -> user));
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repositiry.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return (List<User>)repositiry.values();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream()
                .filter((user) -> (Objects.equals(user.getEmail(), email)))
                .findFirst()
                .get();
    }
}
