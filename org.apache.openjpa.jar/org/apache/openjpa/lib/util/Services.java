package org.apache.openjpa.lib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Services {
   private static final String PREFIX = "META-INF/services/";

   public static String[] getImplementors(Class serviceClass) {
      return getImplementors((Class)serviceClass, (ClassLoader)null);
   }

   public static String[] getImplementors(Class serviceClass, ClassLoader loader) {
      return getImplementors(serviceClass.getName(), loader);
   }

   public static String[] getImplementors(String serviceName) {
      return getImplementors((String)serviceName, (ClassLoader)null);
   }

   public static String[] getImplementors(String serviceName, ClassLoader loader) {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      try {
         Set resourceList = new TreeSet();
         Enumeration resources = (Enumeration)AccessController.doPrivileged(J2DoPrivHelper.getResourcesAction(loader, "META-INF/services/" + serviceName));

         while(resources.hasMoreElements()) {
            addResources((URL)resources.nextElement(), resourceList);
         }

         return (String[])((String[])resourceList.toArray(new String[resourceList.size()]));
      } catch (Exception var4) {
         return new String[0];
      }
   }

   private static void addResources(URL url, Set set) throws IOException {
      InputStream in = null;
      BufferedReader reader = null;
      URLConnection urlCon = null;

      try {
         urlCon = url.openConnection();
         urlCon.setUseCaches(false);
         in = urlCon.getInputStream();
         reader = new BufferedReader(new InputStreamReader(in));

         String line;
         while((line = reader.readLine()) != null) {
            if (!line.trim().startsWith("#") && line.trim().length() != 0) {
               StringTokenizer tok = new StringTokenizer(line, "# \t");
               if (tok.hasMoreTokens()) {
                  String next = tok.nextToken();
                  if (next != null) {
                     next = next.trim();
                     if (next.length() > 0 && !next.startsWith("#")) {
                        set.add(next);
                     }
                  }
               }
            }
         }
      } finally {
         try {
            reader.close();
         } catch (IOException var16) {
         }

         try {
            in.close();
         } catch (IOException var15) {
         }

      }

   }

   public static Class[] getImplementorClasses(Class serviceClass) {
      return getImplementorClasses((String)serviceClass.getName(), (ClassLoader)null);
   }

   public static Class[] getImplementorClasses(Class serviceClass, ClassLoader loader) {
      Set invalid = new HashSet();
      Class[] classes = getImplementorClasses(serviceClass.getName(), loader);

      for(int i = 0; i < classes.length; ++i) {
         if (!serviceClass.isAssignableFrom(classes[i])) {
            invalid.add(classes[i]);
         }
      }

      if (invalid.size() != 0) {
         List list = new ArrayList(Arrays.asList(classes));
         list.removeAll(invalid);
         return (Class[])((Class[])list.toArray(new Class[list.size()]));
      } else {
         return classes;
      }
   }

   public static Class[] getImplementorClasses(String serviceName) {
      return getImplementorClasses((String)serviceName, (ClassLoader)null);
   }

   public static Class[] getImplementorClasses(String serviceName, ClassLoader loader) {
      try {
         return getImplementorClasses(serviceName, loader, true);
      } catch (Exception var3) {
         return new Class[0];
      }
   }

   public static Class[] getImplementorClasses(String serviceName, ClassLoader loader, boolean skipMissing) throws ClassNotFoundException {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      String[] names = getImplementors(serviceName, loader);
      if (names == null) {
         return new Class[0];
      } else {
         List classes = new ArrayList(names.length);

         for(int i = 0; i < names.length; ++i) {
            try {
               classes.add(Class.forName(names[i], false, loader));
            } catch (ClassNotFoundException var7) {
               if (!skipMissing) {
                  throw var7;
               }
            } catch (UnsupportedClassVersionError var8) {
               if (!skipMissing) {
                  throw var8;
               }
            } catch (LinkageError var9) {
               if (!skipMissing) {
                  throw var9;
               }
            }
         }

         return (Class[])((Class[])classes.toArray(new Class[classes.size()]));
      }
   }
}
