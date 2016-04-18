/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.editor.map.minimap.editor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.drawable.SpriteTiled;
import com.b3dgs.lionengine.editor.dialog.AbstractDialog;
import com.b3dgs.lionengine.editor.utility.UtilIcon;
import com.b3dgs.lionengine.editor.utility.control.UtilButton;
import com.b3dgs.lionengine.editor.world.WorldModel;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MinimapConfig;
import com.b3dgs.lionengine.game.tile.TileRef;
import com.b3dgs.lionengine.geom.Geom;
import com.b3dgs.lionengine.geom.Point;
import com.b3dgs.lionengine.graphic.ColorRgba;
import com.b3dgs.lionengine.util.UtilMath;

/**
 * Edit minimap dialog.
 */
public class MinimapEditDialog extends AbstractDialog
{
    /** Editor folder. */
    private static final String EDITOR = "editor";
    /** Icon. */
    private static final Image ICON = UtilIcon.get("dialog", "edit.png");
    /** Previous. */
    private static final Image PREVIOUS = UtilIcon.get(EDITOR, "previous.png");
    /** Next. */
    private static final Image NEXT = UtilIcon.get(EDITOR, "next.png");

    /**
     * Change the label color.
     * 
     * @param colorLabel The label color.
     * @param color The background color.
     */
    private static void changeColor(Label colorLabel, Color color)
    {
        final Color old = colorLabel.getBackground();
        if (old != null)
        {
            old.dispose();
        }
        colorLabel.setBackground(color);
    }

    /** Map reference. */
    private final MapTile map = WorldModel.INSTANCE.getMap();
    /** Minimap config. */
    private final Media minimap;
    /** Minimap data. */
    private final Map<Integer, Map<Point, Color>> data = new HashMap<>();
    /** Selected tile. */
    private final Point selection = Geom.createPoint(0, 0);
    /** Current sheet. */
    private int sheet;

    /**
     * Create the dialog.
     * 
     * @param parent The parent reference.
     * @param destination The configuration destination.
     */
    public MinimapEditDialog(Shell parent, String destination)
    {
        super(parent, Messages.Title, Messages.HeaderTitle, Messages.HeaderDesc, ICON, SWT.SHELL_TRIM);

        minimap = Medias.create(destination);
        load(parent.getDisplay());
        createDialog();
        dialog.setMinimumSize(64, 64);
        finish.setEnabled(true);
    }

    /**
     * Load existing color data.
     * 
     * @param device The device reference.
     */
    private void load(Device device)
    {
        final Map<TileRef, ColorRgba> colors = MinimapConfig.imports(minimap);
        for (final Map.Entry<TileRef, ColorRgba> current : colors.entrySet())
        {
            final TileRef tile = current.getKey();
            if (!data.containsKey(tile.getSheet()))
            {
                data.put(tile.getSheet(), new HashMap<>());
            }

            final int th = map.getSheet(tile.getSheet()).getTilesHorizontal();
            final int x = tile.getNumber() % th;
            final int y = tile.getNumber() / th;
            final ColorRgba c = current.getValue();
            final Color color = new Color(device, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
            data.get(tile.getSheet()).put(Geom.createPoint(x, y), color);
        }
    }

    /**
     * Create the sheet area.
     * 
     * @param parent The parent composite.
     * @return The sheet label.
     */
    private Label createSheetArea(Composite parent)
    {
        int maxWidth = 0;
        int maxHeight = 0;
        for (final Integer sheet : map.getSheets())
        {
            final SpriteTiled sprite = map.getSheet(sheet);
            maxWidth = Math.max(maxWidth, sprite.getWidth());
            maxHeight = Math.max(maxHeight, sprite.getHeight());
        }

        final Label sheetLabel = new Label(parent, SWT.BORDER);
        sheetLabel.setLayoutData(new GridData(maxWidth, maxHeight));
        sheetLabel.setImage(map.getSheet(Integer.valueOf(sheet)).getSurface().getSurface());
        sheetLabel.addPaintListener(event ->
        {
            final GC gc = event.gc;
            gc.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
            final int x = selection.getX() * map.getTileWidth();
            final int y = selection.getY() * map.getTileHeight();
            gc.drawRectangle(x, y, map.getTileWidth(), map.getTileHeight());
        });

        return sheetLabel;
    }

    /**
     * Create the color area chooser.
     * 
     * @param parent The parent composite.
     * @return The color label.
     */
    private Label createColorArea(Composite parent)
    {
        final Composite colorArea = new Composite(parent, SWT.NONE);
        colorArea.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true));
        colorArea.setLayout(new GridLayout(1, true));

