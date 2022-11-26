package weblogic.diagnostics.flightrecorder.event;

import java.lang.reflect.Method;

public final class EJBEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, EJBEventInfo target) {
      if (target != null) {
         if (returnValue != null && returnValue instanceof EJBEventInfo) {
            setFromValue((EJBEventInfo)returnValue, target);
         } else if (args != null && args.length != 0) {
            if (args[0] instanceof String) {
               target.setEjbName((String)args[0]);
               if (args.length > 1) {
                  for(int i = 1; i < args.length; ++i) {
                     if (args[i] == null || args[i] instanceof Method) {
                        target.setEjbMethodName(args[i].toString());
                        return;
                     }
                  }
               }
            }

            Object[] var7 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Object arg = var7[var5];
               if (arg != null && arg instanceof EJBEventInfo) {
                  setFromValue((EJBEventInfo)arg, target);
                  return;
               }
            }

         }
      }
   }

   private static void setFromValue(EJBEventInfo input, EJBEventInfo target) {
      target.setApplicationName(input.getApplicationName());
      target.setComponentName(input.getComponentName());
      target.setEjbName(input.getEjbName());
      target.setEjbMethodName(input.getEjbMethodName());
   }
}
