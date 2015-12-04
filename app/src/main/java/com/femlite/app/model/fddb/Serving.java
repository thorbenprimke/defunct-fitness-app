package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Serving
{
    @Element(required = false)
    private String serving_id;

    @Element(required = false)
    private String weight_gram;

    @Element(required = false)
    private String name;

    public String getServing_id ()
    {
        return serving_id;
    }

    public void setServing_id (String serving_id)
    {
        this.serving_id = serving_id;
    }

    public String getWeight_gram ()
    {
        return weight_gram;
    }

    public void setWeight_gram (String weight_gram)
    {
        this.weight_gram = weight_gram;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [serving_id = "+serving_id+", weight_gram = "+weight_gram+", name = "+name+"]";
    }
}