        final Button colorPicker = new Button(colorArea, SWT.NONE);
        colorPicker.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        colorPicker.setText(Messages.Color);

        final Label colorLabel = new Label(colorArea, SWT.BORDER);
        colorLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
        colorLabel.setLayoutData(new GridData(colorPicker.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + 1, 24));
        colorPicker.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent event)
            {
                final ColorDialog dialog = new ColorDialog(colorPicker.getShell());
                final RGB rgb = dialog.open();
                final Color color = new Color(colorLabel.getDisplay(), rgb);
                changeColor(colorLabel, color);
                setSheetData(color);
            }
        });
        changeColor(colorLabel, getSheetColor());

        return colorLabel;
    }

    /**
     * Create the sheet selector.
     * 
     * @param parent The composite parent.
     * @param sheetLabel The sheet label.
     * @param colorLabel The color label reference.
     */
    private void createSheetSelector(Composite parent, Label sheetLabel, Label colorLabel)
    {
        final Composite sheetSelector = new Composite(parent, SWT.NONE);
        sheetSelector.setLayout(new GridLayout(2, false));
        sheetSelector.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

        final Button previous = UtilButton.create(sheetSelector, Messages.Previous, PREVIOUS);
        UtilButton.setAction(previous, () -> updateSheet(sheetLabel, colorLabel, sheet - 1));

        final Button next = UtilButton.create(sheetSelector, Messages.Next, NEXT);
        UtilButton.setAction(next, () -> updateSheet(sheetLabel, colorLabel, sheet + 1));
    }

    /**
     * Update the sheet with new value.
     * 
     * @param sheetLabel The label reference.
     * @param colorLabel The color label reference.
     * @param next The new sheet index.
     */
    private void updateSheet(Label sheetLabel, Label colorLabel, int next)
    {
        sheet = UtilMath.clamp(next, 0, map.getSheetsNumber() - 1);
        if (!sheetLabel.isDisposed())
        {
            sheetLabel.setImage(map.getSheet(Integer.valueOf(sheet)).getSurface().getSurface());
            sheetLabel.redraw();
        }
        changeColor(colorLabel, getSheetColor());
    }

    /**
     * Get the sheet data. Create it if not existing.
     * 
     * @return The sheet color data.
     */
    private Map<Point, Color> getSheetData()
    {
        final Integer key = Integer.valueOf(sheet);
        if (!data.containsKey(key))
        {
            data.put(key, new HashMap<>());
        }
        return data.get(key);
    }

    /**
     * Set the sheet data.
     * 
     * @param color The tile color.
     */
    private void setSheetData(Color color)
    {
        final Map<Point, Color> sheetData = getSheetData();
        sheetData.put(Geom.createPoint(selection), color);
    }

    /**
     * Get the sheet color.
     * 
     * @return The current tile sheet color.
     */
    private Color getSheetColor()
    {
        final Map<Point, Color> sheetData = getSheetData();
        return sheetData.get(selection);
    }

    /*
     * AbstractDialog
     */

    @Override
    protected void createContent(Composite content)
    {
        content.setLayout(new GridLayout(1, false));
        content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        final Composite area = new Composite(content, SWT.NONE);
        area.setLayout(new GridLayout(3, false));
        area.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

        final Label sheetLabel = createSheetArea(area);

        final Label separator = new Label(area, SWT.SEPARATOR | SWT.VERTICAL);
        separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        final Label colorLabel = createColorArea(area);

        sheetLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseDown(MouseEvent event)
            {
                final int tx = event.x / map.getTileWidth();
                final int ty = event.y / map.getTileHeight();
                selection.set(tx, ty);
                if (!colorLabel.isDisposed())
                {
                    changeColor(colorLabel, getSheetColor());
                }
                if (!sheetLabel.isDisposed())
                {
                    sheetLabel.redraw();
                }
            }
        });

        createSheetSelector(content, sheetLabel, colorLabel);
    }

    @Override
    protected void onFinish()
    {
        final Map<TileRef, ColorRgba> tiles = new HashMap<>();
        for (final Map.Entry<Integer, Map<Point, Color>> currentSheet : data.entrySet())
        {
            final Integer sheet = currentSheet.getKey();
            for (final Map.Entry<Point, Color> current : currentSheet.getValue().entrySet())
            {
                final Point point = current.getKey();
                final int number = point.getX() + point.getY() * map.getSheet(sheet).getTilesHorizontal();
                final Color color = current.getValue();
                tiles.put(new TileRef(sheet, number), new ColorRgba(color.getRed(), color.getGreen(), color.getBlue()));
            }
        }

        MinimapConfig.exports(minimap, tiles);
    }
}