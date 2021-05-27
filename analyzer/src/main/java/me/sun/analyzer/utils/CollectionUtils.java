package me.sun.analyzer.utils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionUtils {
    public static <T> List<List<T>> roundRobin(Collection<T> collection, int size) {
        if (collection.size() <= size) {
            return collection.stream()
                    .map(Collections::singletonList)
                    .collect(Collectors.toList());
        }
        List<List<T>> result = new ArrayList<>(size);
        initList(result, size, LinkedList::new);
        int index = 0;
        for (T t : collection) {
            result.get(index % size).add(t);
            index++;
        }
        return result;
    }

    private static <T> void initList(List<T> target, int size, Supplier<T> builder) {
        IntStream.range(0, size).forEach(i -> target.add(builder.get()));
    }
}
