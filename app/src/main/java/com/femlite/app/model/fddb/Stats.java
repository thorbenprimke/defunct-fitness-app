package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;

/**
 * Created by thorben2 on 12/3/15.
 */
public class Stats
{
    @Element(name = "startfrom")
    private String startfrom;

    @Element(name = "numitemsfound")
    private String numitemsfound;

    public String getStartfrom ()
    {
        return startfrom;
    }

    public void setStartfrom (String startfrom)
    {
        this.startfrom = startfrom;
    }

    public String getNumitemsfound ()
    {
        return numitemsfound;
    }

    public void setNumitemsfound (String numitemsfound)
    {
        this.numitemsfound = numitemsfound;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [startfrom = "+startfrom+", numitemsfound = "+numitemsfound+"]";
    }
}