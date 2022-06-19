package may.armorsets.networking;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

/**
 * This packet is sent from the client side to notify the server that the armor
 * sets should be switched.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
public class ArmorSetsSwitchSetsPacket {

	public ArmorSetsSwitchSetsPacket() {
	}

	public ArmorSetsSwitchSetsPacket(FriendlyByteBuf p_179567_) {
	}

	public void write(FriendlyByteBuf p_133937_) {
	}

	/**
	 * Passes the packet on to the packet handler by calling the corresponding
	 * handle method.
	 * 
	 * @param msg the packet that is passed on
	 * @param ctx a network context that gives access to communication info like the
	 *            sender of the packet
	 */
	public static void handle(ArmorSetsSwitchSetsPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() ->
		// Make sure it's only executed on the physical client
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ArmorSetsPacketHandler.handleSwitchSets(msg, ctx)));
		ctx.get().setPacketHandled(true);
	}

}
