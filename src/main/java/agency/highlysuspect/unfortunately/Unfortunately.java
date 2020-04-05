package agency.highlysuspect.unfortunately;

import net.fabricmc.api.ModInitializer;
import agency.highlysuspect.unfortunately.block.UFBlocks;
import agency.highlysuspect.unfortunately.fortune.fortunes.UFFortunes;
import agency.highlysuspect.unfortunately.item.UFItems;
import agency.highlysuspect.unfortunately.junk.UFBlockTags;
import agency.highlysuspect.unfortunately.junk.UFItemTags;
import agency.highlysuspect.unfortunately.villager.UFPoiTypes;
import agency.highlysuspect.unfortunately.villager.UFVillagerProfessions;

public class Unfortunately implements ModInitializer {
	public static final String MODID = "unfortunately";
	
	@Override
	public void onInitialize() {
		//Blocks and items and all that jazz
		UFBlocks.onInitialize();
		UFItems.onInitialize();
		
		//Villagers
		UFPoiTypes.onInitialize();
		UFVillagerProfessions.onInitialize();
		
		//Misc gunk
		UFBlockTags.onInitialize();
		UFItemTags.onInitialize();
		UFCommands.onInitialize();
		
		//My own stuff
		UFFortunes.onInitialize();
	}
}
