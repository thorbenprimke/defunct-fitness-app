package com.femlite.app.model;

import java.util.List;

/**
 * Created by thorben2 on 12/5/15.
 */
public interface Food {

    String getId();

    String getTitle();

    List<Portion> getPortions();
}
