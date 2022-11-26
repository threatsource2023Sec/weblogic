package weblogic.diagnostics.flightrecorder.event;

public final class ServletEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, ServletEventInfo target) {
      if (target != null && args != null && args.length != 0) {
         if (target.getUri() == null || !target.hasServletName() && target.getServletName() == null) {
            Object[] var3 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Object arg = var3[var5];
               if (arg != null && arg instanceof ServletEventInfo) {
                  setFromValue((ServletEventInfo)arg, target);
                  if (target.getUri() != null && target.getServletName() != null) {
                     return;
                  }
               }
            }

         }
      }
   }

   private static void setFromValue(ServletEventInfo input, ServletEventInfo target) {
      if (target.getUri() == null) {
         target.setUri(input.getUri());
      }

      if (input.hasServletName() && target.getServletName() == null) {
         target.setServletName(input.getServletName());
      }

   }
}
