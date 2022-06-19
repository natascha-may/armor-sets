package may.armorsets.networking;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ArmorSetsSwitchSetsPacket {
	
	
	public ArmorSetsSwitchSetsPacket() {
	}

	public ArmorSetsSwitchSetsPacket(FriendlyByteBuf p_179567_) {
	}

	public void write(FriendlyByteBuf p_133937_) {
	}
	
	public static void handle(ArmorSetsSwitchSetsPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() ->
			// Make sure it's only executed on the physical client
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ArmorSetsPacketHandler.handleSwitchSets(msg, ctx)));
		ctx.get().setPacketHandled(true);
	}

}
