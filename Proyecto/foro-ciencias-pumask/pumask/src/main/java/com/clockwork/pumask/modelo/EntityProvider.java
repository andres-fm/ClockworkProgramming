/**
 * Paquete que representa el modelo, en el patron de diseño
 * Vista-Controldor.
 * El modelo provee una representacion del Diseño de las Entidades
 * que se decidieron en el diseño.
 */
package com.clockwork.pumask.modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase generada con Ingernieria inversa por Netbeans.
 * Utiliza el archivo Percistance de donde obtiene la informacion
 * de la base de datos.
 * @author dima
 */
public class EntityProvider {

    private static EntityManagerFactory _emf;

    /**
     * Constructor vacio EntityProvider.
     */
    private EntityProvider() {
    }

    /**
     * Metodo que conoce la direccion del archivo Percistanece
     * el cual le provee la informaci&oacute;n de la base de datos.
     * @return un objeto de la clase EntityManagerFactory que ayuda a
     * administrar la base de datos.
     */
    public static EntityManagerFactory provider() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("com.clockwork_pumask_war_1.0-SNAPSHOTPU");
        }
        return _emf;
    }

}
