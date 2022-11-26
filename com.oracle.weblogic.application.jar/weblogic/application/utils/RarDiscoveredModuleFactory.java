package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.utils.jars.VirtualJarFile;

class RarDiscoveredModuleFactory extends DiscoveredModuleFactory {
   private static String suffix = ".rar";

   public final DiscoveredModule claim(File f, String relPath) {
      return !f.getName().endsWith(suffix) ? null : new RarDiscoveredModule(relPath);
   }

   public final DiscoveredModule claim(VirtualJarFile vjf, ZipEntry entry, String relPath) throws IOException {
      return relPath.endsWith(suffix) ? new RarDiscoveredModule(relPath) : null;
   }

   public String[] claimedSuffixes() {
      return new String[]{suffix};
   }

   private static class RarDiscoveredModule implements DiscoveredModule {
      private final String relPath;

      public RarDiscoveredModule(String relPath) {
         this.relPath = relPath;
      }

      public void createModule(ApplicationBean bean) {
         ModuleBean module = bean.createModule();
         module.setConnector(this.relPath);
      }

      public String getURI() {
         return this.relPath;
      }
   }
}
