package weblogic.jms.application.bindings;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deployment.jms.PooledConnectionFactory;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public class PooledDefaultJMSConnectionFactoryObjectFactory implements ObjectFactory {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String DEFAULT_JMS_CONNECTION_FACTORY_INTERNAL = "weblogic.jms.DefaultConnectionFactory";
   private String DEFAULT_JMS_CONNECTION_FACTORY = "java:comp/DefaultJMSConnectionFactory";

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      String applicationName = cic.getApplicationId();
      String moduleName = cic.getModuleName();
      String componentName = cic.getComponentName();
      int wrapStyle = 1;
      boolean containerAuth = true;
      Map poolProps = new HashMap();
      poolProps.put("JNDIName", this.DEFAULT_JMS_CONNECTION_FACTORY_INTERNAL);
      poolProps.put("ApplicationName", applicationName);
      poolProps.put("ComponentName", componentName);
      poolProps.put("ComponentType", "EJB");
      String appId = ApplicationVersionUtils.getBindApplicationId();
      if (appId != null) {
         poolProps.put("weblogic.jndi.lookupApplicationId", appId);
      }

      AuthenticatedSubject currentSubject = SecurityManager.getCurrentSubject(KERNEL_ID);
      poolProps.put("RunAsSubject", currentSubject);
      String poolName = applicationName + "-" + moduleName + "-" + componentName + "-" + this.DEFAULT_JMS_CONNECTION_FACTORY;
      return new PooledConnectionFactory(poolName, wrapStyle, containerAuth, poolProps);
   }
}
