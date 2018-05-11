package pruebapacman;

/**
 *:)
 * Esta clase permite generar el objeto Tablero del juego
 * Probando
 * @author Juan Ernesto
 */
import clases.jugador;
import clases.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private final Font smaller= new Font("Helvetica",Font.BOLD, 13);

    private final Color dotColor = new Color(255, 255, 255);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean eatingGhost = false;
    private boolean dying = false;
    

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int MAX_GHOSTS = 2; // TODO: Aumentar la cantidad de fantasmas
    private final int PACMAN_SPEED = 6;

    private int superPacmanCount = 0;
    private int pacmanAnimPoints = 0;
    private boolean dirChanged = false;
    private final int POINTS_EAT_GHOST = 200;
    private boolean dotsEaten = true;

    //private int pacAnimCount = PAC_ANIM_DELAY;
    //private int ghostsAnimCount = GHOSTS_ANIM_COUNT;
    //private int pacAnimDir = 1;
    //private int ghostsAnimDir = 1;
    //private int pacmanAnimPos = 0;
    //private int pacmanAnimPosDie = 0; // para animar cuando pacman muere
    //private int pacmanAnimPoints = 0;
    //private int ghostsAnimPos = 0; // Para animar los fantasmas

    private int N_GHOSTS = 3;
    private int score;
    private int[] dx, dy;
    private jugador jugador;
    private ArrayList <Fantasma> fantasmas = new ArrayList<>(); // array list para crear los objetos Fantasma
    private Mago mago;
    private Ogro ogro;
    private int acumPointsEat = 0;
    private int whatEatGhost = -1;
    private int levelnum = 1;
/////    private Image ghost; //imagen png del fantasma
////    private Image clydeGhost;
////    private Image blinkyGhost;
////    private Image inkyGhost;
////    private Image pinkyGhost;
//    private Image[] pacmanUp = new Image[3];
//    private Image[] pacmanRight = new Image[3];
//    private Image[] pacmanDown = new Image[3];
//    private Image[] pacmanLeft = new Image[3];
//    private Image[] pacmanLeftDie = new Image[6];
//    private Image pacman1;

    private Image ghostEyes;
    private Image ghostScared;

    // Descripción de la variables:
    // pacman.getPosx(), pacman.getPosy() son las posiciones en los dos ejes
    // pacman.getDirx(), pacman.getDiry() es la aceleracion en los dos ejes
    private int req_dx, req_dy, view_dx, view_dy;
    // con bolita
    // 19 (10011) = ┌*
    // 22 (10110) = *┐
    // 25 (11001) = └*
    // 28 (11100) = *┘

    // sin bolita
    // 1 (00001) = linea izq
    // 2 (00010) = Linea superior
    // 4 (00100) = Linea derecha
    // 8 (01000) = Linea inferior
    // 3 (00011) = ┌
    // 6 (00110) =  ┐
    // 9 (01001) = └
    // 12 (01100) =  ┘
    // 15 (01111) = ┌ ┘ (cuadro cerrado sin bolita)
    // 11  (01011) = (cuadro abierto por la derecha)
    // 13  (01101) = (cuadro abierto por arriba)
    // 14  (01110) = (cuadro abierto por izq)

