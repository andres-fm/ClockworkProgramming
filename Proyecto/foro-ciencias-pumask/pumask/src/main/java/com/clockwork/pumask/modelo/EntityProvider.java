/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dima
 */
public class EntityProvider {

    private static EntityManagerFactory _emf;

    private EntityProvider() {
    }

    public static EntityManagerFactory provider() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        }
        return _emf;
    }

}
