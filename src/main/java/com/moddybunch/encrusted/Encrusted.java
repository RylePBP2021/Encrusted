package com.moddybunch.encrusted;

import com.moddybunch.encrusted.util.EncrustedRegistries;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Encrusted implements ModInitializer {

	/**
	 * Our modid, reference this instead of actually typing "encrusted"
 	 */
	public static final String MODID = "encrusted";

	/**
	 * Logger, this can be used to create outputs in the console so that we can test
	 * when things are breaking or create info/error messages. For info messages, use .info(String message)
 	 */
	public static final Logger EncrustedLog = LogManager.getLogger(MODID);

	// no you
	@Override
	public void onInitialize() {
		EncrustedRegistries.init();
	}
}
