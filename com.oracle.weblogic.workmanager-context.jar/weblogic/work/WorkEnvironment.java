package weblogic.work;

import javax.naming.Context;
import javax.naming.NamingException;

public abstract class WorkEnvironment {
   private static WorkEnvironment singleton = tryClass("weblogic.work.WLSWorkEnvironmentImpl");

   public static void setWorkEnvironment(WorkEnvironment env) {
      singleton = env;
   }

   public static WorkEnvironment getWorkEnvironment() {
      return singleton;
   }

   private static WorkEnvironment tryClass(String name) {
      try {
         Class klass = Class.forName(name);
         return (WorkEnvironment)klass.newInstance();
      } catch (Throwable var2) {
         return null;
      }
   }

   public static WorkEnvironment getEnvironment() {
      return singleton;
   }

   public abstract void javaURLContextFactoryPopContext();

   public abstract Context javaURLContextFactoryCreateContext() throws NamingException;

   public abstract void javaURLContextFactoryPushContext(Context var1);

   static {
      if (singleton == null) {
         singleton = tryClass("weblogic.work.WLSClientWorkEnvironmentImpl");
      }

   }
}
