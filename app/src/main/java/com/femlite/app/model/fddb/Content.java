package com.femlite.app.model.fddb;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Content
{
    @ElementList(name = "celement", inline = true, required = false)
    private List<Celement> celement;

    public List<Celement> getCelement ()
    {
        return celement;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [celement = "+celement+"]";
    }
}