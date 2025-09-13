import javax.sound.sampled.*;

public class CustomBeep {
    // Actually, this is a Tone Generator of sorts.
    public static void main(String[] args) throws LineUnavailableException {
        int durationInMilliseconds = 1000; // 1 second
        int frequencyHz = 440; // Frequency: 440 Hz (A4 note)
        int sampleRate = 44100; // Standard sampling rate

        byte[] buf = new byte[1];
        AudioFormat audioFormat = new AudioFormat(sampleRate, 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);

        line.open(audioFormat);
        line.start();

        for (int i=0; i < durationInMilliseconds * sampleRate / 1000; i++) {
            double angle = i / (sampleRate / (double) frequencyHz) * 2.0 * Math.PI;
            buf[0] = (byte) (Math.sin(angle) * 127.0);
            line.write(buf, 0, 1);
        }

        // The final part is to properly close the line.
        line.drain();
        line.close();
    }
}
