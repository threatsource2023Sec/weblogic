package weblogic.management.rest.lib.bean.utils;

import java.security.AccessController;
import javax.management.ObjectName;
import org.glassfish.admin.rest.debug.DebugLogger;
import weblogic.management.internal.WLSObjectNameBuilder;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JMXUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(JMXUtils.class);
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static WLSObjectNameBuilder objectNameBuilder;

   public static Object[] getJMXParams(InvocationContext ic, Object[] javaParams) throws Exception {
      if (javaParams == null) {
         return null;
      } else {
         Object[] jmxParams = new Object[javaParams.length];

         for(int i = 0; i < javaParams.length; ++i) {
            jmxParams[i] = getJMXValue(ic, javaParams[i]);
         }

         return jmxParams;
      }
   }

   public static Object getJMXValue(InvocationContext ic, Object javaVal) throws Exception {
      if (javaVal == null) {
         return null;
      } else {
         Class clazz = javaVal.getClass();
         if (BeanUtils.isBeanClass(clazz)) {
            return getObjectName(ic.clone(javaVal));
         } else if (clazz.isArray() && BeanUtils.isBeanClass(clazz.getComponentType())) {
            Object[] beans = (Object[])((Object[])javaVal);
            ObjectName[] objectNames = new ObjectName[beans.length];

            for(int i = 0; i < beans.length; ++i) {
               objectNames[i] = getObjectName(ic.clone(beans[i]));
            }

            return objectNames;
         } else {
            return javaVal;
         }
      }
   }

   public static ObjectName getObjectName(InvocationContext ic) throws Exception {
      Object bean = ic.bean();
      if (bean == null) {
         return null;
      } else if (bean instanceof ConfigurationManagerMBean) {
         return new ObjectName("com.bea:Name=ConfigurationManager,Type=weblogic.management.mbeanservers.edit.ConfigurationManagerMBean");
      } else {
         String domainName;
         if (bean instanceof ActivationTaskMBean) {
            domainName = ((ActivationTaskMBean)bean).getName();
            String oname = "com.bea:Name=" + domainName + ",Type=weblogic.management.mbeanservers.edit.ActivationTaskMBean,Path=/ConfigurationManager[ConfigurationManager]/ActivationTasks[" + domainName + "]";
            return new ObjectName(oname);
         } else {
            if (objectNameBuilder == null) {
               domainName = ManagementService.getRuntimeAccess(kernelId).getDomainName();
               objectNameBuilder = new WLSObjectNameBuilder(domainName);
            }

            return objectNameBuilder.buildObjectName(bean);
         }
      }
   }
}
