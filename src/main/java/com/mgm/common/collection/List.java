package com.mgm.common.collection;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mgm.common.serialization.Serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class List {
    public static <TInput, TOutput> java.util.List<TOutput> listMap(Function<TInput, TOutput> fn, java.util.List<TInput> list) {
        return list.stream()
                .map(line -> fn.apply(line))
                .collect(Collectors.toList());
    }

    public static <TInput, TOutput> java.util.List<TOutput> listMap(Function<TInput, TOutput> fn, TInput[] list) {
        return Arrays.stream(list)
                .map(line -> fn.apply(line))
                .collect(Collectors.toList());
    }

    public static <TInput> java.util.List<TInput> listFilter(Function<TInput, Boolean> fn, java.util.List<TInput> list) {
        return list.stream()
                .filter(line -> fn.apply(line))
                .collect(Collectors.toList());
    }

    public static <TInput> java.util.List<ObjectNode> listFilterByMap(Map<TInput, ObjectNode> mapInfo, java.util.List<TInput> list) {
        return list.stream().map(mapInfo::get).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T> java.util.List<T> dedup(java.util.List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static <T> java.util.List<T> moveToLast(java.util.List<T> sourceList, java.util.List<T> moveToLastListRange) {
        java.util.List<T> toMoveToLastElements = listFilter(item -> moveToLastListRange.contains(item), sourceList);
        java.util.List<T> filteredElements = listFilter(item -> !moveToLastListRange.contains(item), sourceList);
        java.util.List<T> result = new ArrayList<>(filteredElements);
        result.addAll(toMoveToLastElements);
        return result;
    }

    @FunctionalInterface
    public interface DoubleParamFunction<One, Two, Three> {
        Three apply(One one, Two two);
    }

    public static <T> java.util.List<T> dedup(DoubleParamFunction<T, T, Boolean> isEqual, java.util.List<T> list) {
        var newList = new java.util.ArrayList<T>();
        for (var i = 0; i < list.size(); i++) {
            var item = list.get(i);
            var shouldAdd = true;
            for (var j = 0; j < newList.size(); j++) {
                var item2 = newList.get(j);
                if (isEqual.apply(item, item2)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                newList.add(item);
            }
        }
        return newList;
    }

    public static ArrayNode stringArrayNode(java.util.List<String> list) {
        var newList = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static ArrayNode integerArrayNode(java.util.List<Integer> list) {
        var newList = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static ArrayNode objectNodeArrayNode(java.util.List<ObjectNode> list) {
        var newList = JsonNodeFactory.instance.arrayNode();
        for (var i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static ArrayNode objectArrayNode(java.util.List<Object> list) {
        if (!list.isEmpty() && list.get(0) instanceof String) {
            return stringArrayNode(listMap(l -> l.toString(), list));
        }
        if (!list.isEmpty() && list.get(0) instanceof HashMap) {
            return objectNodeArrayNode(listMap(l -> Serialization.serializeToObjectNode(l), list));
        }
        if (!list.isEmpty() && list.get(0) instanceof Integer) {
            return integerArrayNode(listMap(l -> (Integer) l, list));
        }
        return JsonNodeFactory.instance.arrayNode();
    }

    public static <T> java.util.ArrayList<T> arrayList(T element) {
        var l = new java.util.ArrayList<T>();
        l.add(element);
        return l;
    }

    public static <T> ArrayList<java.util.List<T>> splitListByCapacity(java.util.List<T> source, int capacity) {
        ArrayList<java.util.List<T>> result = new ArrayList<>();
        if (source != null) {
            int size = source.size();
            if (size > 0) {
                for (int i = 0; i < size; ) {
                    java.util.List<T> value = null;
                    int end = i + capacity;
                    if (end > size) {
                        end = size;
                    }
                    value = source.subList(i, end);
                    i = end;
                    result.add(value);
                }

            } else {
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }

    public static java.util.Optional<java.util.List<String>> removeList(java.util.Optional<java.util.List<String>> orignialList, java.util.Optional<java.util.List<String>> targetList) {
        java.util.List<String> original = listMap(String::toLowerCase, listFilter(Objects::nonNull, orignialList.orElse(new ArrayList<>())));
        java.util.List<String> target = listMap(String::toLowerCase, listFilter(Objects::nonNull, targetList.orElse(new ArrayList<>())));
        original.removeAll(target);
        return original.isEmpty() ? java.util.Optional.empty() : java.util.Optional.of(original);
    }

    public static java.util.Optional<java.util.List<String>> addListAndDedup(java.util.Optional<java.util.List<String>> orignialList, java.util.Optional<java.util.List<String>> targetList) {
        java.util.List<String> orginal = listMap(String::toLowerCase, listFilter(Objects::nonNull, orignialList.orElse(new ArrayList<>())));
        java.util.List<String> target = listMap(String::toLowerCase, listFilter(Objects::nonNull, targetList.orElse(new ArrayList<>())));
        orginal.addAll(target);
        java.util.List<String> result = dedup(orginal);
        return result.isEmpty() ? java.util.Optional.empty() : Optional.of(result);
    }
}
