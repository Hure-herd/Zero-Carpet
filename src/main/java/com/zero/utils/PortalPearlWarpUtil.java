package com.zero.utils;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;

public class PortalPearlWarpUtil {
    private static final ImmutableRangeSet<Double> RANGE_SET = ImmutableRangeSet.<Double>builder()
            .add(Range.open(914.0d, 916.0d))
            .add(Range.open(7323.0d, 7325.0d))
            .add(Range.open(58591.0d, 58593.0d))
            .add(Range.open(468742.0d, 468744.0d)) //地狱的地狱门位置
            .add(Range.open(29999599.0d, 29999601.0d))
            .add(Range.open(3749941.0d, 3749943.0d))
            .add(Range.open(468734.0d, 468736.0d))
            .add(Range.open(58584.0d, 58586.0d))
            .add(Range.open(7314.0d, 7316.0d)) //主世界的地狱门位置
            .build();

    public static boolean isInRange(double x, double z) {
        if (!sameSign(x, z)) {
            return false;
        }
        double absX = Math.abs(x);
        double absZ = Math.abs(z);
        if (Math.abs(absX - absZ) > 2.0d) {
            return false;
        }
        return RANGE_SET.contains(absX) && RANGE_SET.contains(absZ);
    }


    private static boolean sameSign(double a, double b) {
        return (Double.doubleToRawLongBits(a) ^ Double.doubleToRawLongBits(b)) >= 0;
    }
}
