import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import samples.logreport.LogParser;

import java.io.IOException;

/**
 * @author Andy Chau on 30/8/20.
 */
public class ReportTest {

    private static Logger log = LogManager.getLogger(ReportTest.class);

    public static void main(String[] args) throws IOException {

        log.info("Begin loading report....");

        final var path = LogParser.class.getResource("/programming-task-example-data.log").getPath();

        final var report = LogParser.parseFile(path);

        log.info("Number of unique IPs: {}", report.getUniqueIps().size());
        log.info("Top 3 most visited URLs: {}", report.getMostVisitedUrls());
        log.info("Top 3 most active IPs: {}", report.getActiveIps());

        log.info("Report completed.");
    }
}
