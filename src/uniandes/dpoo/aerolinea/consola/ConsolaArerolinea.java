package uniandes.dpoo.aerolinea.consola;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;

/**
 * Consola de ejemplo para probar rápidamente algunas operaciones del modelo.
 * 
 * Importante: para poder cargar tiquetes desde un JSON, primero deben existir
 * en la aerolínea las rutas y los vuelos referenciados en ese archivo.
 */
public class ConsolaArerolinea extends ConsolaBasica
{
    private Aerolinea unaAerolinea;

    public void correrAplicacion( )
    {
        try
        {
            unaAerolinea = new Aerolinea( );

            unaAerolinea = new Aerolinea( );

         // 1) Crear datos mínimos para que tiquetes.json sea consistente
         Aeropuerto bogota = new Aeropuerto( "El Dorado", "BOG", "Bogotá", 4.701, -74.146 );
         Aeropuerto medellin = new Aeropuerto( "José María Córdova", "MDE", "Medellín", 6.164, -75.423 );

         Ruta ruta4558 = new Ruta( bogota, medellin, "0800", "0900", "4558" );
         unaAerolinea.agregarRuta( ruta4558 );

         Avion avionDemo = new Avion( "AVION_DEMO", 180 );
         unaAerolinea.agregarAvion( avionDemo );

         unaAerolinea.programarVuelo( "2024-11-05", "4558", "AVION_DEMO" );

         // 2) Ahora sí: cargar tiquetes
         String archivo = "tiquetes.json";
         unaAerolinea.cargarTiquetes( "./datos/" + archivo, CentralPersistencia.JSON );

            System.out.println( "Carga exitosa de tiquetes. Clientes: " + unaAerolinea.getClientes( ).size( ) );
            System.out.println( "Vuelos programados: " + unaAerolinea.getVuelos( ).size( ) );
        }
        catch( AeropuertoDuplicadoException e )
        {
            e.printStackTrace( );
        }
        catch( TipoInvalidoException e )
        {
            e.printStackTrace( );
        }
        catch( IOException e )
        {
            e.printStackTrace( );
        }
        catch( InformacionInconsistenteException e )
        {
            e.printStackTrace( );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

    public static void main( String[] args )
    {
        ConsolaArerolinea ca = new ConsolaArerolinea( );
        ca.correrAplicacion( );
    }
}
