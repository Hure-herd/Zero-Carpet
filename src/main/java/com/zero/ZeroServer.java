/*
 * This file is part of the Carpet AMS Addition project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2025 A Minecraft Server and contributors
 *
 * Carpet AMS Addition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpet AMS Addition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpet AMS Addition. If not, see <https://www.gnu.org/licenses/>.
 */

package com.zero;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import com.zero.utils.ComponentTranslate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ZeroServer implements CarpetExtension, ModInitializer
{
    public static final String MOD_ID = "zerocarpet";

    public static final String MOD_NAME = "Zero Carpet";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static final ZeroServer INSTANCE = new ZeroServer();

    private MinecraftServer minecraftServer;

    public static MinecraftServer getServer() {
        if (INSTANCE.minecraftServer == null) {
            throw new RuntimeException("MinecraftServer hasn't finished initializing yet!");
        } else {
            return INSTANCE.minecraftServer;
        }
    }

    public static String getVersion() {
        return FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
    }

    public static void loadExtension() {
        CarpetServer.manageExtension(INSTANCE);
    }

    @Override
    public void onInitialize() {
        ZeroServer.loadExtension();
    }

    @Override
    public void onGameStarted() {
        LOGGER.info("{} v{} 载入成功", MOD_NAME, getVersion());
        CarpetServer.settingsManager.parseSettingsClass(ZeroSettings.class);

    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return ComponentTranslate.getTranslationFromResourcePath(lang);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        this.minecraftServer = server;
    }
}