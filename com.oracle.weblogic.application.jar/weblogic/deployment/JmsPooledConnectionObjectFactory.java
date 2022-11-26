package weblogic.deployment;

import java.util.HashMap;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.application.naming.EnvReference;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deployment.jms.PooledConnectionFactory;

public class JmsPooledConnectionObjectFactory implements ObjectFactory {
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      if (!(obj instanceof EnvReference)) {
         throw new AssertionError("object factory should have been referenced only from EnvReference");
      } else {
         EnvReference ref = (EnvReference)obj;
         Environment env = ref.getEnvironment();
         Context jmsCtx = null;

         try {
            jmsCtx = (Context)ref.getEnvironment().getRootContext().lookup("app/jms");
         } catch (NameNotFoundException var14) {
         }

         HashMap poolProps = new HashMap();
         poolProps.put("JNDIName", ref.getJndiName());
         poolProps.put("ApplicationName", env.getApplicationName());
         poolProps.put("ComponentName", env.getComponentName());
         if (env.isEjbComponent()) {
            poolProps.put("ComponentType", "EJB");
         }

         poolProps.put("JmsApplicationContext", jmsCtx);
         if (ref.getRunAs() != null) {
            poolProps.put("RunAsSubject", ref.getRunAs());
         }

         String appId = ApplicationVersionUtils.getBindApplicationId();
         if (appId != null) {
            poolProps.put("weblogic.jndi.lookupApplicationId", appId);
         }

         if (ref.getResourceRefBean().getLookupName() == null && ref.getResourceRefBean().getMappedName() == null) {
            poolProps.put("ResourceRefNameAsJNDIName", "true");
         }

         String poolName = ref.getJndiName();
         if (!EnvUtils.isResourceShareable(ref.getResourceRefBean())) {
            poolName = env.getApplicationName() + "-" + env.getComponentName() + "-" + ref.getJndiName();
         }

         try {
            return new PooledConnectionFactory(poolName, env.isEjbComponent() ? 1 : 2, EnvUtils.isContainerAuthEnabled(ref.getResourceRefBean()), poolProps);
         } catch (JMSException var13) {
            NamingException ne = new NamingException(var13.toString());
            ne.setRootCause(var13);
            throw ne;
         }
      }
   }
}
