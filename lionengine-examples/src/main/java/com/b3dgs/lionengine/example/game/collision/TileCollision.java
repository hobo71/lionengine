/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.collision;

import java.util.Collection;
import java.util.HashSet;

import com.b3dgs.lionengine.game.map.CollisionFunction;
import com.b3dgs.lionengine.game.map.CollisionTile;
import com.b3dgs.lionengine.game.map.CollisionTile;

/**
 * List of tile collisions.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see com.b3dgs.lionengine.example.game.collision_tile
 */
enum TileCollision implements CollisionTile
{
    /** Ground collision. */
    GROUND,
    /** Wall collision. */
    WALL,
    /** Tube collision. */
    TUBE,
    /** No collision. */
    NONE;

    /** Vertical collisions list. */
    static final Collection<CollisionTile> COLLISION_VERTICAL = new HashSet<>();
    /** Horizontal collisions list. */
    static final Collection<CollisionTile> COLLISION_HORIZONTAL = new HashSet<>();

    /**
     * Static init.
     */
    static
    {
        TileCollision.COLLISION_VERTICAL.add(TileCollision.GROUND);
        TileCollision.COLLISION_VERTICAL.add(TileCollision.TUBE);

        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.GROUND);
        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.TUBE);
        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.WALL);
    }

    /** Model. */
    private final CollisionTile model = new CollisionTile(this);

    /*
     * CollisionTile
     */

    @Override
    public void addCollisionFunction(CollisionFunction function)
    {
        model.addCollisionFunction(function);
    }

    @Override
    public void removeCollisionFunction(CollisionFunction function)
    {
        model.removeCollisionFunction(function);
    }

    @Override
    public void removeCollisions()
    {
        model.removeCollisions();
    }

    @Override
    public Collection<CollisionFunction> getCollisionFunctions()
    {
        return model.getCollisionFunctions();
    }

    @Override
    public Enum<?> getValue()
    {
        return model.getValue();
    }
}
