package weblogic.application.internal.flow;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.security.SecurityPolicyHelper;
import weblogic.application.utils.EarUtils;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public final class PermissionsRegistrationFlow extends BaseFlow {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PermissionsRegistrationFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      if (System.getSecurityManager() != null) {
         try {
            this.appCtx.setPermissionsDescriptor(new PermissionsDescriptorLoader(this.appCtx.getApplicationFileManager().getVirtualJarFile()));
         } catch (IOException var8) {
            if (this.isDebugEnabled()) {
               this.debug("Error setting permissions descriptor for the application : ", var8);
            }
         }

         String classpath = this.appCtx.getAppClassLoader().getClassPath();
         String[] files = classpath.split(System.getProperty("path.separator"));
         List fileList = new ArrayList();
         String[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String file = var4[var6];
            fileList.add(new File(file));
         }

         this.registerPermissions(fileList);
      }

   }

   private void registerPermissions(List fileList) {
      if (fileList.size() > 0) {
         final File[] files = (File[])fileList.toArray(new File[fileList.size()]);
         SecurityManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Void run() {
               SecurityPolicyHelper.registerSecurityPermissions(files, PermissionsRegistrationFlow.this.getPermissions());
               return null;
            }
         });
      }

   }

   private PermissionCollection getPermissions() {
      PermissionCollection pc = null;

      try {
         PermissionsBean pb = this.appCtx.getPermissionsBean();
         pc = EarUtils.getPermissions(pb);
      } catch (Exception var3) {
         if (this.isDebugEnabled()) {
            this.debug("Error registering security permissions for the application : ", var3);
         }
      }

      return pc;
   }
}
