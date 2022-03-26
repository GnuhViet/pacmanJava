package com.pacman.entity;

import com.pacman.utils.BufferedImageLoader;
import com.pacman.utils.Constants;

import java.awt.*;
import java.io.IOException;

public class Map {
    private Constants.Cell[][] map;
    private boolean isEnergizerOff;
    private SpriteSheet mapSprite;

    public Map() throws IOException {
        mapSprite = new SpriteSheet(BufferedImageLoader.loadImage("src\\com\\pacman\\res\\Entity\\Map32.png"));
        isEnergizerOff = false;
    }

    public void setMap(Constants.Cell[][] map) {
        this.map = map;
    }

    public void setMapItem(int x, int y, Constants.Cell element) {
        map[y][x] = element;
    }

    public Constants.Cell getMapItem(int x, int y) {
        return map[y][x];
    }
    public boolean isClear() {
        for (int i = 0; i < Constants.MAP_WIDTH; i++) {
            for (int j = 0; j < Constants.MAP_HEIGHT; j++) {
                if (map[i][j] == Constants.Cell.Pellet || map[i][j] == Constants.Cell.Energizer) {
                    return false;
                }
            }
        }
        return true;
    }

    public void energizerSwitch() {
        isEnergizerOff = !isEnergizerOff;
    }

    public boolean mapCollision(boolean iUseDoor, int iX, int iY) {
        boolean output = false;

        double cellX = iX / (double)(Constants.CELL_SIZE);
        double cellY = iY / (double)(Constants.CELL_SIZE);

        for (int i = 0; i < 4; i++) {
            int x = 0;
            int y = 0;

            switch (i) {
                case 0://TOP LEFT CELL
                {
                    x = (int)Math.floor(cellX);
                    y = (int)Math.floor(cellY);
                    break;
                }
                case 1: //TOP RIGHT
                {
                    x = (int)Math.ceil(cellX);
                    y = (int)Math.floor(cellY);
                    break;
                }
                case 2:
                {
                    x = (int)Math.floor(cellX);
                    y = (int)Math.ceil(cellY);
                    break;
                }
                case 3:
                {
                    x = (int)Math.ceil(cellX);
                    y = (int)Math.ceil(cellY);
                }
            }

            // kiem tra xem vi tri co trong map khong
            if (0 <= x && 0 <= y && Constants.MAP_HEIGHT > y && Constants.MAP_WIDTH > x)
            {
                if (Constants.Cell.Wall == map[y][x]) {
                    output = true;
                }
                else if (iUseDoor == false && Constants.Cell.Door == map[y][x]) {
                    output = true;
                }
            }
        }

        return output;
    }

    public void drawMap(Graphics2D g2d) throws IOException {
        for (int a = 0; a < Constants.MAP_WIDTH; a++) {
            for (int b = 0; b < Constants.MAP_HEIGHT; b++) {

                int xPos = ((b * Constants.CELL_SIZE));
                int yPos = ((a * Constants.CELL_SIZE));

                switch (map[a][b]) {
                    case Door: {
                        g2d.drawImage(mapSprite.grabImage(1, 2), xPos, yPos, null);
                        break;
                    }
                    case Energizer: {
                        if (isEnergizerOff) {
                            g2d.drawImage(mapSprite.grabImage(1, 3), xPos, yPos, null);
                            break;
                        }
                        g2d.drawImage(mapSprite.grabImage(1, 1), xPos, yPos, null);
                        break;
                    }
                    case Pellet: {
                        g2d.drawImage(mapSprite.grabImage(1, 0), xPos, yPos, null);
                        break;
                    }
                    case Wall: {

                        int up = 0;         // b la truc x
                        int left = 0;       // a la truc y
                        int down = 0;
                        int right = 0;

                        if (b < Constants.MAP_WIDTH - 1) {
                            if (Constants.Cell.Wall == map[a][b + 1]) {
                                right = 1; // right
                            }
                        }

                        if (a > 0) {
                            if (Constants.Cell.Wall == map[a - 1][b]) {
                                up = 1; // up
                            }
                        }

                        if (a < Constants.MAP_HEIGHT - 1) {
                            if (Constants.Cell.Wall == map[a + 1][b]) {
                                down = 1; // dow
                            }
                        }

                        if (b > 0) {
                            if (Constants.Cell.Wall == map[a][b - 1]) {
                                left = 1; // left
                            }
                        }

                        /////// sprite pattern
                        int pos = down + 2 * left + 4 * right + 8 * up;

                        g2d.drawImage(mapSprite.grabImage(0, pos), xPos, yPos, null);
                    }
                }
            }
        }
    }
}
