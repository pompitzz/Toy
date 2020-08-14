package me.sun.excel;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.sun.excel.cell.CellStyleContext;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyRow {
    private int cellNumber;
    private Row row;
    private CellStyle defaultStyle;

    public static MyRow init(Row row) {
        MyRow myRow = new MyRow();
        myRow.row = row;
        myRow.cellNumber = 0;
        return myRow;
    }

    public MyRow writeCell(Long value) {
        Cell cell = getCell();
        cell.setCellValue(value);
        applyStyle(cell);
        return this;
    }

    public MyRow writeHyperLink(String url, String text) {
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

    public MyRow writeCell(String value) {
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
        return getCell(cellNumber, true);
    }

    public Cell getCell(int index) {
        return getCell(index, false);
    }

    public Cell getCell(int index, boolean usingField) {
        Cell cell = row.getCell(index);
        if (Objects.isNull(cell)) {
            cell = row.createCell(index);
        }
        if (usingField) {
            plusCellNumber();
        }
        return cell;
    }

    public void plusCellNumber() {
        this.cellNumber++;
    }
}

