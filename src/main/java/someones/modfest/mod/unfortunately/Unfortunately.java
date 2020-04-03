package someones.modfest.mod.unfortunately;

import net.fabricmc.api.ModInitializer;
import someones.modfest.mod.unfortunately.block.UFBlocks;
import someones.modfest.mod.unfortunately.fortune.fortunes.UFFortunes;
import someones.modfest.mod.unfortunately.item.UFItems;
import someones.modfest.mod.unfortunately.villager.UFPoiTypes;
import someones.modfest.mod.unfortunately.villager.UFVillagerProfessions;

public class Unfortunately implements ModInitializer {
	public static final String MODID = "unfortunately";
	
	@Override
	public void onInitialize() {
		UFBlocks.onInitialize();
		UFItems.onInitialize();
		
		UFPoiTypes.onInitialize();
		UFVillagerProfessions.onInitialize();
		
		UFCommands.onInitialize();
		
		UFFortunes.onInitialize();
	}
}
