package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;

/**
 * Interfaz para cargar y salvar la información base de la aerolínea (aviones, rutas, vuelos, aeropuertos).
 */
public interface IPersistenciaAerolinea
{
    void cargarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException;

    void salvarAerolinea( String archivo, Aerolinea aerolinea ) throws IOException;
}
