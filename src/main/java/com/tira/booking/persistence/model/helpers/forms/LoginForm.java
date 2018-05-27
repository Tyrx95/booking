package com.tira.booking.persistence.model.helpers.forms;


import com.tira.booking.persistence.model.BaseModel;

/**
 * The type Login form.
 */
public class LoginForm extends BaseModel {

    private String email;
    private String password;

    /**
     * Instantiates a new Login form.
     */
    public LoginForm() { }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail()  { return this.email; }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() { return this.password; }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) { this.password = password; }
}
