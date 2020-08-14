package me.sun;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MapTest {
    @Test
    void computeIfAbsent()throws Exception {
        Map<Integer, String> map = new HashMap<>();
        String hello = map.put(1, "Hello");
        String twoKey = map.computeIfAbsent(2, key -> key + "Key");
        String twoKey2 = map.computeIfAbsent(2, key -> key + "FailKey");

        assertThat(map.containsKey(1)).isTrue();
        assertThat(map.containsKey(2)).isTrue();
        assertThat(twoKey).isEqualTo("2Key");
        assertThat(twoKey).isEqualTo(twoKey2);
    }
}
