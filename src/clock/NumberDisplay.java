package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

class NumberDisplay {
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int segWidth; // セグメントの幅
	private final int horizonLineLength; // 水平に引く線の長さ
	private final int perpendicularLineLength; // 垂直に引く線の長さ

	private final Color onColor; // 点灯を示すカラー
	private final Color offColor; // 消灯を示すカラー

	public NumberDisplay(int x, int y, int width, int height, Color onColor,
			Color offColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.segWidth = (width + height) / 20;
		this.horizonLineLength = (width - segWidth * 3);
		this.perpendicularLineLength = (height - segWidth * 2) / 2;
		this.onColor = onColor;
		this.offColor = offColor;
	}

	public void draw(Graphics g, int number) {
		assert (0 < number && number <= 9);

		// セグメントを染める
		drawSegments(g, new SevenSegment(number));
	}

	private void drawSegments(Graphics g, final SevenSegment seg) {
		final List<SevenSegmentPart> parts = Arrays.asList(seg.getParts());
		final Color preColor = g.getColor(); // 終了時に元のカラーに戻すため保持

		// 全てのセグメントパーツごとに
		for (SevenSegmentPart s : SevenSegmentPart.values()) {
			// 点灯すべきセグメントのパーツか
			if (parts.contains(s)) {
				g.setColor(onColor); // 点灯カラーに
			} else {
				g.setColor(offColor); // 消灯カラーに
			}

			// 各セグメントに合わせて染める
			switch (s) {
			case CENTER_UPPER:
				fillHRect(g, x, y);
				break;
			case LEFT_UPPER:
				fillPRect(g, x, y);
				break;
			case RIGHT_UPPER:
				fillPRect(g, x + width - segWidth * 2, y);
				break;
			case CENTER:
				fillHRect(g, x, y + perpendicularLineLength);
				break;
			case LEFT_UNDER:
				fillPRect(g, x, y + perpendicularLineLength);
				break;
			case RIGHT_UNDER:
				fillPRect(g, x + width - segWidth * 2, y
						+ perpendicularLineLength);
				break;
			case CENTER_UNDER:
				fillHRect(g, x, y + height - segWidth);
				break;
			default:

			}
		}

		g.setColor(preColor); // 元のカラーに戻す
	}

	// 水平方向の四角形を染める
	private void fillHRect(Graphics g, int x, int y) {
		g.fillRect(x + segWidth, y, horizonLineLength, segWidth);
	}

	// 垂直方向の四角形を染める
	private void fillPRect(Graphics g, int x, int y) {
		g.fillRect(x, y + segWidth, segWidth, perpendicularLineLength);
	}

}
