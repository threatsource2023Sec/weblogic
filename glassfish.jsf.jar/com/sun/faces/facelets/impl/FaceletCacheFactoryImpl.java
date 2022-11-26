package com.sun.faces.facelets.impl;

import com.sun.faces.config.WebConfiguration;
import javax.faces.view.facelets.FaceletCache;
import javax.faces.view.facelets.FaceletCacheFactory;

public class FaceletCacheFactoryImpl extends FaceletCacheFactory {
   public FaceletCacheFactoryImpl() {
      super((FaceletCacheFactory)null);
   }

   public FaceletCache getFaceletCache() {
      WebConfiguration webConfig = WebConfiguration.getInstance();
      String refreshPeriod = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.FaceletsDefaultRefreshPeriod);
      long period = Long.parseLong(refreshPeriod) * 1000L;
      FaceletCache result = new DefaultFaceletCache(period);
      return result;
   }
}
