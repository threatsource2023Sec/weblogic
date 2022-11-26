package com.bea.core.repackaged.springframework.scheduling.concurrent;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorDelegate;
import com.bea.core.repackaged.springframework.jndi.JndiTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;
import java.util.concurrent.Executor;
import javax.naming.NamingException;

public class DefaultManagedTaskExecutor extends ConcurrentTaskExecutor implements InitializingBean {
   private JndiLocatorDelegate jndiLocator = new JndiLocatorDelegate();
   @Nullable
   private String jndiName = "java:comp/DefaultManagedExecutorService";

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
         this.setConcurrentExecutor((Executor)this.jndiLocator.lookup(this.jndiName, Executor.class));
      }

   }
}
