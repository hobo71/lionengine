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
package com.b3dgs.lionengine.graphic;

import java.io.IOException;
import java.io.OutputStream;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Medias;
import com.b3dgs.lionengine.UtilStream;
import com.b3dgs.lionengine.graphic.drawable.ImageHeader;
import com.b3dgs.lionengine.graphic.drawable.ImageInfo;

/**
 * Factory graphic mock.
 */
public class FactoryGraphicMock implements FactoryGraphic
{
    /**
     * Create mock.
     */
    public FactoryGraphicMock()
    {
        super();
    }

    /*
     * FactoryGraphic
     */

    @Override
    public Screen createScreen(Config config)
    {
        return new ScreenMock(config);
    }

    @Override
    public Text createText(String fontName, int size, TextStyle style)
    {
        return new TextMock(fontName, size, style);
    }

    @Override
    public Graphic createGraphic()
    {
        return new GraphicMock();
    }

    @Override
    public Transform createTransform()
    {
        return new TransformMock();
    }

    @Override
    public ImageBuffer createImageBuffer(int width, int height)
    {
        return new ImageBufferMock(width, height);
    }

    @Override
    public ImageBuffer createImageBuffer(int width, int height, ColorRgba transparency)
    {
        return new ImageBufferMock(width, height);
    }

    @Override
    public ImageBuffer getImageBuffer(Media media)
    {
        final ImageHeader info = ImageInfo.get(media);
        return new ImageBufferMock(info.getWidth(), info.getHeight());
    }

    @Override
    public ImageBuffer getImageBuffer(ImageBuffer imageBuffer)
    {
        return new ImageBufferMock(imageBuffer.getWidth(), imageBuffer.getHeight());
    }

    @Override
    public ImageBuffer applyMask(ImageBuffer imageBuffer, ColorRgba maskColor)
    {
        return new ImageBufferMock(imageBuffer.getWidth(), imageBuffer.getHeight());
    }

    @Override
    public ImageBuffer[] splitImage(ImageBuffer image, int h, int v)
    {
        final ImageBuffer[] buffers = new ImageBuffer[h * v];
        for (int i = 0; i < buffers.length; i++)
        {
            buffers[i] = resize(image, image.getWidth() / h, image.getHeight() / h);
        }
        return buffers;
    }

    @Override
    public ImageBuffer rotate(ImageBuffer image, int angle)
    {
        return new ImageBufferMock(image.getWidth(), image.getHeight());
    }

    @Override
    public ImageBuffer resize(ImageBuffer image, int width, int height)
    {
        return new ImageBufferMock(width, height);
    }

    @Override
    public ImageBuffer flipHorizontal(ImageBuffer image)
    {
        return new ImageBufferMock(image.getWidth(), image.getHeight());
    }

    @Override
    public ImageBuffer flipVertical(ImageBuffer image)
    {
        return new ImageBufferMock(image.getWidth(), image.getHeight());
    }

    @Override
    public void saveImage(ImageBuffer image, Media media)
    {
        media.getFile().getParentFile().mkdirs();
        try (OutputStream output = media.getOutputStream())
        {
            UtilStream.copy(Medias.create("image.png").getInputStream(), output);
        }
        catch (final IOException exception)
        {
            throw new LionEngineException(exception);
        }
    }

    @Override
    public ImageBuffer getRasterBuffer(ImageBuffer image, int fr, int fg, int fb, int er, int eg, int eb, int refSize)
    {
        return new ImageBufferMock(image.getWidth(), image.getHeight());
    }
}
