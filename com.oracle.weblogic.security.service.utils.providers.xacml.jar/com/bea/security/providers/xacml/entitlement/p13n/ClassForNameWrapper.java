package com.bea.security.providers.xacml.entitlement.p13n;

import com.bea.security.xacml.entitlement.ClassForName;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class ClassForNameWrapper {
   private static final String P13N_CLASSFORNAME_WRAPPER_CLASS = "com.bea.p13n.entitlements.rules.internal.ClassForNameWrapper";

   private ClassForName getFactory() {
      ClassForName factory = null;

      try {
         PrivilegedAction getThreadCCLAction = new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         };
         ClassLoader threadCCL = (ClassLoader)AccessController.doPrivileged(getThreadCCLAction);
         factory = (ClassForName)Class.forName("com.bea.p13n.entitlements.rules.internal.ClassForNameWrapper", true, threadCCL).newInstance();
      } catch (ClassNotFoundException var4) {
      } catch (InstantiationException var5) {
      } catch (IllegalAccessException var6) {
      } catch (NoClassDefFoundError var7) {
      }

      return factory;
   }

   public boolean isPortal() {
      return this.getFactory() != null;
   }

   public Class forName(String cname) {
      ClassForName factory = this.getFactory();
      if (factory != null) {
         try {
            return factory.forName(cname);
         } catch (IllegalStateException var4) {
         }
      }

      return null;
   }
}
