package me.sun.analyzer;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class LineMatcherBuilderTest {


    @Test
    void andBuild() throws Exception {
        LineMatcher stringPredicate = StringMatcherBuilder.builder()
                .addMatcher(str -> str.contains("hello"))
                .addMatcher(str -> str.contains("world"))
                .and();

        assertThat(stringPredicate.match("hello world")).isTrue();
        assertThat(stringPredicate.match("hello")).isFalse();
        assertThat(stringPredicate.match("world")).isFalse();
    }

    @Test
    void contains() throws Exception {
        LineMatcher stringPredicate = StringMatcherBuilder.builder()
                .contains(Pattern.compile("(?i)(hello|bye)"))
                .and();

        assertThat(stringPredicate.match("hi hello world")).isTrue();
        assertThat(stringPredicate.match("hi Hello world")).isTrue();
        assertThat(stringPredicate.match("hi HEllo world")).isTrue();
        assertThat(stringPredicate.match("hi HElLo world")).isTrue();
        assertThat(stringPredicate.match("hi Bye world")).isTrue();
        assertThat(stringPredicate.match("hi bye world")).isTrue();
        assertThat(stringPredicate.match("hi ByE world")).isTrue();
        assertThat(stringPredicate.match("hi Helo world")).isFalse();
        assertThat(stringPredicate.match("hi By world")).isFalse();
        assertThat(stringPredicate.match("hi BE world")).isFalse();
    }

    @Test
    void notContains() throws Exception {
        LineMatcher stringPredicate = StringMatcherBuilder.builder()
                .notContains(Pattern.compile("(?i)(hello|bye)"))
                .and();

        assertThat(stringPredicate.match("hi hello world")).isFalse();
        assertThat(stringPredicate.match("hi Hello world")).isFalse();
        assertThat(stringPredicate.match("hi HEllo world")).isFalse();
        assertThat(stringPredicate.match("hi HElLo world")).isFalse();
        assertThat(stringPredicate.match("hi Bye world")).isFalse();
        assertThat(stringPredicate.match("hi bye world")).isFalse();
        assertThat(stringPredicate.match("hi ByE world")).isFalse();
        assertThat(stringPredicate.match("hi Helo world")).isTrue();
        assertThat(stringPredicate.match("hi By world")).isTrue();
        assertThat(stringPredicate.match("hi BE world")).isTrue();
    }
}
