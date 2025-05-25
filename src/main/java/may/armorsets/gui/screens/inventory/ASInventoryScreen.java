package may.armorsets.gui.screens.inventory;


import com.mojang.blaze3d.matrix.MatrixStack;
import may.armorsets.ArmorSets;
import may.armorsets.config.ConfigOptions;
import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The new inventory screen with the added armor sets.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
@OnlyIn(Dist.CLIENT)
public class ASInventoryScreen extends InventoryScreen {
	private static final ResourceLocation MY_BUTTON_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/swap_sets_button.png");
	private ImageButton swapSetsImageButton;
	private static final int BUTTON_SIZE = 18;

	public ASInventoryScreen(PlayerEntity player) {
		super(player);
	}

	@Override
	protected void init() {
		super.init();
		ArmorSets.LOGGER.debug("init");
		swapSetsImageButton = new ImageButton(
				swapSetsImageButtonPosX(), // x
				swapSetsImageButtonPosY(), // y
				BUTTON_SIZE, // width
				BUTTON_SIZE, // height
				0, // xTexStart
				0, // yTexStart
				BUTTON_SIZE, // yDiffTex, diff in yTexStart for hover texture
				MY_BUTTON_LOCATION,
				BUTTON_SIZE, // width of texture map
				BUTTON_SIZE * 2, // height of texture map
				button -> onButtonPress()
		);
		this.addButton(swapSetsImageButton);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		swapSetsImageButton.setPosition(this.leftPos + 76, this.topPos + 62 - 19);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@SubscribeEvent
	public static void setScreen(GuiOpenEvent event) {
		if(!ConfigOptions.showSwapSetsButtonInInventory()){
			return;
		}
		if(event.getGui() instanceof InventoryScreen && !(event.getGui() instanceof ASInventoryScreen)) {
			event.setGui(new ASInventoryScreen(Minecraft.getInstance().player));
			return;
		}
		if (event.getGui() instanceof CreativeScreen && !(event.getGui() instanceof ASCreativeModeInventoryScreen)) {
			event.setGui(new ASCreativeModeInventoryScreen(Minecraft.getInstance().player));
		}
	}

	private int swapSetsImageButtonPosX() {
		return this.leftPos + 76;
	}

	private int swapSetsImageButtonPosY() {
		return this.topPos + 62 - 19;
	}

	private void onButtonPress() {
		ArmorSets.LOGGER.debug("Button pressed");
		ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
	}
}
