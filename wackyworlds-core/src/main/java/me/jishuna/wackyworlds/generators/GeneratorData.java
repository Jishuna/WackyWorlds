package me.jishuna.wackyworlds.generators;

import java.awt.image.BufferedImage;

public class GeneratorData {

    private final boolean[][] data;
    
    private final int width;
    private final int height;

    public GeneratorData(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        
        this.data = new boolean[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int z = 0; z < image.getHeight(); z++) {
                int pixel = image.getRGB(x, z);

                if (((pixel >> 24) & 0xff) >= 255) {
                    data[x][z] = true;
                }
            }
        }
    }
    
    public boolean isFilled(int x, int z) {
        return data[x][z];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
