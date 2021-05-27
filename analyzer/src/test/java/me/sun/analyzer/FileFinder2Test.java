package me.sun.analyzer;

import me.sun.analyzer.filefind.async.SimpleAsynchronousFileFinder;
import me.sun.analyzer.filefind.sync.SynchronousFileFinder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class FileFinder2Test {

    @Test
    void getFiles() throws Exception {
        List<List<String>> filesList = Arrays.asList(
                Arrays.asList("/Users/dongmyeonglee/Projects/toy",
                        "/Users/dongmyeonglee/Projects/msa-study",
                        "/Users/dongmyeonglee/Projects/json-parser"),
                Arrays.asList(
                        "/Users/dongmyeonglee/Projects/ActionsStudy",
                        "/Users/dongmyeonglee/Projects/Study",
                        "/Users/dongmyeonglee/Projects/tools",
                        "/Users/dongmyeonglee/Projects/.DS_Store"),
                Arrays.asList(
                        "/Users/dongmyeonglee/Projects/playground",
                        "/Users/dongmyeonglee/Projects/Toys",
                        "/Users/dongmyeonglee/Projects/simple-summary",
                        "/Users/dongmyeonglee/Projects/TASK-FORCE"),
                Arrays.asList(
                        "/Users/dongmyeonglee/Projects/type-script-block-chain",
                        "/Users/dongmyeonglee/Projects/blog-comments",
                        "/Users/dongmyeonglee/Projects/blog"),
                Arrays.asList(
                        "/Users/dongmyeonglee/Projects/notification-service-v2",
                        "/Users/dongmyeonglee/Projects/java-request",
                        "/Users/dongmyeonglee/Projects/lotto",
                        "/Users/dongmyeonglee/Projects/Examples",
                        "/Users/dongmyeonglee/Projects/tools-backup"),
                Arrays.asList(
                        "/Users/dongmyeonglee/Projects/private-summary",
                        "/Users/dongmyeonglee/Projects/ts-vue-projects"));
        for (List<String> strings : filesList) {
            diff(strings);
        }
    }

    private void diff(List<String> fileStrs) {
        List<File> files = fileStrs.stream().map(File::new).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        Set<File> files1 = new SynchronousFileFinder(f -> true).find(files);
        long end1 = System.currentTimeMillis();
        Set<File> files2 = new SimpleAsynchronousFileFinder(f -> true).find(files);
        long end2 = System.currentTimeMillis();
        System.out.println(files1.size());
        System.out.println(files2.size());
        System.out.println(String.format("sync: %s, async: %s", (end1 - start), (end2 - end1)));
    }
}
