package me.sun.analyzer;

import me.sun.analyzer.filefind.sync.SynchronousFileFinder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

class FileFinder2Test {

    @Test
    void getFiles()throws Exception {
        Set<File> files = new SynchronousFileFinder(file -> true)
                .find("/Users/dongmyeonglee/Projects");
        System.out.println(files.size());
    }
}
