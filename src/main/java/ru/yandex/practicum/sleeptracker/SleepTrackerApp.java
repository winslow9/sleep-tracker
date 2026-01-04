package ru.yandex.practicum.sleeptracker;

import functions.*;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> ANALYTIC_FUNCTIONS = List.of(
            new FindAvgByMinutes(),
            new BadSleepSessionsCounter(),
            new BirdClassificator(),
            new FindMaxSessionByMinutes(),
            new FindMinSessionByMinutes(),
            new SleeplessNightsCounter()
    );

    public static List<SleepAnalysisResult> analyzeFile(String filePath) throws IOException {
        List<SleepingSession> sessions;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            sessions = reader.lines()
                    .map(SleepingSession::fromString)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        return ANALYTIC_FUNCTIONS.stream()
                .map(func -> func.apply(sessions))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
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
                    .map(SleepingSession::fromString)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        try {
            List<SleepAnalysisResult> results = analyzeFile(filePath);
            results.forEach(result ->
                    System.out.println(result.getFunctionDescription() + ": " + result.getResult())
            );
        } catch (IOException e) {
            System.out.println("Ошибка при анализе файла: " + e.getMessage());
        }
    }
}