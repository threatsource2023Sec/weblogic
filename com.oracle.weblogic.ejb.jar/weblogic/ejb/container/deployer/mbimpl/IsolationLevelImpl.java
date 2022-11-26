package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.ejb.container.interfaces.IsolationLevel;
import weblogic.j2ee.descriptor.wl.MethodBean;
import weblogic.j2ee.descriptor.wl.TransactionIsolationBean;

public final class IsolationLevelImpl implements IsolationLevel {
   private final String isolationLevel;
   private final Collection methodDescriptors = new ArrayList();

   public IsolationLevelImpl(TransactionIsolationBean ti) {
      this.isolationLevel = ti.getIsolationLevel();
      MethodBean[] var2 = ti.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodBean method = var2[var4];
         this.methodDescriptors.add(new MethodDescriptorImpl(method));
      }

   }

   public String getIsolationLevel() {
      return this.isolationLevel;
   }

   public Collection getAllMethodDescriptors() {
      return this.methodDescriptors;
   }
}
