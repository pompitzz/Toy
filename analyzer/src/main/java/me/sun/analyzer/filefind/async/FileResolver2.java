package me.sun.analyzer.filefind.async;

import lombok.Builder;
import me.sun.analyzer.filefind.FileMatcher;
import me.sun.analyzer.utils.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileResolver2 {

    private final static ExecutorService executor = Executors.newFixedThreadPool(10);

    private final List<FileMatcher> fileMatchers;
    private final Reporter reporter = new Reporter();

    public FileResolver2(List<FileMatcher> fileMatchers) {
        this.fileMatchers = fileMatchers;
    }

//    public Set<File> getFiles(String rootDir) {
//        return getFiles(List.of(rootDir));
//    }

    public Set<File> getFiles(String rootDirs) {
        reporter.start();
        Set<File> some = some(new File(rootDirs), 5);
        reporter.report();
        return some;
    }

    public Set<File> some(File dir, int thread) {
        Context context = someContext(dir, thread);
        for (Set<File> files : context.directoriesSet) {
            System.out.println(files);
        }
        Set<File> files = retriveFilesInParallelByDirSize(context.directoriesSet);
        files.addAll(context.files);
        return files;
    }

    private Context someContext(File dir, int thread) {
        Set<File> files = new LinkedHashSet<>();
        Set<File> directories = new LinkedHashSet<>();
        parse(dir, thread, files, directories);
        List<List<File>> directoriesList = CollectionUtils.roundRobin(directories, thread);
        return Context.builder()
                .files(files)
                .directoriesSet(directoriesList.stream().map(LinkedHashSet::new).collect(Collectors.toList()))
                .build();
    }

    private void parse(File file, int thread, Set<File> files, Set<File> directories) {
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
                dirs.forEach(dir -> parse(dir, thread, files, directories));
            }
        } else {
            files.add(file);
        }
    }

    @Builder
    private static class Context {
        private Set<File> files;
        private List<Set<File>> directoriesSet;
    }

    public Set<File> retriveFilesInParallelByDirSize(List<Set<File>> directoriesSet) {
        ExecutorService executor = Executors.newFixedThreadPool(directoriesSet.size());
        System.out.println(directoriesSet.size());
        List<CompletableFuture<Set<File>>> futures = directoriesSet.stream()
                .map(files -> CompletableFuture.supplyAsync(() -> retrieveFiles(files), executor)).collect(Collectors.toList());
        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<File> retrieveFiles(Set<File> directories) {
        return directories.stream()
                .map(this::retrieveFiles)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<File> retrieveFiles(File file) {
        Set<File> result = new LinkedHashSet<>();
        doRetrieveFiles(file, result);
        return result;
    }

    private void doRetrieveFiles(File file, Set<File> result) {
        if (file.isDirectory()) {
            for (File innerFile : Objects.requireNonNull(file.listFiles())) {
                doRetrieveFiles(innerFile, result);
            }
        } else {
            reporter.countFile();
            if (isAllMatch(file)) {
                result.add(file);
            }
        }
    }

    private boolean isAllMatch(File file) {
        for (FileMatcher fileMatcher : fileMatchers) {
            if (fileMatcher.match(file)) {
                reporter.countUnMatch(fileMatcher);
                return false;
            }
        }
        return true;
    }

    private static class Reporter {
        private final AtomicInteger fileCount = new AtomicInteger();
        private final Map<Class<?>, AtomicInteger> unMatchCount = new HashMap<>();
        private long startTime;
        private boolean isStarted;

        private void start() {
            isStarted = true;
            startTime = System.currentTimeMillis();
        }

        private void countFile() {
            fileCount.incrementAndGet();
        }

        private void countUnMatch(FileMatcher fileMatcher) {
            unMatchCount.computeIfAbsent(fileMatcher.getClass(), key -> new AtomicInteger(1))
                    .incrementAndGet();
        }

        private void report() {
            if (!isStarted) {
                throw new IllegalStateException("start should be called before report");
            }
            long diff = System.currentTimeMillis() - startTime;
            BigDecimal secondTaken = BigDecimal.valueOf(diff)
                    .setScale(2, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);
            StringBuilder sb = new StringBuilder()
                    .append("찾은 파일 수: ").append(fileCount.get())
                    .append(" 걸린 시간: ").append(secondTaken).append("s");
            System.out.println(sb);
        }
    }
}
