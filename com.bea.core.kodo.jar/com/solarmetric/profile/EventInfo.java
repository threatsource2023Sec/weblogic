package com.solarmetric.profile;

import java.io.Serializable;

public interface EventInfo extends Serializable {
   String getName();

   String getDescription();

   String getViewerClassName();

   String getCategory();
}
