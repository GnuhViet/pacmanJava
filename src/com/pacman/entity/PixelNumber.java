package com.pacman.entity;

import com.pacman.utils.BufferedImageLoader;
import com.pacman.utils.Constants;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PixelNumber {
    public static final String path = "src\\com\\pacman\\res\\Number.png";
    private SpriteSheet numberSheet;

    public PixelNumber() throws IOException {
        numberSheet = new SpriteSheet(BufferedImageLoader.loadImage(path));
    }

    private static Object[] toArray(int value) {
        List<Integer> list = new LinkedList<Integer>();
        while (value > 0) {
            list.add(value % 10);
            value /= 10;
        }
        Collections.reverse(list);
        return list.toArray();
    }

    public void draw(Graphics2D g2d, int value, int x, int y) {
        if (value < 0) {
            return;
        }
        if (value < 10) {
            g2d.drawImage(numberSheet.grabImage(0, value), x, y, null);
            return;
        }
        Object[] number = toArray(value);
        int n = number.length;
        for (Object o : number) {
            g2d.drawImage(numberSheet.grabImage(0, (int) o), x, y, null);
            x += Constants.CELL_SIZE;
        }
    }
}