package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal);

    // false if meal does not belong to userId
    boolean delete(AtomicInteger id);

    // null if meal does not belong to userId
    Meal get(AtomicInteger id);

    // ORDERED dateTime desc
    Collection<Meal> getAll();
}
