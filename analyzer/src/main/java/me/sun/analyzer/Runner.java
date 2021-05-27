package me.sun.analyzer;

import lombok.Builder;
import me.sun.analyzer.filefind.FileFinder;
import me.sun.analyzer.filefind.FileMatcher;
import me.sun.analyzer.filefind.async.SimpleAsynchronousFileFinder;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class Runner {
    private final List<String> paths;
    private final FileMatcher fileMatcher;
    private final LineMatcher lineMatcher;
    private final FileReadReporter fileReadReporter;

    public void doRun() {
        FileFinder fileFinder = new SimpleAsynchronousFileFinder(fileMatcher);
        Set<File> files = fileFinder.find(paths.stream().map(File::new).collect(Collectors.toList()));
        FileReader fileReader = new FileReader(files, lineMatcher);
        fileReadReporter.report(fileReader.read());
    }
}
