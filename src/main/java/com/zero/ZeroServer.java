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
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.zero.utils.ComponentTranslate;

import java.util.Map;

public class ZeroServer implements CarpetExtension, ModInitializer
{
    public static String MOD_ID = "Zero-additions";

    private static MinecraftServer minecraftServer;

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static MinecraftServer getServer() {
        return minecraftServer;
    }

    public String get_version() {
        return "1.0.0";
    }

    public static void loadExtension() {
        CarpetServer.manageExtension(new ZeroServer());
    }

    public static Boolean shouldKeepPearl;

    @Override
    public void onInitialize() {
        shouldKeepPearl = Boolean.getBoolean("pearl.keep");
        ZeroServer.loadExtension();
    }

    @Override
    public void onGameStarted() {
        LOGGER.info(MOD_ID + " " + "v" + get_version() + "载入成功");
        CarpetServer.settingsManager.parseSettingsClass(ZeroSettings.class);

    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return ComponentTranslate.getTranslationFromResourcePath(lang);
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        minecraftServer = server;
    }
}