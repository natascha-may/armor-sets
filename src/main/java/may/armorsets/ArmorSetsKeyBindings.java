package may.armorsets;

import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorSetsKeyBindings {

	public static final KeyMapping AMOR_SETS_SWITCH_KEY = new KeyMapping("Armor Sets Switch", 86,
			KeyMapping.CATEGORY_INTERFACE);
	
	

	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e) {
		if (AMOR_SETS_SWITCH_KEY.isDown()) {
			ArmorSets.LOGGER.debug("Key press recognized");
			ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
		}
	}
}
