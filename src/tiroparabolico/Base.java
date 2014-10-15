

package tiroparabolico;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Base {
    
    private int posX;    //posicion en x.       
    private int posY;	//posicion en y.
    private int iVelocidad;   // velocidad
    private ImageIcon icono;    //icono.
    Animacion animacion;

    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param animacion es la <code>animacion</code> del objeto.
     */
    public Base (int posX, int posY, Animacion animacion) {
        this.posX = posX;
        this.posY = posY;
        this.animacion = animacion;
        this.iVelocidad = 0;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX (int posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX () {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY (int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY () {
        return posY;
    }

    // Metodo que ajusta la imagen del objeto
    public void setAnimacion (Animacion anim) {
        this.animacion = anim;
    }

    // Metodo que regresa la imagen
    public Animacion getAnimacion () {
        return this.animacion;
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho () {
        return new ImageIcon(this.animacion.getImagen()).getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto () {
        return new ImageIcon(this.animacion.getImagen()).getIconHeight();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro () {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Checa si el objeto <code>Movement</code> intersecta a otro
     * <code>Movement</code>
     *
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersecta (Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }
    
    /**
     * arriba
     * 
     * Metodo que sube al personaje de acuerdo a la velocidad
     * 
     */
    public void arriba() {
        this.setPosY(this.getPosY() - iVelocidad);
    }
    
    /**
     * abajo
     * 
     * Metodo que baja al personaje de acuerdo a la velocidad
     * 
     */
    public void abajo() {
        this.setPosY(this.getPosY() + iVelocidad);
    }
    
    /**
     * derecha
     * 
     * Metodo que mueve a la derecha al personaje de acuerdo a la velocidad
     * 
     */
    public void derecha() {
        this.setPosX(this.getPosX() + iVelocidad);
    }
    
    /**
     * izquierda
     * 
     * Metodo que mueve a la izquierda al personaje de acuerdo a la velocidad
     * 
     */
    public void izquierda() {
        this.setPosX(this.getPosX() - iVelocidad);
    }
    
    /**
     * setVelocidad
     * 
     * Metodo modificador usado para cambiar la velocidad del objeto 
     * 
     * @param iVelocidad es un <code>entero</code> con la velocidad del objeto.
     * 
     */
    public void setVelocidad(int iVelocidad) {
            this.iVelocidad = iVelocidad;
    }

    /**
     * getVelocidad
     * 
     * Metodo de acceso que regresa la velocidad del objeto 
     * 
     * @return iVelocidad un <code>entero</code> con velocidad del objeto.
     * 
     */
    public int getVelocidad() {
        return iVelocidad;
    }

    
}
