package weblogic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MsgLocalizer {
   public static void main(String[] args) {
      Throwable disaster = null;

      try {
         Class starterClass = Class.forName("weblogic.i18ntools.gui.MessageLocalizer");

         try {
            Class[] paramTypes = new Class[]{String[].class};
            Method mainMethod = starterClass.getMethod("main", paramTypes);

            try {
               Object[] params = new Object[]{args};
               mainMethod.invoke((Object)null, params);
            } catch (IllegalArgumentException var14) {
               System.err.println(var14.toString());
            } catch (InvocationTargetException var15) {
               System.err.println(var15.toString());
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
