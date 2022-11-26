package weblogic.connector.tools;

import java.io.File;
import java.util.List;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;
import weblogic.j2ee.J2EEUtils;

public class StandaloneRARModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      return J2EEUtils.isRar(m) ? true : null;
   }

   public Boolean claim(File m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(File m) {
      return RARModule.createStandaloneRARModule(m.getName(), (String)null);
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
