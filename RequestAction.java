package org.example;

public enum RequestAction {
    VERTEX_AI("ЗАПРОС К НЕЙРОСЕТИ"),
    OPEN_CHROME("ОТКРОЙ ХРОМ"),
    OPEN_NOTEPAD("ОТКРОЙ БЛОКНОТ"),
    OPEN_CMD("ОТКРОЙ КОМАНДНУЮ СТРОКУ"),
    UNDEFINED(null);

    private final String value;

    RequestAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
