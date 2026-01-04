package ru.yandex.practicum.sleeptracker;

import functions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SleeplessNinghtsTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Test
    void countBadSleepSessions_ShouldReturnZero_WhenEmptyList() {
        List<SleepingSession> emptyList = Collections.emptyList();
        Integer result = SleeplessNightsCounter.countBadSleepSessions(emptyList);
        assertEquals(0, result);
    }

    @Test
    void countBadSleepSessions_ShouldReturn1_WhenMixedSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", SleepQuality.GOOD),
                createSession("02.10.25 23:50", "03.10.25 06:40", SleepQuality.NORMAL),
                createSession("03.10.25 14:10", "03.10.25 15:00", SleepQuality.NORMAL),
                createSession("05.10.25 00:10", "05.10.25 06:20", SleepQuality.GOOD)
        );
        Integer result = SleeplessNightsCounter.countBadSleepSessions(sessions);
        assertEquals(1, result);
    }

    @Test
    void countBadSleepSessions_ShouldReturn3_WhenMixedSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 23:15", "02.10.25 07:30", SleepQuality.GOOD),
                createSession("02.10.25 23:50", "03.10.25 06:40", SleepQuality.NORMAL),
                createSession("03.10.25 14:10", "03.10.25 15:00", SleepQuality.NORMAL),
                createSession("05.10.25 00:10", "05.10.25 06:20", SleepQuality.GOOD),
                createSession("07.10.25 22:10", "08.10.25 06:20", SleepQuality.BAD)
        );
        Integer result = SleeplessNightsCounter.countBadSleepSessions(sessions);
        assertEquals(3, result);
    }

    @Test
    void countBadSleepSessions_ShouldReturnAllNightsSleepless_WhenOnlyDaySessions() {
        List<SleepingSession> sessions = Arrays.asList(
                createSession("01.10.25 10:00", "01.10.25 11:00", SleepQuality.GOOD),
                createSession("03.10.25 16:00", "03.10.25 17:00", SleepQuality.BAD)
        );

        Integer result = SleeplessNightsCounter.countBadSleepSessions(sessions);
        assertEquals(2, result);
    }


    // Вспомогательный метод для создания сессий
    private SleepingSession createSession(String start, String end, SleepQuality quality) {
        LocalDateTime startTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMATTER);
        return new SleepingSession(startTime, endTime, quality);
    }
}