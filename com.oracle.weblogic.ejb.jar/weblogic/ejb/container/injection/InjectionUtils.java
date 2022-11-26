package weblogic.ejb.container.injection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class InjectionUtils {
   public static boolean isMethodOverridden(Class subClass, Method methodToCheck) {
      if (Modifier.isPrivate(methodToCheck.getModifiers())) {
         return false;
      } else {
         Class methodClass = methodToCheck.getDeclaringClass();

         try {
            return !subClass.getDeclaredMethod(methodToCheck.getName(), methodToCheck.getParameterTypes()).getDeclaringClass().equals(methodClass);
         } catch (NoSuchMethodException var4) {
            return false;
         }
      }
   }
}
