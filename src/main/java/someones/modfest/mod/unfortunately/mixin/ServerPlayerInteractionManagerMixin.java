package someones.modfest.mod.unfortunately.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import someones.modfest.mod.unfortunately.junk.LuckyMiningFortuneHandler;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
	@Shadow public ServerWorld world;
	@Shadow public ServerPlayerEntity player;
	
	@Inject(
		method = "tryBreakBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/Block;onBroken(Lnet/minecraft/world/IWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
		),
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void whenBreakingBlock(BlockPos pos, CallbackInfoReturnable<Boolean> ci, BlockState brokenState, BlockEntity notfunny, Block didnt, boolean laugh) {
		LuckyMiningFortuneHandler.whenSomeoneBreaksABlock(world, player, brokenState, pos);
	}
}
