package org.example;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ResponseHandler;

import com.google.cloud.translate.Translation;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import java.io.IOException;

public class VertexAiRequest {
    private static final String PROJECT_ID = "iconic-range-419414";
    private static final String LOCATION = "europe-central2";
    private static final VertexAI vertexAi = new VertexAI(PROJECT_ID, LOCATION);
    private static GenerativeModel model = new GenerativeModel("gemini-pro", vertexAi);
    public String request(String request) throws IOException {

        GenerateContentResponse response = model.generateContent(request + ". Напиши ответ на английском языке");
        String text = ResponseHandler.getText(response);

        return translate(text);
    }

    private static String translate(String text) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(
                text,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage("ru"));

        return translation.getTranslatedText();
    }
}
