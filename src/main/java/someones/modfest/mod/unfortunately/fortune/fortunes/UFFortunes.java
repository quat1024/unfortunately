package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import someones.modfest.mod.unfortunately.Unfortunately;
import someones.modfest.mod.unfortunately.fortune.FortuneQuality;
import someones.modfest.mod.unfortunately.fortune.FortuneRegistry;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;
import someones.modfest.mod.unfortunately.junk.UFBlockTags;

import java.util.Random;
import java.util.function.Function;

public class UFFortunes {
	public static FortuneType<DummyFortune> DUMMY;
	
	public static FortuneType<PotionFortune> WITHER;
	public static FortuneType<PotionFortune> SLOWNESS;
	public static FortuneType<PotionFortune> HUNGER;
	
	public static FortuneType<PotionFortune> REGENERATION;
	public static FortuneType<PotionFortune> HASTE;
	public static FortuneType<PotionFortune> STRENGTH;
	
	public static FortuneType<LuckyMiningFortune> LUCKY_DIAMOND;
	public static FortuneType<LuckyMiningFortune> LUCKY_NETHER_QUARTZ;
	
	public static void onInitialize() {
		DUMMY = reg("dummy_fortune", new DummyFortune());
		
		WITHER = reg("wither", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.wither", 4),
			QualityRange.only(FortuneQuality.TERRIBLE),
			q -> new StatusEffectInstance(StatusEffects.WITHER, 30 * 20, 1)
		));
		
		SLOWNESS = reg("slowness", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.slowness", 2),
			QualityRange.inclusive(FortuneQuality.POOR, FortuneQuality.NEUTRAL),
			q -> {
				int duration = ((-q.getId()) + 1) * 60 * 20;
				int amplifier = q == FortuneQuality.NEUTRAL ? 0 : 1;
				return new StatusEffectInstance(StatusEffects.SLOWNESS, duration, amplifier);
			}
		));
		
		HUNGER = reg("hunger", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.hunger", 2),
			QualityRange.inclusive(FortuneQuality.TERRIBLE, FortuneQuality.BAD),
			q -> {
				int duration = 3 * 60 * 20;
				int amplifier = -q.getId() - 1;
				return new StatusEffectInstance(StatusEffects.HUNGER, duration, amplifier);
			}
		));
		
		REGENERATION = reg("regeneration", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.regeneration", 3),
			QualityRange.inclusive(FortuneQuality.GOOD, FortuneQuality.AMAZING),
			q -> {
				int duration = q == FortuneQuality.AMAZING ? 3 * 60 * 20 : 2 * 60 * 20;
				int amplifier = q.getId() - 1;
				return new StatusEffectInstance(StatusEffects.REGENERATION, duration, amplifier);
			}
		));
		
		HASTE = reg("haste", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.haste", 3),
			QualityRange.inclusive(FortuneQuality.NEUTRAL, FortuneQuality.AMAZING),
			q -> {
				int duration = q.getId() >= FortuneQuality.GOOD.getId() ? 3 * 60 * 20 : 2 * 60 * 20;
				int amplifier = q.getId();
				return new StatusEffectInstance(StatusEffects.HASTE, duration, amplifier);
			}
		));
		
		STRENGTH = reg("strength", new PotionFortune(
			randomFlavor("unfortunately.flavor.fortune.strength", 3),
			QualityRange.inclusive(FortuneQuality.NEUTRAL, FortuneQuality.AMAZING),
			q -> {
				int duration = (q.getId() + 1) * 60 * 20;
				int amplifier = q.getId() + 1;
				return new StatusEffectInstance(StatusEffects.STRENGTH, duration, amplifier);
			}
		));
		
		LUCKY_DIAMOND = reg("lucky_diamond", new LuckyMiningFortune(
			randomFlavor("unfortunately.flavor.fortune.lucky_diamond", 3),
			QualityRange.inclusive(FortuneQuality.GREAT, FortuneQuality.AMAZING),
			(world, pos, state) -> world.getDimension().getType() == DimensionType.OVERWORLD &&
				(state.matches(UFBlockTags.NATURAL_STONES) || state.getBlock().equals(Blocks.DIAMOND_ORE)) &&
				pos.getY() <= 16,
			Blocks.DIAMOND_ORE.getDefaultState(),
			4, 8
		));
		
		LUCKY_NETHER_QUARTZ = reg("lucky_nether_quartz", new LuckyMiningFortune(
			randomFlavor("unfortunately.flavor.fortune.lucky_nether_quartz", 2),
			QualityRange.inclusive(FortuneQuality.GOOD, FortuneQuality.AMAZING),
			(world, pos, state) -> world.getDimension().getType() == DimensionType.THE_NETHER &&
				(state.matches(UFBlockTags.NETHER_STONES) || state.getBlock().equals(Blocks.NETHER_QUARTZ_ORE)),
			Blocks.NETHER_QUARTZ_ORE.getDefaultState(),
			15, 40
		));
	}
	
	private static Function<Random, Text> randomFlavor(String key, int count) {
		return (r) -> new TranslatableText(key + "." + r.nextInt(count));
	}
	
	private static <T extends FortuneType<T>> T reg(String id, T thing) {
		return FortuneRegistry.register(new Identifier(Unfortunately.MODID, id), thing);
	}
}
