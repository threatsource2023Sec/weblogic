package weblogic.rmi.internal.wls;

import java.lang.annotation.Annotation;
import weblogic.application.AppClassLoaderManager;
import weblogic.rmi.utils.ClassLoaderEnvironment;
import weblogic.server.GlobalServiceLocator;

public class WLSClassLoaderEnvironment extends ClassLoaderEnvironment {
   private final AppClassLoaderManager manager = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);

   public ClassLoader findLoader(String applicationName) {
      return this.manager.findLoader(new weblogic.utils.classloaders.Annotation(applicationName));
   }

   public ClassLoader findInterAppLoader(String applicationName, ClassLoader parent) {
      return this.manager.findOrCreateInterAppLoader(new weblogic.utils.classloaders.Annotation(applicationName), parent);
   }
}
