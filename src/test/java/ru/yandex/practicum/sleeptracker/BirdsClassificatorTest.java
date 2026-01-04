package ru.yandex.practicum.sleeptracker;

import functions.BirdClassificator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BirdsClassificatorTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Test
    void birdDetection_ShouldReturnOwl_WhenMostSessionsAreOwlType() {
        //Больше совиных сессий (3 совы, 1 жаворонок, 1 голубь)
        List<SleepingSession> sessions = Arrays.asList(
                // Совы
                createSession("01.10.25 23:30", "02.10.25 10:00", SleepQuality.GOOD),
                createSession("02.10.25 23:45", "03.10.25 09:30", SleepQuality.NORMAL),
                createSession("03.10.25 00:30", "03.10.25 09:15", SleepQuality.BAD), // После 9:00
                // ЖАВОРОНОК
                createSession("04.10.25 19:30", "05.10.25 06:45", SleepQuality.GOOD),
                // Голубь
                createSession("05.10.25 21:30", "05.10.25 23:00", SleepQuality.NORMAL)
        );
        BirdsType result = BirdClassificator.birdDetection(sessions);
        assertEquals(BirdsType.СОВА, result, "Должна быть определена как СОВА");
    }

    @Test
    void birdDetection_ShouldReturnLark_WhenMostSessionsAreLarkType() {
        //Больше жаворонковых сессий (2 жаворонка, 1 сова, 2 голубя)
        List<SleepingSession> sessions = Arrays.asList(
                // Жаворонки
                createSession("01.10.25 19:00", "02.10.25 06:30", SleepQuality.GOOD),
                createSession("02.10.25 18:45", "03.10.25 06:00", SleepQuality.NORMAL),
                // СОВА
                createSession("03.10.25 23:15", "04.10.25 09:15", SleepQuality.BAD),
                // Голуби
                createSession("04.10.25 20:30", "04.10.25 22:00", SleepQuality.GOOD),
                createSession("05.10.25 21:00", "05.10.25 07:30", SleepQuality.NORMAL) // Встал после 7:00
        );
        BirdsType result = BirdClassificator.birdDetection(sessions);
        assertEquals(BirdsType.ЖАВОРОНОК, result, "Должен быть определен как ЖАВОРОНОК");
    }

    @Test
    void birdDetection_ShouldReturnPigeon_WhenNoClearMajority() {
        // 1 сова, 1 жаворонок, 3 голубя
        List<SleepingSession> sessions = Arrays.asList(
                // СОВА
                createSession("01.10.25 23:30", "02.10.25 09:30", SleepQuality.GOOD),
                // ЖАВОРОНОК
                createSession("02.10.25 19:00", "03.10.25 06:30", SleepQuality.NORMAL),
                // Голуби
                createSession("03.10.25 20:30", "03.10.25 22:00", SleepQuality.BAD),
                createSession("04.10.25 21:00", "04.10.25 07:30", SleepQuality.GOOD),
                createSession("05.10.25 22:00", "05.10.25 08:00", SleepQuality.NORMAL)
        );

        BirdsType result = BirdClassificator.birdDetection(sessions);
        assertEquals(BirdsType.ГОЛУБЬ, result, "Должен быть определен как Голубь при отсутствии явного большинства");
    }


    // Вспомогательный метод для создания сессий
    private SleepingSession createSession(String start, String end, SleepQuality quality) {
        LocalDateTime startTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMATTER);
        return new SleepingSession(startTime, endTime, quality);
    }
}
