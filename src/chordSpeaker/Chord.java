package chordSpeaker;

import java.util.ArrayList;
import java.util.List;

/** コード */
public enum Chord {
	/** 単音 */
	SINGLE {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);

			return list;
		}
	},
	/** M */
	MAJOR {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 7);

			return list;
		}
	},
	/** m */
	MINOR {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 3);
			list.add(k + 7);

			return list;
		}
	},
	/** 7 */
	SEVENTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k - 2);

			return list;
		}
	},
	/** M7 */
	MAJOR_SEVENTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k - 1);

			return list;
		}
	},
	/** m7 */
	MINOR_SEVENTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 3);
			list.add(k + 7);
			list.add(k - 2);

			return list;
		}
	},
	/** mM7 */
	MINOR_MAJOR_SEVENTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 3);
			list.add(k + 7);
			list.add(k - 1);

			return list;
		}
	},
	SUS_FOURTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 5);
			list.add(k + 7);

			return list;
		}
	},
	SEVENTH_SUS_FOURTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 5);
			list.add(k + 7);
			list.add(k - 2);

			return list;
		}
	},
	AUG {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k + 8);

			return list;
		}
	},
	SIXTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k + 9);

			return list;
		}
	},
	SEVENTH_ADD_FIFTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 4);
			list.add(k + 8);
			list.add(k - 2);

			return list;
		}
	},
	DIM {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 3);
			list.add(k + 5);
			list.add(k - 3);

			return list;
		}
	},
	MINOR_SEVENTH_MINUS_FIFTH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 3);
			list.add(k + 5);
			list.add(k - 2);

			return list;
		}
	},
	NINETH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 2);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k - 2);

			return list;
		}
	},
	ADD_NINETH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 2);
			list.add(k + 4);
			list.add(k + 7);

			return list;
		}
	},
	SIXTH_ADD_NINETH {
		@Override
		public List<Integer> createList(Integer k) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(k);
			list.add(k + 2);
			list.add(k + 4);
			list.add(k + 7);
			list.add(k - 3);

			return list;
		}
	},

	;

	public abstract List<Integer> createList(Integer k);

	public List<Integer> createIndexList(Key k) {
		return createIndexList(k, 0);
	}

	public List<Integer> createIndexList(Key k, int oct) {
		return createList(k.keyIndex(oct));
	};
}
