package someones.modfest.mod.unfortunately.fortune;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import java.util.Random;
import java.util.function.Function;

public abstract class FortuneType<T extends FortuneType<T>> {
	public FortuneType(Function<Random, Text> flavorTextChooser, QualityRange qualityRange) {
		this.flavorText = flavorTextChooser;
		this.qualityRange = qualityRange;
	}
	
	protected final Function<Random, Text> flavorText;
	protected final QualityRange qualityRange;
	
	public Text pickFlavorText(Random random) {
		return flavorText.apply(random);
	}
	
	public void onAdded(PlayerEntity player, Fortune<T> fortune) {
		
	}
	
	public void pendingTick(PlayerEntity player, Fortune<T> fortune, long timeRemaining) {
		
	}
	
	public void activeTick(PlayerEntity player, Fortune<T> fortune) {
		
	}
}
