package may.armorsets.gui;

import may.armorsets.ArmorSets;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
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
public class ArmorSetContainer implements Container {

	private final int num_of_sets = 1;
	private final int size_of_set = 4;
	private final ArmorSetMenu menu;
	private final Player owner;

	private final NonNullList<ItemStack> armorSetItemStacks;

	public ArmorSetContainer(ArmorSetMenu menu, Player owner) {
		
		this.menu = menu;
		this.armorSetItemStacks = NonNullList.withSize(num_of_sets * size_of_set, ItemStack.EMPTY);
		this.owner = owner;
	}
	
	public void init() {
		for(int i = 0; i < size_of_set; i++) {
			ItemStack itemStack = owner.getInventory().armor.get(0);
			setItem(0, itemStack);
		}
	}

	public void switchSets() {
		ArmorSets.LOGGER.debug("Switch Armor Sets");
		// NonNullList<ItemStack> temp = owner.getInventory().armor;

		// owner.getInventory().armor.forEach((x) ->
		// owner.getInventory().removeItem(x));
		// owner.getInventory().armor.forEach((x) -> owner.getInventory().);

		// extractItem
		// https://forums.minecraftforge.net/topic/75848-solved1144-remove-single-item-from-player-inventory/

		// convert the saved slots armor_set to a collection of ItemStacks
		// Collection<ItemStack> container_set = Arrays.stream(armor_set).map((slot) ->
		// (ItemStack) slot.getItem()).collect(Collectors.toList());
		// owner.getInventory().armor.addAll(container_set);

		// temp.forEach((i) -> this.addItem(i));

		// SETUP
		LazyOptional<IItemHandler> itemHandlerLazy = owner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if (!itemHandlerLazy.isPresent()) {
			return;
		}
		IItemHandler itemHandler = itemHandlerLazy.resolve().get();

		// EXTRACT FROM INVENTORY
		// ItemStack itemStack = itemHandler.extractItem(Player.ARMOR_SLOT_OFFSET,
		// LARGE_MAX_STACK_SIZE, false);
		ItemStack itemStack = owner.getInventory().armor.get(0);
		ArmorSets.LOGGER.debug("extracted " + itemStack.toString() + " from inventory");
		// EXTRACT FROM ARMOR SET
		ItemStack stackFromSet = getItem(0);
		// ItemStack stackFromSet = armor_set[0].safeTake(LARGE_MAX_STACK_SIZE,
		// LARGE_MAX_STACK_SIZE, owner);

		ArmorSets.LOGGER.debug("extracted " + stackFromSet.toString() + " from armor set");

		// INSERT TO INVENTORY
		// itemHandler.insertItem(Player.ARMOR_SLOT_OFFSET, stackFromSet, false);
		owner.getInventory().armor.set(0, stackFromSet);

		// INSERT TO ARMOR SET
		setItem(0, itemStack);
		//inventoryMenu.slotsChanged(owner.getInventory());
		// armor_set[0].safeInsert(itemStack);
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
		for(int i = 0; i < size_of_set; i++) {
			ItemStack stackFromSet = armorSetItemStacks.get(0);
			owner.getInventory().armor.set(0, stackFromSet);
		}
	}

	public NonNullList<ItemStack> getItems() {
		return armorSetItemStacks;
	}
	
	

}
