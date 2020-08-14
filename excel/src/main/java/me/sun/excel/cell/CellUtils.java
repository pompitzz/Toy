package me.sun.excel.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Collection;
import java.util.Map;

public class CellUtils {
    public static void addStyle(Map<Integer, Cell> cells, CellStyleContext cellStyleContext) {
        addStyle(cells.values(), cellStyleContext);
    }

    public static void addStyle(Collection<Cell> cells, CellStyleContext cellStyleContext) {
        cells.forEach(cell -> {
            CellStyle currentStyle = cell.getCellStyle();
            cell.setCellStyle(cellStyleContext.apply(currentStyle));
        });
    }
}
