package weblogic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlanGenerator {
   public static void main(String[] args) throws Throwable {
      Throwable disaster = null;

      try {
         Class starterClass = Class.forName("weblogic.deploy.api.tools.PlanGenerator");

         try {
            Class[] paramTypes = new Class[]{String[].class};
            Method mainMethod = starterClass.getMethod("main", paramTypes);

            try {
               Object[] params = new Object[]{args};
               mainMethod.invoke((Object)null, params);
            } catch (IllegalArgumentException var14) {
               System.err.println(var14.toString());
            } catch (InvocationTargetException var15) {
               if (var15.getCause() != null) {
                  throw var15.getCause();
               }

               throw var15;
            }
         } catch (NoSuchMethodException var16) {
            System.err.println(var16.toString());
         } catch (SecurityException var17) {
            System.err.println(var17.toString());
         }
      } catch (ClassNotFoundException var18) {
         disaster = var18;
      } catch (IllegalAccessException var19) {
         disaster = var19;
      } finally {
         if (disaster != null) {
            System.err.println(((Throwable)disaster).toString());
         }

      }

   }
}
