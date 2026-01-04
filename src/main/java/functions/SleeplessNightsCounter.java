package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Количество бессонных ночей";

    public static Integer countBadSleepSessions(List<SleepingSession> sessions) {
        return countMidnightsBetween(sessions) - getNightsWithSleepCount(sessions);
    }

    // Вернуть общее количество ночей в логе
    public static Integer countMidnightsBetween(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return 0;
        }

        return sessions.stream()
                .findFirst()
                .map(firstSession -> {
                    LocalDateTime start = firstSession.getSleepStart();
                    LocalDateTime end = sessions.get(sessions.size() - 1).getSleepFinish();

                    // Количество дней между датами (исключая начальный день)
                    return (int) ChronoUnit.DAYS.between(
                            start.toLocalDate(),
                            end.toLocalDate()
                    );
                })
                .orElse(0);
    }

    // Возвращает число ночей, когда был ночной сон
    public static Integer getNightsWithSleepCount(List<SleepingSession> sessions) {
        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(session -> {
                    LocalDateTime start = session.getSleepStart();
                    LocalDateTime finish = session.getSleepFinish();

                    // 1. Сон с переходом через полночь
                    if (!start.toLocalDate().equals(finish.toLocalDate())) {
                        return true;
                    }

                    LocalTime startTime = start.toLocalTime();
                    LocalTime finishTime = finish.toLocalTime();
                    LocalTime midnight = LocalTime.MIDNIGHT;
                    LocalTime sixAm = LocalTime.of(6, 0);

                    return startTime.isBefore(sixAm) && finishTime.isAfter(midnight);
                })
                .map(session -> session.getSleepStart().toLocalDate())
                .collect(Collectors.toSet());

        return nightsWithSleep.size();
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int sleeplessNights = countBadSleepSessions(sessions);
        return new SleepAnalysisResult(DESCRIPTION, sleeplessNights);
    }
}