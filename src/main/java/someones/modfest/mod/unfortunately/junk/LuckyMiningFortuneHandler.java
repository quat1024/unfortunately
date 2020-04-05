package someones.modfest.mod.unfortunately.junk;

import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.fortunes.LuckyMiningFortune;

import java.util.Random;

public class LuckyMiningFortuneHandler {
	//split out from ServerPlayerInteractionManager simply because I'm apprehensive about
	//putting a giant block of code in a location I expect will be a hot mixin spot,
	//especially one that people will be using localcaptures a lot in.
	//(...this is probably a baseless fear, lol)
	
	public static void whenSomeoneBreaksABlock(ServerWorld world, ServerPlayerEntity player, BlockState brokenState, BlockPos breakPos) {
		for(Fortune<LuckyMiningFortune> f : ((PlayerExt) player).getActiveFortunesOfType(LuckyMiningFortune.class)) {
			LuckyMiningFortune type = f.getType();
			LuckyMiningFortune.SearchPredicate condition = type.getSearch();
			
			//simulate "revealing" an ore by mining.
			//1. some block passing the fortune's test has to be adjacent to whatever the player just broke.
			//2. that block has to be surrounded on all sides (except the now-air broken block, of course) by opaque blocks
			
			nextWay:
			for(Direction way : randomDirections(world.random)) {
				BlockPos testPos = breakPos.offset(way);
				if(!condition.test(world, testPos, world.getBlockState(testPos))) continue; //1
				
				for(Direction way2 : randomDirections(world.random)) {
					BlockPos testOpaquePos = testPos.offset(way2);
					if(breakPos.equals(testOpaquePos)) continue;
					
					BlockState opaqueState = world.getBlockState(testOpaquePos);
					if(!opaqueState.isOpaque()) continue nextWay; //2
				}
				
				//found a candidate position!
				
				//reveal a block
				world.setBlockState(testPos, type.getReplaceBlock());
				
				//see how many uses are left on the fortune
				int remaining = type.getUses(f);
				
				if(remaining > 0) {
					type.setUses(f, remaining - 1);
				} else {
					f.setFinished();
				}
				
				return;
			}
		}
	}
	
	private static Direction[] randomDirections(Random random) {
		Direction[] directions = Direction.values();
		
		//Copied from the middle of Collections.shuffle's implementation
		//I think this is a fischer-yates shuffle or whatever it's called
		//Holy shit why does java have a method to shuffle a collection but not an array
		//...Just, why does java, in general
		for(int i = directions.length; i > 1; i--) {
			int a = i - 1;
			int b = random.nextInt(i);
			
			Direction blah = directions[a];
			directions[a] = directions[b];
			directions[b] = blah;
		}
		
		return directions;
	}
}
