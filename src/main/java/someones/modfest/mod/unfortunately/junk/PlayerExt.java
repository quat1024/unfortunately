package someones.modfest.mod.unfortunately.junk;

import someones.modfest.mod.unfortunately.fortune.Fortune;
import someones.modfest.mod.unfortunately.fortune.FortuneType;

import java.util.Set;

public interface PlayerExt {
	Set<Fortune> getFortunes();
	
	default Fortune getFirstActiveFortuneOfType(Class<? extends FortuneType> typeClass) {
		for(Fortune fortune : getFortunes()) {
			if(fortune.getStatus() == Fortune.Status.ACTIVE && fortune.getType().getClass().equals(typeClass)) return fortune;
		}
		
		return null;
	}
}
