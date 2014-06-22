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
package com.b3dgs.lionengine.editor.collision;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.b3dgs.lionengine.editor.ObjectProperties;
import com.b3dgs.lionengine.game.Collision;

/**
 * Represents the collisions properties edition view.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class EntityCollisionProperties
        extends ObjectProperties<Collision>
{
    /** Horizontal offset. */
    Text offsetX;
    /** Vertical offset. */
    Text offsetY;
    /** Collision width.. */
    Text width;
    /** Collision height. */
    Text height;
    /** Mirror flag. */
    Button mirror;

    /**
     * Constructor.
     */
    public EntityCollisionProperties()
    {
        // Nothing to do
    }

    /**
     * Set the selected collision, and update the properties fields.
     * 
     * @param collision The selected collision.
     */
    public void setSelectedCollision(Collision collision)
    {
        ObjectProperties.setTextValue(offsetX, String.valueOf(collision.getOffsetX()));
        ObjectProperties.setTextValue(offsetY, String.valueOf(collision.getOffsetY()));
        ObjectProperties.setTextValue(width, String.valueOf(collision.getWidth()));
        ObjectProperties.setTextValue(height, String.valueOf(collision.getHeight()));
        ObjectProperties.setButtonSelection(mirror, collision.hasMirror());
    }

    /**
     * Set the collision range.
     * 
     * @param first The first frame.
     * @param last The last frame.
     */
    public void setAnimationRange(int first, int last)
    {
        ObjectProperties.setTextValue(offsetX, String.valueOf(first));
        ObjectProperties.setTextValue(offsetY, String.valueOf(last));
    }

    /*
     * ObjectProperties
     */

    @Override
    protected void createTextFields(Composite parent)
    {
        final Composite fields = new Composite(parent, SWT.NONE);
        fields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        fields.setLayout(new GridLayout(1, false));

        offsetX = ObjectProperties.createTextField(fields, "Offset X:");
        offsetY = ObjectProperties.createTextField(fields, "Offset Y:");

        width = ObjectProperties.createTextField(fields, "Width:");
        height = ObjectProperties.createTextField(fields, "Height:");

        mirror = new Button(fields, SWT.CHECK | SWT.RIGHT_TO_LEFT);
        mirror.setText("Mirror");
    }

    @Override
    protected Collision createObject()
    {
        final Collision collision = new Collision(Integer.parseInt(offsetX.getText()), Integer.parseInt(offsetY
                .getText()), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()),
                mirror.getSelection());
        return collision;
    }
}