package gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chordSpeaker.Chord;
import chordSpeaker.Key;

public class KeyManager {
	private final Map<Integer, Key> kenbanMap = new HashMap<>();
	private final Map<Integer, Chord> chordMap = new HashMap<>();
	private final Map<Integer, Integer> octaveMap = new HashMap<>();
	private final List<Key> kenbanKeys = new ArrayList<>();
	private final List<Chord> chordKeys = new ArrayList<>();
	private final List<Integer> octaveKeys = new ArrayList<>();

	public KeyManager() {
		keySetting();
	}

	private void keySetting() {
		kenbanMap.put(KeyEvent.VK_H, Key.C);
		kenbanMap.put(KeyEvent.VK_U, Key.CS);
		kenbanMap.put(KeyEvent.VK_J, Key.D);
		kenbanMap.put(KeyEvent.VK_I, Key.DS);
		kenbanMap.put(KeyEvent.VK_K, Key.E);
		kenbanMap.put(KeyEvent.VK_L, Key.F);
		kenbanMap.put(KeyEvent.VK_P, Key.FS);
		kenbanMap.put(KeyEvent.VK_SEMICOLON, Key.G);
		kenbanMap.put(KeyEvent.VK_AT, Key.GS);
		kenbanMap.put(KeyEvent.VK_COLON, Key.A);
		kenbanMap.put(KeyEvent.VK_OPEN_BRACKET, Key.AS);
		kenbanMap.put(KeyEvent.VK_CLOSE_BRACKET, Key.B);

		chordMap.put(KeyEvent.VK_Q, Chord.MAJOR);
		chordMap.put(KeyEvent.VK_A, Chord.MINOR);
		chordMap.put(KeyEvent.VK_W, Chord.SEVENTH);
		chordMap.put(KeyEvent.VK_S, Chord.MINOR_SEVENTH);
		chordMap.put(KeyEvent.VK_E, Chord.MINOR_SEVENTH_MINUS_FIFTH);
		chordMap.put(KeyEvent.VK_D, Chord.MAJOR_SEVENTH);
		chordMap.put(KeyEvent.VK_R, Chord.SEVENTH_ADD_FIFTH);
		chordMap.put(KeyEvent.VK_F, Chord.MINOR_MAJOR_SEVENTH);
		chordMap.put(KeyEvent.VK_1, Chord.SIXTH);
		chordMap.put(KeyEvent.VK_2, Chord.NINETH);
		chordMap.put(KeyEvent.VK_3, Chord.SIXTH_ADD_NINETH);
		chordMap.put(KeyEvent.VK_4, Chord.SUS_FOURTH);

		octaveMap.put(KeyEvent.VK_CONTROL, Integer.valueOf(1));
		octaveMap.put(KeyEvent.VK_SHIFT, Integer.valueOf(-1));
	}

	/**
	 * 特定のコードが押されたことを通知します
	 *
	 * @param code
	 *            KeyEventのコード
	 */
	public synchronized void setKey(int code) {
		System.out.println(code + " --> " + octaveKeys);
		Key key = kenbanMap.get(code);
		if (key != null) {
			if (!kenbanKeys.contains(key)) {
				kenbanKeys.add(key);
			}
		}

		Chord chord = chordMap.get(code);
		if (chord != null) {
			if (!chordKeys.contains(chord)) {
				chordKeys.add(chord);
			}
		}

		Integer octave = octaveMap.get(code);
		if (octave != null) {
			if (!octaveKeys.contains(octave)) {
				octaveKeys.add(octave);
			}
		}
	}

	/**
	 * 特定のコードが離されたことを通知します
	 *
	 * @param code
	 *            KeyEventのコード
	 */
	public synchronized void removeKey(int code) {
		System.out.println(code + " <-- " + octaveKeys);
		Key key = kenbanMap.get(code);
		if (key != null) {
			kenbanKeys.remove(key);
		}

		Chord chord = chordMap.get(code);
		if (chord != null) {
			chordKeys.remove(chord);
		}

		Integer octave = octaveMap.get(code);
		if (octave != null) {
			octaveKeys.remove(octave);
		}
	}

	/**
	 * 現在登録されている音のインデックスリストを返します。登録されているものがない場合、空のリストを返します
	 *
	 * @return 音のインデックスリスト
	 */
	public synchronized List<Integer> getKeys() {
		System.out.println(kenbanKeys);
		if (kenbanKeys.size() == 0) {
			return new ArrayList<Integer>();
		}

		Chord chord = Chord.SINGLE; // Default Chord
		if (chordKeys.size() != 0) {
			chord = chordKeys.get(0);
		}

		int octave = 0; // Default Octave
		if (octaveKeys.size() != 0) {
			octave = octaveKeys.get(0);
		}

		return chord.createIndexList(kenbanKeys.get(0), octave);
	}
}
