package me.sun.analyzer;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class FileReader {

    private static final int THREADS = 20;

    private final Set<File> files;
    private final LineMatcher lineMatcher;

    public List<FileReadRecord> read() {
        List<Set<File>> dividedFiles = getDividedFiles();
        List<CompletableFuture<List<FileReadRecord>>> futures = dividedFiles.stream()
                .map(this::readFilesAsync)
                .collect(Collectors.toList());
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private CompletableFuture<List<FileReadRecord>> readFilesAsync(Set<File> files) {
        return CompletableFuture.supplyAsync(() -> readFiles(files));
    }

    private List<FileReadRecord> readFiles(Set<File> files) {
        List<FileReadRecord> fileReadRecords = new LinkedList<>();
        for (File file : files) {
            try {
                List<String> lines = Files.readAllLines(Path.of(file.toURI()));
                for (int i = 0; i < lines.size(); i++) {
                    int lineNumber = i + 1;
                    String line = lines.get(i);
                    if (lineMatcher.match(line)) {
                        fileReadRecords.add(new FileReadRecord(file, line, lineNumber));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileReadRecords;
    }

    private List<Set<File>> getDividedFiles() {
        List<Set<File>> dividedFiles = IntStream.range(0, THREADS)
                .mapToObj(i -> new LinkedHashSet<File>())
                .collect(Collectors.toList());
        int index = 0;
        for (File file : files) {
            dividedFiles.get(index).add(file);
            index = (index + 1) % dividedFiles.size();
        }
        return dividedFiles;
    }
}
