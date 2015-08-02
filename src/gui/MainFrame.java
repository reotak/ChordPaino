package gui;

import java.awt.event.KeyListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import chordSpeaker.SoundController;
import clock.DigitalClockSegments;

public class MainFrame extends JFrame implements KeyListener {
	private KeyManager keyManager = new KeyManager();
	private SoundController soundController;
	private Piano piano;
	private DigitalClockSegments clock;

	public MainFrame() throws LineUnavailableException {

		// UIManagerの設定。Macでボタンの背景色を設定できない問題に対応
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// Do nothing
		}

		soundController = new SoundController();
		Thread scThread = new Thread(soundController);
		scThread.start();

		addKeyListener(this);

		this.setSize(500, 570);

		// clock
		clock = new DigitalClockSegments(0, 0, 500, 150, this);
		this.add(clock);

		// piano
		piano = new Piano(0, 150, 500, 400, this);
		this.add(piano);

		clock.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
		this.requestFocus();
	}

	public static void main(String args[]) {
		MainFrame frame = null;
		try {
			frame = new MainFrame();
		} catch (LineUnavailableException e) {
			System.err.println("オーディオの接続に失敗しました");
			System.err.println("デフォルトオーディオの設定を確認してください");
			System.exit(1);
		}

		for (;;) {
			frame.clock.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("スリープでエラーが発生しました");
				System.exit(1);
			}
		}
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {
		// Do nothing
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		int code = e.getKeyCode();

		// 現在のキーの情報をキーマネージャに通知します
		keyManager.setKey(code);
		// 現在のキーの情報をサウンドコントローラに設定します
		soundController.setKey(keyManager.getKeys());

		// 現在のキーの情報をピアノに反映します
		piano.paintKeys(soundController.getKeys());
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		int code = e.getKeyCode();

		// 現在のキーの情報をキーマネージャに通知します
		keyManager.removeKey(code);
		// 現在のキーの情報をサウンドコントローラに設定します
		soundController.setKey(keyManager.getKeys());

		// 現在のキーの情報をピアノに反映します
		piano.paintKeys(soundController.getKeys());
	}
}
