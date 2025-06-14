package may.armorsets.config;

import may.armorsets.ArmorSets;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Defines all options that are available in the config file and their initial values.
 * 
 *
 */
public class ConfigOptions {

	private ConfigOptions() {}

	private static ForgeConfigSpec.BooleanValue followVanillaKeepInventoryRule;
	private static ForgeConfigSpec.BooleanValue keepArmorSetOnDeath;
	private static ForgeConfigSpec.BooleanValue showSwapSetsButtonInInventory;
	
	public static void init(ForgeConfigSpec.Builder builder) {
		builder.comment("ArmorSets Config");
		
		followVanillaKeepInventoryRule = builder
				.comment("If set to true, the option \"keepArmorSetOnDeath\" will be ignored and the armor set will be either dropped or kept depending on the vanilla gamerule \"keepInventory\".")
				.define(ArmorSets.MODID + ".followVanillaKeepInventoryRule", true);
		
		keepArmorSetOnDeath = builder
				.comment("If set to true, the extra armor set will not be dropped on death but remain in the inventory.")
				.define(ArmorSets.MODID + ".keepArmorSetOnDeath", false);

		showSwapSetsButtonInInventory = builder
				.comment("If set to true, a button in the inventory will be displayed that swaps the armor sets when pressed.")
				.define(ArmorSets.MODID + ".showSwapSetsButtonInInventory", true);
	}

	public static boolean followVanillaKeepInventoryRule() {
		return followVanillaKeepInventoryRule.get();
	}

	public static boolean keepArmorSetOnDeath() {
		return keepArmorSetOnDeath.get();
	}

	public static boolean showSwapSetsButtonInInventory() {
		return showSwapSetsButtonInInventory.get();
	}
	
}

