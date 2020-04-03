package someones.modfest.mod.unfortunately.fortune.fortunes;

import net.minecraft.util.Identifier;
import someones.modfest.mod.unfortunately.Unfortunately;
import someones.modfest.mod.unfortunately.fortune.FortuneRegistry;
import someones.modfest.mod.unfortunately.fortune.FortuneType;

public class UFFortunes {
	public static FortuneType DUMMY;
	
	public static void onInitialize() {
		DUMMY = FortuneRegistry.register(new Identifier(Unfortunately.MODID, "dummy_fortune"), new DummyFortune());
	}
}
