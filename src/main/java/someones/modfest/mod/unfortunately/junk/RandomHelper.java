package someones.modfest.mod.unfortunately.junk;

import java.util.Random;

public class RandomHelper {
	public static int randomRangeInclusive(Random random, int low, int high) {
		if(low == high) return low;
		else return random.nextInt(high - low + 1) + low;
	}
}
