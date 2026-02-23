package uniandes.dpoo.aerolinea.modelo;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

/**
 * Esta clase encapsula la información sobre los aeropuertos e implementa algunas operaciones relacionadas con la ubicación geográfica de los aeropuertos.
 */
public class Aeropuerto
{
    // =========================================================================
    // CONSTANTES Y CONTROL DE DUPLICADOS
    // =========================================================================

    private static final int RADIO_TERRESTRE = 6371;

    /**
     * Para garantizar que no existan aeropuertos con el mismo código.
     */
    private static Set<String> codigosUsados = new HashSet<String>( );

    // =========================================================================
    // ATRIBUTOS
    // =========================================================================

    private String nombre;
    private String codigo;
    private String nombreCiudad;
    private double latitud;
    private double longitud;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    /**
     * Construye un nuevo aeropuerto e inicializa sus atributos con los valores proporcionados.
     * 
     * @throws AeropuertoDuplicadoException Si ya existía un aeropuerto con el mismo código
     */
    public Aeropuerto( String nombre, String codigo, String nombreCiudad, double latitud, double longitud ) throws AeropuertoDuplicadoException
    {
        // PASO 1: Verificar que el código no esté repetido
        if( codigosUsados.contains( codigo ) )
        {
            throw new AeropuertoDuplicadoException( codigo );
        }

        // PASO 2: Registrar el código como usado
        codigosUsados.add( codigo );

        // PASO 3: Guardar la información
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombreCiudad = nombreCiudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    public String getNombre( )
    {
        return nombre;
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public String getNombreCiudad( )
    {
        return nombreCiudad;
    }

    public double getLatitud( )
    {
        return latitud;
    }

    public double getLongitud( )
    {
        return longitud;
    }

    // =========================================================================
    // OPERACIONES
    // =========================================================================

    /**
     * Este método calcula la distancia aproximada entre dos aeropuertos.
     */
    public static int calcularDistancia( Aeropuerto aeropuerto1, Aeropuerto aeropuerto2 )
    {
        // Convertir a radianes para operaciones trigonométricas
        double latAeropuerto1 = Math.toRadians( aeropuerto1.getLatitud( ) );
        double lonAeropuerto1 = Math.toRadians( aeropuerto1.getLongitud( ) );
        double latAeropuerto2 = Math.toRadians( aeropuerto2.getLatitud( ) );
        double lonAeropuerto2 = Math.toRadians( aeropuerto2.getLongitud( ) );

        // Diferencias
        double deltaX = ( lonAeropuerto2 - lonAeropuerto1 ) * Math.cos( ( latAeropuerto1 + latAeropuerto2 ) / 2 );
        double deltaY = ( latAeropuerto2 - latAeropuerto1 );

        // Distancia aproximada en km
        double distancia = Math.sqrt( deltaX * deltaX + deltaY * deltaY ) * RADIO_TERRESTRE;

        return ( int )Math.round( distancia );
    }
}
