package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.entity.player.PlayerEntity;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

public class DummyFortune extends FortuneType {
	public DummyFortune() {
		super(QualityRange.never());
	}
	
	@Override
	public void pendingTick(PlayerEntity player, Fortune fortune, long timeRemaining) {
		System.out.println("PENDING!!!!!!!!! time until active: " + timeRemaining);
	}
	
	@Override
	public void activeTick(PlayerEntity player, Fortune fortune) {
		System.out.println("ACTIVE!!!!!!!!! now finishing!!!");
		fortune.setFinished();
	}
}
