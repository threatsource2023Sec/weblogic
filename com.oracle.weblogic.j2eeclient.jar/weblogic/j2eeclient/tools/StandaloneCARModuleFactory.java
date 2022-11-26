package weblogic.j2eeclient.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.Attributes.Name;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.utils.IOUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class StandaloneCARModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      if (m.isDirectory() || m.getName().endsWith(".jar")) {
         VirtualJarFile vjf = null;

         try {
            vjf = VirtualJarFactory.createVirtualJar(m);
            if (vjf.getManifest() != null && vjf.getManifest().getMainAttributes().get(Name.MAIN_CLASS) != null || vjf.getEntry("META-INF/application-client.xml") != null) {
               Boolean var3 = true;
               return var3;
            }
         } catch (IOException var7) {
         } finally {
            IOUtils.forceClose(vjf);
         }
      }

      return null;
   }

   public Boolean claim(File m, List allClaimants) {
      return allClaimants.size() == 1 && allClaimants.get(0) == this ? this.claim(m) : null;
   }

   public ToolsModule create(File m) {
      return new CARModule(m.getName(), (String)null);
   }

   public Boolean claim(ApplicationArchive archive) {
      return null;
   }

   public Boolean claim(ApplicationArchive archive, List allClaimants) {
      return null;
   }

   public ToolsModule create(ApplicationArchive archive) {
      return null;
   }
}
