package com.femlite.app.model.fddb;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Servings
{
    @ElementList(name = "serving", inline = true)
    private List<Serving> serving;

    public List<Serving> getServing ()
    {
        return serving;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [serving = "+serving+"]";
    }
}

