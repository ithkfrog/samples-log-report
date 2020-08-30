package samples.logreport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Andy Chau on 29/8/20.
 */
public class UrlRecord {
    /**
     * Map key as URL value as hit count.
     */
    private Map<String, Integer> urlCounts = new HashMap<>();

    /**
     * Get the most visited with a limit.
     * @param limit Limits to number of records.
     * @return A map key as URL and value in number of time visited.
     */
    public Map<String, Integer> getMostVisited(final int limit) {
        return urlCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    /**
     * To log an URL and return hit count, if URL is null or empty string 0 will be returned.
     * @param url The url
     * @return Hit count of the url.
     */
    public int logUrl(final String url) {
        if (Objects.isNull(url)) {
            return 0;
        }

        if (url.length() == 0) {
            return 0;
        }

        var count = 0;

        if (urlCounts.containsKey(url)) {
            count = urlCounts.get(url) + 1;
        } else {
            count = 1;
        }
        urlCounts.put(url, count);

        return count;
    }
}
