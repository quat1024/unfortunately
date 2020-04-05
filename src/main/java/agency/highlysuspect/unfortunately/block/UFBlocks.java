package agency.highlysuspect.unfortunately.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import agency.highlysuspect.unfortunately.Unfortunately;

public class UFBlocks {
	public static CrystalBallBlock CRYSTAL_BALL;
	
	public static void onInitialize() {
		CRYSTAL_BALL = Registry.register(Registry.BLOCK, new Identifier(Unfortunately.MODID, "crystal_ball"),
			new CrystalBallBlock(
				FabricBlockSettings.of(Material.GLASS, MaterialColor.PURPLE)
					.breakByTool(FabricToolTags.PICKAXES, 2)
					.sounds(BlockSoundGroup.LANTERN)
					.strength(3.5f, 0f)
					.nonOpaque()
					.build()
			)
		);
	}
}
