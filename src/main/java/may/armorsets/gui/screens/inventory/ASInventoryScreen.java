package may.armorsets.gui.screens.inventory;


import may.armorsets.ArmorSets;
import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The new inventory screen with the added armor sets.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
@OnlyIn(Dist.CLIENT)
public class ASInventoryScreen extends InventoryScreen{
	private static final ResourceLocation MY_BUTTON_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/my_button.png");
	
	public ASInventoryScreen(Player player) {
		super(player);
	}
	
	protected void init() {
		super.init();
		ArmorSets.LOGGER.debug("init");
		this.addRenderableWidget(new ImageButton(
		  	this.leftPos + 76, // x
		  	this.topPos + 62 - 19, // y
			18, // width
			18, // height
			0, // xTexStart?
			0, // yTexStart?
			0, // no idea, reused height as default
			MY_BUTTON_LOCATION,
			18, // tex width?
			18, // tex height?
			(p_98880_) -> {onButtonPress();}));
		
		/*
		this.addRenderableWidget(new PlainTextButton(
				this.leftPos, // x
			  	this.topPos, // y
				20, // width
				20, // height
				new TextComponent("Test"),
				(p) -> {},
				ArmorSets.mc.font
				));
				*/
	}
	
	@SubscribeEvent
	public static void setScreen(ScreenOpenEvent event) {
		if(!(event.getScreen() instanceof InventoryScreen)) {
			return;
		}
		if(event.getScreen() instanceof ASInventoryScreen) {
			return;
		}
		event.setScreen(new ASInventoryScreen(Minecraft.getInstance().player));
	}
	
	private void onButtonPress() {
		ArmorSets.LOGGER.debug("Button pressed");
		ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
	}
}
