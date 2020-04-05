package agency.highlysuspect.unfortunately.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import agency.highlysuspect.unfortunately.ui.FortuneResultScreen;

@Environment(EnvType.CLIENT)
public class UFNetClient {
	public static void onInitialize() {
		ClientSidePacketRegistry.INSTANCE.register(UFNet.OPEN_RESULT_SCREEN, (ctx, buf) -> {
			String textString = buf.readString();
			
			ctx.getTaskQueue().execute(() -> {
				Text text = Text.Serializer.fromJson(textString);
				
				String stuff;
				if(text == null) {
					stuff = "Problem parsing text :(";
				} else stuff = text.asFormattedString();
				
				MinecraftClient.getInstance().openScreen(new FortuneResultScreen(stuff));
			});
		});
	}
}
