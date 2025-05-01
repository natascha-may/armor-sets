package may.armorsets.config;

import may.armorsets.ArmorSets;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Defines all options that are available in the config file and their initial values.
 * 
 *
 */
public class ConfigOptions {

	public static ForgeConfigSpec.BooleanValue followVanillaKeepInventoryRule;
	public static ForgeConfigSpec.BooleanValue keepArmorSetOnDeath;
	
	
	
	
	public static void init(ForgeConfigSpec.Builder builder) {
		builder.comment("ArmorSets Config");
		
		followVanillaKeepInventoryRule = builder
				.comment("If set to true, the option \"keepArmorSetOnDeath\" will be ignored and the armor set will be either dropped or kept depending on the vanilla gamerule \"keepInventory\".")
				.define(ArmorSets.MODID + ".followVanillaKeepInventoryRule", true);
		
		keepArmorSetOnDeath = builder
				.comment("If set to true, the extra armor set will not be dropped on death but remain in the inventory.")
				.define(ArmorSets.MODID + ".keepArmorSetOnDeath", false);
	
		
	}	
	
}

