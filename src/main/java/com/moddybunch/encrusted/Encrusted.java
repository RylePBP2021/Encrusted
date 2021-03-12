package com.moddybunch.encrusted;

import com.moddybunch.encrusted.util.EncrustedRegistries;
import net.fabricmc.api.ModInitializer;


public class Encrusted implements ModInitializer {

	// Our modid, reference this instead of actually typing "encrusted"
	public static final String MODID = "encrusted";
	// no you

	@Override
	public void onInitialize() {
		EncrustedRegistries.init();
	}


}
