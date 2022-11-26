package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public abstract class BaseWebConfigResourceProvider implements ConfigurationResourceProvider {
   private static final Logger LOGGER;

   public Collection getResources(ServletContext context) {
      WebConfiguration webConfig = WebConfiguration.getInstance(context);
      String paths = webConfig.getOptionValue(this.getParameter());
      Set urls = new LinkedHashSet(6);
      if (paths != null) {
         String[] var5 = Util.split(context, paths.trim(), this.getSeparatorRegex());
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String token = var5[var7];
            String path = token.trim();
            if (!this.isExcluded(path) && path.length() != 0) {
               URI u = this.getContextURLForPath(context, path);
               if (u != null) {
                  urls.add(u);
               } else if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.config.web_resource_not_found", new Object[]{path, WebConfiguration.WebContextInitParameter.JavaxFacesConfigFiles.getQualifiedName()});
               }
            }
         }
      }

      return urls;
   }

   protected abstract WebConfiguration.WebContextInitParameter getParameter();

   protected abstract String[] getExcludedResources();

   protected abstract String getSeparatorRegex();

   protected URI getContextURLForPath(ServletContext context, String path) {
      try {
         URL url = context.getResource(path);
         return url != null ? new URI(url.toExternalForm().replaceAll(" ", "%20")) : null;
      } catch (URISyntaxException | MalformedURLException var4) {
         throw new FacesException(var4);
      }
   }

   protected boolean isExcluded(String path) {
      return Arrays.binarySearch(this.getExcludedResources(), path) >= 0;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
