package com.accenture.logreader;

import com.accenture.logreader.console.ConsoleApplication;

public class Main {

    public static void main(String[] args) {
        try {
            if (args == null || args.length <= 0) {
                runWithGui();
            } else {
                runInConsole(args);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void runInConsole(String[] args) {
        new ConsoleApplication(args).start();
    }

    private static void runWithGui() {
        throw new UnsupportedOperationException("GUI not yet implemented.");
    }
}
