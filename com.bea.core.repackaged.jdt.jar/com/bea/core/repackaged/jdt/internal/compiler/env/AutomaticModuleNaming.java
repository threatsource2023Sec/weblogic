package com.bea.core.repackaged.jdt.internal.compiler.env;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class AutomaticModuleNaming {
   private static final String AUTOMATIC_MODULE_NAME = "Automatic-Module-Name";

   public static char[] determineAutomaticModuleName(String jarFileName) {
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            JarFile jar = new JarFile(jarFileName);

            char[] var10000;
            try {
               Manifest manifest = jar.getManifest();
               if (manifest == null) {
                  return determineAutomaticModuleNameFromFileName(jarFileName, true, true);
               }

               String automaticModuleName = manifest.getMainAttributes().getValue("Automatic-Module-Name");
               if (automaticModuleName == null) {
                  return determineAutomaticModuleNameFromFileName(jarFileName, true, true);
               }

               var10000 = automaticModuleName.toCharArray();
            } finally {
               if (jar != null) {
                  jar.close();
               }

            }

            return var10000;
         } catch (Throwable var13) {
            if (var1 == null) {
               var1 = var13;
            } else if (var1 != var13) {
               var1.addSuppressed(var13);
            }

            throw var1;
         }
      } catch (IOException var14) {
         return determineAutomaticModuleNameFromFileName(jarFileName, true, true);
      }
   }

   public static char[] determineAutomaticModuleName(String fileName, boolean isFile, Manifest manifest) {
      if (manifest != null) {
         String automaticModuleName = manifest.getMainAttributes().getValue("Automatic-Module-Name");
         if (automaticModuleName != null) {
            return automaticModuleName.toCharArray();
         }
      }

      return determineAutomaticModuleNameFromFileName(fileName, true, isFile);
   }

   public static char[] determineAutomaticModuleNameFromManifest(Manifest manifest) {
      if (manifest != null) {
         String automaticModuleName = manifest.getMainAttributes().getValue("Automatic-Module-Name");
         if (automaticModuleName != null) {
            return automaticModuleName.toCharArray();
         }
      }

      return null;
   }

   public static char[] determineAutomaticModuleNameFromFileName(String name, boolean skipDirectory, boolean removeExtension) {
      int start = 0;
      int end = name.length();
      int index;
      if (skipDirectory) {
         index = name.lastIndexOf(File.separatorChar);
         start = index + 1;
      }

      if (removeExtension && (name.endsWith(".jar") || name.endsWith(".JAR"))) {
         end -= 4;
      }

      index = start;

      while(index < end - 1) {
         label108: {
            if (name.charAt(index) == '-' && name.charAt(index + 1) >= '0' && name.charAt(index + 1) <= '9') {
               int index2 = index + 2;

               while(true) {
                  if (index2 >= end) {
                     break label108;
                  }

                  char c = name.charAt(index2);
                  if (c == '.') {
                     break label108;
                  }

                  if (c < '0' || c > '9') {
                     break;
                  }

                  ++index2;
               }
            }

            ++index;
            continue;
         }

         end = index;
         break;
      }

      StringBuilder sb = new StringBuilder(end - start);
      boolean needDot = false;

      for(int i = start; i < end; ++i) {
         char c = name.charAt(i);
         if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9') {
            if (needDot) {
               sb.append('.');
               needDot = false;
            }

            sb.append(c);
         } else if (sb.length() > 0) {
            needDot = true;
         }
      }

      return sb.toString().toCharArray();
   }
}
