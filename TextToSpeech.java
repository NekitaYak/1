package org.example;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class TextToSpeech {
    static final String audioFilePath = "src\\main\\resources\\convertedTextToSpeech.mp3";

    static final TextToSpeechClient textToSpeechClient;

    static {
        try {
            textToSpeechClient = TextToSpeechClient.create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void convertText(String text) throws Exception {

            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("ru-RU")
                            .setSsmlGender(SsmlVoiceGender.FEMALE)
                            .build();

            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            ByteString audioContents = response.getAudioContent();

            try (OutputStream out = new FileOutputStream( audioFilePath)) {
                out.write(audioContents.toByteArray());
            }

    }
}
