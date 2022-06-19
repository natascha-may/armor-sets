package may.armorsets.networking;

import java.util.function.Supplier;

import may.armorsets.ArmorSets;
import may.armorsets.gui.ArmorSetContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
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
	
	public static void handleSwitchSets(ArmorSetsSwitchSetsPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be thread-safe (most work)
			ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
			// Do stuff
			if (!sender.isSpectator()) {
				ArmorSets.LOGGER.debug("Handle switch sets in PacketHandler");
				ArmorSetContainer container = ArmorSets.containerMap.get(sender);
				boolean flag = container.switchSets();
				if (flag) {
					container.broadcastChanges();
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
