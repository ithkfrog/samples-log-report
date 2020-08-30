package samples.logreport;

import org.junit.jupiter.api.Test;
import samples.logreport.IpRecord;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andy Chau on 29/8/20.
 */
public class IpRecordTest {

    @Test
    void shouldPassAll() {
        final var uips = new IpRecord();

        final var ip1 = "ip1";
        final var time = LocalDateTime.now().minus(1, ChronoUnit.SECONDS);

        // test new instance
        assertThat(uips.logIp(ip1, time), equalTo(1));
        assertThat(uips.getRecord(ip1).getIp(), equalTo(ip1));
        assertThat(uips.getRecord(ip1).getCount(), equalTo(1));
        assertThat(uips.getRecord(ip1).getLogTime(), equalTo(time));
        assertThat(uips.getUniqueIps().size(), equalTo(1));

        // test update
        assertThat(uips.logIp(ip1, LocalDateTime.now()), equalTo(2));
        assertThat(uips.getRecord(ip1).getLogTime(), not(equalTo(time)));

        final var ip2 = "ip2";
        assertThat(uips.logIp(ip2, time), equalTo(1));
        assertThat(uips.getUniqueIps().size(), equalTo(2));

        // test most visited should be ip2
        var mostActive = uips.getMostActive(100);
        assertThat(mostActive.get(0), equalTo(ip2));
        assertThat(mostActive.size(), equalTo(2));
    }

    @Test
    void shouldHandleErrors() {
        final var uips = new IpRecord();
        final var time = LocalDateTime.now();
        final var ip1 = "ip1";

        // test error handling
        assertThat(uips.logIp(null, time), equalTo(0));
        assertThat(uips.logIp("", time), equalTo(0));
        assertThat(uips.logIp(ip1, null), equalTo(0));
    }
}
