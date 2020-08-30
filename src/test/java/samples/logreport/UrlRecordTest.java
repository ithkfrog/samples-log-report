package samples.logreport;

import org.junit.jupiter.api.Test;
import samples.logreport.UrlRecord;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Andy Chau on 29/8/20.
 */
public class UrlRecordTest {

    @Test
    void shouldPassAll() {
        var r = new UrlRecord();
        var u1 = "url1";
        var u2 = "url2";
        var u3 = "url3";

        // u1 hit 1 time
        assertThat(r.logUrl(u1), equalTo(1));

        // u2 hit 2 time
        assertThat(r.logUrl(u2), equalTo(1));
        assertThat(r.logUrl(u2), equalTo(2));

        // u3 hit 3 time
        assertThat(r.logUrl(u3), equalTo(1));
        assertThat(r.logUrl(u3), equalTo(2));
        assertThat(r.logUrl(u3), equalTo(3));

        // get most visited with 2 limit should in orders : u3 then u2
        var most = r.getMostVisited(100);
        var orderedKey = most.keySet().stream().collect(Collectors.toList());
        var orderedValue = most.values().stream().collect(Collectors.toList());

        assertThat(orderedKey.get(0), equalTo(u3));
        assertThat(orderedValue.get(0), equalTo(3));
        assertThat(orderedKey.get(1), equalTo(u2));
        assertThat(orderedValue.get(1), equalTo(2));
    }

    @Test
    void shouldHandleErrors() {
        var r = new UrlRecord();

        assertThat(r.logUrl(null), equalTo(0));
        assertThat(r.logUrl(""), equalTo(0));
    }
}
