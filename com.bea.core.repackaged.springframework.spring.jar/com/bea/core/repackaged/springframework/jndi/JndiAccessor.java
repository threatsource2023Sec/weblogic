package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Properties;

public class JndiAccessor {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private JndiTemplate jndiTemplate = new JndiTemplate();

   public void setJndiTemplate(@Nullable JndiTemplate jndiTemplate) {
      this.jndiTemplate = jndiTemplate != null ? jndiTemplate : new JndiTemplate();
   }

   public JndiTemplate getJndiTemplate() {
      return this.jndiTemplate;
   }

   public void setJndiEnvironment(@Nullable Properties jndiEnvironment) {
      this.jndiTemplate = new JndiTemplate(jndiEnvironment);
   }

   @Nullable
   public Properties getJndiEnvironment() {
      return this.jndiTemplate.getEnvironment();
   }
}
