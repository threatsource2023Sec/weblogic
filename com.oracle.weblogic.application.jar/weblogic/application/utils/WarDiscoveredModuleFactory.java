package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.utils.application.WarDetector;
import weblogic.utils.jars.VirtualJarFile;

class WarDiscoveredModuleFactory extends DiscoveredModuleFactory {
   public final DiscoveredModule claim(File f, String relPath) {
      return WarDetector.instance.suffixed(relPath) ? new WarDiscoveredModule(relPath) : null;
   }

   public final DiscoveredModule claim(VirtualJarFile vjf, ZipEntry entry, String relPath) throws IOException {
      return WarDetector.instance.suffixed(relPath) ? new WarDiscoveredModule(relPath) : null;
   }

   public String[] claimedSuffixes() {
      return WarDetector.instance.getSuffixes();
   }

   static class WarDiscoveredModule implements DiscoveredModule {
      private final String relPath;

      public WarDiscoveredModule(String relPath) {
         this.relPath = relPath;
      }

      public void createModule(ApplicationBean bean) {
         ModuleBean module = bean.createModule();
         WebBean webBean = module.createWeb();
         webBean.setWebUri(this.relPath);
         webBean.setContextRoot(this.relPath.substring(0, this.relPath.lastIndexOf(46)));
      }

      public String getURI() {
         return this.relPath;
      }
   }
}
