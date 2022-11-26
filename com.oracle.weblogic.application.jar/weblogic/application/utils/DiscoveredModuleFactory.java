package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.VirtualJarFile;

public abstract class DiscoveredModuleFactory {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final DiscoveredModuleFactory[] factories = new DiscoveredModuleFactory[]{new WarDiscoveredModuleFactory(), new RarDiscoveredModuleFactory(), new JavaDiscoveredModuleFactory(), new EJBDiscoveredModuleFactory()};
   private static String[] knownExtensions = null;

   public abstract DiscoveredModule claim(File var1, String var2);

   public abstract DiscoveredModule claim(VirtualJarFile var1, ZipEntry var2, String var3) throws IOException;

   public abstract String[] claimedSuffixes();

   public static String[] knownExtensions() {
      return knownExtensions;
   }

   static DiscoveredModule makeDiscoveredModule(File f, String relPath) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing: " + relPath);
      }

      for(int i = 0; i < factories.length; ++i) {
         DiscoveredModuleFactory factory = factories[i];
         DiscoveredModule module = factory.claim(f, relPath);
         if (module != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Found module :" + module + " for path: " + relPath);
            }

            return module;
         }
      }

      return null;
   }

   static DiscoveredModule makeDiscoveredModule(VirtualJarFile vjf, ZipEntry entry, String relPath) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing: " + relPath);
      }

      for(int i = 0; i < factories.length; ++i) {
         DiscoveredModuleFactory factory = factories[i];
         DiscoveredModule module = factory.claim(vjf, entry, relPath);
         if (module != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Found module :" + module + " for path: " + relPath);
            }

            return module;
         }
      }

      return null;
   }

   static {
      Set suffixes = new HashSet();
      DiscoveredModuleFactory[] var1 = factories;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         DiscoveredModuleFactory factory = var1[var3];
         String[] var5 = factory.claimedSuffixes();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String suffix = var5[var7];
            suffixes.add(suffix);
         }
      }

      knownExtensions = (String[])suffixes.toArray(new String[0]);
   }
}
