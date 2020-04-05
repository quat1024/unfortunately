package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

public class DummyFortune extends FortuneType<DummyFortune> {
	public DummyFortune() {
		super((r) -> new TranslatableText("unfortunately.fortune.dummy"), QualityRange.never());
	}
	
	@Override
	public void activeTick(PlayerEntity player, Fortune<DummyFortune> fortune) {
		fortune.setFinished();
	}
}
