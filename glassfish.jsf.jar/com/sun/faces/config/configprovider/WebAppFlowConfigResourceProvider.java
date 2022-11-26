package com.sun.faces.config.configprovider;

import com.sun.faces.RIConstants;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.util.FacesLogger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

public class WebAppFlowConfigResourceProvider implements ConfigurationResourceProvider {
   private static final Logger LOGGER;

   public Collection getResources(ServletContext context) {
      List list = Collections.emptyList();
      Set allPaths = context.getResourcePaths("/");
      if (null == allPaths) {
         return list;
      } else {
         list = null;
         Iterator var4 = allPaths.iterator();

         while(true) {
            String cur;
            Set webInfPaths;
            do {
               while(true) {
                  do {
                     if (!var4.hasNext()) {
                        return null == list ? Collections.EMPTY_LIST : list;
                     }

                     cur = (String)var4.next();
                  } while(cur.startsWith("/META-INF"));

                  if (cur.equals("/WEB-INF/")) {
                     webInfPaths = context.getResourcePaths(cur);
                     break;
                  }

                  if (cur.endsWith("/")) {
                     list = this.inspectDirectory(context, cur, list);
                  }
               }
            } while(null == webInfPaths);

            Iterator var7 = webInfPaths.iterator();

            while(var7.hasNext()) {
               String webInfCur = (String)var7.next();
               if (!cur.equals("/WEB-INF/classes/") && webInfCur.endsWith("/")) {
                  list = this.inspectDirectory(context, webInfCur, list);
               }
            }
         }
      }
   }

   private List inspectDirectory(ServletContext context, String toInspect, List list) {
      URL curUrl = null;
      Set allPaths = context.getResourcePaths(toInspect);
      if (null == allPaths) {
         return (List)list;
      } else {
         Iterator var6 = allPaths.iterator();

         while(true) {
            String cur;
            String flowName;
            String dirName;
            do {
               int slash;
               int prevSlash;
               do {
                  int suffixIndex;
                  do {
                     do {
                        if (!var6.hasNext()) {
                           return (List)list;
                        }

                        cur = (String)var6.next();
                     } while(!cur.endsWith("-flow.xml"));

                     suffixIndex = cur.length() - RIConstants.FLOW_DEFINITION_ID_SUFFIX_LENGTH;
                     slash = cur.lastIndexOf("/", suffixIndex);
                  } while(-1 == slash);

                  flowName = cur.substring(slash + 1, suffixIndex);
                  prevSlash = cur.lastIndexOf("/", slash - 1);
               } while(-1 == prevSlash);

               dirName = cur.substring(prevSlash + 1, slash);
            } while(!dirName.equals(flowName));

            if (null == list) {
               list = new ArrayList();
            }

            try {
               curUrl = context.getResource(cur);
               ((List)list).add(curUrl.toURI());
            } catch (MalformedURLException var14) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to get resource for {0}" + cur, var14);
               }
            } catch (URISyntaxException var15) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to get URI for {0}" + curUrl.toExternalForm(), var15);
               }
            }
         }
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
