package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.naming.NamingException;

public abstract class JndiLocatorSupport extends JndiAccessor {
   public static final String CONTAINER_PREFIX = "java:comp/env/";
   private boolean resourceRef = false;

   public void setResourceRef(boolean resourceRef) {
      this.resourceRef = resourceRef;
   }

   public boolean isResourceRef() {
      return this.resourceRef;
   }

   protected Object lookup(String jndiName) throws NamingException {
      return this.lookup(jndiName, (Class)null);
   }

   protected Object lookup(String jndiName, @Nullable Class requiredType) throws NamingException {
      Assert.notNull(jndiName, (String)"'jndiName' must not be null");
      String convertedName = this.convertJndiName(jndiName);

      Object jndiObject;
      try {
         jndiObject = this.getJndiTemplate().lookup(convertedName, requiredType);
      } catch (NamingException var6) {
         if (convertedName.equals(jndiName)) {
            throw var6;
         }

         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Converted JNDI name [" + convertedName + "] not found - trying original name [" + jndiName + "]. " + var6);
         }

         jndiObject = this.getJndiTemplate().lookup(jndiName, requiredType);
      }

      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Located object with JNDI name [" + convertedName + "]");
      }

      return jndiObject;
   }

   protected String convertJndiName(String jndiName) {
      if (this.isResourceRef() && !jndiName.startsWith("java:comp/env/") && jndiName.indexOf(58) == -1) {
         jndiName = "java:comp/env/" + jndiName;
      }

      return jndiName;
   }
}
