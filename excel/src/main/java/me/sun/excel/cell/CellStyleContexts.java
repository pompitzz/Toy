package me.sun.excel.cell;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellStyleContexts {
    public static Consumer<CellStyle> CENTER = cellStyle -> cellStyle.setAlignment(HorizontalAlignment.CENTER);
    public static Consumer<CellStyle> VERTICAL_CENTER = cellStyle -> cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    public static List<Consumer<CellStyle>> ALL_CENTER = Arrays.asList(CENTER, VERTICAL_CENTER);

    public static Consumer<Font> FONT_BOLD = font -> font.setBold(true);
}
