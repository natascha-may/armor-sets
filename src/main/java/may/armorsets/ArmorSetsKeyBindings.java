package may.armorsets;

import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorSetsKeyBindings {

	public static final KeyBinding AMOR_SETS_SWITCH_KEY = new KeyBinding("Armor Sets Switch", 86,
			"key.categories.ui");

	@SubscribeEvent
	public static void onKeyPress(InputEvent.KeyInputEvent e) {
		if (AMOR_SETS_SWITCH_KEY.isDown()) {
			ArmorSets.LOGGER.debug("Key press recognized");
			ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
		}
	}
}
