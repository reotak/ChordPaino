package gui;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import chordSpeaker.Key;

public class Piano extends JPanel {
	private final int width, height;
	private final KeyListener keyListener;
	private final Map<Key, JButton> keyButtonMap = new HashMap<>();

	public Piano(int width, int height, KeyListener keyListener) {
		this(0, 0, width, height, keyListener);
	}

	public Piano(int x, int y, int width, int height, KeyListener keyListener) {
		this.width = width;
		this.height = height;
		this.keyListener = keyListener;

		// キーボード入力を受けつrけるようにする
		this.addKeyListener(keyListener);

		// 位置の指定
		this.setBounds(x, y, width, height);

		// レイアウトマネージャは利用しない
		this.setLayout(null);

		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(x, y, width, height);
		setWhiteKey(pane);
		setBlackKey(pane);

		this.add(pane);
	}

	private void setWhiteKey(JLayeredPane pane) {
		int layerLevel = 10;

		Key[] whiteKeys = { Key.C, Key.D, Key.E, Key.F, Key.G, Key.A, Key.B };

		for (int i = 0; i < 7; i++) {
			JButton key = new JButton();
			key.setBounds(i * (width / 7), 0, width / 7, height);
			key.setBackground(Color.WHITE);
			key.addKeyListener(keyListener);

			pane.setLayer(key, layerLevel);
			pane.add(key);
			keyButtonMap.put(whiteKeys[i], key);
		}
	}

	private void setBlackKey(JLayeredPane pane) {
		int layerLevel = 20;

		int blackKeyWidth = width / 10; // 黒鍵の幅
		int blackKeyHeight = 2 * height / 3; // 黒鍵の高さ

		Key[] blackKeys1 = { Key.CS, Key.DS };
		for (int i = 0; i < 2; i++) {
			JButton key = new JButton();

			int dKeyStartW = width / 7; // Dキーの左端の位置
			key.setBounds(dKeyStartW + (i * width / 7) - (blackKeyWidth / 2),
					0, blackKeyWidth, blackKeyHeight);
			key.setBackground(Color.BLACK);
			key.addKeyListener(keyListener);

			pane.setLayer(key, layerLevel);
			pane.add(key);
			keyButtonMap.put(blackKeys1[i], key);
		}

		Key[] blackKeys2 = { Key.FS, Key.GS, Key.AS };
		for (int i = 0; i < 3; i++) {
			JButton key = new JButton();

			int gKeyStartW = (width / 7) * 4; // Dキーの左端の位置
			key.setBounds(gKeyStartW + (i * width / 7) - (blackKeyWidth / 2),
					0, blackKeyWidth, blackKeyHeight);
			key.setBackground(Color.BLACK);
			key.addKeyListener(keyListener);

			pane.setLayer(key, layerLevel);
			pane.add(key);
			keyButtonMap.put(blackKeys2[i], key);
		}
	}

	public void paintKeys(List<Key> keys) {
		for (Map.Entry<Key, JButton> key : keyButtonMap.entrySet()) {
			if (keys.contains(key.getKey())) {
				key.getValue().setBackground(Color.CYAN);
			} else {
				if (key.getKey().isBlackKey()) {
					key.getValue().setBackground(Color.BLACK);
				} else {
					key.getValue().setBackground(Color.WHITE);
				}
			}
		}
	}
}
