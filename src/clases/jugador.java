/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
import pruebapacman.Board;


public class jugador extends Personaje {
    private int health;

    public jugador(Animation animation, Board tablero, String name) {
        super(animation, tablero, name);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
