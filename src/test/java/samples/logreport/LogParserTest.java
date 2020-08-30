package samples.logreport;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Andy Chau on 29/8/20.
 */
public class LogParserTest {


    private String logLine = "177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] \"GET /intranet-analytics/ HTTP/1.1\" 200 3574\n";

    @Test
    void shouldParseFile() throws Exception {
        // get the test file from resources with full system path
        final var path = LogParser.class.getResource("/test-data.log").getPath();

        final var report = LogParser.parseFile(path);

        assertThat(report.getActiveIps().size(), equalTo(2));
        assertThat(report.getMostVisitedUrls().size(), equalTo(1));
        assertThat(report.getUniqueIps().size(), equalTo(2));

    }

    @Test
    void shouldParseLine() {
        var expectedIp = "177.71.128.21";
        var expectedUrl = "/intranet-analytics/";

        var rl = LogParser.parseLine(logLine);

        assertThat(rl.getIp(), equalTo(expectedIp));
        assertThat(rl.getLogTime().toString(), equalTo("2018-07-10T22:21:28"));
        assertThat(rl.getUrl(), equalTo(expectedUrl));
    }

    @Test
    void shouldHandleErrors() {
        assertThat(LogParser.parseLine("xyz-line"), nullValue());
    }
}
