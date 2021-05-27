package me.sun.analyzer;

import java.util.List;

public interface FileReadReporter {
    void report(List<FileReadRecord> fileReadRecords);
}
