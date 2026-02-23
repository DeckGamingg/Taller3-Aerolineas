package uniandes.dpoo.aerolinea.modelo.cliente;

/**
 * Alias de ClientePersonal para mantener compatibilidad con el esqueleto.
 * 
 * NOTA: En el JavaDoc aparece como ClientePersonal, pero en el código del esqueleto
 * se referencia ClienteNatural. Por eso existe esta clase.
 */
public class ClienteNatural extends ClientePersonal
{
    public static final String NATURAL = "Natural";

    public ClienteNatural( String nombre )
    {
        super( nombre );
    }

    @Override
    public String getTipoCliente( )
    {
        // Para que PersistenciaTiquetesJson funcione con el valor "Natural"
        return NATURAL;
    }
}
