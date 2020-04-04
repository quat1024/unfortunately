package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.fortune.QualityRange;

import java.util.function.Predicate;

public class LuckyMiningFortune extends FortuneType {
	public LuckyMiningFortune(QualityRange qualityRange, Predicate<BlockState> searchBlocks, BlockState replaceBlock) {
		super(qualityRange);
		this.searchBlocks = searchBlocks;
		this.replaceBlock = replaceBlock;
	}
	
	private final Predicate<BlockState> searchBlocks;
	private final BlockState replaceBlock;
	
	public Predicate<BlockState> getSearchBlocks() {
		return searchBlocks;
	}
	
	public BlockState getReplaceBlock() {
		return replaceBlock;
	}
	
	//Other than that: doesn't do anything. Doesn't even cancel itself when it expires.
	//See LuckyMiningFortuneHandler
}
