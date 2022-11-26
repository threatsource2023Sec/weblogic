package weblogic.j2eeclient.tools;

import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.deploy.shared.ModuleType;
import javax.interceptor.Interceptor;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleFactory;
import weblogic.j2ee.descriptor.ModuleBean;

public class CARModuleFactory implements ToolsModuleFactory {
   public Boolean claim(ModuleBean m) {
      return m.getJava() != null ? Boolean.TRUE : null;
   }

   public Boolean claim(ModuleBean m, List allClaimants) {
      return allClaimants.size() == 1 && allClaimants.get(0) == this ? this.claim(m) : null;
   }

   public ToolsModule create(ModuleBean m) {
      return new CARModule(m.getJava(), m.getAltDd());
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.CAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return new Class[]{ManagedBean.class, Interceptor.class};
   }
}
