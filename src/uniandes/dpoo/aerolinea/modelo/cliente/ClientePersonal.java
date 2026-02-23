package uniandes.dpoo.aerolinea.modelo.cliente;

/**
 * Esta clase representa a los clientes personales (personas naturales).
 */
public class ClientePersonal extends Cliente
{
    /**
     * Constante usada para identificar este tipo de cliente en persistencia.
     */
    public static final String PERSONAL = "Personal";

    /**
     * Nombre de la persona (sirve como identificador).
     */
    private String nombre;

    public ClientePersonal( String nombre )
    {
        super( );
        this.nombre = nombre;
    }

    @Override
    public String getIdentificador( )
    {
        return nombre;
    }

    @Override
    public String getTipoCliente( )
    {
        return PERSONAL;
    }
}
