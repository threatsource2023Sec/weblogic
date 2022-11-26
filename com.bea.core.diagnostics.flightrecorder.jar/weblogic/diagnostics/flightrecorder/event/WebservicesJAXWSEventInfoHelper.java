package weblogic.diagnostics.flightrecorder.event;

public final class WebservicesJAXWSEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, WebservicesJAXWSEventInfo target) {
      if (target != null) {
         if (returnValue != null && returnValue instanceof WebservicesJAXWSEventInfo) {
            setFromValue((WebservicesJAXWSEventInfo)returnValue, target);
         }

         if (target.getUri() == null) {
            if (args != null && args.length != 0) {
               Object[] var3 = args;
               int var4 = args.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Object arg = var3[var5];
                  if (arg != null && arg instanceof WebservicesJAXWSEventInfo) {
                     setFromValue((WebservicesJAXWSEventInfo)arg, target);
                     if (target.getUri() != null) {
                        return;
                     }
                  }
               }

            }
         }
      }
   }

   private static void setFromValue(WebservicesJAXWSEventInfo input, WebservicesJAXWSEventInfo target) {
      if (target.getUri() == null) {
         target.setUri(input.getUri());
      }

   }
}
