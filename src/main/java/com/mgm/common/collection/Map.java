package com.mgm.common.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Map.*;

public class Map {


    public static <TInput1, E> java.util.Map<TInput1, java.util.List<E>> groupListToMap(Collection<E> sourceList, ListToMapConverterInterface<TInput1, E> converterInterface) {
        java.util.Map<TInput1, java.util.List<E>> newMap = new HashMap<TInput1, java.util.List<E>>();
        for(E item : sourceList) {
            var key = converterInterface.getItemKey(item);
            var newList = newMap.getOrDefault(key, new java.util.ArrayList<E>());
            newList.add(item);
            newMap.put(key, newList);
        }
        return newMap;
    }
    public interface ListToMapConverterInterface<TInput1, E> {
        public TInput1 getItemKey(E item);
    }

    public static <TInput1, TInput2> java.util.Map<TInput1, TInput2> mapFilterByKeys(Function<TInput1, Boolean> fn, java.util.Map<TInput1, TInput2> map) {
        return map.entrySet().stream()
                .filter(m -> fn.apply(m.getKey()))
                .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));
    }

    public static <TInput1, TInput2> java.util.Map<TInput1, TInput2> mergeMaps(java.util.Map<TInput1, TInput2> map1, java.util.Map<TInput1, TInput2> map2) {
        return Stream.of(map1, map2).flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(java.util.Map.Entry::getKey, java.util.Map.Entry::getValue));
    }

    public static <TInput1, TInput2> java.util.Map<TInput1, TInput2> sortMap(Comparator<Entry<TInput1, TInput2>> comparator, java.util.Map<TInput1, TInput2> unsortedMap) {
        return unsortedMap.entrySet().stream().
                sorted(comparator).
                collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
