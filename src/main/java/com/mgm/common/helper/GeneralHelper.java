package com.mgm.common.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mgm.common.serialization.Serialization;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import static com.mgm.common.collection.List.*;

public class GeneralHelper {

    private static final double EARTH_RADIUS = 6378137;

    public static String convertToMd5String(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static long recordLatencyInfo(Logger log, long oldTimeStamp, int index, String message) {
        long currTimeStamp = System.currentTimeMillis();
        long delta = currTimeStamp - oldTimeStamp;
        log.info("Index " + index + ", latency " + delta / 1000 + "s " + delta % 1000 + "ms; " + message);
        return currTimeStamp;
    }

    public static <TObject> List<TObject> castToList(Object obj, Class<TObject> clazz) {
        List<TObject> finalList = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                finalList.add(clazz.cast(o));
            }
        }
        return finalList;
    }

    public static List<String> getIfNotNull(List<String> arr) {
        if (isNullOrEmpty(arr)) {
            return new ArrayList<>();
        }
        return arr;
    }

    public static List<String> trimLowerStringList(List<String> arr) {
        return listMap(String::trim, listMap(String::toLowerCase, arr));
    }

    public static List<String> toStringList(List arr) {
        if (arr == null || arr.isEmpty()) {
            return List.of();
        }
        List<String> finalList = new ArrayList<>();
        for (Object e : arr) {
            finalList.add((String) e);
        }
        return finalList;
    }

    public static List<String> objectListToStringList(List<Object> arr) {
        if (arr == null || arr.isEmpty()) {
            return List.of();
        }
        List<String> finalList = new ArrayList<>();
        for (Object e : arr) {
            try {
                finalList.add((String) e);
            } catch (Exception ignored) {
            }
        }
        return finalList;
    }

    public static List<List<String>> objectListToStringListList(List<Object> arr) {
        if (arr == null || arr.isEmpty()) {
            return List.of();
        }
        List<List<String>> finalList = new ArrayList<>();
        for (Object e : arr) {
            try {
                finalList.add((List<String>) e);
            } catch (Exception ignored) {
            }
        }
        return finalList;
    }

    public static List<ObjectNode> toObjectNodeFullList(List arr) {
        if (arr == null || arr.isEmpty()) {
            return List.of();
        }
        List<ObjectNode> finalList = new ArrayList<>();
        for (Object e : arr) {
            if (e == null) {
                finalList.add(null);
                continue;
            }
            finalList.add(Serialization.serializeToObjectNode(e));
        }
        return finalList;
    }

    public static List<ObjectNode> toObjectNodeList(List arr) {
        if (arr == null || arr.isEmpty()) {
            return List.of();
        }
        List<ObjectNode> finalList = new ArrayList<>();
        for (Object e : arr) {
            if (e == null) {
                continue;
            }
            finalList.add(Serialization.serializeToObjectNode(e));
        }
        return finalList;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static <T1, T2> boolean isNullOrEmpty(Map<T1, T2> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNumeric(String str) {
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    public static String trimLowerString(String str) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        return str.toLowerCase().trim();
    }

    public static <TObject> boolean isNullOrEmpty(Collection<TObject> arr) {
        return arr == null || arr.isEmpty();
    }

    public static boolean isNullOrEmpty(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return true;
        }
        // check array node and object node
        if (node.isContainerNode()) {
            return node.isEmpty();
        }
        // check bool, string, int, long.. node
        return isNullOrEmpty(node.asText());
    }

    public static <T1, T2> List<T2> flatMapValues(Map<T1, List<T2>> map) {
        List<T2> flatted = new ArrayList<>();
        for (List<T2> value : map.values()) {
            if (!isNullOrEmpty(value)) {
                flatted.addAll(value);
            }
        }
        return flatted;
    }

    public static List<Object> arrayNodeToListObject(ArrayNode arrayNode) {
        List<Object> objects = new ArrayList<>();
        for (Object arr : arrayNode) {
            objects.add(arr);
        }
        return objects;
    }

    public static List<String> jsonNodeToStringList(JsonNode node) {
        if (isNullOrEmpty(node) || !node.isArray()) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (Object ele : node) {
            if (isNullOrEmpty((JsonNode) ele)) {
                continue;
            }
            result.add(((TextNode) ele).asText());
        }
        return result;
    }

    public static List<String> jsonNodeToFullStringList(JsonNode node) {
        if (isNullOrEmpty(node) || !node.isArray()) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (Object ele : node) {
            if (isNullOrEmpty((JsonNode) ele)) {
                result.add(null);
            } else {
                result.add(((TextNode) ele).asText());
            }
        }
        return result;
    }

    public static String stringListContainsSubString(List<String> arr, String str) {
        if (isNullOrEmpty(arr) || isNullOrEmpty(str)) {
            return null;
        }
        for (String ele : arr) {
            if (str.contains(ele)) {
                return ele;
            }
        }
        return null;
    }

    public static List<ObjectNode> jsonNodetoObjectNodeList(JsonNode arr) {
        List<ObjectNode> result = new ArrayList<>();
        if (arr != null) {
            for (Object el : arr) {
                result.add(Serialization.serializeToObjectNode(el));
            }
        }
        return result;
    }

    public static List<String> convertOptionalStringList(List<Optional<String>> arr) {
        List<String> result = new ArrayList<>();
        for (Optional<String> ele : arr) {
            ele.ifPresent(result::add);
        }
        return result;
    }

    public static Map<String, JsonNode> sortMapByList(Map<String, JsonNode> mapToSort, List<String> target) {
        if (isNullOrEmpty(target)) {
            return mapToSort;
        }
        Map<String, JsonNode> sorted = new LinkedHashMap<>();
        for (String key : target) {
            sorted.put(key, mapToSort.get(key));
        }
        return sorted;
    }

    public static String generateSearchResultMapKey(String name, Boolean value) {
        return name + value.toString();
    }

    public static String stripTrailing(String string, String characters) {
        if (isNullOrEmpty(string) || isNullOrEmpty(characters)) {
            return string;
        }
        char[] chars = string.toCharArray();
        int right = chars.length;
        while (right >= 0) {
            if (characters.indexOf(chars[right - 1]) == -1) {
                break;
            }
            right--;
        }
        return string.substring(0, right);
    }

    public static String stripLeading(String string, String characters) {
        if (isNullOrEmpty(string) || isNullOrEmpty(characters)) {
            return string;
        }
        char[] chars = string.toCharArray();
        int left = 0;
        while (left < chars.length) {
            if (characters.indexOf(chars[left]) == -1) {
                break;
            }
            left++;
        }
        return string.substring(left);
    }

    public static String trim(String string, String characters) {
        string = stripLeading(string, characters);
        string = stripTrailing(string, characters);
        return string;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin((rad(lon1) - rad(lon2)) / 2), 2)));
        // kilometer
        return distance * EARTH_RADIUS / 1000;
    }

    public static <S, T> T copy(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String truncateStringIfNeed(String string, Integer len) {
        return string.length() > len ? string.substring(0, len - 1) : string;
    }

    public static Integer findNewStart(String targetId, List<String> idList, Integer limit) {
        Integer index = idList.indexOf(targetId);
        if (index == -1) {
            return -1;
        }
        Integer pageIndex = (int) Math.floor((double) index / (double) limit);
        Integer newStart = pageIndex * limit;
        return newStart;
    }

    public static String formatErrorMessage(String errorMessage, Throwable exception, Object... args) {
        String alertMessage = String.format(errorMessage, args);
        if (exception != null) {
            StringBuilder alertMessageBuilder = new StringBuilder(alertMessage);
            alertMessageBuilder.append("\n").append(exception.getMessage());
            int stackTraceCount = 10;
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                alertMessageBuilder.append("\n").append(stackTraceElement.toString());
                if (--stackTraceCount <= 0) {
                    break;
                }
            }
            alertMessage = alertMessageBuilder.toString();
        }
        return alertMessage;
    }
}
