package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class BadSleepSessionsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Сессий с плохим сном: ";


    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Stream<SleepingSession> badSleepSessionsCounter = sessions.stream()
                .filter(session -> session.getSleepQuality().equals(SleepQuality.BAD));
        return new SleepAnalysisResult(DESCRIPTION, badSleepSessionsCounter.toList().size());
    }
}