package org.example;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.util.Scanner;

public class PlayAudioFIle {
    static private final String filePath = "src\\main\\resources\\convertedTextToSpeech.mp3";

    private static Player player;
    private static FileInputStream fileInputStream;
    private static Thread userInputThread;

    static void play(Scanner scanner) {
        try {
            fileInputStream = new FileInputStream(filePath);
            player = new Player(fileInputStream);

            userInputThread = new Thread(() -> handleUserInput(scanner));
            userInputThread.start();

            player.play();
            player.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void handleUserInput(Scanner scanner) {
        System.out.println("-*-Для остановки воспроизведения введите 'Остановись'-*-");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Остановись")) {
                stop();
                break;
            }
        }
    }

    private static void stop() {
        try {
            player.close();
            fileInputStream.close();
            userInputThread.join();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
