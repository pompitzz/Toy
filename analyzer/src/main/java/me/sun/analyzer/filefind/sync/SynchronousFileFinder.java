package me.sun.analyzer.filefind.sync;

import lombok.RequiredArgsConstructor;
import me.sun.analyzer.filefind.FileFinder;
import me.sun.analyzer.filefind.FileMatcher;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class SynchronousFileFinder implements FileFinder {

    private final FileMatcher fileMatcher;

    @Override
    public Set<File> find(List<File> files) {
        Set<File> result = new LinkedHashSet<>();
        files.forEach(file -> doFind(file, result));
        return result;
    }

    private void doFind(File file, Set<File> result) {
        if (file.isDirectory()) {
            doFindForDirectory(file, result);
        } else {
            if (fileMatcher.match(file)) {
                result.add(file);
            }
        }
    }

    private void doFindForDirectory(File file, Set<File> result) {
        File[] files = file.listFiles();
        if (Objects.isNull(files)) {
            return;
        }
        for (File directory : files) {
            doFind(directory, result);
        }
    }
}
