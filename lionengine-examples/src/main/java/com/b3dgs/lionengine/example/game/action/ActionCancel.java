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
package com.b3dgs.lionengine.example.game.action;

import com.b3dgs.lionengine.game.Action;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.Setup;
import com.b3dgs.lionengine.game.feature.Factory;
import com.b3dgs.lionengine.game.feature.Handler;

/**
 * Cancel action.
 */
class ActionCancel extends ActionFeature
{
    /**
     * Create feature.
     * 
     * @param setup The setup reference.
     */
    public ActionCancel(Setup setup)
    {
        super(setup);
    }

    @Override
    public Action create(Services services)
    {
        final Factory factory = services.get(Factory.class);
        final Handler handler = services.get(Handler.class);

        return new Action()
        {
            @Override
            public void execute()
            {
                final Button buildings = factory.create(Button.BUILDINGS);
                handler.add(buildings);

                getFeature(ButtonLink.class).terminate();
            }
        };
    }
}
