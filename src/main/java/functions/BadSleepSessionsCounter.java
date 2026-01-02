package functions;

import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Stream;

public class BadSleepSessionsCounter implements Function<List<SleepingSession>, Duration> {
    public static Integer badSleepSessionsCounter(List<SleepingSession> sessions) {
         Stream<SleepingSession> counter = sessions.stream()
                .filter(session -> session.getSleepQuality().equals(SleepQuality.BAD));
         return counter.toList().size();

    }


    @Override
    public Duration apply(List<SleepingSession> sleepingSessions) {
        return null;
    }
}
