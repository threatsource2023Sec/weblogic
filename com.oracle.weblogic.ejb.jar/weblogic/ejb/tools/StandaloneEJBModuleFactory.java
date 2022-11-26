package weblogic.ejb.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;
import weblogic.j2ee.J2EEUtils;

public class StandaloneEJBModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      try {
         return J2EEUtils.isEJB(m) ? Boolean.TRUE : null;
      } catch (IOException var3) {
         return null;
      }
   }

   public Boolean claim(File m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(File m) {
      return new EJBModule(m.getName(), (String)null);
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
