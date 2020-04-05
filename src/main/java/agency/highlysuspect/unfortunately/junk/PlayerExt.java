package agency.highlysuspect.unfortunately.junk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.entity.player.PlayerEntity;
import agency.highlysuspect.unfortunately.fortune.Fortune;
import agency.highlysuspect.unfortunately.fortune.FortuneQuality;
import agency.highlysuspect.unfortunately.fortune.FortuneType;

import java.util.Set;

public interface PlayerExt {
	Set<Fortune<?>> getFortunes();
	
	static void addFortune(PlayerEntity player, FortuneType<?> type, FortuneQuality quality, long delay) {
		Set<Fortune<?>> fortunes = ((PlayerExt) player).getFortunes();
		
		Fortune<?> f = new Fortune<>(type, quality, TimeHelper.getTime(player.world) + delay);
		fortunes.add(f);
		f.onAdded(player);
	}
	
	default <T extends FortuneType<?>> Iterable<Fortune<T>> getActiveFortunesOfType(Class<T> typeClass) {
		Set<Fortune<?>> fortunes = getFortunes();
		if(fortunes.isEmpty()) return ImmutableList.of();
		
		//Shut up IntelliJ I really do want to use iterables.filter, streams are way overkill for this.
		//Also I'm doing some absolutely horrendous type punning here. Don't mind me.
		//noinspection StaticPseudoFunctionalStyleMethod, ConstantConditions, unchecked
		return (Iterable<Fortune<T>>) (Object) Iterables.filter(fortunes, f -> f.getStatus() == Fortune.Status.ACTIVE && f.getType().getClass().equals(typeClass));
	}
}
