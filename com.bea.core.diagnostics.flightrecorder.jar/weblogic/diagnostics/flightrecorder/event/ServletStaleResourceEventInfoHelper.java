package weblogic.diagnostics.flightrecorder.event;

public final class ServletStaleResourceEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, ServletStaleResourceEventInfo target) {
      if (target != null) {
         if (returnValue != null && returnValue instanceof Boolean) {
            if (!(Boolean)returnValue) {
               target.setThrottled(true);
            }

         } else if (args != null && args.length != 0) {
            target.setResource(args[0] == null ? null : args[0].toString());
         }
      }
   }
}
