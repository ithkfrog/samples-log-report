package samples.logreport;

import java.time.LocalDateTime;

/**
 * Immutable POJO for a request log.
 * @author Andy Chau on 29/8/20.
 */
public class RequestLog {

    private String ip;
    private LocalDateTime visitTime;
    private String url;

    public RequestLog(final String ip,
                      final LocalDateTime visitTime,
                      final String url) {
        this.ip = ip;
        this.visitTime = visitTime;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getLogTime() {
        return visitTime;
    }

    public String getUrl() {
        return url;
    }
}
