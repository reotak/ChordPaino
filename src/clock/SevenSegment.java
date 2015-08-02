package clock;

import static clock.SevenSegmentPart.*;

class SevenSegment {
	private SevenSegmentPart[] parts; // このセグメントの数字が示す、点灯すべきセグメントの部品のパーツ

	public SevenSegment(SevenSegmentPart... parts) {
		this.parts = parts;
	}

	public SevenSegment(int number) {
		// 各数字（0～9）ごとに、点灯すべきセグメントの色を設定する
		switch (number) {
		case 0:
			setParts(CENTER_UPPER, LEFT_UPPER, RIGHT_UPPER, LEFT_UNDER,
					RIGHT_UNDER, CENTER_UNDER);
			break;
		case 1:
			setParts(RIGHT_UPPER, RIGHT_UNDER);
			break;
		case 2:
			setParts(CENTER_UPPER, RIGHT_UPPER, CENTER, LEFT_UNDER,
					CENTER_UNDER);
			break;
		case 3:
			setParts(CENTER_UPPER, RIGHT_UPPER, CENTER, RIGHT_UNDER,
					CENTER_UNDER);
			break;
		case 4:
			setParts(LEFT_UPPER, RIGHT_UPPER, CENTER, RIGHT_UNDER);
			break;
		case 5:
			setParts(CENTER_UPPER, LEFT_UPPER, CENTER, RIGHT_UNDER,
					CENTER_UNDER);
			break;
		case 6:
			setParts(LEFT_UPPER, CENTER, LEFT_UNDER, RIGHT_UNDER, CENTER_UNDER);
			break;
		case 7:
			setParts(CENTER_UPPER, RIGHT_UPPER, RIGHT_UNDER);
			break;
		case 8:
			setParts(CENTER_UPPER, LEFT_UPPER, RIGHT_UPPER, CENTER, LEFT_UNDER,
					RIGHT_UNDER, CENTER_UNDER);
			break;
		case 9:
			setParts(CENTER_UPPER, LEFT_UPPER, RIGHT_UPPER, CENTER,
					RIGHT_UNDER, CENTER_UNDER);
			break;
		default:
			assert (false);
		}
	}

	private final void setParts(SevenSegmentPart... parts) {
		this.parts = parts;
	}

	public SevenSegmentPart[] getParts() {
		return parts;
	}
}
