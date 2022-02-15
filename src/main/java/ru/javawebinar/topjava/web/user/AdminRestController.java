package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class AdminRestController extends AbstractUserController {

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public User get(AtomicInteger id) {
        return super.get(id);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public void delete(AtomicInteger id) {
        super.delete(id);
    }

    @Override
    public void update(User user, AtomicInteger id) {
        super.update(user, id);
    }

    @Override
    public User getByMail(String email) {
        return super.getByMail(email);
    }
}