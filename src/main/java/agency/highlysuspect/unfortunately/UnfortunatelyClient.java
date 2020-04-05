package agency.highlysuspect.unfortunately;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.MathHelper;
import agency.highlysuspect.unfortunately.block.UFBlocks;
import agency.highlysuspect.unfortunately.fortune.FortuneQuality;
import agency.highlysuspect.unfortunately.item.UFItems;
import agency.highlysuspect.unfortunately.net.UFNetClient;

@Environment(EnvType.CLIENT)
public class UnfortunatelyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		UFNetClient.onInitialize();
		
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), UFBlocks.CRYSTAL_BALL);
		
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			if(tintIndex == 1) {
				FortuneQuality q = UFItems.UNREAD_FORTUNE.getBaseQuality(stack);
				if(q == FortuneQuality.TERRIBLE) return 0xF32819;
				if(q == FortuneQuality.AMAZING) return 0x33A847;
				else {
					float slide = (q.getId() + 2) / 4f;
					return lerpColor(slide, 0x7B166A, 0xB83DC6);
				}
			} else return 0xFFFFFF;
		}, UFItems.UNREAD_FORTUNE);
	}
	
	@SuppressWarnings("SameParameterValue") //shut ur up
	private static int lerpColor(float slide, int colorA, int colorB) {
		int redA = (colorA & 0xFF0000) >> 16;
		int greenA = (colorA & 0x00FF00) >> 8;
		int blueA = colorA & 0x0000FF;
		
		int redB = (colorB & 0xFF0000) >> 16;
		int greenB = (colorB & 0x00FF00) >> 8;
		int blueB = colorB & 0x0000FF;
		
		int red = (int) MathHelper.lerp(slide, redA, redB);
		int green = (int) MathHelper.lerp(slide, greenA, greenB);
		int blue = (int) MathHelper.lerp(slide, blueA, blueB);
		
		return ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
	}
}