//    private final short levelData[] = {
//        19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
//        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
//        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
//        21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
//        17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
//        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
//        25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
//        1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
//        1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
//        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
//        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
//        1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
//        1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
//        1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
//        9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
//    };

 private final short levelData1 [] = {
 //      1   2   3   4   5   6   7   8   9  10  11  12  13  14  15
/*1*/   19, 26, 26, 26, 26, 18, 26, 26, 26, 26, 26, 26, 26, 26, 22,
/*2*/   21, 19, 26, 18, 22, 21, 19, 18, 18, 18, 18, 18, 18, 38, 21,
/*3*/   45, 21, 39, 17, 20, 21, 17, 24, 24, 24, 24, 24, 24, 28, 21,
/*4*/   19, 16, 24, 24, 28, 21, 21, 35, 18, 18, 26, 26, 26, 26, 20,
/*5*/   17, 24, 26, 26, 26, 28, 21, 25, 24, 20, 19, 18, 18, 22, 21,
/*6*/   21, 19, 18, 18, 18, 18, 16, 18, 22, 21, 17, 16, 16, 20, 21,
/*7*/   37, 21, 21, 17, 16, 16, 16, 16, 20, 21, 17, 16, 24, 28, 21,
/*8*/   21, 21, 21, 17, 16, 16, 16, 16, 20, 21, 17, 20, 19, 22, 21,
/*9*/   21, 21, 21, 17, 16, 16, 16, 16, 20, 21, 17, 20, 17, 20, 21,
/*10*/  21, 21, 21, 17, 24, 24, 12, 21, 29, 21, 17, 16, 16, 20, 21,
/*11*/  21, 21, 21, 21, 19, 26, 26, 16, 26, 28, 17, 20, 17, 20, 21,
/*12*/  21, 21, 45, 21, 21, 19, 22, 21, 19, 18, 16, 20, 17, 20, 21,
/*13*/  21, 17, 18, 20, 21, 17, 16, 16, 16, 16, 16, 20, 25, 44, 21,
/*14*/  21, 25, 24, 28, 21, 25, 24, 24, 24, 24, 24, 24, 26, 30, 21,
/*15*/  25, 26, 26, 26, 24, 26, 26, 42, 26, 26, 26, 26, 26, 26, 28,
            };
    private final short levelData[] = {
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 38,
        17, 21, 16, 16, 24, 16, 16, 16, 16, 16, 24, 16, 16, 21, 20,
        17, 16, 16, 20,  0, 17, 16, 16, 16, 20, 0, 17, 16, 16, 20,
        17, 16, 24, 28,  0, 25, 24, 16, 16, 28, 0, 25, 24,  24,20,
        17,  20,  0,  0,  0,  0, 0, 1, 20, 0, 0,  0,  0,  0, 21,
        17, 16, 18, 18, 18, 18, 18, 16, 16, 18, 18, 18, 18, 18, 20,
        17, 16, 16, 16, 0, 16, 24, 24, 24, 16, 16, 16, 16, 16, 20,
        17, 21, 16, 21, 16, 21,  0,  0,  0, 21, 16, 21, 16, 21, 20,
        17, 24, 24, 16, 24, 24, 22,  0, 19, 24, 24, 16, 24, 24, 20,
        21,  0,  0, 21,  0,  0, 21,  0, 21,  0,  0, 21,  0,  0, 21,
        25, 22,  0, 17, 26, 18, 24, 26, 24, 18, 26, 20,  0, 19, 28,
         0, 21,  0, 21,  0, 21,  0,  0,  0, 21,  0, 21,  0, 21,  0,
        35, 24, 26, 28,  0, 25, 22,  0, 35, 28,  0, 25, 26, 24, 22,
        21,  0,  0,  0,  0,  0, 21,  0, 21,  0,  0,  0,  0,  0, 21,
        25, 26, 26, 26, 26, 26, 24, 26, 24, 26, 26, 26, 26, 26, 28
        };

    private final short levelData2[] = {
//      1   2   3   4   5   6   7   8   9   10  11  12  13  14  15
/*1*/   19, 26, 18, 26, 26, 26, 26, 18, 26, 26, 26, 26, 18, 26, 22,
/*2*/   21, 7,  21, 11,  10,  10,  0,  17, 24, 24, 24, 44, 21, 7,  21,
/*3*/   17, 0,  16, 26, 26, 26, 26, 24, 26, 18, 26, 26, 16, 0,  20,
/*4*/   21, 21, 21, 19, 26, 26, 26, 26, 28, 21, 43, 22, 21, 21, 21,
/*5*/   21, 21, 21, 45, 19, 24, 26, 26, 26, 24, 22, 21, 21, 21, 21,
/*6*/   21, 21, 17, 26, 20, 39,  7,  7, 19, 22, 17, 24, 20, 21, 21,
/*7*/   21, 45, 21, 0,  21, 21, 21, 21, 17, 20, 21, 15, 21, 21, 21,
/*8*/   17, 26, 20, 15, 21, 21, 17, 0,  20, 21, 21, 15, 17, 0,  20,
/*9*/   21, 39, 21, 15, 21, 21, 21, 21, 17, 20, 21, 15, 21, 21, 21,
/*10*/  21, 21, 17, 26,  20, 25, 28, 21, 25,28, 17, 26, 20, 21, 21,
/*11*/  21, 21, 17, 22, 25, 26, 26, 24, 26, 26, 28, 39, 21, 21, 21,
/*12*/  21, 21, 21, 25, 18, 26, 26, 26, 26, 26, 26, 28, 21, 21, 21,
/*13*/  17, 0,  16, 14, 21, 11, 26, 18, 26, 26, 26, 26, 16, 0,  20,
/*14*/  21, 45, 21, 11, 24, 26, 14, 21,  0, 15, 15,  0, 21, 45, 21,
/*15*/  25, 26, 24, 26, 26, 26, 26, 24, 26, 26, 26, 26, 24, 26, 28,
            };
    
    
     private final short levelDataFinal[] = {
//      1   2   3   4   5   6   7   8   9   10  11  12  13  14  15
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
         0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     };
    // TODO: Agregar una forma en la que un espacio abierto en el borde te mande al otro extremo de la pantalla


    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 4;

    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;

    public Board() {

        loadImages();
        initVariables();
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    }

    private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        mazeColor = new Color(150, 71, 38);
        d = new Dimension(420, 400); // esta variable solamente se usa para crear un rectangulo negro
        //region Inicializar Pacman
        jugador = new jugador(new Animation(AnimationEnum.PACMAN_NORMAL_LEFT), this, "Pacman");
        mago= new Mago(new Animation(AnimationEnum.MAGO_NORMAL_RIGHT),this, "Mago");
        ogro= new Ogro(new Animation(AnimationEnum.OGRO_NORMAL_RIGHT),this, "Ogro");
        //endregion

        //region Inicializar fantasmas
        // Cargar las 2 imagenes que pertenecen a cada fantasma a la animacion
