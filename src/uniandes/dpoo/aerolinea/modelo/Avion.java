package uniandes.dpoo.aerolinea.modelo;

/**
 * Construye un avión con un determinado nombre y capacidad.
 */
public class Avion
{
    private String nombre;
    private int capacidad;

    public Avion( String nombre, int capacidad )
    {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String getNombre( )
    {
        return nombre;
    }

    public int getCapacidad( )
    {
        return capacidad;
    }
}
