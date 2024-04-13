package org.example;

import java.io.IOException;
import java.util.Scanner;

public class VoiceMessageProcessing {

    static private final MicrophoneRecorder recorder = new MicrophoneRecorder();

    static private final SpeechToText speechToText = new SpeechToText();
    static private final TextToSpeech textToSpeech = new TextToSpeech();
    static private final VertexAiRequest vertexAiRequest = new VertexAiRequest();

    static private final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Приложение готово к записи");
        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String action = scanner.nextLine();
                        if (action.equals("Старт")) {
                            startRecord();
                        } else if (action.equals("Стоп")) {
                            stopRecord();
                            String convertedText = gettingResult();

                            if (ReservedCommandChecker.actionOnRequest(convertedText) == RequestAction.VERTEX_AI) {
                                String answer = gettingResponse(convertedText);
                                playAnswer(answer);
                            } else if (ReservedCommandChecker.actionOnRequest(convertedText) == RequestAction.UNDEFINED) {
                                System.out.println("-*-Запрос не ясен-*-\n");
                            }

                            System.out.println("-*-Окончание ответа-*-\n");
                        }
                        else {
                            System.out.println("Неверный ввод\n");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        inputThread.start();
    }

    private static void startRecord() {
        recorder.startRecording();
        System.out.println("-*-Запись начата-*-\n");
    }

    private static void stopRecord() {
        recorder.stopRecording();
        System.out.println("-*-Запись остановлена-*-");
    }

    private static String gettingResult() throws Exception {
        System.out.println("-*-Обработка запроса-*-");
        String convertedText = speechToText.convertSpeech();
        System.out.println("Результат: " + convertedText + "\n");
        return convertedText;
    }

    private static String gettingResponse(String text) throws IOException {
        System.out.println("-*-Отправка запроса нейросети-*-");
        String answer = TextHandler.handling(vertexAiRequest.request(text));
        System.out.println("Ответ: \n" + answer);
        return answer;
    }

    private static void playAnswer(String answer) throws Exception {
        textToSpeech.convertText(answer);
        PlayAudioFIle.play(scanner);
    }
}