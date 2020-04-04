package someones.modfest.mod.unfortunately.fortune;

import java.util.function.Predicate;

public class QualityRange implements Predicate<FortuneQuality> {
	public QualityRange(int low, int high) {
		this.low = low;
		this.high = high;
	}
	
	public static QualityRange only(FortuneQuality a) {
		return inclusive(a, a);
	}
	
	public static QualityRange inclusive(FortuneQuality low, FortuneQuality high) {
		return new QualityRange(low.getId(), high.getId());
	}
	
	public static QualityRange diverge(FortuneQuality basis, int amount) {
		return new QualityRange(basis.getId() - amount, basis.getId() + amount);
	}
	
	private static QualityRange NEVER = new QualityRange(100, -100);
	public static QualityRange never() {
		return NEVER; //bigger than 100, smaller than -100
	}
	
	private final int low;
	private final int high;
	
	@Override
	public boolean test(FortuneQuality quality) {
		return quality.getId() >= low && quality.getId() <= high;
	}
}
