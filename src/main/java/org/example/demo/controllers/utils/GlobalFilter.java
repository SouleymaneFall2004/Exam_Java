package org.example.demo.controllers.utils;

import lombok.Getter;
import lombok.Setter;

public class GlobalFilter {
    @Getter
    @Setter
    private static String currentComboBoxFilter = "...";
    @Getter
    @Setter
    private static String currentStringFilter = "";
}