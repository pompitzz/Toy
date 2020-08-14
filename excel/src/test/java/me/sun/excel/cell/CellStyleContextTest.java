package me.sun.excel.cell;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CellStyleContextTest {

    Workbook workbook = new HSSFWorkbook();
    CellStyle baseStyle = workbook.createCellStyle();

    @BeforeEach
    void init() {
        baseStyle.setBorderLeft(BorderStyle.DASHED);
        baseStyle.setBorderTop(BorderStyle.DOTTED);
    }

    @Test
    void applyWithEmptyConsumersAndBaseStyle()throws Exception {
        CellStyle cellStyle = CellStyleContext.init()
                .apply(workbook.createCellStyle(), baseStyle);

        assertThat(cellStyle.getBorderLeft()).isEqualTo(BorderStyle.DASHED);
        assertThat(cellStyle.getBorderTop()).isEqualTo(BorderStyle.DOTTED);
    }

    @Test
    void applyWithConsumerAndBaseStyle()throws Exception {
        CellStyle cellStyle = CellStyleContext.init()
                .addStyle(style -> style.setBorderLeft(BorderStyle.THIN))
                .addStyle(style -> style.setBorderRight(BorderStyle.DASH_DOT_DOT))
                .apply(workbook.createCellStyle(), baseStyle);

        assertThat(cellStyle.getBorderLeft()).isEqualTo(BorderStyle.THIN);
        assertThat(cellStyle.getBorderTop()).isEqualTo(BorderStyle.DOTTED);
        assertThat(cellStyle.getBorderRight()).isEqualTo(BorderStyle.DASH_DOT_DOT);
    }

}
