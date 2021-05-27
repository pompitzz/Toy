package me.sun.analyzer;

import lombok.Builder;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class ConsoleFileReadReporter implements FileReadReporter {

    private final boolean showLine;
    private final boolean showOnlyClassName;

    @Override
    public void report(List<FileReadRecord> fileReadRecords) {
        Map<File, List<FileReadRecord>> recordsByFile = fileReadRecords.stream().collect(Collectors.groupingBy(FileReadRecord::getFile));
        recordsByFile.forEach((file, fileReadReports) -> {
            System.out.println(getClassName(file));
            if (showLine) {
                fileReadReports.forEach(fileReadReport -> {
                    System.out.println(String.format("%s -> %s", fileReadReport.getLineNumber(), fileReadReport.getLine().trim()));
                });
                System.out.println();
            }
        });
    }

    private String getClassName(File file) {
        String absolutePath = file.getAbsolutePath().split("\\.")[0];
        if (showOnlyClassName) {
            String[] split = absolutePath.split("/");
            return split[split.length - 1];
        }
        String pattern = "src/main/java/";
        int index = absolutePath.indexOf(pattern);
        if (index > 0) {
            String packagePath = absolutePath.substring(index + pattern.length());
            return packagePath.replace('/', '.');
        }
        return absolutePath;
    }
}
