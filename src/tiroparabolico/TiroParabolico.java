package tiroparabolico;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class TiroParabolico extends JFrame implements Runnable, KeyListener, MouseListener {

    private Animacion animBalon; // Animacion del balon
    private Animacion cuadroCanasta; // Animacion de la canasta
    private int iDireccionCanasta;
    private Balon balon; // Objeto de la clase balon
    private Canasta canasta; // Objeto de la clase Canasta
    private long tiempoActual;  // tiempo actual
    private long tiempoInicial; // tiempo inicial
    private Image background; // Imagen de fondo de JFrame <-- Agregar Imagen
    private Image dbImage; // Imagen
    private Image gg; // Imagen de Game Over
    private Image ins; // Imagen de Instrucciones
    private Graphics dbg; // Objeto Grafico
    private int bVelx; // Velocidad en X del balon
    private int bVely; // Velocidad en Y del balon
    private int cMovx; // Movimiento en X de la canasta
    private int grav; // Gravedad
    private int vidas; // Vidas del usuario
    private int score; // Score del usuario
    private boolean click; // Booleano de click
    private boolean pausa; // Booleano de pausa
    private boolean instruc; // Booleano para desplegar instrucciones
    private boolean gameover; // Booleano para desplegar imagen gg
    private boolean mute; // Control de sonidos
    private boolean bPausado; // control de Pausado
    private boolean bInstrucciones; // control de despliego de instrucciones
    //private int score; // Puntaje del juego
    private int lives; // Vidas del jugador
    private int fouls; // Errores del jugador
    private Font myFont;
    private SoundClip fail;
    private SoundClip goal;
    private SoundClip over;
    private String datos;
    private String[] arr; // Arreglo de datos

    /**
     * Constructor Se inicializan las variables
     */
    public TiroParabolico() {
        myFont = new Font("Serif", Font.BOLD, 30); // Estilo de fuente
        pausa = false;
        mute = false;
        gameover = false;
        instruc = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1008, 758);
        click = false;
        setTitle("NBA Series!");
        score = 0;
        lives = 5;
        fouls = 3;
        bVelx = 0;
        bVely = 0;
        grav = 1;
        vidas = 14;
        datos = "";
        background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/nba.jpg"));
        ins = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/ins.jpg"));
        gg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/gg.jpg"));

        // Carga las imagenes de la animacion del balon
        Image b0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b0.png"));
        Image b1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b1.png"));
        Image b2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b2.png"));
        Image b3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b3.png"));
        Image b4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b4.png"));
        Image b5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b5.png"));
        Image c = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/canasta.png"));

        // Se crea la animacion del balon
        animBalon = new Animacion();
        animBalon.sumaCuadro(b5, 100);
        animBalon.sumaCuadro(b4, 100);
        animBalon.sumaCuadro(b3, 100);
        animBalon.sumaCuadro(b2, 100);
        animBalon.sumaCuadro(b1, 100);
        animBalon.sumaCuadro(b0, 100);

        // Se crea la animacion de la canasta
        cuadroCanasta = new Animacion();
        cuadroCanasta.sumaCuadro(c, 200);

        // Balon
        balon = new Balon(100, 300, animBalon);

        //Canasta & poniendo velocidad
        canasta = new Canasta(900, 680, cuadroCanasta);
        canasta.setVelocidad(7);

        // Se cargan los sonidos
        fail = new SoundClip("sounds/boing2.wav");
        goal = new SoundClip("sounds/bloop_x.wav");
        over = new SoundClip("sounds/buzzer_x.wav");
        
        // inicializa la variable de pausado
        bPausado = false;
        
        // inicializa la variable de instrucciones
        bInstrucciones = false;

        addMouseListener(this);
        addKeyListener(this);
        Thread th = new Thread(this);
        th.start();
    }

    /**
     * Se ejecuta el Thread, el juego no continua si la pausa esta activada. El
     * juego finaliza si el numero de vidas en menor o igual que 0. El juego
     * tambien se pausa si el usuario desea ver las instrucciones.
     */
    public void run() {

        // Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();
        while (!gameover) {
            
            // si el juego no esta pausado
            if(!(bPausado || bInstrucciones)){
                checaColision();
                actualiza();                
            }
            
            // ciclo de pintado
            repaint();
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * En este metodo se actualiza las posiciones del balon y de la canasta.
     */
    public void actualiza() {
        grav = 6 - lives / 3;
        if (click) {
            balon.setPosY(balon.getPosY() - bVely);
            bVely -= grav;
            balon.setPosX(balon.getPosX() + bVelx);
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            tiempoActual += tiempoTranscurrido;
            balon.getAnimacion().actualiza(tiempoTranscurrido);
        }
        
        switch (iDireccionCanasta){
            case 1:{
                canasta.izquierda();
                break;
            }
            case 2:{
                canasta.derecha();
                break;
            }
        }
        
        if (lives <= 0) {
            gameover = true;
            repaint();
            repaint();
        }
    }

    /**
     * Este metodo se encarga de cambiar las posiciones de lso objetos balon y
     * canasta cuando colisionan entre si.
     */
    public void checaColision() {

        // BALON VS JFRAME
        Rectangle cuadro = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        if (!cuadro.intersects(balon.getPerimetro())) {
            bVelx = 0;
            bVely = 0;
            balon.setPosX(100);
            balon.setPosY(300);
            fouls--;
            if (fouls < 1) {
                lives--;
                fouls = 3;
            }
            vidas--;
            click = false;
            if (!mute) {
                fail.play();
            }
        }

        // CANASTA VS BALON
        if (canasta.getPerimetro().intersects(balon.getPerimetro())) {
            bVelx = 0;
            bVely = 0;
            balon.setPosX(100);
            balon.setPosY(300);
            score += 2;
            click = false;
            if (!mute) {
                goal.play();
            }
        }

    }

    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Evento que inicia el movimiento random del balon usando la bandera click.
     *
     * @param e Evento
     */
    public void mouseClicked(MouseEvent e) {
        if (!click) {
            if (balon.getPerimetro().contains(e.getPoint())) {
                click = true;
                bVely = (int) (Math.random() * (Math.sqrt(250 * 2 * grav) / 2)
                        + (Math.sqrt(250 * 2 * grav) / 2));

                bVelx = (int) ((((Math.random() * 500 / 2) + 250) * grav)
                        / (bVely * 2));
            }
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }


    /**
     * Metodo que actualiza las animaciones.
     *
     * @param g es la imagen del objeto
     */
    public void paint(Graphics g) {
        // Inicializa el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
        
    }

    /**
     * Este metodo se encarga de pintar todos los objetos graficos del juego. Se
     * pintan los valores desplegados en el tablero
     *
     * @param g objeto grafico
     */
    public void paint1(Graphics g) {
        
        if (!gameover){
            // si no estan puestas las instrucciones...
            // despliega todo normal
            if (!bInstrucciones){
                g.drawImage(background, 0, 0, this);

                if (balon.getAnimacion() != null) {
                    g.drawImage(balon.animacion.getImagen(), balon.getPosX(), balon.getPosY(), this);
                }
                if (canasta.getAnimacion() != null) {
                    g.drawImage(canasta.animacion.getImagen(), canasta.getPosX(), canasta.getPosY(), this);
                }

                //-----IMPRESION DEL TABLERO
                g.setFont(myFont); // Aplica el estilo fuente a las string
                g.setColor(Color.yellow);
                g.drawString("" + score, 930, 98);
                g.setColor(Color.red);
                g.drawString("" + lives, 754, 99);
                g.drawString("" + fouls, 756, 178);
            }
            // si estan puestas, despliega la imagen de instrucciones
            else {
                g.drawImage(ins, 0, 0, this);
            }
        }
        else {
            g.drawImage(gg, 0, 0, this);
        }

    }

    public static void main(String[] args) {
        TiroParabolico tiro = new TiroParabolico();
        tiro.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            iDireccionCanasta = 1;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            iDireccionCanasta = 2;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            // si no estan las intrucciones
            if (!bInstrucciones){
                bPausado = !bPausado;
            }
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_I) {
            // si no esta pausado
            if (!bPausado){
                bInstrucciones = !bInstrucciones;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT || 
           keyEvent.getKeyCode() == KeyEvent.VK_RIGHT ){
            iDireccionCanasta = 0;
        }
    }

}
