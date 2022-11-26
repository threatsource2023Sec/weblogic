package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiLocatorDelegate extends JndiLocatorSupport {
   public static final String IGNORE_JNDI_PROPERTY_NAME = "spring.jndi.ignore";
   private static final boolean shouldIgnoreDefaultJndiEnvironment = SpringProperties.getFlag("spring.jndi.ignore");

   public Object lookup(String jndiName) throws NamingException {
      return super.lookup(jndiName);
   }

   public Object lookup(String jndiName, @Nullable Class requiredType) throws NamingException {
      return super.lookup(jndiName, requiredType);
   }

   public static JndiLocatorDelegate createDefaultResourceRefLocator() {
      JndiLocatorDelegate jndiLocator = new JndiLocatorDelegate();
      jndiLocator.setResourceRef(true);
      return jndiLocator;
   }

   public static boolean isDefaultJndiEnvironmentAvailable() {
      if (shouldIgnoreDefaultJndiEnvironment) {
         return false;
      } else {
         try {
            (new InitialContext()).getEnvironment();
            return true;
         } catch (Throwable var1) {
            return false;
         }
      }
   }
}
