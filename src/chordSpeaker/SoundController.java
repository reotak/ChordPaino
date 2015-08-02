package chordSpeaker;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

public class SoundController implements Runnable {
	private Speaker speaker;
	// 現在鳴らしている音のリスト
	private List<Integer> keys = new ArrayList<Integer>();
	// 半音区切りの各音のバッファ（69が基準のA）
	private final SoundBuffer[] buffers = new SoundBuffer[128];

	// バッファのサイズ
	private final static int BUFF_SIZE = 20;
	// 基準としている音（69=A）
	private final static int BASE_NOTE = 69;
	// 音量
	private final static byte VELOCITY = 7;

	private boolean changed = false;
	private Object changedMutex = new Object();

	public SoundController() throws LineUnavailableException {
		speaker = Speaker.newSpeaker();
		calcNotes();
		speaker.powerOn();
	}

	public void run() {
		for (;;) {
			sound();
		}
	}

	private synchronized void calcNotes() {
		// 半音の係数(2の12乗根)を計算
		double r = Math.pow(2.0, 1.0 / 12.0);
		double sampleRate = Speaker.SAMPLE_RATE;

		// BASE_NOTE を計算
		buffers[BASE_NOTE] = new SoundBuffer(440.0, VELOCITY, sampleRate); // A
																			// のノート

		// BASE_NOTE より上の音を計算
		for (int i = BASE_NOTE + 1; i < buffers.length; i++) {
			buffers[i] = new SoundBuffer(buffers[i - 1].getFrequency() * r,
					VELOCITY, sampleRate);
		}
		// BASE_NOTE より下の音を計算
		for (int i = BASE_NOTE - 1; i >= 0; i--) {
			buffers[i] = new SoundBuffer(buffers[i + 1].getFrequency() / r,
					VELOCITY, sampleRate);
		}
	}

	private void sound() {
		byte[] buff = calcBuffer(BUFF_SIZE);

		if (buff == null) {
			speaker.drain();
			return;
		}
		synchronized (changedMutex) {
			if (changed) {
				changed = false;
				speaker.drain();
			}
		}
		speaker.sound(buff, buff.length);
	}

	/**
	 * サウンド機能を全てOFFにします
	 */
	public synchronized void off() {
		speaker.powerOff();
	}

	/**
	 * キーをトニックとしてコードを登録します。setKey(k, 0, c)と同じです
	 *
	 * @param k
	 *            トニックとなるキー
	 * @param c
	 *            コード
	 */
	public synchronized void setKey(Key k, Chord c) {
		setKey(k, 0, c);
	}

	/**
	 * キーをトニックとしてコードを登録します。オクターブを変更することが可能です
	 *
	 * @param k
	 *            トニックとなるキー
	 * @param oct
	 *            オクターブ
	 * @param c
	 *            コード
	 */
	public synchronized void setKey(Key k, int oct, Chord c) {
		replaceKeys(c.createIndexList(k, oct));
	}

	/**
	 * キーのインデックスで表現されているキーのリストを登録します
	 *
	 * @param keys
	 *            登録したいキーのリスト
	 */
	public synchronized void setKey(List<Integer> keys) {
		replaceKeys(keys);
	}

	/**
	 * 現在登録されているキーのリストを取得します
	 *
	 * @return 現在登録されているキーのリスト
	 */
	public synchronized List<Key> getKeys() {
		List<Key> result = new ArrayList<Key>();
		for (Integer k : keys) {
			result.add(Key.toKey(k));
		}

		return result;
	}

	/**
	 * 音を停止します
	 */
	public synchronized void reset() {
		keys = new ArrayList<Integer>();
		for (SoundBuffer b : buffers) {
			b.reset();
		}
	}

	private void replaceKeys(List<Integer> indexes) {
		boolean isChange = indexes.size() != keys.size();

		// 事前に入っていたキー以外のキーをリセットする
		for (Integer k : indexes) {
			if (keys.contains(k)) { // 継続して含まれる要素は何もしない
				continue;
			} else {
				isChange = true;
				buffers[k].reset();
			}
		}

		synchronized (changedMutex) {
			if (isChange) {
				changed = true;

				// 変更があった場合、強引に再生を止め次の音の再生を開始する
				speaker.reflesh();
				sound();
			}
		}

		keys = indexes;
	}

	private synchronized byte[] calcBuffer(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length が0以下");
		}

		byte[] buff = null;

		boolean isFirst = true;
		for (Integer k : keys) {
			if (isFirst) {
				buff = buffers[k].getBuffer(length);
				isFirst = false;
			} else {
				buffers[k].addBuffer(buff);
			}
		}

		return buff;
	}
}
