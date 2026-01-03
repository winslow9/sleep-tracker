package ru.yandex.practicum.sleeptracker;

import functions.*;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    public static void main(String[] args) {
        //Читаем файл с сессиями сна
        List<String> logEntries = new ArrayList<>();
        String filePath;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите путь к файлу с логом сна: ");
            filePath = scanner.nextLine();
            scanner.close();
        } else {
            filePath = args[0];
        }

        List<SleepingSession> sessions;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            sessions = reader.lines()
                    .map(SleepingSession::fromString)  // Теперь возвращает Optional
                    .map(Optional::get)                // Берем значение из Optional
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return;
        }

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