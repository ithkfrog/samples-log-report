package samples.logreport;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to register unique IP.
 * @author Andy Chau on 29/8/20.
 */
public class IpRecord {

    class IpData {
        String ip;
        int count;
        LocalDateTime logTime;

        IpData(final String ip, final LocalDateTime logTime) {
            this.ip = ip;
            this.logTime = logTime;
        }

        public String getIp() {
            return ip;
        }

        public int getCount() {
            return count;
        }

        public LocalDateTime getLogTime() {
            return logTime;
        }

        int updateCount() {
            return ++count;
        }
    }

    /**
     * Map store key as IP and value as its visiting count
     */
    Map<String, IpData> ips = new HashMap<>();

    /**
     * Return recorded unique IPs.
     * @return
     */
    List<String> getUniqueIps() {
        return new ArrayList<>(ips.keySet());
    }

    public List<String> getMostActive(final int limit) {
        return ips.entrySet().stream()
                .sorted((id1, id2) ->
                        id1.getValue().logTime.compareTo(id2.getValue().logTime))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    IpData getRecord(final String ip) {
        return ips.get(ip);
    }

    /**
     * To log an IP and return how many time been recorded, if IP is null or empty string 0 will be returned.
     * @param ip The IP.
     * @param logTime Time of the log
     * @return Number of visits.
     */
    public int logIp(final String ip, final LocalDateTime logTime) {
        if (Objects.isNull(ip) || Objects.isNull(logTime)) {
            return 0;
        }

        if (ip.length() == 0) {
            return 0;
        }

        int count;
        var record = ips.get(ip);

        if (record == null) {
            record = new IpData(ip, logTime);
            ips.put(ip, record);
        } else {
            record.logTime = logTime;
        }
        count = record.updateCount();

        return count;
    }
}
