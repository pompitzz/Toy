package me.sun.analyzer;

@FunctionalInterface
public interface LineMatcher {
    boolean match(String line);
}
