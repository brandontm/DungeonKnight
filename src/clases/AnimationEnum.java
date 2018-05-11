package clases;

import javax.swing.*;
import java.awt.*;

public enum AnimationEnum {
    PACMAN_IDLE(new Image[]{new ImageIcon("images/Personaje (Right).png").getImage()}, 1, true),

    PACMAN_NORMAL_UP(new Image[]{
            new ImageIcon("images/Personaje(Walking1Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking1Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking2Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking2Right).png").getImage()}, 1, true),

    PACMAN_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/Personaje(Walking1Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking1Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking2Right).png").getImage(),
            new ImageIcon("images/Personaje(Walking2Right).png").getImage()}, 1, true),

    PACMAN_NORMAL_DOWN(new Image[]{
            new ImageIcon("images/Personaje (Walking1Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking1Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking2Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking2Left).png").getImage()}, 1, true),

    PACMAN_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/Personaje (Walking1Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking1Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking2Left).png").getImage(),
            new ImageIcon("images/Personaje (Walking2Left).png").getImage()}, 1, true),

    PACMAN_DIE(new Image[]{
            new ImageIcon("images/Personaje(DieRight1).png").getImage(),
            new ImageIcon("images/Personaje(DieRight2).png").getImage(),
            new ImageIcon("images/Personaje(DieRight3).png").getImage(),
            new ImageIcon("images/Personaje(DieRight4).png").getImage(),
            new ImageIcon("images/Personaje(DieRight5).png").getImage(),
            new ImageIcon("images/Personaje(DieRight6).png").getImage()}, 1, false),
    
    MAGO_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/mright1.png").getImage(),
            new ImageIcon("images/mright2.png").getImage(),
            new ImageIcon("images/mright3.png").getImage()}, 1, true),
    
    MAGO_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/mleft1.png").getImage(),
            new ImageIcon("images/mleft2.png").getImage(),
            new ImageIcon("images/mleft3.png").getImage()}, 1, true),
    MAGO_NORMAL_DOWN(new Image[]{
            new ImageIcon("images/mfront.png").getImage()}, 1, true),
    MAGO_NORMAL_UP(new Image[]{
            new ImageIcon("images/mdown.png").getImage()}, 1, true),
    
    OGRO_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/Ogreleft.png").getImage(),
            new ImageIcon("images/Ogreleft1.png").getImage(),
            new ImageIcon("images/Ogreleft2.png").getImage()}, 1, true),
    
    OGRO_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/Ogreright.png").getImage(),
            new ImageIcon("images/Ogreright1.png").getImage(),
            new ImageIcon("images/Ogreright2.png").getImage()}, 1, true),
    OGRO_NORMAL_UP(new Image[]{
     new ImageIcon("images/Ogreright.png").getImage(),
            new ImageIcon("images/Ogreright1.png").getImage(),
            new ImageIcon("images/Ogreright2.png").getImage()}, 1, true),     
    
    OGRO_NORMAL_DOWN(new Image[]{
     new ImageIcon("images/Ogreright.png").getImage(),
            new ImageIcon("images/Ogreright1.png").getImage(),
            new ImageIcon("images/Ogreright2.png").getImage()}, 1, true), 

    CLYDE_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/clyde v3.png").getImage(),
            new ImageIcon("images/clyde v4.png").getImage()}, 4, true),
    CLYDE_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/clyde v1.png").getImage(),
            new ImageIcon("images/clyde v2.png").getImage()}, 4, true),

    BLINKY_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/blinky v3.png").getImage(),
            new ImageIcon("images/blinky v4.png").getImage()}, 4, true),
    BLINKY_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/blinky v1.png").getImage(),
            new ImageIcon("images/blinky v2.png").getImage()}, 4, true),

    INKY_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/inky v3.png").getImage(),
            new ImageIcon("images/inky v4.png").getImage()}, 4, true),
    INKY_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/inky v1.png").getImage(),
            new ImageIcon("images/inky v2.png").getImage()}, 4, true),

    PINKY_NORMAL_LEFT(new Image[]{
            new ImageIcon("images/pinky v3.png").getImage(),
            new ImageIcon("images/pinky v4.png").getImage()}, 4, true),
    PINKY_NORMAL_RIGHT(new Image[]{
            new ImageIcon("images/pinky v1.png").getImage(),
            new ImageIcon("images/pinky v2.png").getImage()}, 4, true),
    
    HADA(new Image[]{
            new ImageIcon("images/hada1.png").getImage()
            }, 4, true);
    
    

    private Image[] images;
    private final int delay;
    private final boolean looping;

    AnimationEnum(Image[] images, int delay, boolean looping) {
        this.images = images;
        this.delay = delay;
        this.looping = looping;
    }
    public Image[] getImages() {
        return images;
    }

    public int getDelay() {
        return delay;
    }

    public boolean isLooping() {
        return looping;
    }
}
