package com.zero;

import carpet.api.settings.Rule;
import static com.zero.utils.ZeroRuleCategory.*;


public class ZeroSettings
{
    @Rule(
           categories = {ZERO, FEATURE}
    )
    public static boolean PortalPearlWarp = false;

    @Rule(
            categories = {ZERO, FEATURE, SURVIVAL,TNT}
    )
    public static boolean mergeTNTPro = false;

    @Rule(
            categories = {ZERO, FEATURE}
    )
    public static boolean disableBatCanSpawn = false;

    @Rule(
            categories = {ZERO, FEATURE}
    )
    public static boolean enderpearlloadchunk = false;

    @Rule(
            categories = {ZERO,EXPERIMENTAL,OPTIMIZATION},
            options = {"0","200"},
            strict = false
    )
    public static int projectileRaycastLength = 0;

    @Rule(
            categories = {ZERO,FEATURE},
            options = {"0","40"},
            strict = false
    )
    public static int Pearltime = 40;
}
