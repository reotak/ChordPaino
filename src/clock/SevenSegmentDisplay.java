package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Calendar;

public class SevenSegmentDisplay {
	private NumberDisplay hourTen, hourOne, minTen, minOne, secTen, secOne;
	private int y;
	private int height; // 表示の縦幅
	private final Color onColor; // 点灯時の色

	private int dot1x; // 時と分の間のドットのx座標
	private int dot2x; // 分と秒の間のドットのx座標
	private int dotLength; // ドット表示に使う長さ

	SevenSegmentDisplay(int x, int y, int width, int height, Color onColor,
			Color offColor) {
		this.y = y;
		this.height = height;
		this.dotLength = width / 10;
		this.onColor = onColor;

		int w = (width - dotLength * 2) / 6; // NumberDisplayの幅

		// 時のディスプレイを生成
		hourTen = new NumberDisplay(x, y, w, height, onColor, offColor);
		x += w;
		hourOne = new NumberDisplay(x, y, w, height, onColor, offColor);
		x += w;

		// dot1を設定
		dot1x = x;
		x += dotLength;

		// 分のディスプレイを設定
		minTen = new NumberDisplay(x, y, w, height, onColor, offColor);
		x += w;
		minOne = new NumberDisplay(x, y, w, height, onColor, offColor);
		x += w;

		// dot2を設定
		dot2x = x;
		x += dotLength;

		// 秒のディスプレイを設定
		secTen = new NumberDisplay(x, y, w, height, onColor, offColor);
		x += w;
		secOne = new NumberDisplay(x, y, w, height, onColor, offColor);
	}

	// 時刻を受け取り時計を描写する
	public void draw(Graphics g, Calendar cal) {
		// 各時刻ごとに分解して、転送する
		draw(g, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
				cal.get(Calendar.SECOND));
	}

	// 時刻を受け取り時計を描写する
	public void draw(Graphics g, int hour, int minute, int second) {
		assert (0 <= hour && hour <= 60 && 0 <= minute && minute <= 60
				&& 0 <= second && second <= 60);

		// 各ディスプレイに値を与えて再描写
		hourTen.draw(g, hour / 10);
		hourOne.draw(g, hour % 10);
		minTen.draw(g, minute / 10);
		minOne.draw(g, minute % 10);
		secTen.draw(g, second / 10);
		secOne.draw(g, second % 10); // ドットも再描写する
		drawDot(g);
	}

	// ドットを描写する
	private void drawDot(Graphics g) {
		int dotH = height / 5; // ドットの縦幅
		int hSpace = (height - dotH * 2) / 3; // 縦の空白長さ

		Color c = g.getColor(); // 色を保持しておく

		g.setColor(onColor);
		// dot1 を染める
		g.fillRect(dot1x + dotLength / 3, y + hSpace, dotLength / 3, dotH);
		g.fillRect(dot1x + dotLength / 3, y + hSpace * 2 + dotH, dotLength / 3,
				dotH);

		// dot1 を染める
		g.fillRect(dot2x + dotLength / 3, y + hSpace, dotLength / 3, dotH);
		g.fillRect(dot2x + dotLength / 3, y + hSpace * 2 + dotH,
				+dotLength / 3, dotH);

		g.setColor(c); // 色を戻す
	}

}
