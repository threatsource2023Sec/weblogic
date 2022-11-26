package weblogic.work.concurrent.partition;

import java.util.Map;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.work.concurrent.ConcurrentManagedObject;
import weblogic.work.concurrent.runtime.ConcurrentObjectAndRuntimeManager;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.services.PartitionConcurrrentManagedObjectFactory;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;

public final class PartitionObjectAndRuntimeManager extends ConcurrentObjectAndRuntimeManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private static PartitionObjectAndRuntimeManager instance = new PartitionObjectAndRuntimeManager();

   private PartitionObjectAndRuntimeManager() {
   }

   public static PartitionObjectAndRuntimeManager getInstance() {
      return instance;
   }

   protected String getPrefix(ConcurrentManagedObjectBuilder builder) {
      return builder.getPartitionName();
   }

   protected void checkCreateObject(Map map, String objectName, String simpleClassName) throws DeploymentException {
      if (!map.containsKey(objectName)) {
         throw new DeploymentException(String.format("%s %s was removed before the first attempt to use it.", simpleClassName, objectName));
      }
   }

   protected ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime createConcurrentObjectAndRuntime(ConcurrentManagedObject object, RuntimeMBeanDelegate mbean) {
      throw new IllegalStateException("PartitionObjectAndRuntimeManager do not support eager creation.");
   }

   private void putReference(Map CMOs, ConcurrentManagedObjectReference ref, String simpleClassName) throws DeploymentException {
      String key = this.getObjectName(RuntimeAccessUtils.getCurrentPartitionName(), ref.getName());
      if (CMOs.containsKey(key)) {
         throw new DeploymentException(String.format("%s %s is already exist", simpleClassName, key));
      } else {
         CMOs.put(key, ref);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(ref + "is created with key " + key);
         }

      }
   }

   public ConcurrentManagedObjectReference createManagedExecutorServiceReference(ManagedExecutorServiceMBean mbean) throws DeploymentException {
      ConcurrentManagedObjectReference.ManagedExecutorServiceReference ref = new ConcurrentManagedObjectReference.ManagedExecutorServiceReference(mbean);
      this.putReference(this.managedExecutors, ref, ManagedExecutorService.class.getSimpleName());
      PartitionConcurrrentManagedObjectFactory.createAndBindMESFactory(ref.getName());
      return ref;
   }

   public ConcurrentManagedObjectReference createManagedScheduledExecutorServiceReference(ManagedScheduledExecutorServiceMBean mbean) throws DeploymentException {
      ConcurrentManagedObjectReference.ManagedScheduledExecutorServiceReference ref = new ConcurrentManagedObjectReference.ManagedScheduledExecutorServiceReference(mbean);
      this.putReference(this.managedScheduledExecutors, ref, ManagedScheduledExecutorService.class.getSimpleName());
      PartitionConcurrrentManagedObjectFactory.createAndBindMSESFactory(ref.getName());
      return ref;
   }

   public ConcurrentManagedObjectReference createManagedThreadFactoryReference(ManagedThreadFactoryMBean mbean) throws DeploymentException {
      ConcurrentManagedObjectReference.ManagedThreadFactoryReference ref = new ConcurrentManagedObjectReference.ManagedThreadFactoryReference(mbean);
      this.putReference(this.managedThreadFactories, ref, ManagedThreadFactory.class.getSimpleName());
      PartitionConcurrrentManagedObjectFactory.createAndBindMTFFactory(ref.getName());
      return ref;
   }

   public ConcurrentManagedObjectReference createContextServiceReference() throws DeploymentException {
      ConcurrentManagedObjectReference.ContextServiceReference ref = new ConcurrentManagedObjectReference.ContextServiceReference();
      this.putReference(this.contextServices, ref, ContextService.class.getSimpleName());
      PartitionConcurrrentManagedObjectFactory.createAndBindCSFactory(ref.getName());
      return ref;
   }

   public ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime removeObject(String className, String name, String prefix) {
      ConcurrentObjectAndRuntimeManager.ConcurrentObjectAndRuntime objAndRuntime;
      synchronized(this) {
         objAndRuntime = super.removeObject(className, name, prefix);
      }

      if (objAndRuntime != null) {
         ConcurrentManagedObject obj = objAndRuntime.getObject();
         if (obj != null) {
            obj.stop();
            obj.terminate();
         }

         PartitionConcurrrentManagedObjectFactory.unbindFactory(className, name);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Removing a non-exist partition level CMO: class=" + className + ", name=" + name + ", prefix=" + prefix, new Exception());
      }

      return objAndRuntime;
   }
}
