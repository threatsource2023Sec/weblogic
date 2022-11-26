package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorDelegate;
import com.bea.core.repackaged.springframework.jndi.JndiTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import javax.naming.NamingException;

public class DefaultManagedTaskScheduler extends ConcurrentTaskScheduler implements InitializingBean {
   private JndiLocatorDelegate jndiLocator = new JndiLocatorDelegate();
   @Nullable
   private String jndiName = "java:comp/DefaultManagedScheduledExecutorService";

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
         ScheduledExecutorService executor = (ScheduledExecutorService)this.jndiLocator.lookup(this.jndiName, ScheduledExecutorService.class);
         this.setConcurrentExecutor(executor);
         this.setScheduledExecutor(executor);
      }

   }
}
