package me.sun.excel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.sun.excel.cell.CellStyleContext;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowWrapper {
    private int cellNumber;
    @Getter
    private Row row;
    @Setter
    private CellStyle defaultStyle;
    @Getter
    private Map<Integer, Cell> cellStore;

    public static RowWrapper init(Row row) {
        RowWrapper rowWrapper = new RowWrapper();
        rowWrapper.row = row;
        rowWrapper.cellNumber = 0;
        rowWrapper.cellStore = new TreeMap<>();
        return rowWrapper;
    }

    public static RowWrapper init(Row row, CellStyle defaultStyle) {
        RowWrapper rowWrapper = init(row);
        rowWrapper.defaultStyle = defaultStyle;
        return rowWrapper;
    }

    public RowWrapper writeCell(Integer value) {
        Cell cell = getCell();
        cell.setCellValue(value);
        applyStyle(cell);
        return this;
    }

    public RowWrapper writeHyperLink(String url, String text) {
        Workbook workbook = row.getSheet().getWorkbook();
        Hyperlink hyperlink = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
        hyperlink.setAddress(url);

        Font font = workbook.createFont();
        font.setUnderline(Font.U_SINGLE);
        font.setColor(IndexedColors.BLUE.index);

        CellStyle cellStyle = CellStyleContext.init()
                .addStyle(font)
                .apply(workbook.createCellStyle(), defaultStyle);

        Cell cell = getCell();
        cell.setCellValue(text);
        cell.setHyperlink(hyperlink);
        cell.setCellStyle(cellStyle);
        return this;
    }

    public RowWrapper writeCell(String value) {
        Cell cell = getCell();
        cell.setCellValue(value);
        applyStyle(cell);
        return this;
    }

    private void applyStyle(Cell cell) {
        if (Objects.nonNull(defaultStyle)) {
            cell.setCellStyle(defaultStyle);
        }
    }

    public Cell getCell() {
        return getCellWithComputeIfAbsent(cellNumber, true);
    }

    public Cell getCell(int index) {
        return getCellWithComputeIfAbsent(index, false);
    }

    public Cell getCellWithComputeIfAbsent(int index, boolean usingField) {
        Cell cell = cellStore.computeIfAbsent(index, key -> row.createCell(key));
        if (usingField) {
            plusCellNumber();
        }
        return cell;
    }

    public void plusCellNumber() {
        this.cellNumber++;
    }
}

