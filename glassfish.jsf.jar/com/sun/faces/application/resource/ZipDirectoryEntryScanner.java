package com.sun.faces.application.resource;

import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

class ZipDirectoryEntryScanner {
   private static final Logger LOGGER;
   private static final String PREFIX = "META-INF/resources";
   private static final int PREFIX_LENGTH;
   Map resourceLibraries;

   ZipDirectoryEntryScanner() {
      ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
      Set webInfLibJars = extContext.getResourcePaths("/WEB-INF/lib");
      this.resourceLibraries = new ConcurrentHashMap();
      ZipEntry ze = null;
      String entryName = null;
      Iterator iter;
      String cur;
      if (webInfLibJars != null) {
         iter = webInfLibJars.iterator();

         while(iter.hasNext()) {
            cur = (String)iter.next();

            try {
               ZipInputStream zis = new ZipInputStream(extContext.getResourceAsStream(cur));
               Throwable var8 = null;

               try {
                  while(null != (ze = zis.getNextEntry())) {
                     entryName = ze.getName();
                     if (entryName.startsWith("META-INF/resources") && PREFIX_LENGTH < entryName.length()) {
                        entryName = entryName.substring(PREFIX_LENGTH + 1);
                        if (!entryName.endsWith("/")) {
                           int i = entryName.lastIndexOf("/");
                           if (-1 != i) {
                              entryName = entryName.substring(0, i);
                              if (!this.resourceLibraries.containsKey(entryName)) {
                                 this.resourceLibraries.put(entryName, Boolean.TRUE);
                              }
                           }
                        }
                     }
                  }
               } catch (Throwable var18) {
                  var8 = var18;
                  throw var18;
               } finally {
                  if (zis != null) {
                     if (var8 != null) {
                        try {
                           zis.close();
                        } catch (Throwable var17) {
                           var8.addSuppressed(var17);
                        }
                     } else {
                        zis.close();
                     }
                  }

               }
            } catch (IOException var20) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to inspect resource library " + cur, var20);
               }
            }
         }
      }

      iter = this.resourceLibraries.keySet().iterator();

      while(iter.hasNext()) {
         cur = (String)iter.next();
         if (cur.contains("/")) {
            iter.remove();
         }
      }

   }

   boolean libraryExists(String libraryName, String localePrefix) {
      String key = localePrefix != null ? localePrefix + "/" + libraryName : libraryName;
      return this.resourceLibraries.containsKey(key);
   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
      PREFIX_LENGTH = "META-INF/resources".length();
   }
}
