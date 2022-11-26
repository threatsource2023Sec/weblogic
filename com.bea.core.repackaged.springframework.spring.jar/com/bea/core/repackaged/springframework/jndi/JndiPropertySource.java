package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.core.env.PropertySource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.naming.NamingException;

public class JndiPropertySource extends PropertySource {
   public JndiPropertySource(String name) {
      this(name, JndiLocatorDelegate.createDefaultResourceRefLocator());
   }

   public JndiPropertySource(String name, JndiLocatorDelegate jndiLocator) {
      super(name, jndiLocator);
   }

   @Nullable
   public Object getProperty(String name) {
      if (((JndiLocatorDelegate)this.getSource()).isResourceRef() && name.indexOf(58) != -1) {
         return null;
      } else {
         try {
            Object value = ((JndiLocatorDelegate)this.source).lookup(name);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("JNDI lookup for name [" + name + "] returned: [" + value + "]");
            }

            return value;
         } catch (NamingException var3) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("JNDI lookup for name [" + name + "] threw NamingException with message: " + var3.getMessage() + ". Returning null.");
            }

            return null;
         }
      }
   }
}
