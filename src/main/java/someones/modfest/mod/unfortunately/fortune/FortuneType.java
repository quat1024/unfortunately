package someones.modfest.mod.unfortunately.fortune;

import net.minecraft.entity.player.PlayerEntity;

public abstract class FortuneType {
	public FortuneType(QualityRange qualityRange) {
		this.qualityRange = qualityRange;
	}
	
	protected final QualityRange qualityRange;
	
	public void pendingTick(PlayerEntity player, Fortune fortune, long timeRemaining) {
		
	}
	
	public void activeTick(PlayerEntity player, Fortune fortune) {
		
	}
}
