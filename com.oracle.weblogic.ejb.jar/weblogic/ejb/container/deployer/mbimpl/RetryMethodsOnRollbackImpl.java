package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.ejb.container.interfaces.RetryMethodsOnRollback;
import weblogic.j2ee.descriptor.wl.MethodBean;
import weblogic.j2ee.descriptor.wl.RetryMethodsOnRollbackBean;

public final class RetryMethodsOnRollbackImpl implements RetryMethodsOnRollback {
   private final int retryCount;
   private final Collection methodDescriptors = new ArrayList();

   public RetryMethodsOnRollbackImpl(RetryMethodsOnRollbackBean rm) {
      this.retryCount = rm.getRetryCount();
      MethodBean[] var2 = rm.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodBean method = var2[var4];
         this.methodDescriptors.add(new MethodDescriptorImpl(method));
      }

   }

   public int getRetryCount() {
      return this.retryCount;
   }

   public Collection getAllMethodDescriptors() {
      return this.methodDescriptors;
   }
}
