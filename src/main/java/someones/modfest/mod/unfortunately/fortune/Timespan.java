package someones.modfest.mod.unfortunately.fortune;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import someones.modfest.mod.unfortunately.Unfortunately;

import java.util.Locale;
import java.util.Random;

public enum Timespan {
	IMMEDIATELY   (1        , 1200     , 3),
	SHORTLY       (24000 / 4, 24000    , 5),
	NEAR_FUTURE   (24000    , 24000 * 2, 3),
	FAR_FUTURE    (24000 * 2, 24000 * 4, 2),
	DISTANT_FUTURE(24000 * 4, 24000 * 8, 3),
	;
	
	Timespan(int ticksStart, int ticksEnd, int flavorTextCount) {
		this.ticksStart = ticksStart;
		this.ticksEnd = ticksEnd;
		this.flavorTextCount = flavorTextCount;
	}
	
	private final int ticksStart;
	private final int ticksEnd;
	private final int flavorTextCount;
	
	public int pickTick(Random random) {
		return random.nextInt(ticksEnd - ticksStart) + ticksStart;
	}
	
	public static Timespan pick(Random random) {
		if(random.nextInt(10) == 0) return IMMEDIATELY;
		if(random.nextBoolean()) return SHORTLY;
		if(random.nextBoolean()) return NEAR_FUTURE;
		if(random.nextBoolean()) return FAR_FUTURE;
		return DISTANT_FUTURE;
	}
	
	public int getId() {
		return ordinal();
	}
	
	public static Timespan byId(int id) {
		switch(id) {
			case 0: default: return IMMEDIATELY;
			case 1: return SHORTLY;
			case 2: return NEAR_FUTURE;
			case 3: return FAR_FUTURE;
			case 4: return DISTANT_FUTURE;
		}
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase(Locale.ROOT);
	}
	
	public String toTranslationKey() {
		return Unfortunately.MODID + ".timespan." + toString();
	}
	
	public Text toText() {
		return new TranslatableText(toTranslationKey());
	}
	
	public Text pickFlavorText(Random random) {
		return new TranslatableText(Unfortunately.MODID + ".flavor.timespan." + toString() + "." + random.nextInt(flavorTextCount));
	}
}
