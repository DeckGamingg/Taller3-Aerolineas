package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

/**
 * Clase abstracta para calcular tarifas.
 */
public abstract class CalculadoraTarifas
{
    public static final double IMPUESTO = 0.28;

    /**
     * Calcula la tarifa total:
     * (costoBase - descuento) + impuestos
     */
    public int calcularTarifa( Vuelo vuelo, Cliente cliente )
    {
        // PASO 1: Calcular costo base
        int costoBase = calcularCostoBase( vuelo, cliente );

        // PASO 2: Calcular descuento
        double porcentajeDescuento = calcularPorcentajeDescuento( cliente );
        int descuento = ( int )Math.round( costoBase * porcentajeDescuento );

        int baseConDescuento = costoBase - descuento;

        // PASO 3: Calcular impuestos
        int impuestos = calcularValorImpuestos( baseConDescuento );

        // PASO 4: Total
        return baseConDescuento + impuestos;
    }

    protected int calcularValorImpuestos( int costoBase )
    {
        return ( int )Math.round( costoBase * IMPUESTO );
    }

    protected int calcularDistanciaVuelo( Ruta ruta )
    {
        return Aeropuerto.calcularDistancia( ruta.getOrigen( ), ruta.getDestino( ) );
    }

    protected abstract int calcularCostoBase( Vuelo vuelo, Cliente cliente );

    protected abstract double calcularPorcentajeDescuento( Cliente cliente );
}
