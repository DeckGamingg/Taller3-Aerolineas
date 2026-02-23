package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;

/**
 * Temporada baja:
 * - Naturales: costo por km NATURAL.
 * - Corporativos: costo por km CORPORATIVO y descuento según tamaño.
 */
public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas
{
    public static final int COSTO_POR_KM_NATURAL = 600;
    public static final int COSTO_POR_KM_CORPORATIVO = 900;

    public static final double DESCUENTO_GRANDES = 0.2;
    public static final double DESCUENTO_MEDIANAS = 0.1;
    public static final double DESCUENTO_PEQ = 0.02;

    @Override
    protected int calcularCostoBase( Vuelo vuelo, Cliente cliente )
    {
        int distancia = calcularDistanciaVuelo( vuelo.getRuta( ) );

        if( cliente instanceof ClienteNatural )
        {
            return distancia * COSTO_POR_KM_NATURAL;
        }
        else
        {
            return distancia * COSTO_POR_KM_CORPORATIVO;
        }
    }

    @Override
    protected double calcularPorcentajeDescuento( Cliente cliente )
    {
        // Naturales: no descuento
        if( cliente instanceof ClienteNatural )
        {
            return 0.0;
        }

        // Corporativos: descuento según tamaño
        ClienteCorporativo cc = ( ClienteCorporativo )cliente;

        if( cc.getTamanoEmpresa( ) == ClienteCorporativo.GRANDE )
            return DESCUENTO_GRANDES;

        if( cc.getTamanoEmpresa( ) == ClienteCorporativo.MEDIANA )
            return DESCUENTO_MEDIANAS;

        return DESCUENTO_PEQ;
    }
}
