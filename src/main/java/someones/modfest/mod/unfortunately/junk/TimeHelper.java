package someones.modfest.mod.unfortunately.junk;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class TimeHelper {
	public static long getTime(World world) {
		if(world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).get()) {
			return world.getTimeOfDay(); //the time that skips forward when you sleep
		} else {
			return world.getTime(); //monotonic time
		}
	}
}
