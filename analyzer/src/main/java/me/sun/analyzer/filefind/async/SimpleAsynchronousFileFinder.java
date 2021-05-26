package me.sun.analyzer.filefind.async;

import me.sun.analyzer.CompletableFutureUtils;
import me.sun.analyzer.filefind.FileFinder;
import me.sun.analyzer.filefind.FileMatcher;
import me.sun.analyzer.filefind.sync.SynchronousFileFinder;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SimpleAsynchronousFileFinder implements FileFinder {

    private final FileMatcher fileMatcher;
    private final FileFinder fileFinder;

    public SimpleAsynchronousFileFinder(FileMatcher fileMatcher) {
        this.fileMatcher = fileMatcher;
        this.fileFinder = new SynchronousFileFinder(fileMatcher);
    }

    @Override
    public Set<File> find(List<File> files) {
        List<CompletableFuture<Set<File>>> futures = files.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> fileFinder.find(file)))
                .collect(Collectors.toList());
        return CompletableFutureUtils.combine(futures)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
