package com.oracle.pitchfork.spi;

import com.oracle.pitchfork.intercept.InterceptionMetadata;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import javax.naming.Context;
import javax.naming.NamingException;

public class ManagedBeanProxyMetadata extends InterceptionMetadata {
   private Context context;

   ManagedBeanProxyMetadata(DeploymentUnitMetadataI dum, String name, Class componentClass, boolean usesSpringExtensionModel) {
      super(dum, name, componentClass, usesSpringExtensionModel);
   }

   protected boolean supportsTargetClassProxying() {
      return true;
   }

   protected Object jndiLookup(String jndiName) throws NamingException {
      if (this.context != null) {
         if (jndiName.startsWith("java:")) {
            jndiName = jndiName.substring(5);
         }

         return this.context.lookup(jndiName);
      } else {
         return super.jndiLookup(jndiName);
      }
   }

   public void setNamingContext(Context context) {
      this.context = context;
   }
}
