package com.femlite.app.manager;

import com.femlite.app.model.User;
import com.femlite.app.model.parse.ParseFemliteUser;
import com.parse.ParseUser;

import javax.inject.Inject;

/**
 * Manager for the user object.
 */
public class UserManager {

    @Inject
    public UserManager() {
    }

    public User getUser() {
        return (ParseFemliteUser) ParseUser.getCurrentUser();
    }
}
