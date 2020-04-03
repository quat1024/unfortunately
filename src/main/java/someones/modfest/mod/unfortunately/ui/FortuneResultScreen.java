package someones.modfest.mod.unfortunately.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import someones.modfest.mod.unfortunately.Unfortunately;

public class FortuneResultScreen extends Screen {
	public FortuneResultScreen(String stuff) {
		super(new TranslatableText(Unfortunately.MODID + ".screen.fortune_result"));
		this.stuff = stuff;
	}
	
	private final String stuff;
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		super.render(mouseX, mouseY, delta);
		renderBackground(); //todo probably don't render a background, like a written book ui
		
		drawCenteredString(MinecraftClient.getInstance().textRenderer, stuff, width / 2, height / 2, 0xFFFFFF);
	}
}
