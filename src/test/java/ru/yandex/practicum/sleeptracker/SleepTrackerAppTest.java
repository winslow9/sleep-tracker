package ru.yandex.practicum.sleeptracker;

import functions.BadSleepSessionsCounter;
import functions.FindAvgByMinutes;
import functions.FindMaxSessionByMinutes;
import functions.FindMinSessionByMinutes;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepingSession;
import ru.yandex.practicum.sleeptracker.SleepQuality;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SleepTrackerAppTest {

    //Тесты на минимальное значение сессии
    @Test
    void findMinSessionByMinutes_ShouldReturnCorrectMinimum_WhenSessionsExist() {
        List<SleepingSession> sessions = Arrays.asList(
                // 480 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 1, 22, 0),
                        LocalDateTime.of(2024, 1, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                //  90 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 14, 0),
                        LocalDateTime.of(2024, 1, 2, 15, 30),
                        SleepQuality.NORMAL
                ),
                // 360 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 23, 0),
                        LocalDateTime.of(2024, 1, 3, 5, 0),
                        SleepQuality.GOOD
                ),
                // 45 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 3, 12, 0),
                        LocalDateTime.of(2024, 1, 3, 12, 45),
                        SleepQuality.BAD
                )
        );

        OptionalLong result = FindMinSessionByMinutes.findMinSessionByMinutes(sessions);
        assertEquals(45, result.getAsLong(), "Минимальная длительность должна быть 45 минут");
    }

    @Test
    void findMinSessionByMinutes_ShouldReturnEmptyOptional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        OptionalLong result = FindMinSessionByMinutes.findMinSessionByMinutes(emptySessions);
        assertFalse(result.isPresent(), "Результат должен быть пустым для пустого списка");
    }

    //Тесты на максимальное значение сессии
    @Test
    void findMaxSessionByMinutes_ShouldReturnCorrectMaximum_WhenSessionsExist() {
        List<SleepingSession> sessions = Arrays.asList(
                // 480 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 1, 22, 0),
                        LocalDateTime.of(2024, 1, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                // 90 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 14, 0),
                        LocalDateTime.of(2024, 1, 2, 15, 30),
                        SleepQuality.NORMAL
                ),
                // 360 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 23, 0),
                        LocalDateTime.of(2024, 1, 3, 5, 0),
                        SleepQuality.GOOD
                ),
                // 45 минут
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 3, 12, 0),
                        LocalDateTime.of(2024, 1, 3, 12, 45),
                        SleepQuality.BAD
                )
        );

        Optional<Duration> result = FindMaxSessionByMinutes.findMaxSessionByMinutes(sessions);
        assertEquals(Duration.ofMinutes(480), result.get(),
                "Максимальная длительность должна быть 480 минут (8 часов)");
    }

    @Test
    void findMaxSessionByMinutes_ShouldReturnEmptyOptional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        Optional<Duration> result = FindMaxSessionByMinutes.findMaxSessionByMinutes(emptySessions);
        assertFalse(result.isPresent(), "Результат должен быть пустым для пустого списка");
    }

    //Тесты на среднее значение сессии
    @Test
    void findAvgByMinutes_ShouldReturnCorrectAvg_WhenSessionsExist() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 1, 22, 0),
                        LocalDateTime.of(2024, 1, 2, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 22, 0),
                        LocalDateTime.of(2024, 1, 3, 6, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 3, 22, 0),
                        LocalDateTime.of(2024, 1, 4, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 4, 22, 0),
                        LocalDateTime.of(2024, 1, 5, 6, 0),
                        SleepQuality.BAD
                )
        );

        OptionalDouble result = FindAvgByMinutes.findAvgDurationSession(sessions);
        assertEquals(480, result.getAsDouble(), "Среднее время должно быть 480 минут");
    }
    @Test
    void findAvgByMinutes_ShouldReturnEmptyPtional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        OptionalDouble result = FindAvgByMinutes.findAvgDurationSession(emptySessions);
        assertFalse(result.isPresent(), "Результат должен быть пустым для пустого списка");
    }

    //Тесты на плохие сессии
    @Test
    void badSleepSessionsCounter_ShouldReturnCorrectBadCount_WhenSessionsExist() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 1, 22, 0),
                        LocalDateTime.of(2024, 1, 2, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 2, 22, 0),
                        LocalDateTime.of(2024, 1, 3, 6, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 3, 22, 0),
                        LocalDateTime.of(2024, 1, 4, 6, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2024, 1, 4, 22, 0),
                        LocalDateTime.of(2024, 1, 5, 6, 0),
                        SleepQuality.BAD
                )
        );
        Integer result = BadSleepSessionsCounter.badSleepSessionsCounter(sessions);
        assertEquals(2, result, "Ответ должен быть 2");
    }
    @Test
    void badSleepSessionsCounter_ShouldReturnEmptyPtional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        Integer result = BadSleepSessionsCounter.badSleepSessionsCounter(emptySessions);
        assertFalse(result!=0, "Результат должен быть пустым для пустого списка");
    }
}