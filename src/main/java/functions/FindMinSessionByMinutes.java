package functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class FindMinSessionByMinutes implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "Минимальная продолжительность сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0);  // Если нет сессий, возвращаем 0

        return new SleepAnalysisResult(DESCRIPTION, minDuration + " минут");
    }
}