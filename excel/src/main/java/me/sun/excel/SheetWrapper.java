package me.sun.excel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SheetWrapper {
    private int rowNumber;
    private Sheet sheet;
    @Getter
    private Map<Integer, RowWrapper> rowWrapperStore;

    public static SheetWrapper init(Sheet sheet) {
        SheetWrapper sheetWrapper = new SheetWrapper();
        sheetWrapper.rowNumber = 0;
        sheetWrapper.sheet = sheet;
        sheetWrapper.rowWrapperStore = new TreeMap<>();
        return sheetWrapper;
    }

    public RowWrapper getRow() {
        return getRow(rowNumber++);
    }

    public RowWrapper getRow(Integer rowNumber) {
        return rowWrapperStore.computeIfAbsent(rowNumber, key -> RowWrapper.init(sheet.createRow(key)));
    }
}
