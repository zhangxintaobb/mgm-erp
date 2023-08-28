package com.mgm.common.helper;

import org.apache.commons.lang3.tuple.Triple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedisConfigHelper {
    public static final String ADDRESS_PATTERN_WITH_DB_SELECTOR = "([^:/]*):?(\\d+)?/?(\\d+)?";
    public static final Integer ADDRESS_GROUP = 1;
    public static final Integer PORT_GROUP = 2;
    public static final Integer DB_GROUP = 3;

    public static Integer stringToInteger(String str, Integer defaultVal) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultVal;
        }
    }


    /**
     * L: host,M: port,R: dbNum
     *
     * @param url
     * @return
     */
    public static Triple<String, Integer, Integer> getRedisConfig(String url) {
        Pattern regx = Pattern.compile(ADDRESS_PATTERN_WITH_DB_SELECTOR);
        Matcher matcher = regx.matcher(url);
        Integer port = 6379;
        Integer dbNum = 0;
        String host = "";
        if (matcher.find()) {
            Integer matchGroups = matcher.groupCount();
            if (matchGroups >= DB_GROUP) {
                // might need to have separate dbNum for different service later
                dbNum = stringToInteger(matcher.group(DB_GROUP), dbNum);
            }
            if (matchGroups >= PORT_GROUP) {
                port = stringToInteger(matcher.group(PORT_GROUP), port);
            }
            if (matchGroups >= ADDRESS_GROUP) {
                host = matcher.group(ADDRESS_GROUP);
            }
        }
        return Triple.of(host, port, dbNum);
    }

}
