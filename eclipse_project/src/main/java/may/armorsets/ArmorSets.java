package may.armorsets;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import may.armorsets.gui.ArmorSetContainer;
import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The base class of this mod. Here, blocks, items and entities are registered,
 * saving and loading is intiated and basic, general functionality is provided.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
//The value here should match an entry in the META-INF/mods.toml file
@Mod("armorsets")
public class ArmorSets {

	public static final String MODID = "armorsets";

	public static final Logger LOGGER = LogManager.getLogger();
	public static Minecraft mc = Minecraft.getInstance();

	public static int packetMsgId = 0;
	public static int containerMenuId = 0;
	
	
	public static final KeyMapping AMOR_SETS_SWITCH_KEY = new KeyMapping("Armor Sets Switch", 67, KeyMapping.CATEGORY_INTERFACE);
	
	public static Map<Player, ArmorSetContainer> containerMap = new HashMap<Player, ArmorSetContainer>();
	
	public ArmorSets() {
		
		MinecraftForge.EVENT_BUS.register(this);
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the setup method for modloading
		bus.addListener(this::onFMLClientSetup_registerKeyBindings);
		
		
		ArmorSetsPacketHandler.INSTANCE.registerMessage(packetMsgId++, ArmorSetsSwitchSetsPacket.class,
				(msg, buff) -> msg.write(buff), (buff) -> new ArmorSetsSwitchSetsPacket(buff), (msg, ctx) -> ArmorSetsSwitchSetsPacket.handle(msg, ctx));
		
	}
	
	private void onFMLClientSetup_registerKeyBindings(final FMLClientSetupEvent event) {
		net.minecraftforge.client.ClientRegistry.registerKeyBinding(AMOR_SETS_SWITCH_KEY);
	}
	
	@SubscribeEvent
	public void onWorldLoaded(final WorldEvent.Load event){
	}
	
	@SubscribeEvent
	public void onPlayerJoin_addToContainerMap(final PlayerLoggedInEvent event){
		containerMap.put(event.getPlayer(), new ArmorSetContainer(event.getPlayer()));
	}
	
	
	@SubscribeEvent
    public void onKeyPress (InputEvent.KeyInputEvent e) {
        if (AMOR_SETS_SWITCH_KEY.isDown()) {
        	ArmorSets.LOGGER.debug("Key press recognized");
    		ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
        }
    }

}
