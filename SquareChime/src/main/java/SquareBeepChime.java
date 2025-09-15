/*
* Java - Code sample for the airplane seatbelt chime (Simple version).
* That familiar chime when the seatbelt sign turns on/off is usually a two-tone
* descending interval (like “ding-dong” or a major third/minor third drop depending
* on the airline). It’s not standardized worldwide, but a very common one is
* something like:
*
* First tone: 1000 Hz for ~300 ms
* Small pause (~50 ms)
* Second tone: 800 Hz for ~400 ms
* */
import javax.sound.sampled.*;

public class SquareBeepChime {
    private static final int SAMPLE_RATE = 44100;

    public static void main(String[] args) throws Exception {
        playTone(1000, 300);  // First tone
        Thread.sleep(50);
        playTone(800, 400);   // Second tone
    }

    public static void playTone(int frequencyHz, int durationMilliseconds) throws LineUnavailableException {
        byte[] buf = new byte[1];
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);

        try (SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat)) {
            line.open(audioFormat);
            line.start();

            for(int i=0; i < durationMilliseconds * SAMPLE_RATE / 1000; i++) {
                double angle = i / (SAMPLE_RATE / (double) frequencyHz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0);
                line.write(buf, 0, 1);
            }
            line.drain();
        }
    }
}
