/*
 * Copyright (C) 2013-2017 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Medias;
import com.b3dgs.lionengine.Xml;

/**
 * Test {@link ActionConfig}.
 */
public final class ActionConfigTest
{
    /**
     * Prepare test.
     */
    @BeforeClass
    public static void setUp()
    {
        Medias.setResourcesDirectory(System.getProperty("java.io.tmpdir"));
    }

    /**
     * Clean up test.
     */
    @AfterClass
    public static void cleanUp()
    {
        Medias.setResourcesDirectory(null);
    }

    /**
     * Test exports imports.
     */
    @Test
    public void testExportsImports()
    {
        final ActionConfig action = new ActionConfig("name", "description", 0, 1, 16, 32);
        final Xml root = new Xml("test");
        root.add(ActionConfig.exports(action));

        final Media media = Medias.create("action.xml");
        root.save(media);

        Assert.assertEquals(action, ActionConfig.imports(new Xml(media)));
        Assert.assertEquals(action, ActionConfig.imports(new Configurer(media)));

        Assert.assertTrue(media.getFile().delete());
    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals()
    {
        final ActionConfig action = new ActionConfig("a", "b", 0, 1, 2, 3);

        Assert.assertEquals(action, action);

        Assert.assertNotEquals(action, null);
        Assert.assertNotEquals(action, new Object());
        Assert.assertNotEquals(action, new ActionConfig("", "b", 0, 1, 2, 3));
        Assert.assertNotEquals(action, new ActionConfig("a", "", 0, 1, 2, 3));
        Assert.assertNotEquals(action, new ActionConfig("a", "b", -1, 1, 2, 3));
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, -1, 2, 3));
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, 1, -1, 3));
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, 1, 2, -1));
    }

    /**
     * Test hash code.
     */
    @Test
    public void testHashCode()
    {
        final int action = new ActionConfig("a", "b", 0, 1, 2, 3).hashCode();

        Assert.assertEquals(action, new ActionConfig("a", "b", 0, 1, 2, 3).hashCode());

        Assert.assertNotEquals(action, new ActionConfig("", "b", 0, 1, 2, 3).hashCode());
        Assert.assertNotEquals(action, new ActionConfig("a", "", 0, 1, 2, 3).hashCode());
        Assert.assertNotEquals(action, new ActionConfig("a", "b", -1, 1, 2, 3).hashCode());
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, -1, 2, 3).hashCode());
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, 1, -1, 3).hashCode());
        Assert.assertNotEquals(action, new ActionConfig("a", "b", 0, 1, 2, -1).hashCode());
    }

    /**
     * Test the to string.
     */
    @Test
    public void testToString()
    {
        final ActionConfig action = new ActionConfig("a", "b", 0, 1, 2, 3);

        Assert.assertEquals("ActionConfig [name=a, description=b, x=0, y=1, width=2, height=3]", action.toString());
    }
}
