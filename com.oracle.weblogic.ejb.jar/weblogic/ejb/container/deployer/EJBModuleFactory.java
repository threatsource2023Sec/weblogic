package weblogic.ejb.container.deployer;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleFactory;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.j2ee.descriptor.ModuleBean;

public final class EJBModuleFactory implements ModuleFactory {
   private final CoherenceService coherenceService;

   public EJBModuleFactory(CoherenceService coherenceService) {
      this.coherenceService = coherenceService;
   }

   public Module createModule(ModuleBean moduleDD) throws ModuleException {
      return moduleDD.getEjb() != null ? new EJBModule(moduleDD.getEjb(), this.coherenceService) : null;
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.EJB};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return (Class[])DDConstants.COMPONENT_DEFINING_ANNOS.toArray(new Class[0]);
   }
}
