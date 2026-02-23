package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

/**
 * Esta clase se usa para representar a los clientes de la aerolínea que son empresas.
 */
public class ClienteCorporativo extends Cliente
{
    // =========================================================================
    // CONSTANTES
    // =========================================================================

    public static final String CORPORATIVO = "Corporativo";

    public static final int GRANDE = 1;
    public static final int MEDIANA = 2;
    public static final int PEQUENA = 3;

    // =========================================================================
    // ATRIBUTOS
    // =========================================================================

    protected String nombreEmpresa;
    protected int tamanoEmpresa;

    // =========================================================================
    // CONSTRUCTOR
    // =========================================================================

    public ClienteCorporativo( String nombreEmpresa, int tamanoEmpresa )
    {
        super( );
        this.nombreEmpresa = nombreEmpresa;
        this.tamanoEmpresa = tamanoEmpresa;
    }

    // =========================================================================
    // GETTERS
    // =========================================================================

    public String getNombreEmpresa( )
    {
        return nombreEmpresa;
    }

    public int getTamanoEmpresa( )
    {
        return tamanoEmpresa;
    }

    @Override
    public String getIdentificador( )
    {
        return nombreEmpresa;
    }

    @Override
    public String getTipoCliente( )
    {
        return CORPORATIVO;
    }

    /**
     * Crea un nuevo objeto de tipo ClienteCorporativo a partir de un objeto JSON.
     * 
     * El objeto JSON debe tener dos atributos: nombreEmpresa (una cadena) y tamanoEmpresa (un número).
     */
    public static ClienteCorporativo cargarDesdeJSON( JSONObject cliente )
    {
        String nombreEmpresa = cliente.getString( "nombreEmpresa" );
        int tam = cliente.getInt( "tamanoEmpresa" );
        return new ClienteCorporativo( nombreEmpresa, tam );
    }

    /**
     * Salva este objeto de tipo ClienteCorporativo dentro de un objeto JSONObject para que ese objeto se almacene en un archivo.
     */
    public JSONObject salvarEnJSON( )
    {
        JSONObject jobject = new JSONObject( );
        // Clave esperada por PersistenciaTiquetesJson
        jobject.put( "tipoCliente", CORPORATIVO );
        jobject.put( "nombreEmpresa", this.nombreEmpresa );
        jobject.put( "tamanoEmpresa", this.tamanoEmpresa );
        return jobject;
    }
}
