package me.sun.write;

import me.sun.write.model.ExcelSheetDto;
import me.sun.write.model.LoggingModel;
import org.apache.poi.sl.draw.geom.IAdjustableShape;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ExcelWriteServiceTest {

    @Test
    void test() throws Exception {
        List<ExcelSheetDto> excelSheetDtos = Arrays.asList(buildExcelSheetDto("First"), buildExcelSheetDto("Second"));
        ExcelWriteService excelWriteService = new ExcelWriteService();
        excelWriteService.writeExcel("/Users/dongmyeonglee/Projects/toy/excel/src/test/java/me/sun/write/test.xls", excelSheetDtos);
    }

    private ExcelSheetDto buildExcelSheetDto(String name) {
        return ExcelSheetDto.builder()
                .sheetName(name)
                .loggingModels(Arrays.asList(buildLoggingModel(), buildLoggingModel()))
                .build();
    }

    private LoggingModel buildLoggingModel() {
        return LoggingModel.builder()
                .cause("NullPointerException")
                .key("me.sun.excel.cell.CellStyleContext.addStyle13")
                .detailedException("java.lang.IllegalArgumentException\n" +
                        "\tat me.sun.write.ExcelWriteServiceTest.etest(ExcelWriteServiceTest.java:22)\n" +
                        "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                        "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                        "\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                        "\tat java.base/java.lang.reflect.Method.invoke(Method.java:566)\n" +
                        "\tat org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:686)\n" +
                        "\tat org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)\n" +
                        "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131)\n" +
                        "\tat org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:149)\n" +
                        "\tat org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:140)\n" +
                        "\tat org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:84)\n" +
                        "\tat org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)\n" +
                        "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)\n" +
                        "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)\n" +
                        "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)\n" +
                        "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)\n" +
                        "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)\n" +
                        "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)\n" +
                        "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)\n" +
                        "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:205)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:201)\n" +
                        "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:137)\n" +
                        "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:71)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:135)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                        "\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1540)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                        "\tat java.base/java.util.ArrayList.forEach(ArrayList.java:1540)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)\n" +
                        "\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51)\n" +
                        "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:248)\n" +
                        "\tat org.junit.platform.launcher.core.DefaultLauncher.lambda$execute$5(DefaultLauncher.java:211)\n" +
                        "\tat org.junit.platform.launcher.core.DefaultLauncher.withInterceptedStreams(DefaultLauncher.java:226)\n" +
                        "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:199)\n" +
                        "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:132)\n" +
                        "\tat com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:69)\n" +
                        "\tat com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)\n" +
                        "\tat com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)\n" +
                        "\tat com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)")
                .count(30)
                .build();
    }

}
