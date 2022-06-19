package may.armorsets.networking;

import java.util.function.Supplier;

import may.armorsets.ArmorSets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ArmorSetsMenuButtonClickPacket{

	private final int containerId;
	private final int buttonId;
	private final Player player;

	public ArmorSetsMenuButtonClickPacket(int containerId, int buttonId, Player player) {
		this.containerId = containerId;
		this.buttonId = buttonId;
		this.player = player;
	}

	public ArmorSetsMenuButtonClickPacket(FriendlyByteBuf p_179567_) {
		this.containerId = p_179567_.readInt();
		this.buttonId = p_179567_.readInt();
		this.player = ArmorSets.mc.player;
	}

	public void write(FriendlyByteBuf p_133937_) {
		p_133937_.writeInt(this.containerId);
		p_133937_.writeInt(this.buttonId);
	}

	
	public static void handle(ArmorSetsMenuButtonClickPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() ->
			// Make sure it's only executed on the physical client
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ArmorSetsPacketHandler.handleMenuButtonClick(msg, ctx)));
		ctx.get().setPacketHandled(true);
	}

	public int getContainerId() {
		return this.containerId;
	}

	public int getButtonId() {
		return this.buttonId;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
