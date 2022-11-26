package org.jboss.weld.bootstrap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.util.reflection.Formats;

public class MissingDependenciesRegistry extends AbstractBootstrapService {
   private final ConcurrentMap classToMissingClassMap = new ConcurrentHashMap();

   public void registerClassWithMissingDependency(String className, String missingClassName) {
      this.classToMissingClassMap.put(className, missingClassName);
   }

   public void handleResourceLoadingException(String className, Throwable e) {
      String missingDependency = Formats.getNameOfMissingClassLoaderDependency(e);
      BootstrapLogger.LOG.ignoringClassDueToLoadingError(className, missingDependency);
      BootstrapLogger.LOG.catchingDebug(e);
      this.registerClassWithMissingDependency(className, missingDependency);
   }

   public String getMissingDependencyForClass(String className) {
      return (String)this.classToMissingClassMap.get(className);
   }

   public void cleanupAfterBoot() {
      this.classToMissingClassMap.clear();
   }
}
