package samples.logreport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Andy Chau on 29/8/20.
 */
public class LogParser {

    /**
     * Regex to parse the log line, group 1 as IP, group 2 as the timestamp, group 3 as the URL path.
     */
    public static final String LOG_REG = "^(\\d*.\\d*.\\d*.\\d*).*\\[(.*)] \"GET (.*/) .*";

    // TODO is there away to consider +0200 timezone into parse string?
    public static final String LOG_TIME_PATTERN = "d/MMM/yyyy:HH:mm:ss";

    private static Pattern pattern = Pattern.compile(LOG_REG);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOG_TIME_PATTERN, Locale.ENGLISH);

    /**
     * Parse log file return the request report, if not abel to open the file {@code IOException} will be thrown.
     * @param logFilePath The absolute path the the log file.
     * @return The report.
     * @throws IOException Thrown when file can't be found.
     */
    public static HttpRequestReport parseFile(final String logFilePath) throws IOException {

        var report = new HttpRequestReport();

        LineIterator li = null;

        try {
            li = FileUtils.lineIterator(new File(logFilePath));

            while (li.hasNext()) {
                final var l = li.nextLine();
                final var r = LogParser.parseLine(l);

                if (r != null) {
                    report.addRecord(r);
                }
            }

            // read line by line an scape modify lines
        } finally {
            if (li != null) {
                li.close();
            }
        }

        return report;
    }

    /**
     * To parse the line by using {@link #LOG_TIME_PATTERN}, if the line can't be parsed a null will be return.
     * @param line The line string.
     * @return
     */
    public static RequestLog parseLine(final String line) {
        if (Objects.isNull(line)) {
            return null;
        }

        var matcher = pattern.matcher(line);

        if (!matcher.find()) {
            // not matching found
            return null;
        }

        var ip = matcher.group(1);
        var hitTime = convertTimeStr(matcher.group(2));
        var url = matcher.group(3);

        return new RequestLog(ip, hitTime, url);
    }

    /**
     * Cutting out {@code +0200} timezone.
     */
    public static LocalDateTime convertTimeStr(final String timeStr) {
        // TODO need to investigate formatter pattern that support +0200 timezone
        var cutTimezone = timeStr.substring(0, timeStr.indexOf("+")).trim();
        return LocalDateTime.parse(cutTimezone, formatter);
    }
}
