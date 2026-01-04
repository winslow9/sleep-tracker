package functions;

import ru.yandex.practicum.sleeptracker.BirdsType;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class BirdClassificator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String DESCRIPTION = "В мире спящих птиц вы";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        BirdsType birdType = birdDetection(sessions);
        return new SleepAnalysisResult(DESCRIPTION, birdType);
    }

    public static BirdsType birdDetection(List<SleepingSession> sessions) {
        int owlCount = owlTypeCounter(sessions);
        int larkCount = larkTypeCounter(sessions);
        int totalSessions = sessions.size(); // Используем общее количество сессий
        int pigeonCount = totalSessions - larkCount - owlCount;

        if (owlCount == 0 && larkCount == 0) {
            return BirdsType.ГОЛУБЬ;
        } else if ((owlCount > larkCount) && (owlCount > pigeonCount)) {
            return BirdsType.СОВА;
        } else if ((larkCount > owlCount) && (larkCount > pigeonCount)) {
            return BirdsType.ЖАВОРОНОК;
        } else {
            return BirdsType.ГОЛУБЬ;
        }
    }

    private static Integer owlTypeCounter(List<SleepingSession> sessions) {
        LocalTime nightStart = LocalTime.of(23, 0);
        LocalTime morningEnd = LocalTime.of(9, 0);

        return (int) sessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getSleepStart().toLocalTime();
                    LocalTime finishTime = session.getSleepFinish().toLocalTime();

                    // Проверяем, что сессия начинается после 23:00 и заканчивается после 9:00
                    return startTime.isAfter(nightStart) && finishTime.isAfter(morningEnd);
                })
                .count();
    }

    private static Integer larkTypeCounter(List<SleepingSession> sessions) {
        LocalTime nightStart = LocalTime.of(20, 0);
        LocalTime morningEnd = LocalTime.of(7, 0);

        return (int) sessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getSleepStart().toLocalTime();
                    LocalTime finishTime = session.getSleepFinish().toLocalTime();

                    // Проверяем, что сессия начинается до 20:00 и заканчивается до 7:00
                    return startTime.isBefore(nightStart) && finishTime.isBefore(morningEnd);
                })
                .count();
    }
}