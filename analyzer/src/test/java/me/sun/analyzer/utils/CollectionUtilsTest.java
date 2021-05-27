package me.sun.analyzer.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {
    @Test
    void roundRobin() throws Exception {
        // given
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);

        // when
        List<List<Integer>> result = CollectionUtils.roundRobin(list, 3);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo(List.of(1, 4, 7));
        assertThat(result.get(1)).isEqualTo(List.of(2, 5));
        assertThat(result.get(2)).isEqualTo(List.of(3, 6));
    }
}
