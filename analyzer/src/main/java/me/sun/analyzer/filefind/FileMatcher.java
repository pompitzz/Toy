package me.sun.analyzer.filefind;

import java.io.File;

public interface FileMatcher {
    boolean match(File file);
}
