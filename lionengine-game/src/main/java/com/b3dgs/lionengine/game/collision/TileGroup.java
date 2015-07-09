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
package com.b3dgs.lionengine.game.collision;

import java.util.Collection;

import com.b3dgs.lionengine.Nameable;
import com.b3dgs.lionengine.game.map.Tile;

/**
 * Represents the tile group, which can be applied to a {@link TileRef}.
 * Here a definition example:
 * 
 * <pre>
 * &lt;lionengine:groups xmlns:lionengine="http://lionengine.b3dgs.com">
 *    &lt;lionengine:group name="block">
 *      &lt;lionengine:tile sheet="0" number="1"/>
 *      &lt;lionengine:tile sheet="1" number="5"/>
 *    &lt;/lionengine:group>
 *    &lt;lionengine:group name="top">
 *      &lt;lionengine:tile sheet="0" number="2"/>
 *      &lt;lionengine:tile sheet="0" number="3"/>
 *    &lt;/lionengine:group>
 * &lt;/lionengine:groups>
 * </pre>
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see com.b3dgs.lionengine.game.configurer.ConfigTileGroup
 */
public class TileGroup implements Nameable
{
    /** The group Name. */
    private final String name;
    /** Elements inside group. */
    private final Collection<TileRef> tiles;

    /**
     * Create a tile group.
     * 
     * @param name The group name.
     * @param tiles The tiles inside the group.
     */
    public TileGroup(String name, Collection<TileRef> tiles)
    {
        this.name = name;
        this.tiles = tiles;
    }

    /**
     * Check if tile is contained by the group.
     * 
     * @param tile The tile reference.
     * @return <code>true</code> if part of the group, <code>false</code> else.
     */
    public boolean contains(Tile tile)
    {
        for (final TileRef current : tiles)
        {
            if (current.getSheet() == tile.getSheet().intValue() && current.getNumber() == tile.getNumber())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the tiles inside group.
     * 
     * @return The tiles inside group.
     */
    public Collection<TileRef> getTiles()
    {
        return tiles;
    }

    /*
     * Nameable
     */

    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Represents the tile reference indexes.
     * 
     * @author Pierre-Alexandre (contact@b3dgs.com)
     */
    public static final class TileRef
    {
        /** Sheet id. */
        private final int sheet;
        /** TIle number. */
        private final int number;

        /**
         * Create the tile reference.
         * 
         * @param sheet The tile sheet number.
         * @param number The tile number.
         */
        public TileRef(int sheet, int number)
        {
            this.sheet = sheet;
            this.number = number;
        }

        /**
         * Get the sheet number.
         * 
         * @return The sheet number.
         */
        public int getSheet()
        {
            return sheet;
        }

        /**
         * Get the tile number.
         * 
         * @return The tile number.
         */
        public int getNumber()
        {
            return number;
        }
    }
}
