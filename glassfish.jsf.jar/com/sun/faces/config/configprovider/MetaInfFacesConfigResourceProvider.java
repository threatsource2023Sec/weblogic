package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.util.Classpath;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class MetaInfFacesConfigResourceProvider implements ConfigurationResourceProvider {
   private static final Pattern JAR_PATTERN = Pattern.compile(".*/(\\S*\\.jar).*");
   private static final String META_INF_RESOURCES = "META-INF/faces-config.xml";
   private static final String WEB_INF_CLASSES = "/WEB-INF/classes/META-INF";
   private static final String FACES_CONFIG_EXTENSION = ".faces-config.xml";

   public Collection getResources(ServletContext context) {
      WebConfiguration webConfig = WebConfiguration.getInstance(context);
      String duplicateJarPattern = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.DuplicateJARPattern);
      Pattern duplicatePattern = null;
      if (duplicateJarPattern != null) {
         duplicatePattern = Pattern.compile(duplicateJarPattern);
      }

      SortedMap sortedJarMap = new TreeMap();
      List unsortedResourceList = new ArrayList();

      try {
         Iterator var7 = this.loadURLs(context).iterator();

         while(var7.hasNext()) {
            URI uri = (URI)var7.next();
            String jarUrl = uri.toString();
            String jarName = null;
            Matcher m = JAR_PATTERN.matcher(jarUrl);
            if (m.matches()) {
               jarName = m.group(1);
            }

            if (jarName != null) {
               if (duplicatePattern != null) {
                  m = duplicatePattern.matcher(jarName);
                  if (m.matches()) {
                     jarName = m.group(1);
                  }
               }

               Set uris = (Set)sortedJarMap.get(jarName);
               if (uris == null) {
                  uris = new HashSet();
                  sortedJarMap.put(jarName, uris);
               }

               ((Set)uris).add(uri);
            } else {
               unsortedResourceList.add(0, uri);
            }
         }
      } catch (IOException var13) {
         throw new FacesException(var13);
      }

      List result = new ArrayList(sortedJarMap.size() + unsortedResourceList.size());
      Iterator var15 = sortedJarMap.entrySet().iterator();

      while(var15.hasNext()) {
         Map.Entry entry = (Map.Entry)var15.next();
         result.addAll((Collection)entry.getValue());
      }

      result.addAll(unsortedResourceList);
      return result;
   }

   private Collection loadURLs(ServletContext context) throws IOException {
      Set urls = new HashSet();

      try {
         Enumeration e = Util.getCurrentLoader(this).getResources("META-INF/faces-config.xml");

         while(e.hasMoreElements()) {
            String urlString = ((URL)e.nextElement()).toExternalForm();
            urlString = urlString.replaceAll(" ", "%20");
            urls.add(new URI(urlString));
         }

         URL[] urlArray = Classpath.search("META-INF/", ".faces-config.xml");
         URL[] var11 = urlArray;
         int var5 = urlArray.length;

         String urlString;
         for(int var6 = 0; var6 < var5; ++var6) {
            URL cur = var11[var6];
            urlString = cur.toExternalForm();
            urlString = urlString.replaceAll(" ", "%20");
            urls.add(new URI(urlString));
         }

         Set paths = context.getResourcePaths("/WEB-INF/classes/META-INF");
         if (paths != null) {
            Iterator var13 = paths.iterator();

            while(var13.hasNext()) {
               Object path = var13.next();
               String p = path.toString();
               if (p.endsWith(".faces-config.xml")) {
                  urlString = context.getResource(p).toExternalForm();
                  urlString = urlString.replaceAll(" ", "%20");
                  urls.add(new URI(urlString));
               }
            }
         }

         return urls;
      } catch (URISyntaxException var9) {
         throw new IOException(var9);
      }
   }
}
