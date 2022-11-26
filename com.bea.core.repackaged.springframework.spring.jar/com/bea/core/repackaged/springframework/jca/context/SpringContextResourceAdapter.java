package com.bea.core.repackaged.springframework.jca.context;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.context.ConfigurableApplicationContext;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import com.bea.core.repackaged.springframework.core.env.StandardEnvironment;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public class SpringContextResourceAdapter implements ResourceAdapter {
   public static final String CONFIG_LOCATION_DELIMITERS = ",; \t\n";
   public static final String DEFAULT_CONTEXT_CONFIG_LOCATION = "META-INF/applicationContext.xml";
   protected final Log logger = LogFactory.getLog(this.getClass());
   private String contextConfigLocation = "META-INF/applicationContext.xml";
   @Nullable
   private ConfigurableApplicationContext applicationContext;

   public void setContextConfigLocation(String contextConfigLocation) {
      this.contextConfigLocation = contextConfigLocation;
   }

   protected String getContextConfigLocation() {
      return this.contextConfigLocation;
   }

   protected ConfigurableEnvironment createEnvironment() {
      return new StandardEnvironment();
   }

   public void start(BootstrapContext bootstrapContext) throws ResourceAdapterInternalException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Starting SpringContextResourceAdapter with BootstrapContext: " + bootstrapContext);
      }

      this.applicationContext = this.createApplicationContext(bootstrapContext);
   }

   protected ConfigurableApplicationContext createApplicationContext(BootstrapContext bootstrapContext) {
      ResourceAdapterApplicationContext applicationContext = new ResourceAdapterApplicationContext(bootstrapContext);
      applicationContext.setClassLoader(this.getClass().getClassLoader());
      String[] configLocations = StringUtils.tokenizeToStringArray(this.getContextConfigLocation(), ",; \t\n");
      this.loadBeanDefinitions(applicationContext, configLocations);
      applicationContext.refresh();
      return applicationContext;
   }

   protected void loadBeanDefinitions(BeanDefinitionRegistry registry, String[] configLocations) {
      (new XmlBeanDefinitionReader(registry)).loadBeanDefinitions((String[])configLocations);
   }

   public void stop() {
      this.logger.debug("Stopping SpringContextResourceAdapter");
      if (this.applicationContext != null) {
         this.applicationContext.close();
      }

   }

   public void endpointActivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec) throws ResourceException {
      throw new NotSupportedException("SpringContextResourceAdapter does not support message endpoints");
   }

   public void endpointDeactivation(MessageEndpointFactory messageEndpointFactory, ActivationSpec activationSpec) {
   }

   @Nullable
   public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException {
      return null;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof SpringContextResourceAdapter && ObjectUtils.nullSafeEquals(this.getContextConfigLocation(), ((SpringContextResourceAdapter)other).getContextConfigLocation());
   }

   public int hashCode() {
      return ObjectUtils.nullSafeHashCode((Object)this.getContextConfigLocation());
   }
}
