package weblogic.servlet.tools;

import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.Factory;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleFactory;
import weblogic.j2ee.descriptor.ModuleBean;

public class WARModuleFactory implements ToolsModuleFactory {
   public Boolean claim(ModuleBean m) {
      return m.getWeb() != null ? true : null;
   }

   public Boolean claim(ModuleBean m, List allClaimants) {
      Iterator var3 = allClaimants.iterator();

      Factory f;
      do {
         if (!var3.hasNext()) {
            return this.claim(m);
         }

         f = (Factory)var3.next();
      } while(f.getClass() == WARModuleFactory.class || !WARModuleFactory.class.isAssignableFrom(f.getClass()));

      return false;
   }

   public ToolsModule create(ModuleBean m) {
      return new WARModule(m.getWeb().getWebUri(), m.getAltDd(), m.getWeb().getContextRoot());
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[0];
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return new Class[0];
   }
}
