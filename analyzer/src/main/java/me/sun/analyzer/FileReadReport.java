package me.sun.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class FileReadReport {
    private final File file;
    private final String line;
    private final int lineNumber;
}
