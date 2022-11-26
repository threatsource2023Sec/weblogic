package org.apache.openjpa.datacache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.openjpa.conf.OpenJPAConfiguration;

class Caches {
   static Set addTypesByName(OpenJPAConfiguration conf, Collection classNames, Set classes) {
      if (classNames.isEmpty()) {
         return (Set)classes;
      } else {
         ClassLoader loader = conf.getClassResolverInstance().getClassLoader((Class)null, (ClassLoader)null);
         Iterator iter = classNames.iterator();

         while(iter.hasNext()) {
            String className = (String)iter.next();

            try {
               Class cls = Class.forName(className, true, loader);
               if (classes == null) {
                  classes = new HashSet();
               }

               ((Set)classes).add(cls);
            } catch (Throwable var8) {
               conf.getLog("openjpa.Runtime").warn((Object)null, var8);
            }
         }

         return (Set)classes;
      }
   }
}
