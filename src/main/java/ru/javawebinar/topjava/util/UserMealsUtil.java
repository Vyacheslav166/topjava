package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    /**
     * Вывод приемов пищи согласно фильтра и с указанием, превышена ли дневная норма в этот день
     *
     * @param meals - список всех приемов пищи
     * @param startTime - начиная с какого времени отображать акты приема пищи
     * @param endTime - до какого времени отображать акты приема пищи
     * @param caloriesPerDay - дневная норма приема пищи
     *
     * @return список приемов пищи, соответствующий условиям
     */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals
            , LocalTime startTime
            , LocalTime endTime
            , int caloriesPerDay) {
        //Создаем карту дней, в которой отображается суммарное количество калорий за данный день
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();
        for(UserMeal userMeal : meals) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            Integer caloriesMeal = userMeal.getCalories();
            if(sumCaloriesPerDay.containsKey(localDate)) {
                caloriesMeal += sumCaloriesPerDay.get(localDate);
                sumCaloriesPerDay.replace(localDate, caloriesMeal);
            } else {
                sumCaloriesPerDay.put(localDate, caloriesMeal);
            }
        }

        List<UserMealWithExcess> listWithExcess = new ArrayList<>();

        //Фильтруем список приема пищи и добавляем отметку о превышении дневной нормы по калориям
        for(UserMeal userMeal : meals) {
            if(userMeal.getDateTime().toLocalTime().isAfter(startTime) &&
                    userMeal.getDateTime().toLocalTime().isBefore(endTime)) {
                LocalDate localDate = userMeal.getDateTime().toLocalDate();
                boolean excess = sumCaloriesPerDay.get(localDate) > caloriesPerDay;
                listWithExcess.add(new UserMealWithExcess(userMeal.getDateTime()
                        , userMeal.getDescription()
                        , userMeal.getCalories()
                        , excess));
            }
        }
        return listWithExcess;
    }

    /**
     * Вывод приемов пищи согласно фильтра и с указанием, превышена ли дневная норма в этот день
     *
     * @param meals - список всех приемов пищи
     * @param startTime - начиная с какого времени отображать акты приема пищи
     * @param endTime - до какого времени отображать акты приема пищи
     * @param caloriesPerDay - дневная норма приема пищи
     *
     * @return список приемов пищи, соответствующий условиям
     */
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //Создаем карту дней, в которой отображается суммарное количество калорий за данный день
        Map<LocalDate, Integer> sumCaloriesPerDay = meals.stream()
                .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime()
                        , userMeal.getDescription()
                        , userMeal.getCalories()
                        , sumCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .filter(userMealWithExcess -> userMealWithExcess.getDateTime().toLocalTime().isAfter(startTime) &&
                    userMealWithExcess.getDateTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList());



    }
}
