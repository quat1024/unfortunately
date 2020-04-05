package agency.highlysuspect.unfortunately.fortune.fortunes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import agency.highlysuspect.unfortunately.fortune.Fortune;
import agency.highlysuspect.unfortunately.fortune.FortuneType;
import agency.highlysuspect.unfortunately.fortune.QualityRange;

public class DummyFortune extends FortuneType<DummyFortune> {
	public DummyFortune() {
		super((r) -> new TranslatableText("unfortunately.fortune.dummy"), QualityRange.never());
	}
	
	@Override
	public void activeTick(PlayerEntity player, Fortune<DummyFortune> fortune) {
		fortune.setFinished();
	}
}
