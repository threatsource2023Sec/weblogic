package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.ejb.container.interfaces.ExcludeList;
import weblogic.j2ee.descriptor.ExcludeListBean;
import weblogic.j2ee.descriptor.MethodBean;

public final class ExcludeListImpl implements ExcludeList {
   private final ExcludeListBean excludeListBean;

   public ExcludeListImpl(ExcludeListBean bean) {
      this.excludeListBean = bean;
   }

   public Collection getAllMethodDescriptors() {
      Collection result = new ArrayList();
      MethodBean[] var2 = this.excludeListBean.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodBean method = var2[var4];
         result.add(new MethodDescriptorImpl(method));
      }

      return result;
   }
}
