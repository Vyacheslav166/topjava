package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll().stream()
                .filter(meal -> meal.getUserId().equals(SecurityUtil.authUserId()))
                .collect(Collectors.toList());
    }

    public Meal get(AtomicInteger id) {
        log.info("get {}", id);
        return service.get(id).getUserId().equals(SecurityUtil.authUserId()) ? service.get(id) : null;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal);
    }

    public void delete(AtomicInteger id) {
        log.info("delete {}", id);
        if (service.get(id).getUserId().equals(SecurityUtil.authUserId())) {
            service.delete(id);
        } else {
            throw new NotFoundException("Еда не найдена");
        }
    }

    public void update(Meal meal, AtomicInteger id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        if (service.get(id).getUserId().equals(SecurityUtil.authUserId())) {
            service.update(meal);
        } else {
            throw new NotFoundException("Еда не найдена");
        }
    }
}