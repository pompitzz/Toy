package me.sun.analyzer.filefind;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface FileFinder {

    default Set<File> find(String path) {
        return find(Collections.singletonList(new File(path)));
    }

    default Set<File> find(File file) {
        return find(Collections.singletonList(file));
    }

    Set<File> find(List<File> files);
}


