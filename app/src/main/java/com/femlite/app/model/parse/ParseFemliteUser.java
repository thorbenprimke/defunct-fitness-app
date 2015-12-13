package com.femlite.app.model.parse;

import com.femlite.app.model.User;
import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by thorben2 on 12/13/15.
 */
@ParseClassName("_User")
public class ParseFemliteUser extends ParseUser implements User {

    @Override
    public String getName() {
        return getString("name");
    }
}
