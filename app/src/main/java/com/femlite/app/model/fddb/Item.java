package com.femlite.app.model.fddb;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by thorben2 on 12/3/15.
 */
@Root(strict = false)
public class Item
{
    @Element(name = "productcode_ean", data = true, required = false)
    private String productcode_ean;

    @Element(name = "data")
    private Data data;

    @Element(name = "ratings_avg_perc")
    private String ratings_avg_perc;

    @Element(name = "content", required = false)
    private Content content;

    @Element(name = "id", required = false)
    private String id;

    @Element(name = "thumbsrc", data = true, required = false)
    private String thumbsrc;

    @Element(name = "servings")
    private Servings servings;

    @Element(name = "producerid")
    private String producerid;

    @Element(name = "token", required = false)
    private Token token;

    @Element(name = "description", required = false)
    private Description description;

    @Element(name = "thumbsrclarge", data = true, required = false)
    private String thumbsrclarge;

    @Element(name = "markedfordeletion")
    private String markedfordeletion;

    @Element(name = "groupid")
    private String groupid;

    @Element(name = "ratings_num")
    private String ratings_num;

    @Element(name = "datasource")
    private String datasource;

    @Element(name = "foodrank")
    private String foodrank;

    public String getProductcode_ean ()
    {
        return productcode_ean;
    }

    public void setProductcode_ean (String productcode_ean)
    {
        this.productcode_ean = productcode_ean;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getRatings_avg_perc ()
    {
        return ratings_avg_perc;
    }

    public void setRatings_avg_perc (String ratings_avg_perc)
    {
        this.ratings_avg_perc = ratings_avg_perc;
    }

//    public Content getContent ()
//    {
//        return content;
//    }
//
//    public void setContent (Content content)
//    {
//        this.content = content;
//    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getThumbsrc ()
    {
        return thumbsrc;
    }

    public void setThumbsrc (String thumbsrc)
    {
        this.thumbsrc = thumbsrc;
    }

    public Servings getServings ()
    {
        return servings;
    }

    public void setServings (Servings servings)
    {
        this.servings = servings;
    }

    public String getProducerid ()
    {
        return producerid;
    }

    public void setProducerid (String producerid)
    {
        this.producerid = producerid;
    }

    public Token getToken ()
    {
        return token;
    }

    public void setToken (Token token)
    {
        this.token = token;
    }

    public Description getDescription ()
    {
        return description;
    }

    public void setDescription (Description description)
    {
        this.description = description;
    }

    public String getThumbsrclarge ()
    {
        return thumbsrclarge;
    }

    public void setThumbsrclarge (String thumbsrclarge)
    {
        this.thumbsrclarge = thumbsrclarge;
    }

    public String getMarkedfordeletion ()
    {
        return markedfordeletion;
    }

    public void setMarkedfordeletion (String markedfordeletion)
    {
        this.markedfordeletion = markedfordeletion;
    }

    public String getGroupid ()
    {
        return groupid;
    }

    public void setGroupid (String groupid)
    {
        this.groupid = groupid;
    }

    public String getRatings_num ()
    {
        return ratings_num;
    }

    public void setRatings_num (String ratings_num)
    {
        this.ratings_num = ratings_num;
    }

    public String getDatasource ()
    {
        return datasource;
    }

    public void setDatasource (String datasource)
    {
        this.datasource = datasource;
    }

    public String getFoodrank ()
    {
        return foodrank;
    }

    public void setFoodrank (String foodrank)
    {
        this.foodrank = foodrank;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productcode_ean = "+productcode_ean+", data = "+data+", ratings_avg_perc = "+ratings_avg_perc+", content = "+/*content+*/", id = "+id+", thumbsrc = "+thumbsrc+", servings = "+servings+", producerid = "+producerid+", token = "+/*token+*/", description = "/*+description*/+", thumbsrclarge = "+thumbsrclarge+", markedfordeletion = "+markedfordeletion+", groupid = "+groupid+", ratings_num = "+ratings_num+", datasource = "+datasource+", foodrank = "+foodrank+"]";
    }
}