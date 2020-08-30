package samples.logreport;

import java.util.*;

/**
 * HTTP request report class, response to capture HTTP request record and provide:
 * <ul>
 *     <li>Unique IPs</li>
 *     <li>Top 3 most visited URLs</li>
 *     <li>Top 3 most active IP</li>
 * </ul>
 * @author Andy Chau on 29/8/20.
 */
public class HttpRequestReport {

    /**
     * Limits the number of keeping most visited and most active IPs.
     */
    public static final int MOST_COUNT_SIZE = 3;

    private IpRecord ipRecord = new IpRecord();
    private UrlRecord urlRecord = new UrlRecord();

    public void addRecord(final RequestLog record) {
        Objects.requireNonNull(record, "record");

        ipRecord.logIp(record.getIp(), record.getLogTime());
        urlRecord.logUrl(record.getUrl());
    }

    public List<String> getUniqueIps() {
        return ipRecord.getUniqueIps();
    }

    public List<String> getMostVisitedUrls() {
        return new ArrayList<>(urlRecord.getMostVisited(MOST_COUNT_SIZE).keySet());
    }

    public List<String> getActiveIps() {
        return new ArrayList<>(ipRecord.getMostActive(MOST_COUNT_SIZE));
    }
}
