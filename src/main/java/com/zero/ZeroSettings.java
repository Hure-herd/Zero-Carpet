package com.zero;

import carpet.api.settings.Rule;
import carpet.api.settings.Validators;

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
            strict = false,
            validators = Validators.NonNegativeNumber.class
    )
    public static int projectileRaycastLength = 0;

    @Rule(
            categories = {ZERO,FEATURE},
            options = {"0","40"},
            strict = false,
            validators = Validators.NonNegativeNumber.class
    )
    public static int Pearltime = 40;

    @Rule(
            categories = {ZERO,FEATURE}
    )
    public static boolean soundsuppression = false;

    @Rule(
            categories = {ZERO, EXPERIMENTAL}
    )
    public static boolean  endstonefram = false;

}
