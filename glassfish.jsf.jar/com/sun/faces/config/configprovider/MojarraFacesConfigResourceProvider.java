package com.sun.faces.config.configprovider;

import com.sun.faces.spi.ConfigurationResourceProvider;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class MojarraFacesConfigResourceProvider implements ConfigurationResourceProvider {
   private static final String JSF_RI_CONFIG = "com/sun/faces/jsf-ri-runtime.xml";

   public Collection getResources(ServletContext context) {
      List list = new ArrayList(1);
      ClassLoader loader = this.getClass().getClassLoader();

      try {
         URL url = loader.getResource("com/sun/faces/jsf-ri-runtime.xml");
         String urlStr = url.toExternalForm();
         if (urlStr.contains(" ")) {
            urlStr = urlStr.replaceAll(" ", "%20");
         }

         list.add(new URI(urlStr));
         return list;
      } catch (URISyntaxException var6) {
         throw new FacesException(var6);
      }
   }
}
