package someones.modfest.mod.unfortunately;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.command.arguments.IdentifierArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneQuality;
import someones.modfest.mod.unfortunately.fortune.FortuneRegistry;
import someones.modfest.mod.unfortunately.fortune.FortuneType;
import someones.modfest.mod.unfortunately.junk.PlayerExt;

import java.util.Set;
import java.util.function.Predicate;

public class UFCommands {
	//TODO futz with this
	private static final SuggestionProvider<ServerCommandSource> FORTUNE_ID_SUGGESTER = (context, builder) -> CommandSource.suggestIdentifiers(FortuneRegistry.FORTUNE_TYPES.getIds(), builder);
	
	private static final SuggestionProvider<ServerCommandSource> FORTUNE_QUALITY_SUGGESTER = (context, builder) -> {
		for(FortuneQuality quality : FortuneQuality.values()) builder.suggest(quality.toString());
		return builder.buildFuture();
	};
	
	public static void onInitialize() {
		CommandRegistry.INSTANCE.register(false, UFCommands::registerCommand);
	}
	
	public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(
			CommandManager.literal("unfortunately")
				.requires(src -> src.hasPermissionLevel(2))
				.then(CommandManager.literal("add")
					.then(CommandManager.argument("players", EntityArgumentType.players())
						.then(CommandManager.argument("fortune", IdentifierArgumentType.identifier()).suggests(FORTUNE_ID_SUGGESTER)
							.then(CommandManager.argument("quality", StringArgumentType.string()).suggests(FORTUNE_QUALITY_SUGGESTER)
								.then(CommandManager.argument("delay", LongArgumentType.longArg(0))
									.executes(ctx -> {
										addFortunes(
											ctx,
											EntityArgumentType.getPlayers(ctx, "players"),
											IdentifierArgumentType.getIdentifier(ctx, "fortune"),
											StringArgumentType.getString(ctx, "quality"),
											LongArgumentType.getLong(ctx, "delay")
										);
										return 0;
									})
								)
							)
						)
					)
				)
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
	
	public static void addFortunes(CommandContext<ServerCommandSource> ctx, Iterable<? extends Entity> entities, Identifier fortuneId, String qualityStr, long delay) {
		FortuneType<?> type = FortuneRegistry.get(fortuneId); //TODO handle error cases better (this silently switches to the dummy fortune)
		FortuneQuality quality = FortuneQuality.byString(qualityStr);
		if(quality == null) return; //TODO send actual command feedback
		
		int count = 0;
		for (Entity entity : entities) {
			if(entity instanceof PlayerEntity) {
				PlayerExt.addFortune((PlayerEntity) entity, type, quality, delay);
				count++;
			}
		}
		
		//TODO send command feedback
	}
	
	public static void clearFortunes(CommandContext<ServerCommandSource> ctx, Iterable<? extends Entity> entities, Predicate<Fortune> removeIf) {
		int playerCount = 0;
		int fortuneCount = 0;
		
		for (Entity entity : entities) {
			if(entity instanceof PlayerEntity) {
				Set<Fortune<?>> fortuneSet = ((PlayerExt) entity).getFortunes();
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
