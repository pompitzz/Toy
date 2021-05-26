package me.sun.analyzer.filefind.async;

import lombok.Builder;

import java.io.File;
import java.util.*;

@Builder
public class DivideContext {
    private final Set<File> files = new LinkedHashSet<>();
    private final List<Set<File>> directoriesSet = new LinkedList<>();

    public void divide(File file, int thread) {
        Set<File> directories = new LinkedHashSet<>();
        doDivide(file, thread, directories);

    }

    private void doDivide(File file, int thread, Set<File> directories) {
        if (file.isDirectory()) {
            Set<File> dirs = new LinkedHashSet<>();
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (!file.isDirectory()) {
                    files.add(file);
                } else {
                    dirs.add(listFile);
                }
            }
            if (directories.size() + dirs.size() > thread) {
                directories.addAll(dirs);
            } else {
                for (File dir : dirs) {
                }
            }
        } else {
            files.add(file);
        }
    }
}
