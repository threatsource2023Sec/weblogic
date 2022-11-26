package weblogic.coherence.container.server;

import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.WeblogicModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class CoherenceModuleFactory implements WeblogicModuleFactory {
   public Module createModule(WeblogicModuleBean bean) throws ModuleException {
      return bean.getType().equals("GAR") ? new CoherenceModule(bean.getPath(), bean.getName(), false) : null;
   }
}
