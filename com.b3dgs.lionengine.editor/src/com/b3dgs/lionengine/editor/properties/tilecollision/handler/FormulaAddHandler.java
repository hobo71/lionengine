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
package com.b3dgs.lionengine.editor.properties.tilecollision.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.b3dgs.lionengine.editor.project.dialog.formula.FormulasEditDialog;
import com.b3dgs.lionengine.editor.world.WorldViewModel;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MapTileCollision;

/**
 * Add formula handler.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public final class FormulaAddHandler
{
    /**
     * Create handler.
     */
    public FormulaAddHandler()
    {
        // Nothing to do
    }

    /**
     * Execute the handler.
     * 
     * @param parent The parent reference.
     */
    @Execute
    @SuppressWarnings("static-method")
    public void execute(Shell parent)
    {
        final MapTile map = WorldViewModel.INSTANCE.getMap();
        final MapTileCollision mapCollision = map.getFeature(MapTileCollision.class);
        final FormulasEditDialog dialog = new FormulasEditDialog(parent, mapCollision.getFormulasConfig());
        dialog.open();
    }
}
