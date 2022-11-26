package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class WebResourceProvider implements ConfigurationResourceProvider {
   private static final String WEB_INF_RESOURCE = "/WEB-INF/faces-config.xml";

   public Collection getResources(ServletContext context) {
      WebConfiguration webConfig = WebConfiguration.getInstance(context);
      String paths = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.JavaxFacesConfigFiles);
      Set set = new LinkedHashSet(6);
      if (paths != null) {
         String[] arr$ = Util.split(context, paths.trim(), ",");
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String token = arr$[i$];
            String path = token.trim();
            if (!"/WEB-INF/faces-config.xml".equals(path) && path.length() != 0) {
               URL u = this.getContextURLForPath(context, path);
               if (u != null) {
                  set.add(u);
               }
            }
         }
      }

      URL webFacesConfig = this.getContextURLForPath(context, "/WEB-INF/faces-config.xml");
      if (webFacesConfig != null) {
         set.add(webFacesConfig);
      }

      context.setAttribute("com.sun.faces.webresources", set);
      return set;
   }

   private URL getContextURLForPath(ServletContext context, String path) {
      try {
         return context.getResource(path);
      } catch (MalformedURLException var4) {
         throw new FacesException(var4);
      }
   }
}
