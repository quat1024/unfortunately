package someones.modfest.mod.unfortunately.fortune;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.Objects;

@SuppressWarnings("rawtypes")
public class Fortune<T extends FortuneType> implements Comparable<Fortune<?>> {
	public Fortune(T type, FortuneQuality quality, long activationTick) {
		this(type, quality, activationTick, Status.PENDING, new CompoundTag());
	}
	
	private Fortune(T type, FortuneQuality quality, long activationTick, Status status, CompoundTag customData) {
		this.type = type;
		this.quality = quality;
		this.activationTick = activationTick;
		this.status = status;
		this.customData = customData;
	}
	
	private final T type;
	private final FortuneQuality quality;
	private final long activationTick;
	
	private Status status;
	private CompoundTag customData;
	
	public void onAdded(PlayerEntity player) {
		//noinspection unchecked
		type.onAdded(player, this);
	}
	
	public void tick(PlayerEntity player) {
		if(status == Status.FINISHED) return;
		
		long now = player.world.getTimeOfDay();
		
		if(status == Status.PENDING) {
			if(now >= activationTick) {
				status = Status.ACTIVE;
			} else {
				//TODO how to make generics line up
				//noinspection unchecked
				type.pendingTick(player, this, activationTick - now);
				return;
			}
		}
		
		if(status == Status.ACTIVE) {
			//TODO how to make generics line up
			//noinspection unchecked
			type.activeTick(player, this);
		}
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setFinished() {
		status = Status.FINISHED;
	}
	
	public T getType() {
		return type;
	}
	
	public FortuneQuality getQuality() {
		return quality;
	}
	
	public CompoundTag getCustomData() {
		return customData;
	}
	
	public enum Status {
		PENDING,
		ACTIVE,
		FINISHED,
		;
		
		public int getId() {
			return ordinal();
		}
		
		public static Status byId(int id) {
			switch(id) {
				case 0: default: return PENDING;
				case 1: return ACTIVE;
				case 2: return FINISHED;
			}
		}
	}
	
	public CompoundTag toTag() {
		CompoundTag a = new CompoundTag();
		//noinspection ConstantConditions
		a.putString("type", FortuneRegistry.FORTUNE_TYPES.getId(type).toString());
		a.putInt("quality", quality.getId());
		a.putLong("activationTick", activationTick);
		a.putInt("status", status.getId());
		a.put("data", customData);
		
		return a;
	}
	
	public static Fortune<?> fromTag(CompoundTag a) {
		FortuneType<?> type = FortuneRegistry.get(Identifier.tryParse(a.getString("type")));
		if(type == null) return null;
		else return new Fortune<>(
			type,
			FortuneQuality.byId(a.getInt("quality")),
			a.getLong("activationTick"),
			Status.byId(a.getInt("status")),
			a.getCompound("data")
		);
	}
	
	@Override
	public int compareTo(Fortune other) {
		return Long.compare(activationTick, other.activationTick);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Fortune<?> fortune = (Fortune<?>) o;
		return activationTick == fortune.activationTick &&
			type.equals(fortune.type) &&
			quality == fortune.quality &&
			status == fortune.status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(type, quality, activationTick, status);
	}
}
