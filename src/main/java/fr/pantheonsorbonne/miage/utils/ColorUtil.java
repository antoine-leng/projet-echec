package fr.pantheonsorbonne.miage.utils;

public class ColorUtil {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m"; 
    public static final String GRAY = "\u001B[90m"; // Code ANSI pour gris

    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
}