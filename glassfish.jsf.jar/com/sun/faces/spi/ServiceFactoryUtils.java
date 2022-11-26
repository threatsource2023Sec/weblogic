package com.sun.faces.spi;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;

final class ServiceFactoryUtils {
   private static final Logger LOGGER;
   private static final String[] EMPTY_ARRAY;

   static Object getProviderFromEntry(String entry, Class[] argumentTypes, Object[] arguments) throws FacesException {
      if (entry == null) {
         return null;
      } else {
         try {
            Class clazz = Util.loadClass(entry, (Object)null);
            Constructor c = clazz.getDeclaredConstructor(argumentTypes);
            if (c == null) {
               throw new FacesException("Unable to find constructor accepting arguments: " + Arrays.toString(arguments));
            } else {
               return c.newInstance(arguments);
            }
         } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var5) {
            throw new FacesException(var5);
         }
      }
   }

   static String[] getServiceEntries(String key) {
      List results = null;
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         return EMPTY_ARRAY;
      } else {
         Enumeration urls = null;
         String serviceName = "META-INF/services/" + key;

         try {
            urls = loader.getResources(serviceName);
         } catch (IOException var27) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var27.toString(), var27);
            }
         }

         if (urls != null) {
            InputStream input = null;
            BufferedReader reader = null;

            while(urls.hasMoreElements()) {
               try {
                  if (results == null) {
                     results = new ArrayList();
                  }

                  URL url = (URL)urls.nextElement();
                  URLConnection conn = url.openConnection();
                  conn.setUseCaches(false);
                  input = conn.getInputStream();
                  if (input != null) {
                     try {
                        reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                     } catch (Exception var22) {
                        reader = new BufferedReader(new InputStreamReader(input));
                     }

                     for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                        results.add(line.trim());
                     }
                  }
               } catch (Exception var25) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "jsf.spi.provider.cannot_read_service", new Object[]{serviceName});
                     LOGGER.log(Level.SEVERE, var25.toString(), var25);
                  }
               } finally {
                  if (input != null) {
                     try {
                        input.close();
                     } catch (Exception var24) {
                        if (LOGGER.isLoggable(Level.FINEST)) {
                           LOGGER.log(Level.FINEST, "Closing stream", var24);
                        }
                     }
                  }

                  if (reader != null) {
                     try {
                        reader.close();
                     } catch (Exception var23) {
                        if (LOGGER.isLoggable(Level.FINEST)) {
                           LOGGER.log(Level.FINEST, "Closing stream", var23);
                        }
                     }
                  }

               }
            }
         }

         return results != null && !results.isEmpty() ? (String[])results.toArray(new String[results.size()]) : EMPTY_ARRAY;
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      EMPTY_ARRAY = new String[0];
   }
}
