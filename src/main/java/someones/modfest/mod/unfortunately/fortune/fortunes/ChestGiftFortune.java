package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

import java.util.Random;
import java.util.function.Function;

public class ChestGiftFortune extends FortuneType<ChestGiftFortune> {
	public ChestGiftFortune(Function<Random, Text> flavorTextChooser, QualityRange qualityRange, Function<Random, ItemStack> gift, Function<Random, Integer> uses) {
		super(flavorTextChooser, qualityRange);
		this.gift = gift;
		this.uses = uses;
	}
	
	private final Function<Random, ItemStack> gift;
	private final Function<Random, Integer> uses;
	
	public Function<Random, ItemStack> getGift() {
		return gift;
	}
	
	@Override
	public void onAdded(PlayerEntity player, Fortune<ChestGiftFortune> fortune) {
		fortune.getCustomData().putInt("uses", uses.apply(player.world.random));
	}
	
	public int getUses(Fortune<ChestGiftFortune> f) {
		return f.getCustomData().getInt("uses");
	}
	
	public void setUses(Fortune<ChestGiftFortune> f, int uses) {
		f.getCustomData().putInt("uses", uses);
	}
}
