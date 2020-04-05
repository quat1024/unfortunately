package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

import java.util.Random;
import java.util.function.Function;

public class LuckyMiningFortune extends FortuneType<LuckyMiningFortune> {
	public LuckyMiningFortune(Function<Random, Text> flavorText, QualityRange qualityRange, SearchPredicate search, BlockState replaceBlock, int countLow, int countHigh) {
		super(flavorText, qualityRange);
		this.search = search;
		this.replaceBlock = replaceBlock;
		this.countLow = countLow;
		this.countHigh = countHigh;
	}
	
	private final SearchPredicate search;
	private final BlockState replaceBlock;
	private final int countLow;
	private final int countHigh;
	
	public SearchPredicate getSearch() {
		return search;
	}
	
	public BlockState getReplaceBlock() {
		return replaceBlock;
	}
	
	@Override
	public void onAdded(PlayerEntity player, Fortune<LuckyMiningFortune> fortune) {
		fortune.getCustomData().putInt("uses", player.world.random.nextInt(countHigh - countLow) + countLow);
	}
	
	public int getUses(Fortune<LuckyMiningFortune> fortune) {
		return fortune.getCustomData().getInt("uses");
	}
	
	public void setUses(Fortune<LuckyMiningFortune> fortune, int uses) {
		fortune.getCustomData().putInt("uses", uses);
	}
	
	public static interface SearchPredicate {
		boolean test(World world, BlockPos pos, BlockState state);
	}
	
	//See LuckyMiningFortuneHandler for the rest of the impl
}