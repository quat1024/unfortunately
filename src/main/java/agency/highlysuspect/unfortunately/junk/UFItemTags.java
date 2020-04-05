package agency.highlysuspect.unfortunately.junk;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import agency.highlysuspect.unfortunately.Unfortunately;

public class UFItemTags {
	public static Tag<Item> GARBAGE;
	
	public static void onInitialize() {
		GARBAGE = TagRegistry.item(new Identifier(Unfortunately.MODID, "garbage"));
	}
}
