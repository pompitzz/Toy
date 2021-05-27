package me.sun.analyzer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.sun.analyzer.filefind.FileMatcher;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompositeFileMatcherBuilder {

    private final List<FileMatcher> fileMatchers = new LinkedList<>();

    public static CompositeFileMatcherBuilder builder() {
        return new CompositeFileMatcherBuilder();
    }

    public CompositeFileMatcherBuilder addMatcher(FileMatcher fileMatcher) {
        fileMatchers.add(fileMatcher);
        return this;
    }

    public FileMatcher and() {
        return file -> {
            for (FileMatcher fileMatcher : fileMatchers) {
                if (!fileMatcher.match(file)) {
                    return false;
                }
            }
            return true;
        };
    }
}
