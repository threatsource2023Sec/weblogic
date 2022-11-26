package com.sun.faces.config.configprovider;

import com.sun.faces.facelets.util.Classpath;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class MetaInfFaceletTaglibraryConfigProvider implements ConfigurationResourceProvider {
   private static final String SUFFIX = ".taglib.xml";
   private static final String WEB_INF_CLASSES = "/WEB-INF/classes/META-INF";
   private static final String[] FACELET_CONFIG_FILES = new String[]{"META-INF/jsf-core.taglib.xml", "META-INF/jsf-html.taglib.xml", "META-INF/jsf-ui.taglib.xml", "META-INF/jstl-core.taglib.xml", "META-INF/jstl-fn.taglib.xml"};
   private static final String[] BUILT_IN_TAGLIB_XML_FILES = new String[]{"META-INF/mojarra_ext.taglib.xml"};

   public Collection getResources(ServletContext context) {
      try {
         URL[] externalTaglibUrls = Classpath.search(Util.getCurrentLoader(this), "META-INF/", ".taglib.xml");
         URL[] builtInTaglibUrls = new URL[BUILT_IN_TAGLIB_XML_FILES.length];
         ClassLoader runtimeClassLoader = this.getClass().getClassLoader();

         for(int i = 0; i < BUILT_IN_TAGLIB_XML_FILES.length; ++i) {
            builtInTaglibUrls[i] = runtimeClassLoader.getResource(BUILT_IN_TAGLIB_XML_FILES[i]);
         }

         URL[] urls = new URL[externalTaglibUrls.length + builtInTaglibUrls.length];
         System.arraycopy(externalTaglibUrls, 0, urls, 0, externalTaglibUrls.length);
         System.arraycopy(builtInTaglibUrls, 0, urls, externalTaglibUrls.length, builtInTaglibUrls.length);
         List urlsList = this.pruneURLs(urls);
         Set paths = context.getResourcePaths("/WEB-INF/classes/META-INF");
         if (paths != null) {
            Iterator var8 = paths.iterator();

            while(var8.hasNext()) {
               String path = (String)var8.next();
               if (path.endsWith(".taglib.xml")) {
                  try {
                     urlsList.add(new URI(context.getResource(path).toExternalForm().replaceAll(" ", "%20")));
                  } catch (URISyntaxException var11) {
                     throw new FacesException(var11);
                  }
               }
            }
         }

         return urlsList;
      } catch (IOException var12) {
         throw new FacesException("Error searching classpath from facelet-taglib documents", var12);
      }
   }

   private List pruneURLs(URL[] urls) {
      List ret = null;
      if (urls != null && urls.length > 0) {
         URL[] var3 = urls;
         int var4 = urls.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            URL url = var3[var5];
            String u = url.toString();
            boolean found = false;
            String[] var9 = FACELET_CONFIG_FILES;
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String excludeName = var9[var11];
               if (u.contains(excludeName)) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               if (ret == null) {
                  ret = new ArrayList();
               }

               try {
                  ((List)ret).add(new URI(url.toExternalForm().replaceAll(" ", "%20")));
               } catch (URISyntaxException var13) {
                  throw new FacesException(var13);
               }
            }
         }
      }

      if (ret == null) {
         ret = Collections.emptyList();
      }

      return (List)ret;
   }
}
