package com.l33tfox.gliding;

import com.l33tfox.gliding.items.ModItemsRegistry;
import com.l33tfox.gliding.networking.ModPacketsRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gliding implements ModInitializer {
	public static final String MOD_ID = "gliding";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemsRegistry.initialize();

		// Registered on common side (both client and server)
		ModPacketsRegistry.registerC2SPackets();
		ModPacketsRegistry.registerS2CPackets();
		ModPacketsRegistry.registerC2SReceivers();
	}
}