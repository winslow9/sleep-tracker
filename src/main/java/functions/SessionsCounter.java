package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class SessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Общее количество сессий сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        int count = sessions.size();
        return new SleepAnalysisResult(DESCRIPTION, count);
    }
}