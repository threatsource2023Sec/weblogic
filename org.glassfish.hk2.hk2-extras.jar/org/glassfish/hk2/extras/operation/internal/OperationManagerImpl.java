package org.glassfish.hk2.extras.operation.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.glassfish.hk2.extras.operation.OperationManager;

@Singleton
public class OperationManagerImpl implements OperationManager {
   private final HashMap children = new HashMap();
   @Inject
   private ServiceLocator locator;

   public OperationHandle createOperation(Annotation scope) {
      SingleOperationManager manager;
      synchronized(this) {
         manager = (SingleOperationManager)this.children.get(scope.annotationType());
         if (manager == null) {
            manager = new SingleOperationManager(scope, this.locator);
            this.children.put(scope.annotationType(), manager);
         }
      }

      return manager.createOperation();
   }

   public OperationHandle createAndStartOperation(Annotation scope) {
      OperationHandle retVal = this.createOperation(scope);
      retVal.resume();
      return retVal;
   }

   public Set getCurrentOperations(Annotation scope) {
      SingleOperationManager manager;
      synchronized(this) {
         manager = (SingleOperationManager)this.children.get(scope.annotationType());
         if (manager == null) {
            return Collections.emptySet();
         }
      }

      return manager.getAllOperations();
   }

   public OperationHandle getCurrentOperation(Annotation scope) {
      SingleOperationManager manager;
      synchronized(this) {
         manager = (SingleOperationManager)this.children.get(scope.annotationType());
         if (manager == null) {
            return null;
         }
      }

      return manager.getCurrentOperationOnThisThread();
   }

   public void shutdownAllOperations(Annotation scope) {
      synchronized(this) {
         SingleOperationManager manager = (SingleOperationManager)this.children.remove(scope.annotationType());
         if (manager != null) {
            manager.shutdown();
         }
      }
   }
}
