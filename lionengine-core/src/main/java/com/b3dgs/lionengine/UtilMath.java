/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine;

import com.b3dgs.lionengine.geom.Coord;
import com.b3dgs.lionengine.geom.Geom;
import com.b3dgs.lionengine.geom.Line;

/**
 * Static functions around math manipulation.
 * <p>
 * This class is Thread-Safe.
 * </p>
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class UtilMath
{
    /**
     * Check if value is between an interval.
     * 
     * @param value The value to check.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return <code>true</code> if between, <code>false</code> else.
     */
    public static boolean isBetween(int value, int min, int max)
    {
        return value >= min && value <= max;
    }

    /**
     * Check if value is between an interval.
     * 
     * @param value The value to check.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return <code>true</code> if between, <code>false</code> else.
     */
    public static boolean isBetween(double value, double min, double max)
    {
        return value >= min && value <= max;
    }

    /**
     * Fix a value between an interval.
     * 
     * @param value The value to fix.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The fixed value.
     */
    public static int fixBetween(int value, int min, int max)
    {
        if (value < min)
        {
            return min;
        }
        else if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Fix a value between an interval.
     * 
     * @param value The value to fix.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The fixed value.
     */
    public static double fixBetween(double value, double min, double max)
    {
        if (value < min)
        {
            return min;
        }
        else if (value > max)
        {
            return max;
        }
        return value;
    }

    /**
     * Apply progressive modifications to a value.
     * 
     * @param value The value.
     * @param dest The value to reach.
     * @param speed The effect speed.
     * @return The modified value.
     */
    public static double curveValue(double value, double dest, double speed)
    {
        final double reciprocal = 1.0 / speed;
        final double invReciprocal = 1.0 - reciprocal;

        return value * invReciprocal + dest * reciprocal;
    }

    /**
     * Get the intersection point of two lines.
     * 
     * @param l1 The first line.
     * @param l2 The second line.
     * @return The intersection point.
     */
    public static Coord intersection(Line l1, Line l2)
    {
        final int x1 = (int) l1.getX1();
        final int x2 = (int) l1.getX2();
        final int y1 = (int) l1.getY1();
        final int y2 = (int) l1.getY2();

        final int x3 = (int) l2.getX1();
        final int x4 = (int) l2.getX2();
        final int y3 = (int) l2.getY1();
        final int y4 = (int) l2.getY2();

        final int d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (0 == d)
        {
            throw new IllegalStateException();
        }

        final int xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
        final int yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

        return Geom.createCoord(xi, yi);
    }

    /**
     * Get integer distance of two points.
     * 
     * @param x1 The point 1 x.
     * @param y1 The point 1 y.
     * @param x2 The point 2 x.
     * @param y2 The point 2 y.
     * @return The distance between point 1 and 2.
     */
    public static double getDistance(int x1, int y1, int x2, int y2)
    {
        return StrictMath.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Get distance of two points.
     * 
     * @param x1 The point 1 x.
     * @param y1 The point 1 y.
     * @param x2 The point 2 x.
     * @param y2 The point 2 y.
     * @return The distance between point 1 and 2.
     */
    public static double getDistance(double x1, double y1, double x2, double y2)
    {
        return StrictMath.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Get distance in from area to area.
     * 
     * @param x1 The first area x.
     * @param y1 The first area y.
     * @param w1 The first area width.
     * @param h1 The first area height.
     * @param x2 The second area x.
     * @param y2 The second area y.
     * @param w2 The second area width.
     * @param h2 The second area height.
     * @return The number of tiles between them.
     */
    public static double getDistance(double x1, double y1, int w1, int h1, double x2, double y2, int w2, int h2)
    {
        double min = Double.MAX_VALUE;
        for (double x = x2; x < x2 + w2; x++)
        {
            for (double y = y2; y < y2 + h2; y++)
            {
                final double dist = getDistance(x1, y1, x, y);
                if (dist < min)
                {
                    min = dist;
                }
            }
        }
        return min;
    }

    /**
     * Get distance in tile between the area.
     * 
     * @param tx1 The first tile x.
     * @param ty1 The first tile y.
     * @param tw1 The first width in tile.
     * @param th1 The first height in tile.
     * @param tx2 The second tile x.
     * @param ty2 The second tile y.
     * @param tw2 The second width in tile.
     * @param th2 The second height in tile.
     * @return The number of tiles between them.
     */
    public static double getDistance(int tx1, int ty1, int tw1, int th1, int tx2, int ty2, int tw2, int th2)
    {
        double min = Double.MAX_VALUE;
        for (int h = tx2; h < tx2 + tw2; h++)
        {
            for (int v = ty2; v < ty2 + th2; v++)
            {
                final double dist = getDistance(tx1, ty1, h, v);
                if (dist < min)
                {
                    min = dist;
                }
            }
        }
        return min;
    }

    /**
     * Wrap value (keep value between min and max). Useful to keep an angle between 0 and 360 for example.
     * 
     * @param value The input value.
     * @param min The minimum value (included).
     * @param max The maximum value (excluded).
     * @return The wrapped value.
     */
    public static double wrapDouble(double value, double min, double max)
    {
        double newValue = value;
        final double step = max - min;

        if (newValue >= max)
        {
            while (newValue >= max)
            {
                newValue -= step;
            }
        }
        else if (newValue < min)
        {
            while (newValue < min)
            {
                newValue += step;
            }
        }
        return newValue;
    }

    /**
     * Get the rounded value.
     * 
     * @param value The value.
     * @param round The round factor.
     * @return The rounded value.
     */
    public static int getRounded(double value, int round)
    {
        return (int) Math.floor(value / round) * round;
    }

    /**
     * Get cosinus in degree.
     * 
     * @param degree The angle in degree.
     * @return The cos value.
     */
    public static double cos(double degree)
    {
        return StrictMath.cos(StrictMath.toRadians(degree));
    }

    /**
     * Get sinus in degree.
     * 
     * @param degree The angle in degree.
     * @return The the sin value.
     */
    public static double sin(double degree)
    {
        return StrictMath.sin(StrictMath.toRadians(degree));
    }

    /**
     * Get the sign of a value.
     * 
     * @param value The value to check (must be != 0).
     * @return -1 if negative, 1 if positive.
     * @throws LionEngineException If argument is equal to 0.
     */
    public static int getSign(int value) throws LionEngineException
    {
        Check.different(value, 0);

        return Math.abs(value) / value;
    }

    /**
     * Get the current time in millisecond.
     * 
     * @return The current time in millisecond.
     */
    public static long time()
    {
        return System.currentTimeMillis();
    }

    /**
     * Get the current time in nano second.
     * 
     * @return The current time in nano second.
     */
    public static long nano()
    {
        return System.nanoTime();
    }

    /**
     * Private constructor.
     */
    private UtilMath()
    {
        throw new RuntimeException();
    }
}
