package someones.modfest.mod.unfortunately.fortune;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import someones.modfest.mod.unfortunately.Unfortunately;

import java.util.Locale;
import java.util.Random;

public enum FortuneQuality {
	TERRIBLE(-3),
	POOR(-2),
	BAD(-1),
	NEUTRAL(0),
	GOOD(1),
	GREAT(2),
	AMAZING(3)
	;
	
	FortuneQuality(int num) {
		this.num = num;
	}
	
	private int num;
	
	public int getId() {
		return num;
	}
	
	public static FortuneQuality byId(int id) {
		switch(id) {
			case -3: default: return TERRIBLE;
			case -2: return POOR;
			case -1: return BAD;
			case 0: return NEUTRAL;
			case 1: return GOOD;
			case 2: return GREAT;
			case 3: return AMAZING;
		}
	}
	
	public FortuneQuality perturb(Random random) {
		int newId = getId() + (random.nextBoolean() ? 1 : -1);
		if(newId <= -4 || newId >= 4) return this;
		else return byId(newId);
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase(Locale.ROOT);
	}
	
	public String toTranslationKey() {
		return Unfortunately.MODID + ".quality." + toString();
	}
	
	public Text toText() {
		return new TranslatableText(toTranslationKey());
	}
}
