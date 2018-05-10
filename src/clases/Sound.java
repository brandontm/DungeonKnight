/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.applet.Applet;
import java.applet.AudioClip;

/**
 *
 * @author mac
 */
public class Sound {
//    public static final AudioClip PACMAN_MUNCH = Applet.newAudioClip(Sound.class.getResource("sounds/pacman_munch.wav"));
    public static final AudioClip INTRO = Applet.newAudioClip(Sound.class.getResource("sounds/Intro2.wav"));
    public static final AudioClip PACMAN_DEATH = Applet.newAudioClip(Sound.class.getResource("sounds/pacman_death.wav"));
    public static final AudioClip FONDO = Applet.newAudioClip(Sound.class.getResource("sounds/Fondo.wav"));
    public static final AudioClip HADA = Applet.newAudioClip(Sound.class.getResource("sounds/Hada.wav"));
    public static final AudioClip GHOST_EATEN = Applet.newAudioClip(Sound.class.getResource("sounds/ghost_eaten.wav"));
}