package com.bea.adaptive.mbean.typing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.PropertyHelper;

class PatternsHelper {
   static final String IGNORE_PATTERNS_PROPERTY_NAME = "weblogic.diagnostics.harvester.ignorepatterns";
   static final String DEFAULT_IGNORE_PATTERNS_FILENAME;
   private static final DebugLogger debugLogger;

   public static boolean isOnIgnoreList(ObjectName name) {
      Set ignorePatterns = PatternsHelper.IgnorePatternsHolder.getPatterns();
      if (ignorePatterns != null) {
         Iterator var2 = ignorePatterns.iterator();

         while(var2.hasNext()) {
            ObjectName onPattern = (ObjectName)var2.next();
            if (onPattern.apply(name)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(name.getCanonicalName() + " matches ObjectName pattern on IGNORE list " + onPattern.getCanonicalName());
               }

               return true;
            }
         }
      }

      return false;
   }

   private static HashSet readIgnorePatterns() {
      String ignorePatternsFile = PropertyHelper.getProperty("weblogic.diagnostics.harvester.ignorepatterns", DEFAULT_IGNORE_PATTERNS_FILENAME);
      File ignoreFile = new File(ignorePatternsFile);
      HashSet ignoreSet = null;
      if (!ignoreFile.exists()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Ignore patterns file " + ignoreFile.getAbsolutePath() + " does not exist, no ingore patterns set");
         }
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("READING ignore patterns file " + ignoreFile.getAbsolutePath());
         }

         try {
            List allPatterns = Files.readAllLines(Paths.get(ignoreFile.getAbsolutePath()));
            Iterator var4 = allPatterns.iterator();

            label61:
            while(true) {
               String patternString;
               do {
                  do {
                     if (!var4.hasNext()) {
                        break label61;
                     }

                     String patternLine = (String)var4.next();
                     patternString = patternLine.trim();
                  } while(patternString.length() <= 0);
               } while(patternString.startsWith("#"));

               try {
                  ObjectName pattern = new ObjectName(patternString);
                  if (ignoreSet == null) {
                     ignoreSet = new HashSet();
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("ADDING pattern " + pattern);
                  }

                  ignoreSet.add(pattern);
               } catch (MalformedObjectNameException var8) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Invalid ObjectName pattern: " + var8.getMessage());
                  }
               }
            }
         } catch (IOException var9) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unexpected error reading " + ignoreFile.getAbsolutePath() + ", " + var9.getMessage());
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Ignore patterns: " + (ignoreSet == null ? "" : ignoreSet.toString()));
      }

      return ignoreSet;
   }

   static {
      DEFAULT_IGNORE_PATTERNS_FILENAME = PropertyHelper.getProperty("java.io.tmpdir", ".") + File.separator + "harvester-ignore-patterns.txt";
      debugLogger = DebugLogger.getDebugLogger("DebugMTUListenerIgnoreList");
   }

   private static class IgnorePatternsHolder {
      private static final Set ignorePatterns = PatternsHelper.readIgnorePatterns();

      private static Set getPatterns() {
         return ignorePatterns;
      }
   }
}
