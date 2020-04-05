package agency.highlysuspect.unfortunately.fortune;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import agency.highlysuspect.unfortunately.fortune.fortunes.UFFortunes;

import java.util.Random;

public class FortuneRegistry {
	public static final Registry<FortuneType> FORTUNE_TYPES = new SimpleRegistry<>();
	
	public static <T extends FortuneType> T register(Identifier id, T thing) {
		return ((SimpleRegistry<FortuneType>) FORTUNE_TYPES).add(id, thing);
	}
	
	public static FortuneType get(Identifier id) {
		return FORTUNE_TYPES.getOrEmpty(id).orElse(UFFortunes.DUMMY);
	}
	
	public static FortuneType pick(Random random, FortuneQuality quality) {
		FortuneType type;
		int attempts = 0;
		
		do {
			type = FORTUNE_TYPES.getRandom(random);
		} while(type != null && !type.qualityRange.test(quality) && attempts++ < 50);
		
		if(type == null) return UFFortunes.DUMMY;
		
		return type;
	}
}
