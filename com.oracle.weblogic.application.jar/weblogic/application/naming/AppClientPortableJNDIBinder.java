package weblogic.application.naming;

import javax.naming.NamingException;

public class AppClientPortableJNDIBinder extends GeneralPortableJNDIBinder {
   AppClientPortableJNDIBinder(Environment env) {
      super(env);
   }

   public void bind(String name, Object value) throws NamingException {
      if (!this.shouldIgnore(name)) {
         super.bind(name, value);
      }
   }

   public void unbind(String name) throws NamingException {
      if (!this.shouldIgnore(name)) {
         super.unbind(name);
      }
   }

   public Object lookup(String name) throws NamingException {
      return this.shouldIgnore(name) ? null : super.lookup(name);
   }

   private boolean shouldIgnore(String name) {
      return name.startsWith("java:comp") || name.startsWith("java:module") || !name.startsWith("java:");
   }
}
