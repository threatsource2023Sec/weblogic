package weblogic.diagnostics.flightrecorder.event;

public final class WebApplicationEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, WebApplicationEventInfo target) {
      if (target != null && args != null && args.length != 0) {
         Object[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object arg = var3[var5];
            if (arg != null && arg instanceof WebApplicationEventInfo) {
               target.setModuleName(((WebApplicationEventInfo)arg).getModuleName());
               if (target.getModuleName() != null) {
                  return;
               }
            }
         }

      }
   }
}
