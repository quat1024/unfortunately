package agency.highlysuspect.unfortunately.net;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.PacketByteBuf;

public class UFNetServer {
	public static void openResultScreen(PlayerEntity player, Text stuff) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		
		buf.writeString(Text.Serializer.toJson(stuff));
		
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, UFNet.OPEN_RESULT_SCREEN, buf);
	}
}
