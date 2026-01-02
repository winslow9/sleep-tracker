package functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

public class FindMinSessionByMinutes implements Function<List<SleepingSession>, Duration> {
    public static OptionalLong findMinSessionByMinutes(List<SleepingSession> sessions) {
        OptionalLong minSession = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min();
        return minSession;
    }


    @Override
    public Duration apply(List<SleepingSession> sleepingSessions) {
        return null;
    }
}
