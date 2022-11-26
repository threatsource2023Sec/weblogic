package weblogic.j2eeclient;

import javax.annotation.ManagedBean;
import javax.enterprise.deploy.shared.ModuleType;
import javax.interceptor.Interceptor;
import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.ModuleFactory;
import weblogic.j2ee.descriptor.ModuleBean;

public final class AppClientModuleFactory implements ModuleFactory {
   public Module createModule(ModuleBean moduleDD) throws ModuleException {
      String uri = moduleDD.getJava();
      return uri != null ? new AppClientModule(uri) : null;
   }

   public ModuleType[] getSupportedModuleTypes() {
      return new ModuleType[]{ModuleType.CAR};
   }

   public Class[] getSupportedClassLevelAnnotations() {
      return new Class[]{ManagedBean.class, Interceptor.class};
   }
}
