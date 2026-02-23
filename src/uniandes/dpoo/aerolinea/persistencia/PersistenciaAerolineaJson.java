package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

/**
 * Persistencia en JSON para la información base de la aerolínea.
 * 
 * Estructura:
 * {
 *   "aeropuertos": [ ... ],
 *   "aviones": [ ... ],
 *   "rutas": [ ... ],
 *   "vuelos": [ ... ]
 * }
 */
public class PersistenciaAerolineaJson implements IPersistenciaAerolinea
{
    @Override
    public void cargarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException
    {
        // PASO 1: Leer archivo completo
        String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
        JSONObject raiz = new JSONObject( jsonCompleto );

        // PASO 2: Cargar aeropuertos (map por código para conectar rutas)
        Map<String, Aeropuerto> aeropuertos = new HashMap<String, Aeropuerto>( );
        JSONArray jAeropuertos = raiz.getJSONArray( "aeropuertos" );

        for( int i = 0; i < jAeropuertos.length( ); i++ )
        {
            JSONObject a = jAeropuertos.getJSONObject( i );

            String nombre = a.getString( "nombre" );
            String codigo = a.getString( "codigo" );
            String ciudad = a.getString( "ciudad" );
            double lat = a.getDouble( "latitud" );
            double lon = a.getDouble( "longitud" );

            try
            {
                Aeropuerto nuevo = new Aeropuerto( nombre, codigo, ciudad, lat, lon );
                aeropuertos.put( codigo, nuevo );
            }
            catch( AeropuertoDuplicadoException e )
            {
                throw new InformacionInconsistenteException( "Aeropuerto duplicado: " + codigo );
            }
        }

        // PASO 3: Cargar aviones
        JSONArray jAviones = raiz.getJSONArray( "aviones" );
        for( int i = 0; i < jAviones.length( ); i++ )
        {
            JSONObject a = jAviones.getJSONObject( i );
            String nombre = a.getString( "nombre" );
            int capacidad = a.getInt( "capacidad" );

            aerolinea.agregarAvion( new Avion( nombre, capacidad ) );
        }

        // PASO 4: Cargar rutas
        JSONArray jRutas = raiz.getJSONArray( "rutas" );
        for( int i = 0; i < jRutas.length( ); i++ )
        {
            JSONObject r = jRutas.getJSONObject( i );

            String codigoRuta = r.getString( "codigoRuta" );
            String codigoOrigen = r.getString( "origen" );
            String codigoDestino = r.getString( "destino" );
            String horaSalida = r.getString( "horaSalida" );
            String horaLlegada = r.getString( "horaLlegada" );

            Aeropuerto origen = aeropuertos.get( codigoOrigen );
            Aeropuerto destino = aeropuertos.get( codigoDestino );

            if( origen == null || destino == null )
            {
                throw new InformacionInconsistenteException( "Ruta con aeropuertos inexistentes: " + codigoRuta );
            }

            aerolinea.agregarRuta( new Ruta( origen, destino, horaSalida, horaLlegada, codigoRuta ) );
        }

        // PASO 5: Cargar vuelos
        JSONArray jVuelos = raiz.getJSONArray( "vuelos" );
        for( int i = 0; i < jVuelos.length( ); i++ )
        {
            JSONObject v = jVuelos.getJSONObject( i );

            String fecha = v.getString( "fecha" );
            String codigoRuta = v.getString( "codigoRuta" );
            String nombreAvion = v.getString( "avion" );

            Ruta ruta = aerolinea.getRuta( codigoRuta );
            Avion avion = buscarAvionPorNombre( aerolinea, nombreAvion );

            if( ruta == null )
                throw new InformacionInconsistenteException( "Vuelo con ruta inexistente: " + codigoRuta );

            if( avion == null )
                throw new InformacionInconsistenteException( "Vuelo con avión inexistente: " + nombreAvion );

            aerolinea.getVuelos( ).add( new Vuelo( ruta, fecha, avion ) );
        }
    }

    @Override
    public void salvarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException
    {
        JSONObject raiz = new JSONObject( );

        // PASO 1: Aeropuertos (se obtienen desde las rutas)
        Map<String, Aeropuerto> unicos = new HashMap<String, Aeropuerto>( );
        for( Ruta r : aerolinea.getRutas( ) )
        {
            unicos.put( r.getOrigen( ).getCodigo( ), r.getOrigen( ) );
            unicos.put( r.getDestino( ).getCodigo( ), r.getDestino( ) );
        }

        JSONArray jAeropuertos = new JSONArray( );
        for( Aeropuerto a : unicos.values( ) )
        {
            JSONObject o = new JSONObject( );
            o.put( "nombre", a.getNombre( ) );
            o.put( "codigo", a.getCodigo( ) );
            o.put( "ciudad", a.getNombreCiudad( ) );
            o.put( "latitud", a.getLatitud( ) );
            o.put( "longitud", a.getLongitud( ) );
            jAeropuertos.put( o );
        }
        raiz.put( "aeropuertos", jAeropuertos );

        // PASO 2: Aviones
        JSONArray jAviones = new JSONArray( );
        for( Avion a : aerolinea.getAviones( ) )
        {
            JSONObject o = new JSONObject( );
            o.put( "nombre", a.getNombre( ) );
            o.put( "capacidad", a.getCapacidad( ) );
            jAviones.put( o );
        }
        raiz.put( "aviones", jAviones );

        // PASO 3: Rutas
        JSONArray jRutas = new JSONArray( );
        for( Ruta r : aerolinea.getRutas( ) )
        {
            JSONObject o = new JSONObject( );
            o.put( "codigoRuta", r.getCodigoRuta( ) );
            o.put( "origen", r.getOrigen( ).getCodigo( ) );
            o.put( "destino", r.getDestino( ).getCodigo( ) );
            o.put( "horaSalida", r.getHoraSalida( ) );
            o.put( "horaLlegada", r.getHoraLlegada( ) );
            jRutas.put( o );
        }
        raiz.put( "rutas", jRutas );

        // PASO 4: Vuelos
        JSONArray jVuelos = new JSONArray( );
        for( Vuelo v : aerolinea.getVuelos( ) )
        {
            JSONObject o = new JSONObject( );
            o.put( "fecha", v.getFecha( ) );
            o.put( "codigoRuta", v.getRuta( ).getCodigoRuta( ) );
            o.put( "avion", v.getAvion( ).getNombre( ) );
            jVuelos.put( o );
        }
        raiz.put( "vuelos", jVuelos );

        // PASO 5: Escribir archivo
        PrintWriter pw = new PrintWriter( archivo );
        raiz.write( pw, 2, 0 );
        pw.close( );
    }

    private Avion buscarAvionPorNombre( Aerolinea aerolinea, String nombre )
    {
        for( Avion a : aerolinea.getAviones( ) )
        {
            if( a.getNombre( ).equals( nombre ) )
                return a;
        }
        return null;
    }
}
