package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Token
{
    @Element(required = false)
    private String vegan;

    @Element(required = false)
    private String vegetarian;

    @Element(required = false)
    private String lactosefree;

    @Element(required = false)
    private String glutenfree;

    public String getVegan ()
    {
        return vegan;
    }

    public void setVegan (String vegan)
    {
        this.vegan = vegan;
    }

    public String getVegetarian ()
    {
        return vegetarian;
    }

    public void setVegetarian (String vegetarian)
    {
        this.vegetarian = vegetarian;
    }

    public String getLactosefree ()
    {
        return lactosefree;
    }

    public void setLactosefree (String lactosefree)
    {
        this.lactosefree = lactosefree;
    }

    public String getGlutenfree ()
    {
        return glutenfree;
    }

    public void setGlutenfree (String glutenfree)
    {
        this.glutenfree = glutenfree;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vegan = "+vegan+", vegetarian = "+vegetarian+", lactosefree = "+lactosefree+", glutenfree = "+glutenfree+"]";
    }
}
