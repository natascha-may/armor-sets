package may.armorsets.gui.screens.inventory;

import may.armorsets.ArmorSets;
import may.armorsets.networking.ArmorSetsPacketHandler;
import may.armorsets.networking.ArmorSetsSwitchSetsPacket;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ASCreativeModeInventoryScreen extends CreativeModeInventoryScreen {
    private static final ResourceLocation MY_BUTTON_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/swap_sets_button.png");
    private ImageButton swapSetsImageButton;
    private static final int BUTTON_SIZE = 18;

    public ASCreativeModeInventoryScreen(Player player) {
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
    }

    @Override
    protected void selectTab(CreativeModeTab tab){
        if(tab == CreativeModeTab.TAB_INVENTORY){
            this.addRenderableWidget(swapSetsImageButton);
        }else {
            this.removeWidget(swapSetsImageButton);
        }
        super.selectTab(tab);
    }

    private int swapSetsImageButtonPosX() {
        return this.leftPos + 10;
    }

    private int swapSetsImageButtonPosY() {
        return this.topPos + 19;
    }

    private void onButtonPress() {
        ArmorSets.LOGGER.debug("Button pressed");
        ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsSwitchSetsPacket());
    }
}
