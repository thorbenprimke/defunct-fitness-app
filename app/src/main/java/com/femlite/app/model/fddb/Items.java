package com.femlite.app.model.fddb;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Items
{
    @ElementList(name = "item", inline = true)
    private List<Item> item;

    public List<Item> getItem ()
    {
        return item;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item = "+item+"]";
    }
}