package may.armorsets.gui;

import com.mojang.datafixers.util.Pair;

import may.armorsets.ArmorSets;
import may.armorsets.gui.screens.inventory.ArmorSetScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * The menu that controls the container and screen for the armor sets.
 * 
 * @author Natascha May
 * @since 1.18.2-1.0
 */
public class ArmorSetMenu extends AbstractContainerMenu {

	private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST,
			EquipmentSlot.LEGS, EquipmentSlot.FEET };
	static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[] { InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
			InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
			InventoryMenu.EMPTY_ARMOR_SLOT_HELMET };
	
	
	public final boolean active;

	public final ArmorSetContainer armorSetContainer = new ArmorSetContainer(this, ArmorSets.mc.player);

	public ArmorSetMenu(Inventory inventory, boolean active, Player owner, int containerId) {
		super((MenuType<?>) null, containerId);
		this.active = active;
		
		// armor inventory slots
		for (int k = 0; k < 4; k++) {
			//this.addSlot(owner.inventoryMenu.getSlot(k));
			final EquipmentSlot equipmentslot = SLOT_IDS[k];
			this.addSlot(new Slot(armorSetContainer, k + 5, 26, 8 + k * 18) {
				public int getMaxStackSize() {
					return 1;
				}

				public boolean mayPlace(ItemStack p_39746_) {
					return p_39746_.canEquip(equipmentslot, owner);
				}

				public boolean mayPickup(Player p_39744_) {
					ItemStack itemstack = this.getItem();
					return !itemstack.isEmpty() && !p_39744_.isCreative()
							&& EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(p_39744_);
				}

				public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
					return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
				}
			});
			
		}
		/*
		// extra set of armor slots
		for (int k = 0; k < 4; ++k) {
			final EquipmentSlot equipmentslot = SLOT_IDS[k];
			this.addSlot(new Slot(armorSetContainer, k, 26, 8 + k * 18) {
				public int getMaxStackSize() {
					return 1;
				}

				public boolean mayPlace(ItemStack p_39746_) {
					return p_39746_.canEquip(equipmentslot, owner);
				}

				public boolean mayPickup(Player p_39744_) {
					ItemStack itemstack = this.getItem();
					return !itemstack.isEmpty() && !p_39744_.isCreative()
							&& EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(p_39744_);
				}

				public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
					return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
				}
			});
		}
		*/
	}

	public static ArmorSetMenu openMenu(int containerMenuId) {
		ArmorSetMenu newMenu = new ArmorSetMenu(ArmorSets.mc.player.getInventory(), true, ArmorSets.mc.player, containerMenuId);
		ArmorSetScreen asInventoryScreen = new ArmorSetScreen(newMenu, ArmorSets.mc.player);
		ArmorSets.mc.setScreen(asInventoryScreen);
		return newMenu;
	}
	
	public boolean setContent(NonNullList<ItemStack> content) {
		for(int i = 0; i < content.size(); ++i) {
			this.getSlot(i).set(content.get(i));
	    }
		return true;
	}
	
	public boolean clickMenuButton(Player p_38875_, int p_38876_) {
		ArmorSets.LOGGER.debug("Click Menu Button (Server side)");
		if (p_38876_ == 0) {
			armorSetContainer.switchSets();
		}
		return true;
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		return true;
	}

}
