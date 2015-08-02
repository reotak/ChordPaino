package chordSpeaker;

/** 特定の音 */
public enum Key {
	C {
		@Override
		public int keyIndex() {
			return 60;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	CS {
		@Override
		public int keyIndex() {
			return 61;
		}

		@Override
		public boolean isBlackKey() {
			return true;
		}
	},
	D {
		@Override
		public int keyIndex() {
			return 62;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	DS {
		@Override
		public int keyIndex() {
			return 63;
		}

		@Override
		public boolean isBlackKey() {
			return true;
		}
	},
	E {
		@Override
		public int keyIndex() {
			return 64;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	F {
		@Override
		public int keyIndex() {
			return 65;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	FS {
		@Override
		public int keyIndex() {
			return 66;
		}

		@Override
		public boolean isBlackKey() {
			return true;
		}
	},
	G {
		@Override
		public int keyIndex() {
			return 67;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	GS {
		@Override
		public int keyIndex() {
			return 68;
		}

		@Override
		public boolean isBlackKey() {
			return true;
		}
	},
	A {
		@Override
		public int keyIndex() {
			return 69;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},
	AS {
		@Override
		public int keyIndex() {
			return 70;
		}

		@Override
		public boolean isBlackKey() {
			return true;
		}
	},
	B {
		@Override
		public int keyIndex() {
			return 71;
		}

		@Override
		public boolean isBlackKey() {
			return false;
		}
	},

	;

	/**
	 * このキーのデフォルトのインデックスを取得します
	 *
	 * @return このキーのデフォルトのインデックス
	 */
	public abstract int keyIndex();

	/**
	 * このキーが黒鍵のキーかどうかを返します
	 *
	 * @return 黒鍵ならばtrue
	 */
	public abstract boolean isBlackKey();

	/**
	 * このキーのオクターブを変更した番号を取得します
	 *
	 * @param oct
	 *            変化させたいオクターブ数。ex)1：1オクターブ上のキー、-2: 2オクターブ下のキー
	 * @return インデックス
	 */
	public int keyIndex(int oct) {
		int index = this.keyIndex() + oct * 12;

		if (index < 0) {
			throw new IllegalArgumentException("octが小さすぎます");
		}
		if (index > 128) {
			throw new IllegalArgumentException("octが大きすぎます");
		}

		return index;
	};

	/**
	 * キーのインデックスからキーを取得します
	 *
	 * @param index
	 *            何らかのキーを示すインデックス
	 * @return キー
	 */
	static public Key toKey(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("indexが小さすぎます");
		}

		// indexを正規化
		while (index < 60) {
			index += 12;
		}

		while (index > 71) {
			index -= 12;
		}

		for (Key k : Key.values()) {
			if (index == k.keyIndex()) {
				return k;
			}
		}

		throw new AssertionError("indexの正規化に失敗しました");
	}

}
