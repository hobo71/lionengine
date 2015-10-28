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
package com.b3dgs.lionengine.core;

import java.io.File;

import com.b3dgs.lionengine.Constant;

/**
 * Default media factory implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class FactoryMediaDefault implements FactoryMedia
{
    /**
     * Remove unwanted end element.
     * 
     * @param path The input path.
     * @return The formatted path.
     */
    private static String format(String path)
    {
        if (path.endsWith(Constant.SLASH) || path.endsWith("\\"))
        {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /** Path separator. */
    private String separator;

    /**
     * Internal constructor.
     */
    public FactoryMediaDefault()
    {
        separator = File.separator;
    }

    /*
     * FactoryMedia
     */

    @Override
    public Media create(String path)
    {
        return new MediaImpl(path);
    }

    @Override
    public Media create(String... path)
    {
        final StringBuilder fullPath = new StringBuilder();
        for (int i = 0; i < path.length; i++)
        {
            if (path[i] != null && !path[i].isEmpty())
            {
                fullPath.append(format(path[i]));
                if (i < path.length - 1)
                {
                    fullPath.append(getSeparator());
                }
            }
        }
        return new MediaImpl(fullPath.toString());
    }

    @Override
    public String getSeparator()
    {
        return separator;
    }

    @Override
    public void setSeparator(String separator)
    {
        this.separator = separator;
    }
}
