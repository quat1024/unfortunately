package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import someones.modfest.mod.unfortunately.Unfortunately;
import someones.modfest.mod.unfortunately.fortune.FortuneQuality;
import someones.modfest.mod.unfortunately.fortune.FortuneRegistry;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;
import someones.modfest.mod.unfortunately.junk.UFBlockTags;

public class UFFortunes {
	public static FortuneType DUMMY;
	
	public static FortuneType WITHER;
	public static FortuneType SLOWNESS;
	public static FortuneType HUNGER;
	
	public static FortuneType REGENERATION;
	public static FortuneType HASTE;
	public static FortuneType STRENGTH;
	
	public static FortuneType LUCKY_DIAMOND;
	
	public static void onInitialize() {
		DUMMY = reg("dummy_fortune", new DummyFortune());
		
		WITHER = reg("wither", new PotionFortune(
			QualityRange.only(FortuneQuality.TERRIBLE),
			q -> new StatusEffectInstance(StatusEffects.WITHER, 30 * 20, 1)
		));
		
		SLOWNESS = reg("slowness", new PotionFortune(
			QualityRange.inclusive(FortuneQuality.POOR, FortuneQuality.NEUTRAL),
			q -> {
				int duration = ((-q.getId()) + 1) * 60 * 20;
				int amplifier = q == FortuneQuality.NEUTRAL ? 0 : 1;
				return new StatusEffectInstance(StatusEffects.SLOWNESS, duration, amplifier);
			}
		));
		
		HUNGER = reg("hunger", new PotionFortune(
			QualityRange.inclusive(FortuneQuality.TERRIBLE, FortuneQuality.BAD),
			q -> {
				int duration = 3 * 60 * 20;
				int amplifier = -q.getId() - 1;
				return new StatusEffectInstance(StatusEffects.HUNGER, duration, amplifier);
			}
		));
		
		REGENERATION = reg("regeneration", new PotionFortune(
			QualityRange.inclusive(FortuneQuality.GOOD, FortuneQuality.AMAZING),
			q -> {
				int duration = q == FortuneQuality.AMAZING ? 3 * 60 * 20 : 2 * 60 * 20;
				int amplifier = q.getId() - 1;
				return new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier);
			}
		));
		
		HASTE = reg("haste", new PotionFortune(
			QualityRange.inclusive(FortuneQuality.NEUTRAL, FortuneQuality.AMAZING),
			q -> {
				int duration = q.getId() >= FortuneQuality.GOOD.getId() ? 3 * 60 * 20 : 2 * 60 * 20;
				int amplifier = q.getId();
				return new StatusEffectInstance(StatusEffects.HASTE, duration, amplifier);
			}
		));
		
		STRENGTH = reg("strength", new PotionFortune(
			QualityRange.inclusive(FortuneQuality.NEUTRAL, FortuneQuality.AMAZING),
			q -> {
				int duration = (q.getId() + 1) * 60 * 20;
				int amplifier = q.getId() + 1;
				return new StatusEffectInstance(StatusEffects.STRENGTH, duration, amplifier);
			}
		));
		
		LUCKY_DIAMOND = reg("lucky_diamond", new LuckyMiningFortune(
			QualityRange.inclusive(FortuneQuality.GREAT, FortuneQuality.AMAZING),
			state -> state.getBlock().matches(UFBlockTags.NATURAL_STONES),
			Blocks.DIAMOND_ORE.getDefaultState()
		));
	}
	
	private static <T extends FortuneType> T reg(String id, T thing) {
		return FortuneRegistry.register(new Identifier(Unfortunately.MODID, id), thing);
	}
}
