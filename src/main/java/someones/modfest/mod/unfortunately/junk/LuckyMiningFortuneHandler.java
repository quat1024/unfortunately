package someones.modfest.mod.unfortunately.junk;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.fortunes.LuckyMiningFortune;

import java.util.function.Predicate;

public class LuckyMiningFortuneHandler {
	//split out from ServerPlayerInteractionManager simply because I'm apprehensive about
	//putting a giant block of code in a location I expect will be a hot mixin spot,
	//especially one that people will be using localcaptures a lot in.
	//this is probably a baseless fear, lol
	
	public static void whenSomeoneBreaksABlock(ServerWorld world, ServerPlayerEntity player, BlockState brokenState, BlockPos breakPos) {
		Fortune f = ((PlayerExt) player).getFirstActiveFortuneOfType(LuckyMiningFortune.class);
		if(f == null) return;
		
		LuckyMiningFortune type = (LuckyMiningFortune) f.getType();
		Predicate<BlockState> condition = type.getSearchBlocks();
		
		//simulate "revealing" an ore by mining.
		//1. some block passing the fortune's test has to be adjacent to whatever the player just broke.
		//2. that block has to be surrounded on all sides (except the now-air broken block, of course) by opaque blocks
		
		nextWay:
		for(Direction way : Direction.values()) {
			BlockPos testPos = breakPos.offset(way);
			if(!condition.test(world.getBlockState(testPos))) continue; //1
			
			for(Direction way2 : Direction.values()) {
				BlockPos testOpaquePos = testPos.offset(way2);
				if(breakPos.equals(testOpaquePos)) continue;
				
				BlockState opaqueState = world.getBlockState(testOpaquePos);
				if(!opaqueState.isOpaque()) continue nextWay; //2
			}
			
			//found a candidate position, let's go!
			world.setBlockState(testPos, type.getReplaceBlock());
			f.setFinished();
			return;
		}
	}
}
