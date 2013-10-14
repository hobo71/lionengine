/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test utility conversion class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class UtilityConversionTest
{
    /**
     * Test the utility conversion class.
     * 
     * @throws SecurityException If error.
     * @throws NoSuchMethodException If error.
     * @throws IllegalArgumentException If error.
     * @throws IllegalAccessException If error.
     * @throws InstantiationException If error.
     */
    @Test
    public void testUtilityConversionClass() throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException
    {
        final Constructor<UtilityConversion> utilityConversion = UtilityConversion.class.getDeclaredConstructor();
        utilityConversion.setAccessible(true);
        try
        {
            final UtilityConversion clazz = utilityConversion.newInstance();
            Assert.assertNotNull(clazz);
            Assert.fail();
        }
        catch (final InvocationTargetException exception)
        {
            // Success
        }
    }

    /**
     * Test the utility conversion.
     */
    @Test
    public void testUtilityConversion()
    {
        final short s = 12345;
        Assert.assertEquals(s, UtilityConversion.byteArrayToShort(UtilityConversion.shortToByteArray(s)));

        final int i = 123456789;
        Assert.assertEquals(i, UtilityConversion.byteArrayToInt(UtilityConversion.intToByteArray(i)));

        Assert.assertEquals(s, UtilityConversion.fromUnsignedShort(UtilityConversion.toUnsignedShort(s)));

        final byte b = 123;
        Assert.assertEquals(b, UtilityConversion.fromUnsignedByte(UtilityConversion.toUnsignedByte(b)));
    }
}
