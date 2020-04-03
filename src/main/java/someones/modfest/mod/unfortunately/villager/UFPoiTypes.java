package someones.modfest.mod.unfortunately.villager;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.poi.PointOfInterestType;
import someones.modfest.mod.unfortunately.Unfortunately;
import someones.modfest.mod.unfortunately.block.UFBlocks;
import someones.modfest.mod.unfortunately.mixin.PointOfInterestTypeMixin;

import java.util.HashSet;
import java.util.Set;

public class UFPoiTypes {
	public static PointOfInterestType CRYSTAL_BALL;
	
	public static void onInitialize() {
		CRYSTAL_BALL = PointOfInterestTypeMixin.setup(
			Registry.register(Registry.POINT_OF_INTEREST_TYPE, new Identifier(Unfortunately.MODID, "crystal_ball_poi"),
				PointOfInterestTypeMixin.construct(
					Unfortunately.MODID + "_crystal_ball_poi",
					setOf(UFBlocks.CRYSTAL_BALL.getDefaultState()),
					1,
					1
				)
			)
		);
	}
	
	private static <T> Set<T> setOf(T thing) {
		HashSet<T> a = new HashSet<>();
		a.add(thing);
		return a;
	}
}
