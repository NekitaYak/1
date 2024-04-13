package org.example;

import javax.sound.sampled.*;

public class MicrophoneRecorder {

    static final String audioFilePath = "src\\main\\resources\\record_out.wav";
    private volatile boolean isRecording = false;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    Thread recordingThread;
    public void startRecording() {
        isRecording = true;

        try {
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            recordingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);
                    try {
                        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new java.io.File(audioFilePath));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            recordingThread.start();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    public void stopRecording() {
        isRecording = false;
        if (targetDataLine != null) {
            targetDataLine.stop();
            targetDataLine.close();
            recordingThread.interrupt();
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
}
