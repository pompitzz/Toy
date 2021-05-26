package me.sun.analyzer;

import me.sun.analyzer.filefind.FileFinder;
import me.sun.analyzer.filefind.JavaFileMatcher;
import me.sun.analyzer.filefind.sync.SynchronousFileFinder;

import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FileFinder fileFinder = new SynchronousFileFinder(JavaFileMatcher.INSTANCE);
        Set<File> files = fileFinder.find("/Users/dongmyeonglee/Projects/tools/tools-server");
        FileReader fileReader = new FileReader(files, line -> line.contains("Long"));
        fileReader.read();
    }
}
