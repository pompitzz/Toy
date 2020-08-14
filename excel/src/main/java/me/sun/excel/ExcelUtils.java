package me.sun.excel;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static void save(String path, Workbook workbook) {
        try (FileOutputStream stream = new FileOutputStream(path)) {
            workbook.write(stream);
        } catch (IOException e) {
            System.out.println(String.format("ERROR -> %s", path));
            e.printStackTrace();
        }
    }
}
