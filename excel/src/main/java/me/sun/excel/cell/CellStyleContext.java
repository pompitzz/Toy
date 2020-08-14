package me.sun.excel.cell;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellStyleContext {
    private List<Consumer<CellStyle>> consumers;

    public static CellStyleContext init() {
        CellStyleContext cellStyleContext = new CellStyleContext();
        cellStyleContext.consumers = new ArrayList<>();
        return cellStyleContext;
    }

    public CellStyleContext addStyle(Consumer<CellStyle> consumer) {
        consumers.add(consumer);
        return this;
    }

    public CellStyleContext addStyle(Font font, Consumer<Font> consumer) {
        consumer.accept(font);
        return addStyle(font);
    }

    public CellStyleContext addStyle(Font font) {
        Consumer<CellStyle> cellStyleConsumer = cellStyle -> cellStyle.setFont(font);
        return addStyle(cellStyleConsumer);
    }

    public CellStyleContext addStyles(List<Consumer<CellStyle>> consumers) {
        this.consumers.addAll(consumers);
        return this;
    }

    public CellStyle apply(CellStyle targetStyle) {
        return apply(targetStyle, null);
    }

    public CellStyle apply(CellStyle targetStyle, CellStyle baseStyle) {
        if (Objects.nonNull(baseStyle)) {
            targetStyle.cloneStyleFrom(baseStyle);
        }
        consumers.forEach(consumer -> consumer.accept(targetStyle));
        return targetStyle;
    }
}
