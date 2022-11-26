package weblogic.ejb.tools;

import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.ToolsModuleFactory;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.j2ee.descriptor.ModuleBean;

public class EJBModuleFactory implements ToolsModuleFactory {
   public Boolean claim(ModuleBean m) {
      return m.getEjb() != null ? Boolean.TRUE : null;
   }

   public Boolean claim(ModuleBean m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(ModuleBean m) {
      return new EJBModule(m.getEjb(), m.getAltDd());
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.EJB};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return (Class[])DDConstants.COMPONENT_DEFINING_ANNOS.toArray(new Class[0]);
   }
}
