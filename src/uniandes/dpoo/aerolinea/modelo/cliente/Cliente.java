package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.LinkedList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

/**
 * Clase abstracta para representar un cliente.
 * 
 * Un cliente puede comprar muchos tiquetes y luego usarlos cuando realiza un vuelo.
 */
public abstract class Cliente
{
    // =========================================================================
    // ATRIBUTOS
    // =========================================================================

    /**
     * Lista con los tiquetes que ha comprado el cliente
     */
    private List<Tiquete> tiquetes;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    public Cliente( )
    {
        this.tiquetes = new LinkedList<Tiquete>( );
    }

    // =========================================================================
    // MÉTODOS ABSTRACTOS
    // =========================================================================

    /**
     * Retorna el identificador del cliente (por ejemplo, el nombre o el nombre de la empresa).
     */
    public abstract String getIdentificador( );

    /**
     * Retorna el tipo del cliente (por ejemplo, PERSONAL o CORPORATIVO).
     */
    public abstract String getTipoCliente( );

    // =========================================================================
    // MÉTODOS DE COMPORTAMIENTO
    // =========================================================================

    /**
     * Agrega un tiquete a la lista del cliente.
     */
    public void agregarTiquete( Tiquete tiquete )
    {
        tiquetes.add( tiquete );
    }

    /**
     * Calcula el valor total de los tiquetes comprados por el cliente.
     */
    public int calcularValorTotalTiquetes( )
    {
        int total = 0;

        for( int i = 0; i < tiquetes.size( ); i++ )
        {
            Tiquete t = tiquetes.get( i );
            total = total + t.getTarifa( );
        }

        return total;
    }

    /**
     * Marca como usados los tiquetes del cliente que correspondan al vuelo dado.
     */
    public void usarTiquetes( Vuelo vuelo )
    {
        for( int i = 0; i < tiquetes.size( ); i++ )
        {
            Tiquete t = tiquetes.get( i );

            if( t.getVuelo( ).equals( vuelo ) && !t.esUsado( ) )
            {
                t.marcarComoUsado( );
            }
        }
    }
}
