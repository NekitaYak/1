package org.example;

import java.awt.Desktop;

public class ReservedCommandChecker {

   // private static final Desktop desktop = Desktop.getDesktop();
    private static RequestAction requestVerification(String request) {
        request = request.toUpperCase();

        try {
            for(var requestAction : RequestAction.values()) {
                if(request.contains(requestAction.getValue())) {
                    return requestAction;
                }
            }

        } catch (NullPointerException e) {
            return RequestAction.UNDEFINED;
        }

        return RequestAction.UNDEFINED;
    }

    public static RequestAction actionOnRequest(String request) {
        switch (requestVerification(request)) {
            case OPEN_CHROME -> { return openChrome(); }
            case OPEN_CMD -> { return openCMD(); }
            case OPEN_NOTEPAD -> { return openNotepad(); }
            case VERTEX_AI -> { return RequestAction.VERTEX_AI; }
            default -> { return RequestAction.UNDEFINED; }
        }
    }

    private static RequestAction openChrome() {
        try {
            Runtime.getRuntime().exec("cmd /c start chrome"); // Для Windows
            return RequestAction.OPEN_CHROME;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RequestAction openNotepad() {
        try {
            //desktop.open(new java.io.File("notepad.exe"));
            Runtime.getRuntime().exec("notepad");
            return RequestAction.OPEN_NOTEPAD;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RequestAction openCMD() {
        try {
            //desktop.open(new java.io.File("cmd.exe"));
            Runtime.getRuntime().exec("cmd /c start cmd");
            return RequestAction.OPEN_CMD;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
