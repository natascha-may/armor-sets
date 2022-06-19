package may.armorsets;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import may.armorsets.gui.ArmorSetMenu;
import may.armorsets.gui.screens.inventory.ArmorSetScreen;
import may.armorsets.networking.ArmorSetsContainerSetContentPacket;
import may.armorsets.networking.ArmorSetsMenuButtonClickPacket;
import may.armorsets.networking.ArmorSetsPacketHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.common.MinecraftForge;
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
	
	private static HashMap<Integer, ArmorSetMenu> containerMenus = new HashMap<Integer, ArmorSetMenu>();
	
	public static final KeyMapping AMOR_SETS_MENU_KEY = new KeyMapping("Armor Sets Menu", 67, KeyMapping.CATEGORY_INTERFACE);
	
	public ArmorSets() {
		
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(ArmorSetScreen.class);
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		// Register the setup method for modloading
		bus.addListener(this::onFMLClientSetup_registerKeyBindings);
		
		ArmorSetsPacketHandler.INSTANCE.registerMessage(packetMsgId++, ArmorSetsMenuButtonClickPacket.class,
				(msg, buff) -> msg.write(buff), (buff) -> new ArmorSetsMenuButtonClickPacket(buff), (msg, ctx) -> ArmorSetsMenuButtonClickPacket.handle(msg, ctx));
		ArmorSetsPacketHandler.INSTANCE.registerMessage(packetMsgId++, ArmorSetsContainerSetContentPacket.class,
				(msg, buff) -> msg.write(buff), (buff) -> new ArmorSetsContainerSetContentPacket(buff), (msg, ctx) -> ArmorSetsContainerSetContentPacket.handle(msg, ctx));
		
		
	}
	
	private void onFMLClientSetup_registerKeyBindings(final FMLClientSetupEvent event) {
		net.minecraftforge.client.ClientRegistry.registerKeyBinding(AMOR_SETS_MENU_KEY);
	}
	
	@SubscribeEvent
	public void onWorldLoaded(final WorldEvent.Load event){
	}
	
	@SubscribeEvent
    public void onKeyPress (InputEvent.KeyInputEvent e) {
        if (AMOR_SETS_MENU_KEY.isDown()) {
        	ArmorSetMenu menu = ArmorSetMenu.openMenu(containerMenuId);
            containerMenus.put(containerMenuId, menu);
    		containerMenuId++;
        }
    }

	
	/*
	@SubscribeEvent
	public void setScreen(ScreenOpenEvent event) {
		if(!(event.getScreen() instanceof InventoryScreen)) {
			return;
		}
		if(event.getScreen() instanceof ArmorSetScreen) {
			return;
		}
		
		
		event.setScreen(asInventoryScreen);
	}*/
	
	
	public static ArmorSetMenu getContainerMenu(int containerId) {
		return containerMenus.get(containerId);
	}
}
