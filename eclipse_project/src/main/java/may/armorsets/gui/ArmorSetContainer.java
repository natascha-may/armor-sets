package may.armorsets.gui;

import may.armorsets.ArmorSets;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * The container that holds the extra armor sets.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
public class ArmorSetContainer extends AbstractContainerMenu implements Container{

	private final int num_of_sets = 1;
	private final int size_of_set = 4;
	private final Player owner;

	private final NonNullList<ItemStack> armorSetItemStacks;

	public ArmorSetContainer(Player owner) {
		super(null, 0);
		this.armorSetItemStacks = NonNullList.withSize(num_of_sets * size_of_set, ItemStack.EMPTY);
		this.owner = owner;
	}
	
	public void init() {
		for(int i = 0; i < size_of_set; i++) {
			ItemStack itemStack = owner.getInventory().armor.get(0);
			setItem(0, itemStack);
		}
	}

	public boolean switchSets() {
		ArmorSets.LOGGER.debug("Switch Armor Sets");
		
		for(int i = 0; i < size_of_set; i++) {
			
			// EXTRACT FROM INVENTORY
			ItemStack itemStack = owner.getInventory().armor.get(i);
			ArmorSets.LOGGER.debug("extracted " + itemStack.toString() + " from inventory");
			
			// EXTRACT FROM ARMOR SET
			ItemStack stackFromSet = getItem(i);
			
			ArmorSets.LOGGER.debug("extracted " + stackFromSet.toString() + " from armor set");
	
			// INSERT TO INVENTORY
			owner.getInventory().armor.set(i, stackFromSet);
	
			// INSERT TO ARMOR SET
			setItem(i, itemStack);
			
		}
		return true;
	}

	@Override
	public void clearContent() {
		this.armorSetItemStacks.clear();
	}

	@Override
	public int getContainerSize() {
		return this.armorSetItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		return this.armorSetItemStacks.stream().allMatch(i -> i.isEmpty());
	}

	@Override
	public ItemStack getItem(int index) {
		return index >= this.getContainerSize() ? ItemStack.EMPTY : this.armorSetItemStacks.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int amount) {
		return ContainerHelper.removeItem(this.armorSetItemStacks, index, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.armorSetItemStacks, index);
	}

	@Override
	public void setItem(int index, ItemStack itemStack) {
		this.armorSetItemStacks.set(index, itemStack);
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player p_18946_) {
		return true;
	}

	public void tick() {
		/*
		for(int i = 0; i < size_of_set; i++) {
			ItemStack stackFromSet = armorSetItemStacks.get(0);
			owner.getInventory().armor.set(0, stackFromSet);
		}
		*/
	}

	public NonNullList<ItemStack> getItems() {
		return armorSetItemStacks;
	}
	
	

}
