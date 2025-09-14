/*
*  Java code for extending the tone generator (project name: CustomBeepSound) into a melody.
*  The 'SourceDataLine' class is reused by wrapping it in a helper function that can play
*  tones of different frequency and duration in sequence.
* */
import javax.sound.sampled.*;

public class ToneGeneratorExtended {
    private static final int SAMPLE_RATE = 44100;

    public static void main(String[] args) throws Exception {
        playTone(523, 300); // The C5 key
        Thread.sleep(100);
        playTone(659, 300); // The E5 key
        Thread.sleep(100);
        playTone(784, 500); // The G5 key
    }

    public static void playTone(int frequencyHz, int durationMilliseconds) throws LineUnavailableException {
        byte[] buf = new byte[1];
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        try(SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat)) {
            line.open(audioFormat);
            line.start();

            for (int i=0; i < durationMilliseconds * SAMPLE_RATE / 1000; i++) {
                double angle = i / (SAMPLE_RATE / (double) frequencyHz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0);
                line.write(buf, 0, 1);
            }
            line.drain();
        }
    }
}
