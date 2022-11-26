package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;
import java.net.URI;
import java.util.Collection;
import javax.servlet.ServletContext;

public class WebFacesConfigResourceProvider extends BaseWebConfigResourceProvider {
   private static final String WEB_INF_RESOURCE = "/WEB-INF/faces-config.xml";
   private static final String[] EXCLUDES = new String[]{"/WEB-INF/faces-config.xml"};
   private static final String SEPARATORS = ",|;";

   public Collection getResources(ServletContext context) {
      Collection urls = super.getResources(context);
      URI webFacesConfig = this.getContextURLForPath(context, "/WEB-INF/faces-config.xml");
      if (webFacesConfig != null) {
         urls.add(webFacesConfig);
      }

      context.setAttribute("com.sun.faces.webresources", urls);
      return urls;
   }

   protected WebConfiguration.WebContextInitParameter getParameter() {
      return WebConfiguration.WebContextInitParameter.JavaxFacesConfigFiles;
   }

   protected String[] getExcludedResources() {
      return EXCLUDES;
   }

   protected String getSeparatorRegex() {
      return ",|;";
   }
}
