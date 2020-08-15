package me.sun.excel;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {
    public static void save(String path, Workbook workbook) {
        new File(path).delete();
        try (FileOutputStream stream = new FileOutputStream(path)) {
            workbook.write(stream);
        } catch (IOException e) {
            System.out.println(String.format("ERROR -> %s", path));
            e.printStackTrace();
        }
    }

    public static short toShort(int size) {
        return (short) (size * 20);
    }
}
