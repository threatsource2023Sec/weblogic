package weblogic.coherence.container.tools;

import java.util.List;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.WebLogicToolsModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class GARModuleFactory implements WebLogicToolsModuleFactory {
   public Boolean claim(WeblogicModuleBean m) {
      return m.getType().equals("GAR");
   }

   public Boolean claim(WeblogicModuleBean m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(WeblogicModuleBean m) {
      return new GARModule(m.getPath());
   }
}
