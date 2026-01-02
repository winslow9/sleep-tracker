package functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FindMaxSessionByMinutes implements Function<List<SleepingSession>, Duration> {
    public static Optional<Duration> findMaxSessionByMinutes(List<SleepingSession> sessions) {
        Optional<Duration> maxSession = sessions.stream()
                .map(session -> Duration.between(session.getSleepStart(), session.getSleepFinish()))
                .max(Duration::compareTo);
        return maxSession;
    }


    @Override
    public Duration apply(List<SleepingSession> sleepingSessions) {
        return null;
    }
}
