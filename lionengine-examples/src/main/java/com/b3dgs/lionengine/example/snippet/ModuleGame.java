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
package com.b3dgs.lionengine.example.snippet;

import java.io.IOException;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.InputDeviceDirectional;
import com.b3dgs.lionengine.core.Loader;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.core.Sequence;
import com.b3dgs.lionengine.game.Alterable;
import com.b3dgs.lionengine.game.Attribute;
import com.b3dgs.lionengine.game.Damages;
import com.b3dgs.lionengine.game.Resource;
import com.b3dgs.lionengine.game.WorldGame;
import com.b3dgs.lionengine.game.map.LevelRipConverter;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.TileExtractor;
import com.b3dgs.lionengine.game.state.StateGame;
import com.b3dgs.lionengine.game.state.StateTransition;
import com.b3dgs.lionengine.game.state.StateTransitionInputDirectionalChecker;
import com.b3dgs.lionengine.stream.FileReading;
import com.b3dgs.lionengine.stream.FileWriting;
import com.b3dgs.lionengine.stream.Stream;

@SuppressWarnings("all")
public class ModuleGame
{
    class World
            extends WorldGame
    {
        World(Config config)
        {
            super(config);
        }

        @Override
        public void update(double extrp)
        {
        }

        @Override
        public void render(Graphic g)
        {
        }

        @Override
        protected void saving(FileWriting file) throws IOException
        {
        }

        @Override
        protected void loading(FileReading file) throws IOException
        {
        }
    }

    enum States
    {
        TEST1,
        TEST2;
    }

    class StateTest
            extends StateGame
    {
        public StateTest()
        {
            super(States.TEST1);
            addTransition(new TransitionTest());
        }

        @Override
        public void enter()
        {
        }

        @Override
        public void update(double extrp)
        {
        }

        private final class TransitionTest
                extends StateTransition
                implements StateTransitionInputDirectionalChecker
        {
            public TransitionTest()
            {
                super(States.TEST2);
            }

            @Override
            public boolean check(InputDeviceDirectional input)
            {
                return true;
            }
        }
    }

    MapTile map;

    /*
     * Snippet code
     */

    void alterable()
    {
        final Alterable life = new Alterable(100);
        life.decrease(25); // life = 75
        life.fill(); // life = 100
        life.isFull(); // returns true
        life.getNeeded(150); // returns 50
        life.setMax(150);
        life.fill(); // life = 150
        life.increase(25); // life = 150
        life.set(0); // life = 0
        life.isEmpty(); // returns true
    }

    void attribute()
    {
        final Attribute vitality = new Attribute();
        vitality.set(1);
        vitality.increase(2);
        System.out.println(vitality.get()); // print 3
    }

    void damages()
    {
        final Damages dmg = new Damages(1, 3);
        dmg.getMax(); // returns 3
        dmg.getRandom(); // returns a value between 1 and 3 included
        dmg.getLast(); // returns the last value return by getRandom()
        dmg.setMin(5);
        dmg.setMax(10);
        dmg.getMin(); // returns 5
        dmg.getRandom(); // returns a value between 5 and 10 included
    }

    void resource()
    {
        final Resource gold = new Resource();
        gold.add(100);
        gold.get(); // returns 100
        gold.canSpend(25); // returns true
        gold.spend(25); // returns 75
        gold.canSpend(100); // returns false
    }

    public static class MySequence
            extends Sequence
    {
        private static final Resolution NATIVE = new Resolution(320, 240, 60);

        public MySequence(Loader loader)
        {
            super(loader, MySequence.NATIVE);
            // Initialize variables here
        }

        @Override
        protected void load()
        {
            // Load resources here
        }

        @Override
        public void update(double extrp)
        {
            // Update routine
        }

        @Override
        public void render(Graphic g)
        {
            // Render routine
        }
    }

    private void ripLevel(Media levelrip, Media tilesheet, Media output)
    {
        final LevelRipConverter rip = new LevelRipConverter(levelrip, tilesheet, map);
        rip.start();
        try (FileWriting file = Stream.createFileWriting(output))
        {
            map.save(file);
        }
        catch (final IOException exception)
        {
            // Error
        }
    }

    private void utilityTileExtractor()
    {
        TileExtractor.start(Medias.create("level.png"), Medias.create("sheet.png"), 16, 16, 256, 256);
    }
}
