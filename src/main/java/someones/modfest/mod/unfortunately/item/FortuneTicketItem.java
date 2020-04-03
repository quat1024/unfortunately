package someones.modfest.mod.unfortunately.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import someones.modfest.mod.unfortunately.Unfortunately;
import someones.modfest.mod.unfortunately.fortune.*;
import someones.modfest.mod.unfortunately.net.UFNetServer;
import someones.modfest.mod.unfortunately.util.PlayerExt;

import java.util.List;
import java.util.Random;

public class FortuneTicketItem extends Item {
	public FortuneTicketItem(Settings settings) {
		super(settings);
	}
	
	private static final String BASE_QUALITY = "base_quality";
	
	public void setBaseQuality(ItemStack stack, FortuneQuality quality) {
		stack.getOrCreateTag().putInt(BASE_QUALITY, quality.getId());
	}
	
	public FortuneQuality getBaseQuality(ItemStack stack) {
		if(!stack.hasTag()) return FortuneQuality.TERRIBLE;
		else {
			assert stack.getTag() != null; //ij doesn't know about hasTag == true implying getTag != null
			return FortuneQuality.byId(stack.getTag().getInt(BASE_QUALITY));
		}
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if(world.isClient()) return TypedActionResult.consume(stack);
		
		FortuneQuality quality = getBaseQuality(stack);
		
		Random random = world.random;
		
		if(random.nextBoolean()) {
			//Poke the quality around a little bit, so it's not entirely predictable.
			int lol = 0;
			while (random.nextBoolean() && lol++ < 25) {
				quality = quality.perturb(random);
			}
		}
		
		FortuneType type = FortuneRegistry.pick(random, quality);
		Timespan timespan = Timespan.pick(random);
		
		Fortune fortune = new Fortune(type, quality, world.getTimeOfDay() + timespan.pickTick(random));
		((PlayerExt) user).getFortunes().add(fortune);
		
		stack.decrement(1);
		
		UFNetServer.openResultScreen(user, new LiteralText("you got " + FortuneRegistry.FORTUNE_TYPES.getId(type) + " timespan: " + timespan.toString()));
		
		return TypedActionResult.consume(stack);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		GameOptions options = MinecraftClient.getInstance().options;
		
		tooltip.add(
			new TranslatableText(Unfortunately.MODID + ".tooltip.read_fortune",
				new TranslatableText(options.keyUse.getLocalizedName())
			)
		);
		
		if(context.isAdvanced()) {
			Text ayy = new TranslatableText("unfortunately.quality.debug", getBaseQuality(stack).toText());
			ayy.getStyle().setColor(Formatting.RED);
			tooltip.add(ayy);
		}
	}
	
	public ItemStack stackWithBaseQuality(FortuneQuality quality) {
		ItemStack stack = new ItemStack(this);
		setBaseQuality(stack, quality);
		return stack;
	}
}
