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
package com.b3dgs.lionengine.editor.project.dialog;

import org.eclipse.osgi.util.NLS;

import com.b3dgs.lionengine.editor.Activator;

/**
 * Messages internationalization.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class Messages
        extends NLS
{
    /** Bundle name. */
    private static final String BUNDLE_NAME = Activator.PLUGIN_ID + ".project.dialog.messages"; //$NON-NLS-1$

    /** Classes. */
    public static String AbstractProjectDialog_Classes;
    /** Libraries */
    public static String AbstractProjectDialog_Libraries;
    /** Resources */
    public static String AbstractProjectDialog_Resources;
    /** Project name. */
    public static String AbstractProjectDialog_Name;
    /** Location. */
    public static String AbstractProjectDialog_Location;
    /** Folders. */
    public static String AbstractProjectDialog_Folders;

    /** Invalid import. */
    public static String ImportProjectDialog_InvalidImport;
    /** Import project dialog title. */
    public static String ImportProjectDialogTitle;
    /** Title header. */
    public static String ImportProjectDialog_HeaderTitle;
    /** Description header. */
    public static String ImportProjectDialog_HeaderDesc;
    /** Classes folder existence. */
    public static String ImportProjectDialog_InfoClasses;
    /** Libraries folder existence. */
    public static String ImportProjectDialog_InfoLibraries;
    /** Resources folder existence. */
    public static String ImportProjectDialog_InfoResources;
    /** Classes and resources folder existence. */
    public static String ImportProjectDialog_InfoBoth;
    /** Import project error dialog title. */
    public static String ImportProjectDialog_ErrorTitle;
    /** Import project error dialog text. */
    public static String ImportProjectDialog_ErrorText;
    /** Import project error dialog text properties. */
    public static String ImportProjectDialog_ErrorPropertiesText;

    /** Edit tile sheets dialog title. */
    public static String EditTilesheetsDialog_Title;
    /** Edit tile sheets dialog title header. */
    public static String EditTilesheetsDialog_HeaderTitle;
    /** Edit tile sheets dialog description header. */
    public static String EditTilesheetsDialog_HeaderDesc;

    /**
     * Initialize.
     */
    static
    {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    /**
     * Private constructor.
     */
    private Messages()
    {
        throw new RuntimeException();
    }
}