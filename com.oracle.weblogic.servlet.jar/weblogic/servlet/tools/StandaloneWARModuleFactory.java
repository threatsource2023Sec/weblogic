package weblogic.servlet.tools;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.Factory;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;
import weblogic.servlet.utils.WarUtils;

public class StandaloneWARModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      try {
         return !WarUtils.isWar(m) && !WarUtils.isAvatarApplication(m) ? null : true;
      } catch (IOException var3) {
         return null;
      }
   }

   public Boolean claim(File m, List allClaimants) {
      Iterator var3 = allClaimants.iterator();

      Factory f;
      do {
         if (!var3.hasNext()) {
            return this.claim(m);
         }

         f = (Factory)var3.next();
      } while(f.getClass() == StandaloneWARModuleFactory.class || !StandaloneWARModuleFactory.class.isAssignableFrom(f.getClass()));

      return false;
   }

   public ToolsModule create(File m) {
      return new WARModule(m.getName(), (String)null, (String)null);
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
