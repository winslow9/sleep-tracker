package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class SleepingSession {
    private LocalDateTime sleepStart;
    private LocalDateTime sleepFinish;
    private SleepQuality sleepQuality;

    // Формат для дат в логе
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime sleepFinish, SleepQuality sleepQuality) {
        setSleepStart(sleepStart);
        setSleepFinish(sleepFinish);
        setSleepQuality(sleepQuality);
    }

    // Метод для создания объекта из строки (возвращает Optional)
    public static Optional<SleepingSession> fromString(String logEntry) {
        try {
            String[] parts = logEntry.split(";");

            if (parts.length != 3) {
                System.err.println("Неверный формат строки. Ожидается: дата_начала;дата_окончания;качество");
                return Optional.empty();
            }

            // Парсим даты
            LocalDateTime start = parseDateTime(parts[0].trim());
            LocalDateTime finish = parseDateTime(parts[1].trim());

            // Парсим качество сна
            SleepQuality quality;
            try {
                quality = SleepQuality.valueOf(parts[2].trim());
            } catch (IllegalArgumentException e) {
                System.err.println("Неверное значение качества сна: " + parts[2] +
                        ". Допустимые значения: " + String.join(", ", getSleepQualityValues()));
                return Optional.empty();
            }

            return Optional.of(new SleepingSession(start, finish, quality));

        } catch (Exception e) {
            System.err.println("Ошибка при парсинге строки: " + logEntry + " - " + e.getMessage());
            return Optional.empty();
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Неверный формат даты: '" + dateTimeStr +
                    "'. Ожидается формат: дд.мм.гг чч:мм (например: 10.10.25 13:00)");
        }
    }

    private static String[] getSleepQualityValues() {
        SleepQuality[] values = SleepQuality.values();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].name();
        }
        return result;
    }

    // Геттеры и сеттеры
    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public void setSleepStart(LocalDateTime sleepStart) {
        if (sleepStart == null) {
            throw new IllegalArgumentException("Время начала сна не может быть null");
        }
        this.sleepStart = sleepStart;
    }

    public LocalDateTime getSleepFinish() {
        return sleepFinish;
    }

    public void setSleepFinish(LocalDateTime sleepFinish) {
        if (sleepFinish == null) {
            throw new IllegalArgumentException("Время окончания сна не может быть null");
        }
        this.sleepFinish = sleepFinish;
    }

    public void setSleepQuality(SleepQuality sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    // Вернуть длительность сна в минутах
    public long getDurationInMinutes() {
        return java.time.Duration.between(sleepStart, sleepFinish).toMinutes();
    }

    // Вернуть длительность сна в формате HH:mm
    public String getFormattedDuration() {
        long minutes = getDurationInMinutes();
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return String.format("%d ч %d мин", hours, remainingMinutes);
    }

    @Override
    public String toString() {
        return String.format("S!!eepingSession{start=%s, finish=%s, duration=%s, quality=%s}",
                sleepStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                sleepFinish.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                getFormattedDuration(),
                sleepQuality);
    }
}