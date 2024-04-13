package org.example;

public class TextHandler {
    static public String handling(String text) {
        return text.replaceAll("[#*]", "").
                replaceAll("\\. ", ".\n");
    }
}
