package weblogic.jaxrs.monitoring.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.glassfish.jersey.server.ResourceConfig;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsApplicationRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceConfigTypeRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

/** @deprecated */
@Deprecated
final class JaxRsResourceConfigTypeMBeanImpl extends RuntimeMBeanDelegate implements JaxRsResourceConfigTypeRuntimeMBean {
   private final ResourceConfig config;
   private final Properties properties = new Properties();

   public JaxRsResourceConfigTypeMBeanImpl(JaxRsApplicationRuntimeMBean app, ResourceConfig config) throws ManagementException {
      super("ResourceConfig", app);
      this.config = config;
      Iterator var3 = config.getProperties().entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         this.properties.setProperty((String)entry.getKey(), entry.getValue().toString());
      }

   }

   public String getClassName() {
      return this.config.getClass().getName();
   }

   public Properties getProperties() {
      return this.properties;
   }
}
