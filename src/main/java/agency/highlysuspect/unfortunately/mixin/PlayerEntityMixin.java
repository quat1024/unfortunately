package agency.highlysuspect.unfortunately.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import agency.highlysuspect.unfortunately.Unfortunately;
import agency.highlysuspect.unfortunately.fortune.Fortune;
import agency.highlysuspect.unfortunately.junk.PlayerExt;

import java.util.Set;
import java.util.TreeSet;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerExt {
	@Unique private final Set<Fortune<?>> fortunes = new TreeSet<>();
	@Unique private static final String FORTUNES_KEY = Unfortunately.MODID + "-fortunes";
	
	@Override
	public Set<Fortune<?>> getFortunes() {
		return fortunes;
	}
	
	@Inject(
		method = "tick",
		at = @At("TAIL")
	)
	private void tick(CallbackInfo ci) {
		if(((PlayerEntity) (Object) this).world.isClient) return;
		
		for (Fortune<?> fortune : fortunes) {
			try {
				fortune.tick((PlayerEntity) (Object) this);
			} catch(Exception boyIHopeTheModfestServerDoesntCrash) {
				if(fortune != null) fortune.setFinished();
			}
		}
		
		fortunes.removeIf(Fortune::isFinished);
	}
	
	@Inject(
		method = "writeCustomDataToTag",
		at = @At("TAIL")
	)
	private void whenWritingCustomData(CompoundTag tag, CallbackInfo ci) {
		ListTag fortunesTag = new ListTag();
		fortunes.forEach(f -> fortunesTag.add(f.toTag()));
		tag.put(FORTUNES_KEY, fortunesTag);
	}
	
	@Inject(
		method = "readCustomDataFromTag",
		at = @At("TAIL")
	)
	private void whenReadingCustomData(CompoundTag tag, CallbackInfo ci) {
		fortunes.clear();
		
		ListTag fortunesTag = tag.getList(FORTUNES_KEY, 10);
		fortunesTag.forEach(a -> {
			if(!(a instanceof CompoundTag)) return;
			Fortune<?> fortune = Fortune.fromTag((CompoundTag) a);
			if(fortune != null) fortunes.add(fortune);
		});
	}
}
