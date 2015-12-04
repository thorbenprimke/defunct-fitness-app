package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(name = "result", strict = false)
public class FddbResponse {

    @Element(name = "items")
    private Items items;

    @Element(name = "stats")
    private Stats stats;

    public Items getItems ()
    {
        return items;
    }

    public void setItems (Items items)
    {
        this.items = items;
    }

    public Stats getStats ()
    {
        return stats;
    }

    public void setStats (Stats stats)
    {
        this.stats = stats;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+", stats = "+stats+"]";
    }
}
