package com.moddybunch.encrusted;

import com.moddybunch.encrusted.util.EncrustedRegistries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;

public class Encrusted implements ModInitializer {

	// Our modid, reference this instead of actually typing "encrusted"
	public static final String MODID = "encrusted";
	// no you

	// Creates the new chest loot tables
	public static final Identifier Ruined_Portal_Loot_Table_ID = new Identifier("minecraft", "chests/ruined_portal");

	@Override
	public void onInitialize() {

		EncrustedRegistries.init();
		modifyLootTables();
	}

	private void modifyLootTables() {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			// Checks for the ruined portal chest loot table
			if (Ruined_Portal_Loot_Table_ID.equals(id)){
				// Adds the ruby to the loot table
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(LootTableEntry.builder(Ruined_Portal_Loot_Table_ID));
				supplier.withPool(poolBuilder.build());
			}


		} );
	}
}