//        Animation animations[] = new Animation[4];
//        for(int i = 0; i < 4; i++ ) animations[i] = new Animation(new Image[2], 5);
//        int arrPos = 0;
//
//        for(GhostType ghost : GhostType.values()) {
//            Image gImages[] = animations[arrPos].getImages();
//            String name = ghost.name().toLowerCase();
//            gImages[0] = new ImageIcon("images/" + name + " v1.png").getImage();
//            gImages[1] = new ImageIcon("images/" + name + " v2.png").getImage();
//
//            animations[arrPos++].setImages(gImages);
//        }

        // aquí se agregan los objetos fantasma al array list
        fantasmas.add(new Fantasma(new Animation(AnimationEnum.MAGO_NORMAL_RIGHT),this,"Mago1"));       
        fantasmas.add(new Fantasma(new Animation(AnimationEnum.OGRO_NORMAL_RIGHT),this,"Ogro1"));
        fantasmas.add(new Fantasma(new Animation(AnimationEnum.OGRO_NORMAL_LEFT),this,"Ogro2"));
        //endregion



        // esto agregará los fantasmas restantes según el nivel de dificultad
        // hasta llegar al máximo de fantasmas
//        ghost_x = new int[MAX_GHOSTS];
//        ghost_dx = new int[MAX_GHOSTS];
//        ghost_y = new int[MAX_GHOSTS];
//        ghost_dy = new int[MAX_GHOSTS];
//        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(60, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    private void playGame(Graphics2D g2d){

        if (dying) {

            if (jugador.getCurrentAnimation().getCurrentFrame() == 0){
//                Sound.FONDO.stop();
//                timer.stop();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.start();
//                Sound.PACMAN_DEATH.play();
            }
            jugador.update();
            drawPacmanDie(g2d);
           // timer.wait(1000);
            if (jugador.getCurrentAnimation().isFinished()){
                death();
//                timer.stop();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.start();
                
            }
        } else {
            if (whatEatGhost == -1){
                 moveJugador();
            }
            drawPacman(g2d);
            moveVillano(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
       
        Font small = new Font("Helvetica", Font.BOLD, 14);   
        FontMetrics metr = this.getFontMetrics(small);
        g2d.setFont(small);
        String s = "Dungeon Knight" ;
        g2d.setColor(Color.white);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s))/ 2, SCREEN_SIZE/2 - 10);
        g2d.setColor(Color.LIGHT_GRAY);
        String S= "Press s to start.";
        g2d.drawString(S, (SCREEN_SIZE - metr.stringWidth(S)) / 2, SCREEN_SIZE / 2 + 10);
        
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;      
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Puntuacion: " + score;
        
        g.drawString(s, SCREEN_SIZE / 2 + 50, SCREEN_SIZE + 16);
        
       
        for (i = 0; i < jugador.getHealth(); i++) {
            g.drawImage(new ImageIcon("images/Health4.png").getImage(), i * 3, SCREEN_SIZE + 7, this);
        }
        String H;
        H = "Vida";
        g.setFont(smallFont);
        g.setColor(new Color(0, 150, 0));
        g.drawString(H, SCREEN_SIZE / 2 -155, SCREEN_SIZE + 30);
    }
    

    private void checkMaze() {

        short i = 0;
        dotsEaten = true;
        boolean hadasEaten = true;

        while (i < N_BLOCKS * N_BLOCKS && dotsEaten) {

            if ((screenData[i] & 16) != 0) {
                dotsEaten = false;
            }

            i++;
        }

        i = 0;
        while (i < N_BLOCKS * N_BLOCKS && hadasEaten) {

            if ((screenData[i] & 32) != 0) {
                hadasEaten = false;
            }

            i++;
        }

        if (hadasEaten) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }
            
            levelnum++;

            initLevel ();
        }
    }

    private void death() {

        jugador.setHealth(jugador.getHealth() - 5);
        jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_NORMAL_LEFT));
        if (jugador.getHealth() == 0) {
            inGame = false;
            Sound.FONDO.stop();
        }

        continueLevel();
    }

  private void moveVillano(Graphics2D g2d) {
        short i;
        int pos;
        int count;
        int currentDirX = -1;
        fantasmas.get(0).update();
        fantasmas.get(1).update();
        fantasmas.get(2).update();
//        fantasmas.get(3).update();

        for (i = 0; i < N_GHOSTS; i++) {
            if (fantasmas.get(i).getPosx() % BLOCK_SIZE == 0 && fantasmas.get(i).getPosy() % BLOCK_SIZE == 0) {
                pos = fantasmas.get(i).getPosx() / BLOCK_SIZE + N_BLOCKS * (int) (fantasmas.get(i).getPosy() / BLOCK_SIZE);
                count = 0;
                if (eatingGhost) {
                    if ((screenData[pos] & 1) == 0 && fantasmas.get(i).getDirx() != 1 && fantasmas.get(i).getPosx() <= jugador.getPosx()) {
                        dx[count] = -1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 2) == 0 && fantasmas.get(i).getDiry() != 1 && fantasmas.get(i).getPosy() <= jugador.getPosy()) {
                        dx[count] = 0;
                        dy[count] = -1;
                        count++;
                    }

                    if ((screenData[pos] & 4) == 0 && fantasmas.get(i).getDirx() != -1 && fantasmas.get(i).getPosx() >= jugador.getPosx()) {
                        dx[count] = 1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 8) == 0 && fantasmas.get(i).getDiry() != -1 && fantasmas.get(i).getPosy() >= jugador.getPosy()) {
                        dx[count] = 0;
                        dy[count] = 1;
                        count++;
                    }
                    superPacmanCount++;
                    if (superPacmanCount > 50){
                        eatingGhost = false;
                        superPacmanCount = 0;
//                        Sound.GHOST_SCARED.stop();
                    }

                } else {
                    if ((screenData[pos] & 1) == 0 && fantasmas.get(i).getDirx() != 1) {
                        dx[count] = -1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 2) == 0 && fantasmas.get(i).getDiry() != 1) {
                        dx[count] = 0;
                        dy[count] = -1;
                        count++;
                    }

                    if ((screenData[pos] & 4) == 0 && fantasmas.get(i).getDirx() != -1) {
                        dx[count] = 1;
                        dy[count] = 0;
                        count++;
                    }

                    if ((screenData[pos] & 8) == 0 && fantasmas.get(i).getDiry() != -1) {
                        dx[count] = 0;
                        dy[count] = 1;
                        count++;
                    }
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        //ghost_dx[i] = 0;
                        fantasmas.get(i).setDirx(0);
                        //ghost_dy[i] = 0;
                         fantasmas.get(i).setDiry(0);
                    } else {
                        //ghost_dx[i] = -fantasmas.get(i).getDirx();
                        fantasmas.get(i).setDirx(-fantasmas.get(i).getDirx());
                        //ghost_dy[i] = -ghost_dy[i];
                        fantasmas.get(i).setDiry(-fantasmas.get(i).getDiry());
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }
                    // Se guarda la dirección horizonta (x) actual del fantasma
                    // para saber si no ha cambiado desde la última vez
                    // esto permite que no se pierda la animación
                    currentDirX = fantasmas.get(i).getDirx();
                    //ghost_dx[i] = dx[count];
                    fantasmas.get(i).setDirx(dx[count]);
                    //ghost_dy[i] = dy[count];
                    fantasmas.get(i).setDiry(dy[count]);
                }

            }
            fantasmas.get(i).setPosx(fantasmas.get(i).getPosx() + (fantasmas.get(i).getDirx() * fantasmas.get(i).getSpeed()));
            fantasmas.get(i).setPosy(fantasmas.get(i).getPosy() + (fantasmas.get(i).getDiry() * fantasmas.get(i).getSpeed()));
            if (fantasmas.get(i).getDirx() != currentDirX && fantasmas.get(i).getDirx() != 0) { // se revisa si no ha cambiado de dirección o está detenido el fantasma
                if (fantasmas.get(i).getDirx() == -1) {  // Si la dirección es IZQ
                    switch (i) {
                        case 0:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.MAGO_NORMAL_LEFT));
                            break;
                        case 1:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.OGRO_NORMAL_LEFT));
                            break;
                        case 2:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.OGRO_NORMAL_LEFT));
                            break;
