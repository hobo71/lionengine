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
package com.b3dgs.lionengine.game;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the factory game.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class FactoryGameTest
{
    /**
     * Test the game factory.
     */
    @Test
    public void testFactoryGame()
    {
        final Factory factory = new Factory();

        Assert.assertTrue(Arrays.equals(Type.values(), factory.getTypes()));
        Assert.assertNull(factory.getSetup(Type.TYPE));

        factory.load();

        Assert.assertNotNull(factory.getSetup(Type.TYPE));
        Assert.assertTrue(Arrays.equals(Type.values(), factory.getTypes()));
    }
}
