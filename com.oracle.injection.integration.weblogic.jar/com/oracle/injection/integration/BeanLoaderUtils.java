package com.oracle.injection.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class BeanLoaderUtils {
   private static Logger m_logger = Logger.getLogger(BeanLoaderUtils.class.getName());

   private BeanLoaderUtils() {
   }

   static List getBeanClassNamesFromJar(ZipInputStream zipInputStream) throws IOException {
      List beanClassNames = new ArrayList();

      for(ZipEntry embeddedEntry = zipInputStream.getNextEntry(); embeddedEntry != null; embeddedEntry = zipInputStream.getNextEntry()) {
         String embeddedEntryName = embeddedEntry.getName();
         if (!embeddedEntry.isDirectory() && isClassName(embeddedEntryName)) {
            beanClassNames.add(getLoadableClassName(embeddedEntryName, 0));
         }
      }

      return beanClassNames;
   }

   static boolean isClassName(String entryName) {
      return entryName.endsWith(".class");
   }

   static String getLoadableClassName(String embeddedEntryName, int beginIndex) {
      int endIndex = embeddedEntryName.length() - 6;
      return embeddedEntryName.substring(beginIndex, endIndex).replace('/', '.');
   }

   static String getEmbeddedLibJarName(String path, String prefix) {
      try {
         int indexOfLibDir = path.lastIndexOf(prefix);
         int indexOfJarEnd = path.indexOf("!", indexOfLibDir);
         return indexOfLibDir != -1 && indexOfJarEnd != -1 ? path.substring(indexOfLibDir, indexOfJarEnd) : null;
      } catch (IndexOutOfBoundsException var4) {
         m_logger.log(Level.INFO, "IndexOutOfBoundsException getting embedded library jar name.  Path: " + path + ".  Prefix: " + prefix);
         return null;
      }
   }
}
