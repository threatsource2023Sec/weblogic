package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.security.AccessController;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class SecurityMBeanMgmtOpsInterceptor extends WLSMBeanServerInterceptorBase {
   private static final String REALM_MBEAN = "weblogic.management.security.RealmMBean";
   private static final String SECURITY_STORE_MBEAN = "weblogic.management.security.RDBMSSecurityStoreMBean";
   private static final String ULM_MBEAN = "weblogic.management.security.authentication.UserLockoutManagerMBean";
   private static final String PROVIDER_MBEAN = "weblogic.management.security.ProviderMBean";
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMX");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final int EDIT_MBS = 1;
   public static final int DOMAIN_RUNTIME_MBS = 2;
   private int mbsType;

   public SecurityMBeanMgmtOpsInterceptor(int mbsType) {
      this.mbsType = mbsType;
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      if (!this.isInvokeAllowed(objectName, s)) {
         String errorMsg = ManagementTextTextFormatter.getInstance().getMgmtOperationsIllegal();
         if (this.mbsType == 2) {
            errorMsg = ManagementTextTextFormatter.getInstance().getMgmtOperationsIllegalDomainRuntime();
         }

         throw new MBeanException(new RuntimeException(errorMsg), errorMsg);
      } else {
         return super.invoke(objectName, s, objects, strings);
      }
   }

   private boolean isInvokeAllowed(ObjectName oname, String operationName) throws InstanceNotFoundException, IOException, MBeanException, ReflectionException {
      try {
         if (this.mbsType == 2 && !ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isRestartRequired()) {
            return true;
         } else {
            MBeanInfo mbeanInfo = super.getMBeanInfo(oname);
            if (!(mbeanInfo instanceof ModelMBeanInfo)) {
               return true;
            } else {
               ModelMBeanInfo modelMBeanInfo = (ModelMBeanInfo)mbeanInfo;
               String clzName = modelMBeanInfo.getClassName();
               Class securityMBean = DescriptorClassLoader.loadClass(clzName);
               if (this.isSecurityMBean(securityMBean)) {
                  if (modelMBeanInfo.getOperation(operationName) == null || modelMBeanInfo.getOperation(operationName).getImpact() == 0) {
                     return true;
                  }

                  Descriptor descriptor = modelMBeanInfo.getOperation(operationName).getDescriptor();
                  Boolean allowSecurityOps = (Boolean)descriptor.getFieldValue("com.bea.allowSecurityOperations");
                  if (allowSecurityOps != null && allowSecurityOps) {
                     return true;
                  }

                  String role = (String)descriptor.getFieldValue("com.bea.collectionRole");
                  if (role != null) {
                     if (debug.isDebugEnabled()) {
                        debug.debug("This operation " + operationName + " is a Management operation on MBean " + oname + " and will be prevented.");
                     }

                     return false;
                  }
               }

               return true;
            }
         }
      } catch (IntrospectionException var10) {
         if (debug.isDebugEnabled()) {
            debug.debug("IntrospectionException thrown while checking the mgmt operations.", var10);
         }

         return true;
      } catch (ClassNotFoundException var11) {
         if (debug.isDebugEnabled()) {
            debug.debug("ClassNotFoundException thrown while checking the mgmt operations.", var11);
         }

         return true;
      }
   }

   private boolean isSecurityMBean(Class securityMBean) throws ClassNotFoundException {
      if (!Class.forName("weblogic.management.security.RealmMBean").isAssignableFrom(securityMBean) && !Class.forName("weblogic.management.security.ProviderMBean").isAssignableFrom(securityMBean) && !Class.forName("weblogic.management.security.authentication.UserLockoutManagerMBean").isAssignableFrom(securityMBean) && !Class.forName("weblogic.management.security.RDBMSSecurityStoreMBean").isAssignableFrom(securityMBean)) {
         return false;
      } else {
         if (debug.isDebugEnabled()) {
            debug.debug("Invoking a management operation on a security mbean.");
         }

         return true;
      }
   }
}
