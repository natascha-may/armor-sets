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
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ASCreativeModeInventoryScreen extends CreativeModeInventoryScreen {
    private static final ResourceLocation MY_BUTTON_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/my_button.png");
    private ImageButton swapSetsImageButton;

    public ASCreativeModeInventoryScreen(Player player) {
        super(player);
    }

    @Override
    protected void init() {
        super.init();
        ArmorSets.LOGGER.debug("init");
        swapSetsImageButton = new ImageButton(
                swapSetsImageButtonPosX(),
                swapSetsImageButtonPosY(),
                18, // width
                18, // height
                0, // xTexStart?
                0, // yTexStart?
                0, // no idea, reused height as default
                MY_BUTTON_LOCATION,
                18, // tex width?
                18, // tex height?
                button -> onButtonPress()
        );
    }

    @Override
    protected void selectTab(@NotNull CreativeModeTab tab){
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
