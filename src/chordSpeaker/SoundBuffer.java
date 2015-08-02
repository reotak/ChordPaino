package chordSpeaker;

/**
 * ある特定の波長・音量の音のバッファを生成するためのクラス
 *
 */
public final class SoundBuffer {
	private final double frequency; // 波長(Hz)
	private final double sampleRate; // サンプルレート
	private final byte velocity; // 音の音量

	private final double amplitude;
	private int amplIndex; // amplitudeのどこまでをバッファ外部に渡したか

	public SoundBuffer(double frequency, byte velocity, double sampleRate) {
		if (frequency <= 0)
			throw new IllegalArgumentException("frequency が 0 以下");
		if (velocity <= 0)
			throw new IllegalArgumentException("velocity が 0 以下");
		if (sampleRate <= 0)
			throw new IllegalArgumentException("sampleRate が 0 以下");

		this.frequency = frequency;
		this.velocity = velocity;
		this.sampleRate = sampleRate;

		amplitude = sampleRate / frequency;
		amplIndex = 0;
	}

	/**
	 * この波長・音量を表す波形のバッファを生成
	 *
	 * @param length
	 *            波形の長さ
	 * @return 波形
	 */
	public synchronized byte[] getBuffer(int length) {
		if (length <= 0)
			throw new IllegalArgumentException("length が 0 以下");

		byte[] buff = new byte[length];

		for (int i = 0; i < length; i++) {
			buff[i] += next();
		}

		return buff;
	}

	/**
	 * この波長・音量を表す波形のバッファを生成し、buffに加えます
	 *
	 * @param buff
	 *            加える先のバッファ
	 * @return 計算後のbuff
	 */
	public synchronized byte[] addBuffer(byte[] buff) {
		if (buff == null)
			throw new IllegalArgumentException("buff が null");

		for (int i = 0; i < buff.length; i++) {
			int v = buff[i] + next();
			v = Math.min(Math.max(v, Byte.MIN_VALUE), Byte.MAX_VALUE);
			buff[i] = (byte) v;
		}

		return buff;
	}

	public String toString() {
		return String.format("f:%.3f, v:%d, s:%.3f, a:%d\n", frequency,
				velocity, sampleRate, amplitude);
	}

	public double getFrequency() {
		return frequency;
	}

	public double getAmplitude() {
		return amplitude;
	}

	public synchronized void reset() {
		amplIndex = 0;
	}

	private byte next() {
		byte result = (byte) ((double) velocity * Math
				.sin((amplIndex / amplitude) * Math.PI * 2));
		amplIndex++;
		if (amplIndex > amplitude) {
			amplIndex = 0;
		}

		return result;
	}
}
