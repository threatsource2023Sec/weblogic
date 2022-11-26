package weblogic.jms.application.bindings;

import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.naming.Reference;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.env.bindings.DefaultBindings;
import weblogic.deployment.jms.PooledConnectionFactory;

@Service
public class JMSDefaultApplicationBindings implements DefaultBindings {
   static final String DEFAULT_JMS_CONNECTION_FACTORY = "java:comp/DefaultJMSConnectionFactory";
   private Map defaultBindings = new HashMap();

   public JMSDefaultApplicationBindings() throws NamingException {
      this.defaultBindings.put("java:comp/DefaultJMSConnectionFactory", new Reference(PooledConnectionFactory.class.getName(), PooledDefaultJMSConnectionFactoryObjectFactory.class.getName(), (String)null));
   }

   public Map getDefaultBindings() {
      return this.defaultBindings;
   }
}
