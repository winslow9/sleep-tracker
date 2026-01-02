package functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;

public class FindAvgByMinutes implements Function<List<SleepingSession>, Duration> {
    public static OptionalDouble findAvgDurationSession(List<SleepingSession> sessions) {
        return sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average();

    }


    @Override
    public Duration apply(List<SleepingSession> sleepingSessions) {
        return null;
    }
}
