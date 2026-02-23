package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

/**
 * Representa un tiquete comprado por un cliente para un vuelo.
 */
public class Tiquete
{
    private String codigo;
    private Vuelo vuelo;
    private Cliente clienteComprador;
    private int tarifa;
    private boolean usado;

    /**
     * Crea un nuevo tiquete con los parámetros recibidos y se lo agrega al cliente que lo compró.
     */
    public Tiquete( String codigo, Vuelo vuelo, Cliente clienteComprador, int tarifa )
    {
        this.codigo = codigo;
        this.vuelo = vuelo;
        this.clienteComprador = clienteComprador;
        this.tarifa = tarifa;
        this.usado = false;

        // Al crear el tiquete, queda asociado al cliente
        this.clienteComprador.agregarTiquete( this );
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public Vuelo getVuelo( )
    {
        return vuelo;
    }

    public Cliente getCliente( )
    {
        return clienteComprador;
    }

    public int getTarifa( )
    {
        return tarifa;
    }

    public boolean esUsado( )
    {
        return usado;
    }

    public void marcarComoUsado( )
    {
        this.usado = true;
    }
}
