package someones.modfest.mod.unfortunately;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import someones.modfest.mod.unfortunately.block.UFBlocks;
import someones.modfest.mod.unfortunately.net.UFNetClient;

@Environment(EnvType.CLIENT)
public class UnfortunatelyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		UFNetClient.onInitialize();
		
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), UFBlocks.CRYSTAL_BALL);
	}
}
