package me.sun.analyzer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringMatcherBuilder {

    private final List<LineMatcher> matchers = new LinkedList<>();

    public static StringMatcherBuilder builder() {
        return new StringMatcherBuilder();
    }

    public StringMatcherBuilder addMatcher(LineMatcher matcher) {
        matchers.add(matcher);
        return this;
    }

    public StringMatcherBuilder notContains(Pattern pattern) {
        matchers.add(str -> !pattern.matcher(str).find());
        return this;
    }

    public StringMatcherBuilder contains(Pattern pattern) {
        matchers.add(str -> pattern.matcher(str).find());
        return this;
    }

    public StringMatcherBuilder notContains(String pattern) {
        matchers.add(str -> !str.contains(pattern));
        return this;
    }

    public StringMatcherBuilder contains(String pattern) {
        matchers.add(str -> str.contains(pattern));
        return this;
    }

    public LineMatcher and() {
        return line -> {
            for (LineMatcher matcher : matchers) {
                if (!matcher.match(line)) {
                    return false;
                }
            }
            return true;
        };
    }
}
