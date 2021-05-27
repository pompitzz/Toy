package me.sun.analyzer;

import me.sun.analyzer.filefind.FileMatcher;
import me.sun.analyzer.filefind.JavaFileMatcher;

import java.util.List;
import java.util.regex.Pattern;

public class Main {

    public static final List<String> PATHS = List.of("/Users/dongmyeonglee/Projects/msa-study/spring-boot-study/src");

    public static final FileMatcher FILE_MATCHER = CompositeFileMatcherBuilder.builder()
            .addMatcher(JavaFileMatcher.INSTANCE)
            .and();

    public static final LineMatcher PATH_VARIABLE = StringMatcherBuilder.builder()
            .contains(Pattern.compile("(?i)long"))
            .notContains(Pattern.compile("\\{|\\}"))
            .notContains("PathVariable")
            .and();

    public static final FileReadReporter FILE_READ_REPORTER = ConsoleFileReadReporter.builder()
            .showLine(true)
            .showOnlyClassName(true)
            .build();

    public static void main(String[] args) {
        Runner.builder()
                .paths(PATHS)
                .fileMatcher(FILE_MATCHER)
                .lineMatcher(PATH_VARIABLE)
                .fileReadReporter(FILE_READ_REPORTER)
                .build()
                .doRun();
    }
}
