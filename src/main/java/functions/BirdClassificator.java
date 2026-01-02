package functions;

import ru.yandex.practicum.sleeptracker.BirdsType;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class BirdClassificator implements Function<List<SleepingSession>, String> {
    public static BirdsType birdDetection(List<SleepingSession> sessions) {
        int owlCount = owlTypeCounter(sessions);
        int larkCount = larkTypeCounter(sessions);
        int totalSessions = SleeplessNightsCounter.countMidnightsBetween(sessions);
        int pigeonCount = totalSessions - larkCount - owlCount;

        /*System.out.println("Сова: "+owlCount);
        System.out.println("Жаваронок: "+larkCount);
        System.out.println("Голубь "+pigeonCount);*/

        if (owlCount == 0 && larkCount == 0) {
            return BirdsType.ГОЛУБЬ; // Ни сова, ни жаворонок
        } else if ((owlCount > larkCount) && (owlCount > pigeonCount)) {
            return BirdsType.Сова;
        } else if ((larkCount > owlCount) && (larkCount > pigeonCount)) {
            return BirdsType.Жаворонок;
        } else {
            return BirdsType.ГОЛУБЬ; // Равное количество
        }
    }

    private static Integer owlTypeCounter(List<SleepingSession> sessions) {
        LocalTime nightStart = LocalTime.of(23, 0);  // 23:00
        LocalTime morningEnd = LocalTime.of(9, 0);   // 09:00

        return (int) sessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getSleepStart().toLocalTime();
                    LocalTime finishTime = session.getSleepFinish().toLocalTime();

                    // Совы: ложатся спать после 23:00 и встают после 9:00
                    return startTime.isAfter(nightStart) && finishTime.isAfter(morningEnd);
                })
                .count();
    }

    private static Integer larkTypeCounter(List<SleepingSession> sessions) {
        LocalTime nightStart = LocalTime.of(20, 0);  // 23:00
        LocalTime morningEnd = LocalTime.of(7, 0);   // 09:00

        return (int) sessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getSleepStart().toLocalTime();
                    LocalTime finishTime = session.getSleepFinish().toLocalTime();

                    // Совы: ложатся спать после 23:00 и встают после 9:00
                    return startTime.isBefore(nightStart) && finishTime.isBefore(morningEnd);
                })
                .count();
    }

    @Override
    public String apply(List<SleepingSession> sleepingSessions) {
        return "";
    }
}
