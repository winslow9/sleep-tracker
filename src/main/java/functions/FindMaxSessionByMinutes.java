package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FindMaxSessionByMinutes implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Максимальная продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Duration maxDuration = sessions.stream()
                .map(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()))
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);

        long minutes = maxDuration.toMinutes();
        return new SleepAnalysisResult(DESCRIPTION, minutes + " минут");
    }
}