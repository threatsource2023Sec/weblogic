package weblogic.jdbc.tools;

import java.io.File;
import java.util.List;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;

public class StandaloneJDBCModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      if (m.isDirectory()) {
         return null;
      } else {
         return m.getName().endsWith("-jdbc.xml") ? true : null;
      }
   }

   public Boolean claim(File m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(File m) {
      return new JDBCModule(m.getPath(), (String)null, (String)null);
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
