package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class FindAvgByMinutes implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Средняя продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .map(session -> Duration.between(session.getSleepStart(), session.getSleepFinish())
                        .toMinutes())
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
        return new SleepAnalysisResult(DESCRIPTION, averageDuration);
    }

}