//                        case 3:
//                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.PINKY_NORMAL_LEFT));
//                            break;
                    }

                } else {
                    // si no es DER 
                    switch (i) {
                        case 0:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.MAGO_NORMAL_RIGHT));
                            break;
                         case 1:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.OGRO_NORMAL_RIGHT));
                            break;
                        case 2:
                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.OGRO_NORMAL_RIGHT));
                            break;
//                        case 3:
//                            fantasmas.get(i).setCurrentAnimation(new Animation(AnimationEnum.PINKY_NORMAL_RIGHT));
//                            break;
                    }

                }
            }                
            if (fantasmas.get(i).getEating() == false && eatingGhost == false){
                drawVillano(g2d, i, fantasmas.get(i).getCurrentAnimation().getCurrentFrame());
            }
//            if (fantasmas.get(i).getEating() == true){
//                drawEatenGhost(g2d,i); // si el fantasma ya está comido
//            }
//            if (fantasmas.get(i).getEating() == false && eatingGhost == true){
//                drawScaredGhost(g2d,i); // si el fantasma ya está comido
//            }
            if (jugador.getPosx() > (fantasmas.get(i).getPosx() - 12) && jugador.getPosx() < (fantasmas.get(i).getPosx() + 12)
                    && jugador.getPosy() > (fantasmas.get(i).getPosy() - 12) && jugador.getPosy() < (fantasmas.get(i).getPosy() + 12)
                    && inGame && eatingGhost == false && fantasmas.get(i).getEating() == false ) {
                dying = true;
                jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_DIE));
            }
            if (jugador.getPosx() > (fantasmas.get(i).getPosx() - 12) && jugador.getPosx() < (fantasmas.get(i).getPosx() + 12)
                    && jugador.getPosy() > (fantasmas.get(i).getPosy() - 12) && jugador.getPosy() < (fantasmas.get(i).getPosy() + 12)
                    && inGame && eatingGhost == true && fantasmas.get(i).getEating() == false ) {

//                Sound.GHOST_SCARED.stop();
                Sound.GHOST_EATEN.play();
                whatEatGhost = i;
                fantasmas.get(i).setEating(true);
//                Sound.GHOST_SCARED.loop();
                score = score + acumPointsEat;
                fantasmas.get(i).setVisible(false);
            }
        }

    }
  
    private void drawVillano(Graphics2D g2d, int i, int frame) {
        // para dibujar el fantasma se toma la imagen del objeto
        g2d.drawImage(fantasmas.get(i).getCurrentAnimation().getImages()[frame], fantasmas.get(i).getPosx(), fantasmas.get(i).getPosy(), this);
        //g2d.drawImage(fantasmas.get(i).getImages()[frame], fantasmas.get(i).getPosx(), fantasmas.get(i).getPosy(), this);  
      //g2d.drawImage(ghost, x, y, this);
    }
    private void drawEatenGhost(Graphics2D g2d, int i) {
        // para dibujar el fantasma se toma la imagen del objeto
        if (fantasmas.get(i).getVisible()){
            g2d.drawImage(ghostEyes, fantasmas.get(i).getPosx(), fantasmas.get(i).getPosy(), this);
        }
        //g2d.drawImage(ghost, x, y, this);
    }
