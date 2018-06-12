package com.tira.booking.controllers;


import com.google.common.cache.LoadingCache;
import com.tira.booking.exceptions.ServiceException;
import com.tira.booking.persistence.model.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Callable;

/**
 * The type Base controller.
 */
abstract class BaseController{

    /**
     * The Cache.
     */
    @Autowired
    LoadingCache<String, User> userCache;

    @Autowired
    HttpSession httpSession;


    private ResponseEntity catchExceptions(final Callable block) {
        try {
            return (ResponseEntity) block.call();
        } catch (ServiceException se) {
            se.printStackTrace();
            String message = se.getMessage() != null ? se.getMessage() : "Unknown Error";
            return ResponseEntity.badRequest().body("Service exception");
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage() != null ? e.getMessage() : "Unknown Error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error");
        }
    }

    /**
     * Wrapper for publicly available methods.
     *
     * @param block the block
     * @return the result
     */
    ResponseEntity wrapForPublic(final Callable block) {
        return catchExceptions(() -> ResponseEntity.ok(block.call()));
    }

    /**
     * Wrapper for registered users.
     *
     * @param block the block
     * @return the result
     */
    ResponseEntity wrapForUser(final Callable block) {
        return catchExceptions(() -> {
            User user = this.userCache.get((String) httpSession.getAttribute("uid"));
            if (user != null) {
                return wrapForPublic(block);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("You must be logged in to have access to this Request");
            }
        });
    }

    /**
     * Wrapper for administrators.
     *
     * @param block the block
     * @return the result
     */
    ResponseEntity wrapForAdmin(final Callable block) {
        return catchExceptions(() -> {
            String uid = (String) httpSession.getAttribute("uid");
            User user = null;
            if(uid != null){
                user = this.userCache.get(uid);
            }
            if (user != null && user.getIsAdmin() != null && user.getIsAdmin()) {
                return wrapForPublic(block);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Only Administrators have access to this Request");
            }
        });
    }


    /**
     * Gets query parameter as Integer.
     *
     * @param query        the query
     * @param defaultValue the default value
     * @return the query integer
     */
    Integer getQueryInt(final String query, final Integer defaultValue) {
        try {
            return Integer.parseInt(query);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

}
