package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.JPanel;

public class DigitalClockSegments extends JPanel {
	private SevenSegmentDisplay disp; // 時計オブジェクト
	private Calendar now; // 現在時刻

	public DigitalClockSegments(int x, int y, int width, int height,
			KeyListener keyListener) {

		// キーボード入力を受けつrけるようにする
		this.addKeyListener(keyListener);

		// 描画位置の指定
		this.setBounds(x, y, width, height);
		// this.setSize(width, height);

		// 時計に割り当てられる領域で時計を生成する
		disp = new SevenSegmentDisplay(x, y, width, height, Color.ORANGE,
				this.getBackground());
	}

	// 現在時刻を取得し時計を描画する
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setTime(); // 現在時刻を取得

		disp.draw(g, now); // 今の時間をディスプレイに描写する
	}

	// 現在時刻を設定する
	private void setTime() {
		now = Calendar.getInstance();
	}
}
