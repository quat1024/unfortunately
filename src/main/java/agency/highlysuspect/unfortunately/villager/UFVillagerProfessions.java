package agency.highlysuspect.unfortunately.villager;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import agency.highlysuspect.unfortunately.Unfortunately;
import agency.highlysuspect.unfortunately.fortune.FortuneQuality;
import agency.highlysuspect.unfortunately.item.UFItems;
import agency.highlysuspect.unfortunately.junk.RandomHelper;
import agency.highlysuspect.unfortunately.mixin.VillagerProfessionMixin;

import java.util.Random;

public class UFVillagerProfessions {
	public static VillagerProfession FORTUNE_TELLER;
	
	public static void onInitialize() {
		FORTUNE_TELLER = Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(Unfortunately.MODID, "fortuneteller"),
			VillagerProfessionMixin.construct(
				Unfortunately.MODID + "_fortune_teller",
				UFPoiTypes.CRYSTAL_BALL,
				ImmutableSet.of(),
				ImmutableSet.of(),
				null
			)
		);
		
		TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(UFVillagerProfessions.FORTUNE_TELLER,
			copyToFastUtilMap(ImmutableMap.<Integer, TradeOffers.Factory[]>builder()
				.put(1, new TradeOffers.Factory[]{
					new ChoiceOffer(
						new BuyFortuneOffer(FortuneQuality.POOR, 7, new ItemStack(Items.IRON_INGOT), 5, 12),
						new BuyFortuneOffer(FortuneQuality.POOR, 7, new ItemStack(Items.COMPASS), 1, 2)
					),
					new ChoiceOffer(
						new BuyFortuneOffer(FortuneQuality.BAD, 7, new ItemStack(Items.GOLD_INGOT), 5, 12),
						new BuyFortuneOffer(FortuneQuality.BAD, 7, new ItemStack(Items.GOLDEN_APPLE), 1, 2)
					)
				})
				.put(2, new TradeOffers.Factory[]{
					new ChoiceOffer(
						new BuyFortuneOffer(FortuneQuality.NEUTRAL, 30, new ItemStack(Items.LAPIS_LAZULI), 5, 12),
						new BuyFortuneOffer(FortuneQuality.NEUTRAL, 30, new ItemStack(Items.REDSTONE), 12, 20)
					),
					new BuyFortuneOffer(FortuneQuality.GOOD, 30, new ItemStack(Items.EMERALD), 6, 15),
				})
				.put(3, new TradeOffers.Factory[]{
					new BuyFortuneOffer(FortuneQuality.GREAT, 90, new ItemStack(Items.DIAMOND), 4, 8)
				})
				.put(4, new TradeOffers.Factory[]{
					new BuyFortuneOffer(FortuneQuality.AMAZING, 90, new ItemStack(Items.HEART_OF_THE_SEA), 1, 1)
				})
				.build()
			)
		);
	}
	
	private static class ChoiceOffer implements TradeOffers.Factory {
		public ChoiceOffer(TradeOffers.Factory... others) {
			this.others = others;
		}
		
		private final TradeOffers.Factory[] others;
		
		@Override
		public TradeOffer create(Entity entity, Random random) {
			return others[random.nextInt(others.length)].create(entity, random);
		}
	}
	
	private static class BuyFortuneOffer implements TradeOffers.Factory {
		public BuyFortuneOffer(FortuneQuality baseQuality, int xp, ItemStack buy, int buyCountMin, int buyCountMax) {
			this.baseQuality = baseQuality;
			this.xp = xp;
			this.buy = buy;
			this.buyCountMin = buyCountMin;
			this.buyCountMax = buyCountMax;
		}
		
		private final FortuneQuality baseQuality;
		private final int xp;
		private final ItemStack buy;
		private final int buyCountMin;
		private final int buyCountMax;
		
		@Override
		public TradeOffer create(Entity entity, Random random) {
			ItemStack buyy = buy.copy();
			
			buyy.setCount(RandomHelper.randomRangeInclusive(random, buyCountMin, buyCountMax));
			
			ItemStack sell = UFItems.UNREAD_FORTUNE.stackWithBaseQuality(baseQuality);
			if(random.nextInt(10) == 0) sell.setCount(2);
			
			return new TradeOffer(
				new ItemStack(Items.WRITABLE_BOOK),
				buyy,
				sell,
				0,
				1,
				xp,
				1f
			);
		}
	}
	
	private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> immutableMap) {
		return new Int2ObjectOpenHashMap<>(immutableMap);
	}
}
