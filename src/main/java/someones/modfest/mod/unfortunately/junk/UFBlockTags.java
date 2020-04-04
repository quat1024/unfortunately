package someones.modfest.mod.unfortunately.junk;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import someones.modfest.mod.unfortunately.Unfortunately;

public class UFBlockTags {
	public static Tag<Block> NATURAL_STONES;
	
	public static void onInitialize() {
		NATURAL_STONES = TagRegistry.block(new Identifier(Unfortunately.MODID, "natural_stones"));
	}
}
