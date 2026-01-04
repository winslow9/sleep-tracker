package ru.yandex.practicum.sleeptracker;

import functions.BadSleepSessionsCounter;
import functions.FindAvgByMinutes;
import functions.FindMaxSessionByMinutes;
import functions.FindMinSessionByMinutes;
import org.junit.jupiter.api.Test;


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

        FindMinSessionByMinutes finder = new FindMinSessionByMinutes();
        SleepAnalysisResult result = finder.apply(sessions);
        assertEquals("45 минут", result.getResult());
    }

    @Test
    void findMinSessionByMinutes_ShouldHandleEmptyList() {
        List<SleepingSession> emptySessions = Collections.emptyList();

        FindMinSessionByMinutes finder = new FindMinSessionByMinutes();
        SleepAnalysisResult result = finder.apply(emptySessions);
        assertEquals("0 минут", result.getResult());
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

        FindMaxSessionByMinutes finder = new FindMaxSessionByMinutes();
        SleepAnalysisResult result = finder.apply(sessions);
        assertEquals("480 минут", result.getResult());
    }

    @Test
    void findMaxSessionByMinutes_ShouldReturnEmptyOptional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        FindMaxSessionByMinutes finder = new FindMaxSessionByMinutes();
        SleepAnalysisResult result = finder.apply(emptySessions);
        assertEquals("0 минут", result.getResult());
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

        FindAvgByMinutes finder = new FindAvgByMinutes();
        SleepAnalysisResult result = finder.apply(sessions);
        assertEquals(480.0, (Double) result.getResult());
    }

    @Test
    void findAvgByMinutes_ShouldReturnEmptyPtional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        FindAvgByMinutes finder = new FindAvgByMinutes();
        SleepAnalysisResult result = finder.apply(emptySessions);
        assertEquals(0.0, (Double) result.getResult());
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
        BadSleepSessionsCounter counter = new BadSleepSessionsCounter();
        SleepAnalysisResult result = counter.apply(sessions);
        assertEquals(2, result.getResult());
    }

    @Test
    void badSleepSessionsCounter_ShouldReturnEmptyPtional_WhenSessionsListIsEmpty() {
        List<SleepingSession> emptySessions = Collections.emptyList();
        BadSleepSessionsCounter counter = new BadSleepSessionsCounter();
        SleepAnalysisResult result = counter.apply(emptySessions);
        assertEquals(0, result.getResult());
    }
}