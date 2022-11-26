package weblogic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import weblogic.utils.ArrayUtils;

public class GetMessage {
   public static void main(String[] args) {
      Throwable disaster = null;

      try {
         Class starterClass = Class.forName("weblogic.i18ntools.CatInfo");

         try {
            Class[] paramTypes = new Class[]{String[].class};
            Method mainMethod = starterClass.getMethod("main", paramTypes);

            try {
               ArrayList list = new ArrayList();
               ArrayUtils.addAll(list, args);
               list.add("__name__");
               list.add("weblogic.GetMessage");
               Object[] bugs = list.toArray();
               String[] newargs = new String[bugs.length];
               System.arraycopy(bugs, 0, newargs, 0, bugs.length);
               Object[] params = new Object[]{newargs};
               mainMethod.invoke((Object)null, params);
            } catch (IllegalArgumentException var17) {
               System.err.println(var17.toString());
               var17.printStackTrace();
            } catch (InvocationTargetException var18) {
               System.err.println(var18.toString());
            }
         } catch (NoSuchMethodException var19) {
            System.err.println(var19.toString());
         } catch (SecurityException var20) {
            System.err.println(var20.toString());
         }
      } catch (ClassNotFoundException var21) {
         disaster = var21;
      } catch (IllegalAccessException var22) {
         disaster = var22;
      } finally {
         if (disaster != null) {
            System.err.println(((Throwable)disaster).toString());
         }

      }

   }
}
