package may.armorsets.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


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

	public ArmorSetsSwitchSetsPacket(PacketBuffer p_179567_) {
	}

	public void write(PacketBuffer p_133937_) {
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
		ctx.get().enqueueWork(() -> ArmorSetsPacketHandler.handleSwitchSets(msg, ctx));
		ctx.get().setPacketHandled(true);
	}

}
