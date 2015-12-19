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
package com.b3dgs.lionengine.game.object;

import java.util.ArrayList;
import java.util.List;

import com.b3dgs.lionengine.ColorRgba;
import com.b3dgs.lionengine.ImageBuffer;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.UtilConversion;
import com.b3dgs.lionengine.core.Graphics;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteAnimated;
import com.b3dgs.lionengine.game.raster.Rasterable;

/**
 * Define a structure used to create multiple rastered surface, sharing the same data.
 * 
 * @see com.b3dgs.lionengine.game.Configurer
 */
public class SetupSurfaceRastered extends SetupSurface
{
    /**
     * Load all rasters data.
     * 
     * @param rasters The raster data.
     * @param color The raster colors.
     * @param next The next colors.
     * @param m The raster smooth.
     * @param i The raster id.
     * @param smooth <code>true</code> to smooth raster, <code>false</code> else.
     */
    public static void loadRaster(int[][] rasters, int[] color, int[] next, int m, int i, boolean smooth)
    {
        for (int c = 0; c < rasters.length; c++)
        {
            final int[] data = rasters[c];
            if (smooth)
            {
                if (m == 0)
                {
                    color[c] = ColorRgba.getRasterColor(i, data, Rasterable.MAX_RASTERS);
                    next[c] = ColorRgba.getRasterColor(i + 1, data, Rasterable.MAX_RASTERS);
                }
                else
                {
                    color[c] = ColorRgba.getRasterColor(Rasterable.MAX_RASTERS - i, data, Rasterable.MAX_RASTERS);
                    next[c] = ColorRgba.getRasterColor(Rasterable.MAX_RASTERS - i - 1, data, Rasterable.MAX_RASTERS);
                }
            }
            else
            {
                color[c] = ColorRgba.getRasterColor(i, rasters[c], Rasterable.MAX_RASTERS);
                next[c] = color[c];
            }
        }
    }

    /** List of rasters animation. */
    private final List<SpriteAnimated> rastersAnim;
    /** Raster filename. */
    private final Media rasterFile;
    /** Raster smooth flag. */
    private final boolean smoothRaster;
    /** Vertical frames. */
    private final int vf;
    /** Horizontal frames. */
    private final int hf;
    /** frame height. */
    private final int frameHeight;

    /**
     * Create a setup.
     * 
     * @param config The config media.
     * @param rasterFile The raster media.
     * @param smoothRaster The raster smooth flag.
     * @throws LionEngineException If error when opening the media.
     */
    public SetupSurfaceRastered(Media config, Media rasterFile, boolean smoothRaster)
    {
        super(config);
        this.rasterFile = rasterFile;
        this.smoothRaster = smoothRaster;
        if (rasterFile != null)
        {
            rastersAnim = new ArrayList<SpriteAnimated>(Rasterable.MAX_RASTERS);
            final FramesConfig framesData = FramesConfig.create(getConfigurer());
            hf = framesData.getHorizontal();
            vf = framesData.getVertical();
            frameHeight = surface.getHeight() / vf;
            loadRasters();
        }
        else
        {
            rastersAnim = null;
            hf = 0;
            vf = 0;
            frameHeight = 0;
        }
    }

    /**
     * Get the rasters.
     * 
     * @return The rasters.
     */
    public List<SpriteAnimated> getRasters()
    {
        return rastersAnim;
    }

    /**
     * Get the raster file.
     * 
     * @return The raster file.
     */
    public Media getFile()
    {
        return rasterFile;
    }

    /**
     * Check if smooth raster.
     * 
     * @return <code>true</code> if smooth enabled, <code>false</code> else.
     */
    public boolean hasSmooth()
    {
        return smoothRaster;
    }

    /**
     * Load rasters.
     *
     * @throws LionEngineException If the raster data from the media are invalid.
     */
    private void loadRasters()
    {
        final int[][] rasters = Graphics.loadRaster(rasterFile);
        final int[] color = new int[rasters.length];
        final int[] colorNext = new int[rasters.length];
        final int max = UtilConversion.boolToInt(smoothRaster) + 1;

        for (int m = 0; m < max; m++)
        {
            for (int i = 1; i <= Rasterable.MAX_RASTERS; i++)
            {
                loadRaster(rasters, color, colorNext, m, i, smoothRaster);
                addRaster(color[0], color[1], color[2], colorNext[0], colorNext[1], colorNext[2]);
            }
        }
    }

    /**
     * Add raster.
     * 
     * @param fr The first red.
     * @param fg The first green.
     * @param fb The first blue.
     * @param er The end red.
     * @param eg The end green.
     * @param eb The end blue.
     * @throws LionEngineException If arguments are invalid.
     */
    private void addRaster(int fr, int fg, int fb, int er, int eg, int eb)
    {
        final ImageBuffer rasterBuf = Graphics.getRasterBuffer(surface, fr, fg, fb, er, eg, eb, frameHeight);
        final SpriteAnimated raster = Drawable.loadSpriteAnimated(rasterBuf, hf, vf);
        rastersAnim.add(raster);
    }
}
