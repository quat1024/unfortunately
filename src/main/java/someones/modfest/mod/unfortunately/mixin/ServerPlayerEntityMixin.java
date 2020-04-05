package someones.modfest.mod.unfortunately.mixin;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.GenericContainer;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.inventory.Inventory;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.fortunes.ChestGiftFortune;
import someones.modfest.mod.unfortunately.junk.PlayerExt;

import java.util.OptionalInt;
import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	//TODO the proper place to inject is probably using an invoke_assign after containerfactory.createmenu
	//but for some reason Mixin doesn't resolve that target reference. dunno why
	//maybe it's something about how nameablecontainerfactory extends containerfactory which contains the actual method
	@Inject(
		method = "openContainer(Lnet/minecraft/container/NameableContainerFactory;)Ljava/util/OptionalInt;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"
		),
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void whenOpeningContainer(NameableContainerFactory containerFactory, CallbackInfoReturnable<OptionalInt> cir, Container container) {
		ServerPlayerEntity thi$ = (ServerPlayerEntity) (Object) this;
		if(thi$.world.isClient()) return;
		
		Random random = thi$.world.random;
		
		if(container instanceof GenericContainer && ((GenericContainer) container).getInventory() instanceof ChestBlockEntity) {
			Inventory inv = ((GenericContainer) container).getInventory();
			if(!inv.canPlayerUseInv(thi$)) return;
			
			//find a random empty slot
			for(int slot : shuffledInts(random, inv.getInvSize())) {
				if(inv.getInvStack(slot).isEmpty()) {
					//put a gift in it
					for(Fortune<ChestGiftFortune> f : ((PlayerExt) this).getActiveFortunesOfType(ChestGiftFortune.class)) {
						inv.setInvStack(slot, f.getType().getGift().apply(random));
						
						int usesLeft = f.getType().getUses(f) - 1;
						if(usesLeft == 0) f.setFinished();
						else f.getType().setUses(f, usesLeft);
						
						return;
					}
				}
			}
		}
	}
	
	@Unique
	private static int[] shuffledInts(Random random, int max) {
		int[] ints = new int[max];
		for(int i = 0; i < max; i++) ints[i] = i;
		
		for(int i = ints.length; i > 1; i--) {
			int a = i - 1;
			int b = random.nextInt(i);
			
			int blah = ints[a];
			ints[a] = ints[b];
			ints[b] = blah;
		}
		
		return ints;
	}
}
