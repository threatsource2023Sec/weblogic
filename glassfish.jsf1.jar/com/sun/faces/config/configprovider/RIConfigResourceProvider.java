package com.sun.faces.config.configprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletContext;

public class RIConfigResourceProvider implements ConfigurationResourceProvider {
   private static final String JSF_RI_CONFIG = "com/sun/faces/jsf-ri-runtime.xml";

   public Collection getResources(ServletContext context) {
      List list = new ArrayList(1);
      ClassLoader loader = this.getClass().getClassLoader();
      list.add(loader.getResource("com/sun/faces/jsf-ri-runtime.xml"));
      return list;
   }
}
