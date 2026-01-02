package ru.yandex.practicum.sleeptracker;

import functions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        //Читаем файл с сессиями сна
        List<String> logEntries = new ArrayList<>();
        String filePath = "sleep_log.txt";

        try (InputStream inputStream = SleepTrackerApp.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Файл логами сна не найден: " + filePath);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Конверт сессий из строк файла в лист объектов Sleepeng Session
        List<SleepingSession> sessions = logEntries.stream()
                .map(logEntrie -> SleepingSession.fromString(logEntrie))
                .collect(Collectors.toList());

        //Подсчет сессий через функцию SessionCounter
        Integer sessionsCounter = SessionsCounter.countSession(sessions);

        //Поиск наименьшей сессии в минутах
        OptionalLong minSession = FindMinSessionByMinutes.findMinSessionByMinutes(sessions);
        System.out.println("минимальная продолжительность сессии: " + minSession.getAsLong());

        //Поиск наибольшей сессии в минутах
        //Прикинул другую вариацию вызова, отличную от функции findMinSessionByMinutes
        Optional<Duration> maxSession = FindMaxSessionByMinutes.findMaxSessionByMinutes(sessions);
        long maxMinutes = maxSession.get().toMinutes();
        System.out.println("максимальная продолжительность сессии: " + maxMinutes);

        //Попробовал вывод сразу в println
        System.out.println("средняя продолжительность сессии: " + Math.round(FindAvgByMinutes.findAvgDurationSession(sessions).getAsDouble()));

        //Количество плохих сессий
        System.out.println("количество сессий с плохим качество сна: " + BadSleepSessionsCounter.badSleepSessionsCounter(sessions));

        //Колличество бессонных ночей
        System.out.println("колличество бессонных ночей: " + SleeplessNightsCounter.countBadSleepSessions(sessions));

        //Птичий вопрос
        System.out.println("В мире спящих птиц вы " + BirdClassificator.birdDetection(sessions));
    }
}