package weblogic.application.naming;

import javax.naming.Context;
import javax.naming.NamingException;

public class GeneralPortableJNDIBinder implements PortableJNDIBinder {
   private Context globalContext;
   private Context appContext;
   private Context moduleContext;
   private Context compContext;
   private Context compEnvContext;

   GeneralPortableJNDIBinder(Context globalContext, Context appContext, Context moduleContext, Context compContext) {
      this.globalContext = globalContext;
      this.appContext = appContext;
      this.moduleContext = moduleContext;
      this.compContext = compContext;
      this.compEnvContext = this.findCompEnvContext();
   }

   GeneralPortableJNDIBinder(Environment environment) {
      this.globalContext = environment.getGlobalNSContext();
      this.appContext = environment.getAppNSContext();
      this.moduleContext = environment.getModuleNSContext();
      this.compContext = environment.getCompContext();
      this.compEnvContext = environment.getCompEnvContext();
   }

   public void bind(String name, Object value) throws NamingException {
      this.validatePortableJndiName(name);
      this.bindWithPortableJNDIName(name, value);
   }

   public void unbind(String name) throws NamingException {
      this.unbindWithPortableJNDIName(name);
   }

   public Object lookup(String name) throws NamingException {
      return this.lookupWithPortableJNDIName(name);
   }

   private void bindWithPortableJNDIName(String name, Object value) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.bind(name.substring("java:comp".length() + 1), value);
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.bind(name.substring("java:module".length() + 1), value);
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.bind(name.substring("java:app".length() + 1), value);
      } else if (this.isValidJavaGlobalName(name)) {
         this.globalContext.bind(name.substring("java:global".length() + 1), value);
      } else if (this.compEnvContext != null) {
         this.compEnvContext.bind(name, value);
      }

   }

   private void unbindWithPortableJNDIName(String name) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.unbind(name.substring("java:comp".length() + 1));
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.unbind(name.substring("java:module".length() + 1));
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.unbind(name.substring("java:app".length() + 1));
      } else if (this.isValidJavaGlobalName(name)) {
         this.globalContext.unbind(name.substring("java:global".length() + 1));
      } else if (this.compEnvContext != null) {
         this.compEnvContext.unbind(name);
      }

   }

   private Object lookupWithPortableJNDIName(String name) throws NamingException {
      Object result = null;
      if (this.isValidJavaCompName(name)) {
         result = this.compContext.lookup(name.substring("java:comp".length() + 1));
      } else if (this.isValidJavaModuleName(name)) {
         result = this.moduleContext.lookup(name.substring("java:module".length() + 1));
      } else if (this.isValidJavaAppName(name)) {
         result = this.appContext.lookup(name.substring("java:app".length() + 1));
      } else if (this.isValidJavaGlobalName(name)) {
         result = this.globalContext.lookup(name.substring("java:global".length() + 1));
      } else if (this.compEnvContext != null) {
         result = this.compEnvContext.lookup(name);
      }

      return result;
   }

   private void validatePortableJndiName(String name) throws NamingException {
      if (this.isInvalidJavaGlobalName(name) || this.isInvalidJavaAppName(name) || this.isInvalidJavaModuleName(name) || this.isInvalidJavaCompName(name) || this.isInvalidJavaCompEnvName(name)) {
         throw new NamingException(name + " is not valid for the current context");
      }
   }

   private boolean isInvalidJavaCompName(String name) {
      return name.startsWith("java:comp") && this.compContext == null;
   }

   private boolean isInvalidJavaCompEnvName(String name) {
      return !name.startsWith("java:") && this.compContext == null;
   }

   private boolean isInvalidJavaModuleName(String name) {
      return name.startsWith("java:module") && this.moduleContext == null;
   }

   private boolean isInvalidJavaAppName(String name) {
      return name.startsWith("java:app") && this.appContext == null;
   }

   private boolean isInvalidJavaGlobalName(String name) {
      return name.startsWith("java:global") && this.globalContext == null;
   }

   private boolean isValidJavaGlobalName(String name) {
      return name.startsWith("java:global") && this.globalContext != null;
   }

   private boolean isValidJavaAppName(String name) {
      return name.startsWith("java:app") && this.appContext != null;
   }

   private boolean isValidJavaModuleName(String name) {
      return name.startsWith("java:module") && this.moduleContext != null;
   }

   private boolean isValidJavaCompName(String name) {
      return name.startsWith("java:comp") && this.compContext != null;
   }

   private Context findCompEnvContext() {
      if (this.compContext == null) {
         return null;
      } else {
         try {
            return (Context)this.compContext.lookup("env");
         } catch (NamingException var2) {
            return null;
         }
      }
   }
}
