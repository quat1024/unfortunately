package someones.modfest.mod.unfortunately;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneRegistry;
import someones.modfest.mod.unfortunately.util.PlayerExt;

import java.util.Set;
import java.util.function.Predicate;

public class UFCommands {
	//TODO futz with this
	private static final SuggestionProvider<ServerCommandSource> FORTUNE_ID_SUGGESTER = (context, builder) -> CommandSource.suggestIdentifiers(FortuneRegistry.FORTUNE_TYPES.getIds(), builder);
	
	public static void onInitialize() {
		CommandRegistry.INSTANCE.register(false, UFCommands::registerCommand);
	}
	
	public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			CommandManager.literal("unfortunately")
				.then(CommandManager.literal("remove")
					.then(CommandManager.argument("players", EntityArgumentType.players())
						.executes(ctx -> {
							clearFortunes(ctx, EntityArgumentType.getEntities(ctx, "players"), f -> true);
							return 0;
						})
					)
				)
		);
	}
	
	public static void clearFortunes(CommandContext<ServerCommandSource> ctx, Iterable<? extends Entity> entities, Predicate<Fortune> removeIf) {
		int playerCount = 0;
		int fortuneCount = 0;
		
		for (Entity entity : entities) {
			if(entity instanceof PlayerEntity) {
				Set<Fortune> fortuneSet = ((PlayerExt) entity).getFortunes();
				int oldCount = fortuneSet.size();
				
				fortuneSet.removeIf(removeIf);
				
				int newCount = fortuneSet.size();
				if(newCount != oldCount) {
					playerCount++;
					fortuneCount += oldCount - newCount;
				}
			}
		}
		
		ctx.getSource().sendFeedback(new TranslatableText(Unfortunately.MODID + ".command.clear.success", fortuneCount, playerCount), true);
	}
}
