package agency.highlysuspect.unfortunately.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import agency.highlysuspect.unfortunately.Unfortunately;
import agency.highlysuspect.unfortunately.block.UFBlocks;
import agency.highlysuspect.unfortunately.fortune.FortuneQuality;

public class UFItems {
	public static BlockItem CRYSTAL_BALL;
	public static FortuneTicketItem UNREAD_FORTUNE;
	
	public static Item FORTUNE_SOAP;
	
	public static void onInitialize() {
		CRYSTAL_BALL = Registry.register(Registry.ITEM, new Identifier(Unfortunately.MODID, "crystal_ball"), new BlockItem(
			UFBlocks.CRYSTAL_BALL,
			new Item.Settings().group(GROUP).rarity(Rarity.UNCOMMON)
		));
		
		UNREAD_FORTUNE = Registry.register(Registry.ITEM, new Identifier(Unfortunately.MODID, "unread_fortune"), new FortuneTicketItem(
			new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(1)
		));
		
		FORTUNE_SOAP = Registry.register(Registry.ITEM, new Identifier(Unfortunately.MODID, "fortune_soap"), new FortuneSoapItem(
			new Item.Settings().group(GROUP).rarity(Rarity.EPIC).maxCount(1)
		));
	}
	
	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(Unfortunately.MODID, "item_group"))
		.icon(() -> new ItemStack(CRYSTAL_BALL))
		.appendItems(list -> {
			list.add(new ItemStack(CRYSTAL_BALL));
			
			for (FortuneQuality quality : FortuneQuality.values()) {
				list.add(UNREAD_FORTUNE.stackWithBaseQuality(quality));
			}
			
			list.add(new ItemStack(FORTUNE_SOAP));
		})
		.build();
}
