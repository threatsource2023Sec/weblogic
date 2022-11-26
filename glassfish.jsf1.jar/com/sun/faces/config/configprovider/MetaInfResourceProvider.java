package com.sun.faces.config.configprovider;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.FacesException;
import javax.servlet.ServletContext;

public class MetaInfResourceProvider implements ConfigurationResourceProvider {
   private static final Pattern JAR_PATTERN = Pattern.compile(".*/(\\S*\\.jar).*");
   private static final String META_INF_RESOURCES = "META-INF/faces-config.xml";

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
         Enumeration items = Util.getCurrentLoader(this).getResources("META-INF/faces-config.xml");

         while(items.hasMoreElements()) {
            URL nextElement = (URL)items.nextElement();
            String jarUrl = nextElement.toString();
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

               sortedJarMap.put(jarName, nextElement);
            } else {
               unsortedResourceList.add(0, nextElement);
            }
         }
      } catch (IOException var12) {
         throw new FacesException(var12);
      }

      List result = new ArrayList(sortedJarMap.size() + unsortedResourceList.size());
      Iterator i$ = sortedJarMap.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         result.add(entry.getValue());
      }

      result.addAll(unsortedResourceList);
      return result;
   }
}
