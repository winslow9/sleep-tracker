package functions;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class SessionsCounter implements Function<List<SleepingSession>, Integer> {
    public static Integer countSession(List<SleepingSession> sessions) {
        return sessions.size();
    }

    @Override
    public Integer apply(List<SleepingSession> sleepingSessions) {
        return 0;
    }
}
