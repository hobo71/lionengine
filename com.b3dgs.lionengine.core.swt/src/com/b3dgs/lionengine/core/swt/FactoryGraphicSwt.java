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
package com.b3dgs.lionengine.core.swt;

import com.b3dgs.lionengine.ColorRgba;
import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.Filter;
import com.b3dgs.lionengine.Hq2x;
import com.b3dgs.lionengine.Hq3x;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.TextStyle;
import com.b3dgs.lionengine.Transparency;
import com.b3dgs.lionengine.core.FactoryGraphic;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Graphics;
import com.b3dgs.lionengine.core.ImageBuffer;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.Renderer;
import com.b3dgs.lionengine.core.Screen;
import com.b3dgs.lionengine.core.Text;
import com.b3dgs.lionengine.core.Transform;

/**
 * Graphic factory implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
final class FactoryGraphicSwt implements FactoryGraphic
{
    /**
     * Internal constructor.
     */
    FactoryGraphicSwt()
    {
        // Nothing to do
    }

    /*
     * FactoryGraphic
     */

    @Override
    public Screen createScreen(Renderer renderer)
    {
        final Config config = renderer.getConfig();
        if (config.isWindowed())
        {
            return new ScreenWindowedSwt(renderer);
        }
        return new ScreenFullSwt(renderer);
    }

    @Override
    public Graphic createGraphic()
    {
        return new GraphicSwt();
    }

    @Override
    public Transform createTransform()
    {
        return new TransformSwt();
    }

    @Override
    public Text createText(String fontName, int size, TextStyle style)
    {
        return new TextSwt(fontName, size, style);
    }

    @Override
    public ImageBuffer createImageBuffer(int width, int height, Transparency transparency)
    {
        return UtilityImage.createImage(width, height, transparency);
    }

    @Override
    public ImageBuffer getImageBuffer(Media media)
    {
        return UtilityImage.getImage(media);
    }

    @Override
    public ImageBuffer getImageBuffer(ImageBuffer imageBuffer)
    {
        return UtilityImage.getImage(imageBuffer);
    }

    @Override
    public ImageBuffer applyMask(ImageBuffer imageBuffer, ColorRgba maskColor)
    {
        return UtilityImage.applyMask(imageBuffer, maskColor);
    }

    @Override
    public ImageBuffer[] splitImage(ImageBuffer imageBuffer, int h, int v)
    {
        return UtilityImage.splitImage(imageBuffer, h, v);
    }

    @Override
    public ImageBuffer rotate(ImageBuffer imageBuffer, int angle)
    {
        return UtilityImage.rotate(imageBuffer, angle);
    }

    @Override
    public ImageBuffer resize(ImageBuffer imageBuffer, int width, int height)
    {
        return UtilityImage.resize(imageBuffer, width, height);
    }

    @Override
    public ImageBuffer flipHorizontal(ImageBuffer imageBuffer)
    {
        return UtilityImage.flipHorizontal(imageBuffer);
    }

    @Override
    public ImageBuffer flipVertical(ImageBuffer imageBuffer)
    {
        return UtilityImage.flipVertical(imageBuffer);
    }

    @Override
    public ImageBuffer applyFilter(ImageBuffer imageBuffer, Filter filter) throws LionEngineException
    {
        final ImageBuffer filtered;
        switch (filter)
        {
            case NONE:
                filtered = imageBuffer;
                break;
            case BILINEAR:
                filtered = UtilityImage.applyBilinearFilter(imageBuffer);
                break;
            case HQ2X:
                final Hq2x hq2x = new Hq2x(imageBuffer);
                filtered = hq2x.getScaledImage();
                break;
            case HQ3X:
                final Hq3x hq3x = new Hq3x(imageBuffer);
                filtered = hq3x.getScaledImage();
                break;
            default:
                throw new RuntimeException();
        }
        return filtered;
    }

    @Override
    public void saveImage(ImageBuffer imageBuffer, Media media)
    {
        UtilityImage.saveImage(imageBuffer, media);
    }

    @Override
    public ImageBuffer getRasterBuffer(ImageBuffer imageBuffer, int fr, int fg, int fb, int er, int eg, int eb,
            int refSize)
    {
        return UtilityImage.getRasterBuffer(imageBuffer, fr, fg, fb, er, eg, eb, refSize);
    }

    @Override
    public int[][] loadRaster(Media media) throws LionEngineException
    {
        return Graphics.loadRaster(media);
    }
}
