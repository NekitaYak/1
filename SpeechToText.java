package org.example;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpeechToText {
    static final String credentialsPath = "src\\main\\resources\\iconic-range-419414-54b862118e60.json";
    static final String audioFilePath = "src\\main\\resources\\record_out.wav";
    static final RecognitionConfig config =
            RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setLanguageCode("ru-RU")
                    .build();

    static final  SpeechClient speechClient;

    static {
        try {
            speechClient = SpeechClient.create(SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(
                            GoogleCredentials.fromStream(new FileInputStream(credentialsPath))))
                    .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String convertSpeech() throws Exception {
        Path path = Paths.get(audioFilePath);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
        RecognizeResponse response = speechClient.recognize(config, audio);

        String alternativeText = null;
        for (SpeechRecognitionResult result : response.getResultsList()) {
            for (SpeechRecognitionAlternative alternative : result.getAlternativesList()) {
                //System.out.println("Результат: " + alternative.getTranscript() + "\n");
                alternativeText = alternative.getTranscript();
            }
        }

        return alternativeText;
    }
}
