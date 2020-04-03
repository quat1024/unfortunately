package someones.modfest.mod.unfortunately;

import net.fabricmc.api.ClientModInitializer;
import someones.modfest.mod.unfortunately.net.UFNetClient;

public class UnfortunatelyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		UFNetClient.onInitialize();
	}
}
