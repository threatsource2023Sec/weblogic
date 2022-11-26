package weblogic.coherence.container.tools;

import java.io.File;
import java.util.List;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.StandaloneToolsModuleFactory;
import weblogic.application.compiler.ToolsModule;

public class StandaloneGARModuleFactory implements StandaloneToolsModuleFactory {
   public Boolean claim(File m) {
      return this.isGAR(m) ? true : null;
   }

   public Boolean claim(File m, List allClaimants) {
      return null;
   }

   public ToolsModule create(File m) {
      return new GARModule(m.getName());
   }

   public Boolean claim(ApplicationArchive archive) {
      return null;
   }

   public Boolean claim(ApplicationArchive archive, List allClaimants) {
      return null;
   }

   public ToolsModule create(ApplicationArchive archive) {
      return new GARModule(archive.getName());
   }

   private boolean isGAR(File file) {
      return file.isDirectory() ? (new File(file, "META-INF/coherence-application.xml")).exists() : file.getName().endsWith(".gar");
   }
}
