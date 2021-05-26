package me.sun.analyzer;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class FileReader {

    private static final int THREADS = 20;

    private final Set<File> files;
    private final Predicate<String> linePredicate;

    public void read() {
        List<Set<File>> dividedFiles = getDividedFiles();
        List<CompletableFuture<List<FileReadReport>>> futures = dividedFiles.stream()
                .map(this::readFilesAsync)
                .collect(Collectors.toList());
        Map<File, List<FileReadReport>> result = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(FileReadReport::getFile));

        result.forEach((file, fileReadReports) -> {
            System.out.println("======================================================================================================");
            System.out.println(String.format("### %s ###", file.getName()));
            fileReadReports.forEach(fileReadReport -> {
                System.out.println(String.format(" %s -> %s", fileReadReport.getLineNumber(), fileReadReport.getLine().trim()));
            });
        });
    }

    private CompletableFuture<List<FileReadReport>> readFilesAsync(Set<File> files) {
        return CompletableFuture.supplyAsync(() -> readFiles(files));
    }

    private List<FileReadReport> readFiles(Set<File> files) {
        List<FileReadReport> fileReadReports = new LinkedList<>();
        for (File file : files) {
            try {
                List<String> lines = Files.readAllLines(Path.of(file.toURI()));
                for (int i = 0; i < lines.size(); i++) {
                    int lineNumber = i + 1;
                    String line = lines.get(i);
                    if (linePredicate.test(line)) {
                        fileReadReports.add(new FileReadReport(file, line, lineNumber));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileReadReports;
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
