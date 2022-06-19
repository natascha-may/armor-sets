package may.armorsets.networking;

import java.util.function.Supplier;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ArmorSetsContainerSetContentPacket{

	private final int containerId;
	private final NonNullList<ItemStack> content;
	

	public ArmorSetsContainerSetContentPacket(int containerId, NonNullList<ItemStack> content) {
		this.containerId = containerId;
		this.content = content;
	}

	public ArmorSetsContainerSetContentPacket(FriendlyByteBuf p_179567_) {
		this.containerId = p_179567_.readInt();
		this.content = p_179567_.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readItem);
	}

	public void write(FriendlyByteBuf p_133937_) {
		p_133937_.writeInt(this.containerId);
		p_133937_.writeCollection(this.content, FriendlyByteBuf::writeItem);
	}

	
	public static void handle(ArmorSetsContainerSetContentPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() ->
			// Make sure it's only executed on the physical client
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> ArmorSetsPacketHandler.handleContainerSetContent(msg, ctx)));
		ctx.get().setPacketHandled(true);
	}

	public int getContainerId() {
		return this.containerId;
	}

	public NonNullList<ItemStack> getContent() {
		return content;
	}

}
