package weblogic.ejb.spi;

import java.lang.reflect.Method;

public final class MethodUtils {
   private MethodUtils() {
   }

   public static String getWSOPreInvokeMethodName(Method method) {
      return "__WL_" + method.getName() + "_WS_preInvoke";
   }

   public static String getWSOBusinessMethodName(Method method) {
      return "__WL_" + method.getName() + "_WS";
   }

   public static String getWSOPostInvokeMethodName() {
      return "__WL__WS_postInvoke";
   }
}
