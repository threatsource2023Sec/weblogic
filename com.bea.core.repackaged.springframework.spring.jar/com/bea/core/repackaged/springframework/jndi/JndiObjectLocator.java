package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import javax.naming.NamingException;

public abstract class JndiObjectLocator extends JndiLocatorSupport implements InitializingBean {
   @Nullable
   private String jndiName;
   @Nullable
   private Class expectedType;

   public void setJndiName(@Nullable String jndiName) {
      this.jndiName = jndiName;
   }

   @Nullable
   public String getJndiName() {
      return this.jndiName;
   }

   public void setExpectedType(@Nullable Class expectedType) {
      this.expectedType = expectedType;
   }

   @Nullable
   public Class getExpectedType() {
      return this.expectedType;
   }

   public void afterPropertiesSet() throws IllegalArgumentException, NamingException {
      if (!StringUtils.hasLength(this.getJndiName())) {
         throw new IllegalArgumentException("Property 'jndiName' is required");
      }
   }

   protected Object lookup() throws NamingException {
      String jndiName = this.getJndiName();
      Assert.state(jndiName != null, "No JNDI name specified");
      return this.lookup(jndiName, this.getExpectedType());
   }
}
