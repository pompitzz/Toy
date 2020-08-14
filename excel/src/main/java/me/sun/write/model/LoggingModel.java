package me.sun.write.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoggingModel {
    private String cause;
    private String key;
    private String detailedException;
    private int count;
}
