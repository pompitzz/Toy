package me.sun.write;

import me.sun.excel.ExcelUtils;
import me.sun.excel.RowWrapper;
import me.sun.excel.SheetWrapper;
import me.sun.excel.cell.CellStyleContext;
import me.sun.excel.cell.CellStyleContexts;
import me.sun.excel.cell.CellUtils;
import me.sun.write.model.LoggingModel;
import me.sun.write.model.ExcelSheetDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class ExcelWriteService {
    public void writeExcel(String path, List<ExcelSheetDto> excelSheetDtos) {
        Workbook workbook = new HSSFWorkbook();
        excelSheetDtos.forEach(excelSheetDto -> {
            Sheet sheet = workbook.createSheet(excelSheetDto.getSheetName());
            writeSheet(SheetWrapper.init(sheet), excelSheetDto.getLoggingModels());
        });
        ExcelUtils.save(path, workbook);
    }

    private void writeSheet(SheetWrapper sheetWrapper, List<LoggingModel> loggingModels) {
        loggingModels.forEach(dto -> writeRow(sheetWrapper.getRow(), dto));
    }

    private void writeRow(RowWrapper rowWrapper, LoggingModel loggingModel) {
        preProcess(rowWrapper);

        rowWrapper.writeCell(loggingModel.getCause())
                .writeCell(loggingModel.getKey())
                .writeCell(loggingModel.getDetailedException())
                .writeCell(loggingModel.getCount());

        postProcess(rowWrapper);
    }

    private void preProcess(RowWrapper rowWrapper) {
        rowWrapper.getRow().setHeight(ExcelUtils.toShort(40));
    }

    private void postProcess(RowWrapper rowWrapper) {
        CellStyleContext cellStyleContext = CellStyleContext.init()
                .addStyles(CellStyleContexts.ALL_CENTER);
        CellUtils.applyStyle(rowWrapper.getCellStore(), cellStyleContext);

        Sheet sheet = rowWrapper.getRow().getSheet();
        rowWrapper.getCellStore().values()
                .forEach(cell -> sheet.autoSizeColumn(cell.getColumnIndex()));
    }
}
