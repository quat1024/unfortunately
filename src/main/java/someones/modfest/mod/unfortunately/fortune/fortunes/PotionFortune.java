package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneQuality;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

import java.util.function.Function;

public class PotionFortune extends FortuneType {
	public PotionFortune(QualityRange qualityRange, Function<FortuneQuality, StatusEffectInstance> effSupplier) {
		super(qualityRange);
		
		this.effSupplier = effSupplier;
	}
	
	private final Function<FortuneQuality, StatusEffectInstance> effSupplier;
	
	@Override
	public void activeTick(PlayerEntity player, Fortune fortune) {
		player.addStatusEffect(effSupplier.apply(fortune.getQuality()));
		
		fortune.setFinished();
	}
}
