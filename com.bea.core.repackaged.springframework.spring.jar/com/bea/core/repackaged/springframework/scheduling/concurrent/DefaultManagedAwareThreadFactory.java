package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorDelegate;
import com.bea.core.repackaged.springframework.jndi.JndiTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;
import java.util.concurrent.ThreadFactory;
import javax.naming.NamingException;

public class DefaultManagedAwareThreadFactory extends CustomizableThreadFactory implements InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private JndiLocatorDelegate jndiLocator = new JndiLocatorDelegate();
   @Nullable
   private String jndiName = "java:comp/DefaultManagedThreadFactory";
   @Nullable
   private ThreadFactory threadFactory;

   public void setJndiTemplate(JndiTemplate jndiTemplate) {
      this.jndiLocator.setJndiTemplate(jndiTemplate);
   }

   public void setJndiEnvironment(Properties jndiEnvironment) {
      this.jndiLocator.setJndiEnvironment(jndiEnvironment);
   }

   public void setResourceRef(boolean resourceRef) {
      this.jndiLocator.setResourceRef(resourceRef);
   }

   public void setJndiName(String jndiName) {
      this.jndiName = jndiName;
   }

   public void afterPropertiesSet() throws NamingException {
      if (this.jndiName != null) {
         try {
            this.threadFactory = (ThreadFactory)this.jndiLocator.lookup(this.jndiName, ThreadFactory.class);
         } catch (NamingException var2) {
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Failed to retrieve [" + this.jndiName + "] from JNDI", var2);
            }

            this.logger.info("Could not find default managed thread factory in JNDI - proceeding with default local thread factory");
         }
      }

   }

   public Thread newThread(Runnable runnable) {
      return this.threadFactory != null ? this.threadFactory.newThread(runnable) : super.newThread(runnable);
   }
}
