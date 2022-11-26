package org.glassfish.hk2.extras.operation.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;

public class OperationDescriptor extends AbstractActiveDescriptor {
   private final SingleOperationManager parent;

   public OperationDescriptor(Annotation scope, SingleOperationManager parent) {
      this.parent = parent;
      this.setImplementation(OperationHandleImpl.class.getName());
      this.addContractType(new ParameterizedTypeImpl(OperationHandle.class, new Type[]{scope.annotationType()}));
      this.setScopeAsAnnotation(scope);
   }

   public Class getImplementationClass() {
      return OperationHandleImpl.class;
   }

   public Type getImplementationType() {
      return OperationHandleImpl.class;
   }

   public void setImplementationType(Type t) {
      throw new AssertionError("Cannot set type of OperationHandle");
   }

   public OperationHandle create(ServiceHandle root) {
      OperationHandleImpl retVal = this.parent.getCurrentOperationOnThisThread();
      if (retVal == null) {
         throw new IllegalStateException("There is no active operation in scope " + this.getScopeAnnotation().getName() + " on thread " + Thread.currentThread().getId());
      } else {
         return retVal;
      }
   }
}
