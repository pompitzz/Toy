package me.sun.write.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ExcelSheetDto {
    private String sheetName;
    private List<LoggingModel> loggingModels;
}
