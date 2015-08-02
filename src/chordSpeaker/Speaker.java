package chordSpeaker;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Speaker {
	public static final int SAMPLE_RATE = 44100; // 44.1KHz

	private static Speaker singleton = null;
	private static final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1,
			true, true);
	private static final DataLine.Info info = new DataLine.Info(
			SourceDataLine.class, af);
	private SourceDataLine line;

	private Speaker() throws LineUnavailableException {
		line = (SourceDataLine) AudioSystem.getLine(info);
	}

	public static synchronized Speaker newSpeaker()
			throws LineUnavailableException {
		if (singleton != null) {
			return singleton;
		}

		singleton = new Speaker();
		return singleton;
	}

	public synchronized void powerOn() throws LineUnavailableException {
		line.open();
		line.start();
	}

	public synchronized void sound(byte[] buffer, int len) {
		line.write(buffer, 0, len);
	}

	public synchronized void drain() {
		line.drain();
	}

	public synchronized void reflesh() {
		line.stop();
		line.flush();
		line.start();
	}

	public synchronized void powerOff() {
		line.drain();
		line.close();
	}
}
