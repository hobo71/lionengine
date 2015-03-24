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
package com.b3dgs.lionengine.game.trait.rasterable;

import java.util.List;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Localizable;
import com.b3dgs.lionengine.UtilMath;
import com.b3dgs.lionengine.Viewer;
import com.b3dgs.lionengine.anim.Animator;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.drawable.SpriteAnimated;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.SetupSurfaceRastered;
import com.b3dgs.lionengine.game.trait.Trait;
import com.b3dgs.lionengine.game.trait.TraitModel;
import com.b3dgs.lionengine.game.trait.mirrorable.Mirrorable;

/**
 * Default rasterable implementation.
 * <p>
 * The {@link ObjectGame} owner must have the following {@link Trait}:
 * </p>
 * <ul>
 * <li>{@link Localizable}</li>
 * <li>{@link Mirrorable}</li>
 * <li>{@link Animator}</li>
 * </ul>
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class RasterableModel
        extends TraitModel
        implements Rasterable
{
    /** The viewer reference. */
    private final Viewer viewer;
    /** List of rastered frames. */
    private final List<SpriteAnimated> rastersAnim;
    /** Rastered flag. */
    private final boolean rastered;
    /** Smooth raster flag. */
    private final boolean smooth;
    /** Tile height. */
    private final int tileHeight;
    /** Localizable reference. */
    private Localizable localizable;
    /** Mirrorable reference. */
    private Mirrorable mirrorable;
    /** Animator reference. */
    private Animator animator;
    /** Last raster. */
    private SpriteAnimated raster;

    /**
     * Create a rasterable model.
     * 
     * @param owner The owner reference.
     * @param services The services reference.
     * @param setup The setup reference.
     * @param tileHeight The tile height value (must be strictly positive).
     * @throws LionEngineException If missing {@link Services}.
     */
    public RasterableModel(ObjectGame owner, Services services, SetupSurfaceRastered setup, int tileHeight)
            throws LionEngineException
    {
        super(owner, services);
        Check.superiorStrict(tileHeight, 0);
        this.tileHeight = tileHeight;
        viewer = services.get(Viewer.class);
        rastersAnim = setup.rastersAnim;
        rastered = setup.rasterFile != null;
        smooth = setup.smoothRaster;
    }

    /*
     * Rasterable
     */

    @Override
    public void prepare(Services services)
    {
        localizable = owner.getTrait(Localizable.class);
        mirrorable = owner.getTrait(Mirrorable.class);
        animator = owner.getTrait(Animator.class);
    }

    @Override
    public void update(double extrp)
    {
        final int index = getRasterIndex(localizable.getY());
        raster = getRasterAnim(index);
        if (raster != null)
        {
            raster.setFrame(animator.getFrame());
            raster.setMirror(mirrorable.getMirror());
        }
    }

    @Override
    public void render(Graphic g)
    {
        if (raster != null)
        {
            final double x = viewer.getViewpointX(localizable.getX() - raster.getFrameWidth() / 2);
            final double y = viewer.getViewpointY(localizable.getY() + raster.getFrameHeight());
            raster.setLocation(x, y);
            raster.render(g);
        }
    }

    @Override
    public int getRasterIndex(double y)
    {
        final double value = y / tileHeight;
        final int i = (int) value % Rasterable.MAX_RASTERS_R;
        int index = i;

        if (!smooth && index > Rasterable.MAX_RASTERS_M)
        {
            index = Rasterable.MAX_RASTERS_M - (index - Rasterable.MAX_RASTERS);
        }
        return UtilMath.fixBetween(index, 0, Rasterable.MAX_RASTERS);
    }

    @Override
    public SpriteAnimated getRasterAnim(int rasterIndex)
    {
        Check.superiorOrEqual(rasterIndex, 0);
        return rastersAnim.get(rasterIndex);
    }

    @Override
    public boolean isRastered()
    {
        return rastered;
    }
}