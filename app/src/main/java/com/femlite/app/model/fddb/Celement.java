package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Celement
{
    @Element(required = false)
    private String content;

    @Element(required = false)
    private String ctype;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getCtype ()
    {
        return ctype;
    }

    public void setCtype (String ctype)
    {
        this.ctype = ctype;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", ctype = "+ctype+"]";
    }
}