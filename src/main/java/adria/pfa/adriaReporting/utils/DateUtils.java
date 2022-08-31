package adria.pfa.adriaReporting.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {
    public Date generateRandomDate() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 8, 30);
        long randomDate = ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay());
        LocalDate localDate = LocalDate.ofEpochDay(randomDate);
        LocalTime localTime = LocalTime.now();
        Date date = Timestamp.valueOf(LocalDateTime.of(localDate, localTime));
        return date;
    }
}
