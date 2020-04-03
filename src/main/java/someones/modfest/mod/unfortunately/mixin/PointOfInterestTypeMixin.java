package someones.modfest.mod.unfortunately.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeMixin {
	@Invoker("<init>") static PointOfInterestType construct(String id, Set<BlockState> blockStates, int ticketCount, int searchDistance) {
		throw new AssertionError("dummy method body");
	}
	
	@Invoker("setup") static PointOfInterestType setup(PointOfInterestType pointOfInterestType) {
		throw new AssertionError("dummy method body");
	}
}
