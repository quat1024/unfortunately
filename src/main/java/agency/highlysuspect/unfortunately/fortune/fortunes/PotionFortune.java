package agency.highlysuspect.unfortunately.fortune.fortunes;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import agency.highlysuspect.unfortunately.fortune.Fortune;
import agency.highlysuspect.unfortunately.fortune.FortuneQuality;
import agency.highlysuspect.unfortunately.fortune.FortuneType;
import agency.highlysuspect.unfortunately.fortune.QualityRange;

import java.util.Random;
import java.util.function.Function;

public class PotionFortune extends FortuneType<PotionFortune> {
	public PotionFortune(Function<Random, Text> flavorText, QualityRange qualityRange, Function<FortuneQuality, StatusEffectInstance> effSupplier) {
		super(flavorText, qualityRange);
		
		this.effSupplier = effSupplier;
	}
	
	private final Function<FortuneQuality, StatusEffectInstance> effSupplier;
	
	@Override
	public void activeTick(PlayerEntity player, Fortune<PotionFortune> fortune) {
		player.addStatusEffect(effSupplier.apply(fortune.getQuality()));
		
		fortune.setFinished();
	}
}
