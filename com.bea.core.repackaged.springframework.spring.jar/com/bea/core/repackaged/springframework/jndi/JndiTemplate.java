package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

public class JndiTemplate {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Properties environment;

   public JndiTemplate() {
   }

   public JndiTemplate(@Nullable Properties environment) {
      this.environment = environment;
   }

   public void setEnvironment(@Nullable Properties environment) {
      this.environment = environment;
   }

   @Nullable
   public Properties getEnvironment() {
      return this.environment;
   }

   @Nullable
   public Object execute(JndiCallback contextCallback) throws NamingException {
      Context ctx = this.getContext();

      Object var3;
      try {
         var3 = contextCallback.doInContext(ctx);
      } finally {
         this.releaseContext(ctx);
      }

      return var3;
   }

   public Context getContext() throws NamingException {
      return this.createInitialContext();
   }

   public void releaseContext(@Nullable Context ctx) {
      if (ctx != null) {
         try {
            ctx.close();
         } catch (NamingException var3) {
            this.logger.debug("Could not close JNDI InitialContext", var3);
         }
      }

   }

   protected Context createInitialContext() throws NamingException {
      Hashtable icEnv = null;
      Properties env = this.getEnvironment();
      if (env != null) {
         icEnv = new Hashtable(env.size());
         CollectionUtils.mergePropertiesIntoMap(env, icEnv);
      }

      return new InitialContext(icEnv);
   }

   public Object lookup(String name) throws NamingException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Looking up JNDI object with name [" + name + "]");
      }

      Object result = this.execute((ctx) -> {
         return ctx.lookup(name);
      });
      if (result == null) {
         throw new NameNotFoundException("JNDI object with [" + name + "] not found: JNDI implementation returned null");
      } else {
         return result;
      }
   }

   public Object lookup(String name, @Nullable Class requiredType) throws NamingException {
      Object jndiObject = this.lookup(name);
      if (requiredType != null && !requiredType.isInstance(jndiObject)) {
         throw new TypeMismatchNamingException(name, requiredType, jndiObject.getClass());
      } else {
         return jndiObject;
      }
   }

   public void bind(String name, Object object) throws NamingException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Binding JNDI object with name [" + name + "]");
      }

      this.execute((ctx) -> {
         ctx.bind(name, object);
         return null;
      });
   }

   public void rebind(String name, Object object) throws NamingException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Rebinding JNDI object with name [" + name + "]");
      }

      this.execute((ctx) -> {
         ctx.rebind(name, object);
         return null;
      });
   }

   public void unbind(String name) throws NamingException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Unbinding JNDI object with name [" + name + "]");
      }

      this.execute((ctx) -> {
         ctx.unbind(name);
         return null;
      });
   }
}
