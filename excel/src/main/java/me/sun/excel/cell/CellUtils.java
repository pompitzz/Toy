package me.sun.excel.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Collection;
import java.util.Map;

public class CellUtils {
    public static void applyStyle(Map<Integer, Cell> cells, CellStyleContext cellStyleContext) {
        applyStyle(cells.values(), cellStyleContext);
    }

    public static void applyStyle(Collection<Cell> cells, CellStyleContext cellStyleContext) {
        cells.forEach(cell -> {
            CellStyle currentStyle = cell.getCellStyle();
            cell.setCellStyle(cellStyleContext.apply(currentStyle));
        });
    }
}
