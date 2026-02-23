package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.LinkedList;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

/**
 * Esta clase representa un vuelo, es decir, una ruta en una fecha con un avión.
 */
public class Vuelo
{
    private Ruta ruta;
    private String fecha;
    private Avion avion;

    private Collection<Tiquete> tiquetes;

    public Vuelo( Ruta ruta, String fecha, Avion avion )
    {
        this.ruta = ruta;
        this.fecha = fecha;
        this.avion = avion;
        this.tiquetes = new LinkedList<Tiquete>( );
    }

    public Ruta getRuta( )
    {
        return ruta;
    }

    public String getFecha( )
    {
        return fecha;
    }

    public Avion getAvion( )
    {
        return avion;
    }

    public Collection<Tiquete> getTiquetes( )
    {
        return tiquetes;
    }

    /**
     * Vende una cierta cantidad de tiquetes para este vuelo.
     * 
     * @return El valor total cobrado por la venta
     */
    public int venderTiquetes( Cliente cliente, CalculadoraTarifas calculadora, int cantidad ) throws VueloSobrevendidoException
    {
        // PASO 1: Verificar cupo
        int capacidad = avion.getCapacidad( );
        int vendidos = tiquetes.size( );

        if( vendidos + cantidad > capacidad )
        {
            throw new VueloSobrevendidoException( this );
        }

        // PASO 2: Generar y registrar tiquetes
        int total = 0;

        for( int i = 0; i < cantidad; i++ )
        {
            int tarifa = calculadora.calcularTarifa( this, cliente );

            Tiquete nuevo = GeneradorTiquetes.generarTiquete( this, cliente, tarifa );
            GeneradorTiquetes.registrarTiquete( nuevo );

            tiquetes.add( nuevo );
            total = total + tarifa;
        }

        return total;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;

        if( obj == null )
            return false;

        if( getClass( ) != obj.getClass( ) )
            return false;

        Vuelo other = ( Vuelo )obj;

        if( !fecha.equals( other.fecha ) )
            return false;

        if( !ruta.getCodigoRuta( ).equals( other.ruta.getCodigoRuta( ) ) )
            return false;

        return true;
    }
}
