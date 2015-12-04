package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Description
{
    @Element(required = false)
    private String imagedescription;

    @Element(required = false)
    private String name;

    @Element(required = false)
    private String producer;

    @Element(required = false)
    private String group;

    @Element(required = false)
    private String option;

    public String getImagedescription ()
    {
        return imagedescription;
    }

    public void setImagedescription (String imagedescription)
    {
        this.imagedescription = imagedescription;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getProducer ()
    {
        return producer;
    }

    public void setProducer (String producer)
    {
        this.producer = producer;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    public String getOption ()
    {
        return option;
    }

    public void setOption (String option)
    {
        this.option = option;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [imagedescription = "+imagedescription+", name = "+name+", producer = "+producer+", group = "+group+", option = "+option+"]";
    }
}