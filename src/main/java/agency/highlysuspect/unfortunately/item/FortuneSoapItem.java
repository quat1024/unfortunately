package agency.highlysuspect.unfortunately.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import agency.highlysuspect.unfortunately.Unfortunately;
import agency.highlysuspect.unfortunately.fortune.Fortune;
import agency.highlysuspect.unfortunately.junk.PlayerExt;

import java.util.List;
import java.util.Set;

public class FortuneSoapItem extends Item {
	public FortuneSoapItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack held = user.getStackInHand(hand);
		
		if(!world.isClient()) {
			Set<Fortune<?>> fortunes = ((PlayerExt) user).getFortunes();
			int count = fortunes.size();
			
			fortunes.clear();
			
			user.sendMessage(new TranslatableText(Unfortunately.MODID + ".fortune_soap.done", count));
			
			if(!user.isCreative()) held.decrement(1);
		}
		
		return TypedActionResult.success(held);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(Unfortunately.MODID + ".tooltip.fortune_soap").styled(s -> s.setItalic(true)));
	}
	
	@Override
	public boolean hasEnchantmentGlint(ItemStack stack) {
		return true;
	}
}