//    private void drawScaredGhost(Graphics2D g2d, int i) {
//        // para dibujar el fantasma se toma la imagen del objeto
//        //if (fantasmas.get(i).getVisible()){
//            g2d.drawImage(ghostScared, fantasmas.get(i).getPosx(), fantasmas.get(i).getPosy(), this);
//        //}
//        //g2d.drawImage(ghost, x, y, this);
//    }
    private void moveJugador() {

        jugador.update();
        int pos;
        short ch;
        boolean flag = false;
        if (req_dx == -jugador.getDirx() && req_dy == -jugador.getDiry()) {
            // checar si cambio la direccion
            dirChanged = (req_dx != jugador.getDirx() || req_dy != jugador.getDiry());
            flag = true;
            jugador.setDirx(req_dx);
            jugador.setDiry(req_dy);
            view_dx = jugador.getDirx();
            view_dy = jugador.getDiry();
        }

        if (jugador.getPosx() % BLOCK_SIZE == 0 && jugador.getPosy() % BLOCK_SIZE == 0) {
            pos = jugador.getPosx() / BLOCK_SIZE + N_BLOCKS * (int) (jugador.getPosy() / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
//                Sound.PACMAN_MUNCH.play();
            }
            // si se come una hada y ya se comió todas las bolitas
            if ((ch & 32) != 0 && dotsEaten) {
                screenData[pos] = (short) (ch & 15);
                score = score + 100;
                superPacmanCount = 0;
                eatingGhost = false;
                acumPointsEat = 200;
                Sound.HADA.play();
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    // checar si cambio la direccion
                    if(!flag) dirChanged = ((req_dx != jugador.getDirx() || req_dy != jugador.getDiry()));
                    jugador.setDirx(req_dx);
                    jugador.setDiry(req_dy);
                    view_dx = jugador.getDirx();
                    view_dy = jugador.getDiry();
                }
            }

            // Check for standstill
            if ((jugador.getDirx() == -1 && jugador.getDiry() == 0 && (ch & 1) != 0)
                    || (jugador.getDirx() == 1 && jugador.getDiry() == 0 && (ch & 4) != 0)
                    || (jugador.getDirx() == 0 && jugador.getDiry() == -1 && (ch & 2) != 0)
                    || (jugador.getDirx() == 0 && jugador.getDiry() == 1 && (ch & 8) != 0)
                    || (jugador.getDirx() == -1 && jugador.getDiry() == 0 && (ch & 1) != 0)) {
                jugador.setDirx(0);
                jugador.setDiry(0);
            }
        }
        jugador.setPosx(jugador.getPosx() + PACMAN_SPEED * jugador.getDirx());
        jugador.setPosy(jugador.getPosy() + PACMAN_SPEED * jugador.getDiry());
    }

    // dibujar a personaje
    private void drawPacman(Graphics2D g2d) {
        //TODO: Recordar el frame en que se quedo la animacion
       if (whatEatGhost == -1) { 
        if(dirChanged) {
            int frame = jugador.getCurrentAnimation().getCurrentFrame();
          
            if (view_dx == -1) {
                jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_NORMAL_LEFT));
            } else if (view_dx == 1) {
                jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_NORMAL_RIGHT));
            } else if (view_dy == -1) {
                jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_NORMAL_UP));
            } else {
                jugador.setCurrentAnimation(new Animation(AnimationEnum.PACMAN_NORMAL_DOWN));
            }
          }
        g2d.drawImage(jugador.getCurrentAnimation().getImages()[jugador.getCurrentAnimation().getCurrentFrame()],
                jugador.getPosx() + 1 , jugador.getPosy() + 1 , this);
       }
       else{
            if (pacmanAnimPoints <= 6){
                pacmanAnimPoints++;
                g2d.drawString(String.valueOf(acumPointsEat), jugador.getPosx() + 2, jugador.getPosy() + 14);
            }
            else{
                pacmanAnimPoints = 0;
                timer.stop();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.start();
                fantasmas.get(whatEatGhost).setVisible(true);
                whatEatGhost = -1;
                acumPointsEat = acumPointsEat + POINTS_EAT_GHOST;
                Sound.HADA.play();
            }
            
          
        }
    }
    // este método hace la animación de cuando Pacman muere
    private void drawPacmanDie(Graphics2D g2d) {
        g2d.drawImage(jugador.getCurrentAnimation().getImages()[jugador.getCurrentAnimation().getCurrentFrame()], 
                jugador.getPosx() + 1 , jugador.getPosy() + 1 , this);
    }
    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(5));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                // Dibuja el cuadro de poder de super pacman
                if ((screenData[i] & 32) != 0) {
                    g2d.drawImage(AnimationEnum.HADA.getImages()[0], x + 3 , y + 3 , this);
                }
                
                

                i++;
            }
        }
    }

    private void initGame() {
        jugador.setHealth(20);
        score = 0;
        initLevel();
        N_GHOSTS = 3;
        currentSpeed = 3;
    }
    private void drawFinal(Graphics2D g2d) {
        initLevel();
        if(levelnum>3){
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        Font small = new Font("Helvetica", Font.BOLD, 14);   
        FontMetrics metr = this.getFontMetrics(small);
        g2d.setFont(small);
        String s = "Fin del juego" ;
        g2d.setColor(Color.white);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s))/ 2, SCREEN_SIZE/2 - 10);
        g2d.setColor(Color.LIGHT_GRAY);
        String S= "Gracias por jugar";
        g2d.drawString(S, (SCREEN_SIZE - metr.stringWidth(S)) / 2, SCREEN_SIZE / 2 + 10);
        }}
    private void initLevel() {

        int i;
        if (levelnum == 1) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = levelData[i];
            }
        }
        if (levelnum == 2) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = levelData1[i];
            }
        }
        
        if (levelnum == 3) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = levelData2[i];
            }

            inGame = false;
        }
        if (levelnum > 3) {
            for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
                screenData[i] = levelDataFinal[i];
            }
                         
        }      
        continueLevel();
    }
    
    private void continueLevel() {
//      short i;
        int dx = 1;
        int random;
         // se configuran los fantasmas del array list
        for (Fantasma oFantasma : fantasmas) {
            oFantasma.setPosx(4 * BLOCK_SIZE);
            oFantasma.setPosy(4 * BLOCK_SIZE);
            oFantasma.setDiry(0);
            oFantasma.setDirx(dx);
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));
            if (random > currentSpeed) {
                random = currentSpeed;
            }
            oFantasma.setSpeed(validSpeeds[random]);
            oFantasma.setEating(false);
        }

        jugador.setPosx(7 * BLOCK_SIZE);
        jugador.setPosy(10 * BLOCK_SIZE);
        jugador.setDirx(0);
        jugador.setDiry(0);
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        dying = false;
        eatingGhost = false; // indica si pacman puede comer fantasmas
        superPacmanCount = 0;
//        Sound.GHOST_SCARED.stop();
    }

    private void loadImages() {
        ghostEyes = new ImageIcon("images/ghost eyes.png").getImage();
        ghostScared = new ImageIcon("images/ghost scared.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            if(levelnum > 3) {
                drawFinal(g2d);
            } else {
                Sound.FONDO.stop();
                showIntroScreen(g2d);
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if(levelnum > 3) return;

                if (key == 's' || key == 'S') {
                    Sound.INTRO.play();
                    inGame = true;
                    initGame();
                    Sound.FONDO.loop(); // inicia a sonar la sirena del juego
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
