package me.sun.analyzer.filefind;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaFileMatcher implements FileMatcher {

    public static final JavaFileMatcher INSTANCE = new JavaFileMatcher();

    @Override
    public boolean match(File file) {
        boolean isJavaFile = "java".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()));
        boolean isTestFile = file.getName().contains("Test");
        return isJavaFile && !isTestFile;
    }
}
