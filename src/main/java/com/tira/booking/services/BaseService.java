package com.tira.booking.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.Base64;

/**
 * The type Base service.
 */
@Transactional
abstract class BaseService {

    /**
     * Gets session.
     *
     * @return the session
     */

    @Autowired
    private SessionFactory sessionFactory;

    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Base 64 encode string.
     *
     * @param bytes the bytes
     * @return the string
     */
    String base64Encode(final byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base 64 decode byte [ ].
     *
     * @param string the string
     * @return the byte [ ]
     */
    byte[] base64Decode(final String string) {
        return Base64.getDecoder().decode(string);
    }

}