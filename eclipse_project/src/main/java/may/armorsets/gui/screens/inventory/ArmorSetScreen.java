package may.armorsets.gui.screens.inventory;


import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import may.armorsets.ArmorSets;
import may.armorsets.gui.ArmorSetMenu;
import may.armorsets.networking.ArmorSetsContainerSetContentPacket;
import may.armorsets.networking.ArmorSetsMenuButtonClickPacket;
import may.armorsets.networking.ArmorSetsPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The new screen with the added armor sets.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
@OnlyIn(Dist.CLIENT)
public class ArmorSetScreen extends EffectRenderingInventoryScreen<ArmorSetMenu>{
	public static final ResourceLocation ARMOR_SET_SCREEN_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/container/armor_set.png");
	private static final ResourceLocation MY_BUTTON_LOCATION = new ResourceLocation(ArmorSets.MODID, "textures/gui/my_button.png");
	public final Player player;
	private float xMouse;
	private float yMouse;
	
	public ArmorSetScreen(ArmorSetMenu menu, Player player) {
		super(menu, player.getInventory(), new TranslatableComponent(ArmorSets.MODID, "container.armor_set"));
		this.player = player;
		this.titleLabelX = 97;
		this.imageWidth = 119;
		this.imageHeight = 86;
	}
	
	protected void init() {
		super.init();
		ArmorSets.LOGGER.debug("init");

		
		this.addRenderableWidget(new ImageButton(
		  	this.leftPos + 94, // x
		  	this.topPos + 61, // y
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
		sendInitialData(menu.armorSetContainer.getItems());
		
	}
	
	public void sendInitialData(NonNullList<ItemStack> content) {
        ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsContainerSetContentPacket(menu.containerId, content));
     }
	
	protected void containerTick() {
		menu.armorSetContainer.tick();
	}
	
	public void render(PoseStack p_98875_, int p_98876_, int p_98877_, float p_98878_) {
		this.renderBackground(p_98875_);
		super.render(p_98875_, p_98876_, p_98877_, p_98878_);

		this.renderTooltip(p_98875_, p_98876_, p_98877_);
		this.xMouse = (float) p_98876_;
		this.yMouse = (float) p_98877_;
	}
	
	protected void renderLabels(PoseStack p_98889_, int p_98890_, int p_98891_) {
		// overrides renderLabels from AbstractContainerScreen      
	}
	
	protected void renderBg(PoseStack p_98870_, float p_98871_, int p_98872_, int p_98873_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, ARMOR_SET_SCREEN_LOCATION);
		int i = this.leftPos;
		int j = this.topPos;
		this.blit(p_98870_, i, j, 0, 0, this.imageWidth, this.imageHeight);
		renderEntityInInventory(i + 51 + 18, j + 75, 30, (float) (i + 51) - this.xMouse, (float) (j + 75 - 50) - this.yMouse,
				this.minecraft.player);
	}

	@SuppressWarnings("deprecation")
	public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_,
			LivingEntity p_98856_) {
		float f = (float) Math.atan((double) (p_98854_ / 40.0F));
		float f1 = (float) Math.atan((double) (p_98855_ / 40.0F));
		PoseStack posestack = RenderSystem.getModelViewStack();
		posestack.pushPose();
		posestack.translate((double) p_98851_, (double) p_98852_, 1050.0D);
		posestack.scale(1.0F, 1.0F, -1.0F);
		RenderSystem.applyModelViewMatrix();
		PoseStack posestack1 = new PoseStack();
		posestack1.translate(0.0D, 0.0D, 1000.0D);
		posestack1.scale((float) p_98853_, (float) p_98853_, (float) p_98853_);
		Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
		Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
		quaternion.mul(quaternion1);
		posestack1.mulPose(quaternion);
		float f2 = p_98856_.yBodyRot;
		float f3 = p_98856_.getYRot();
		float f4 = p_98856_.getXRot();
		float f5 = p_98856_.yHeadRotO;
		float f6 = p_98856_.yHeadRot;
		p_98856_.yBodyRot = 180.0F + f * 20.0F;
		p_98856_.setYRot(180.0F + f * 40.0F);
		p_98856_.setXRot(-f1 * 20.0F);
		p_98856_.yHeadRot = p_98856_.getYRot();
		p_98856_.yHeadRotO = p_98856_.getYRot();
		Lighting.setupForEntityInInventory();
		EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		quaternion1.conj();
		entityrenderdispatcher.overrideCameraOrientation(quaternion1);
		entityrenderdispatcher.setRenderShadow(false);
		MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers()
				.bufferSource();
		RenderSystem.runAsFancy(() -> {
			entityrenderdispatcher.render(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1,
					multibuffersource$buffersource, 15728880);
		});
		multibuffersource$buffersource.endBatch();
		entityrenderdispatcher.setRenderShadow(true);
		p_98856_.yBodyRot = f2;
		p_98856_.setYRot(f3);
		p_98856_.setXRot(f4);
		p_98856_.yHeadRotO = f5;
		p_98856_.yHeadRot = f6;
		posestack.popPose();
		RenderSystem.applyModelViewMatrix();
		Lighting.setupFor3DItems();
	}
	
	
	private void onButtonPress() {
		ArmorSets.LOGGER.debug("Button pressed");
		ArmorSetsPacketHandler.INSTANCE.sendToServer(new ArmorSetsMenuButtonClickPacket(menu.containerId, 0, player));
	}

}
