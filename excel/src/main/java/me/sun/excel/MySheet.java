package me.sun.excel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MySheet {

    private int rowNumber;
    private Sheet sheet;

    public static MySheet init(Sheet sheet) {
        MySheet mySheet = new MySheet();
        mySheet.rowNumber = 0;
        mySheet.sheet = sheet;
        return mySheet;
    }

    public Row getRow() {
        Row row = sheet.getRow(rowNumber);
        if (Objects.isNull(row)) {
            row = sheet.createRow(rowNumber);
        }
        plusRowNumber();
        return row;
    }

    public void plusRowNumber() {
        this.rowNumber++;
    }
}
