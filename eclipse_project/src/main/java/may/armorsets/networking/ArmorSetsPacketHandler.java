package may.armorsets.networking;

import java.util.function.Supplier;

import may.armorsets.ArmorSets;
import may.armorsets.gui.ArmorSetMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ArmorSetsPacketHandler{

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	  new ResourceLocation(ArmorSets.MODID, "main"),
	  () -> PROTOCOL_VERSION,
	  PROTOCOL_VERSION::equals,
	  PROTOCOL_VERSION::equals
	);
	
	public static void handleMenuButtonClick(ArmorSetsMenuButtonClickPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be thread-safe (most work)
			ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
			// Do stuff
			if (!msg.getPlayer().isSpectator()) {
				AbstractContainerMenu menu = ArmorSets.getContainerMenu(msg.getContainerId());
				boolean flag = menu.clickMenuButton(msg.getPlayer(), msg.getButtonId());
				if (flag) {
					menu.broadcastChanges();
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public static void handleContainerSetContent(ArmorSetsContainerSetContentPacket msg, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be thread-safe (most work)
			ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
			// Do stuff
			ArmorSetMenu menu = ArmorSets.getContainerMenu(msg.getContainerId());
			boolean flag = menu.setContent(msg.getContent());
			if (flag) {
				menu.broadcastChanges();
			}
			
		});
		ctx.get().setPacketHandled(true);
	}
}
