package weblogic.ejb.container.deployer.mbimpl;

import java.util.ArrayList;
import java.util.Collection;
import weblogic.ejb.container.interfaces.MethodPermission;
import weblogic.j2ee.descriptor.MethodBean;
import weblogic.j2ee.descriptor.MethodPermissionBean;

public final class MethodPermissionImpl implements MethodPermission {
   private final MethodPermissionBean methodPermissionBean;

   public MethodPermissionImpl(MethodPermissionBean bean) {
      this.methodPermissionBean = bean;
   }

   public Collection getAllMethodDescriptors() {
      Collection result = new ArrayList();
      MethodBean[] var2 = this.methodPermissionBean.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodBean method = var2[var4];
         result.add(new MethodDescriptorImpl(method));
      }

      return result;
   }

   public Collection getAllRoleNames() {
      Collection result = new ArrayList();
      String[] var2 = this.methodPermissionBean.getRoleNames();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String role = var2[var4];
         result.add(role);
      }

      return result;
   }

   public boolean isUnchecked() {
      return this.methodPermissionBean.getUnchecked() != null;
   }
}
