package weblogic.diagnostics.flightrecorder.event;

import java.util.ArrayList;

public final class WebservicesJAXRPCEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, WebservicesJAXRPCEventInfo target, boolean isAfter) {
      if (target != null) {
         if (returnValue != null && returnValue instanceof WebservicesJAXRPCEventInfo) {
            setFromValue((WebservicesJAXRPCEventInfo)returnValue, target);
         }

         if (target.getCurrentParty() == null || target.getAction() == null) {
            Object[] dpargs = args;
            ArrayList deferredArguments = target.getDeferredArguments();
            if (isAfter && deferredArguments != null) {
               dpargs = deferredArguments.toArray();
            }

            if (dpargs != null && dpargs.length != 0) {
               Object[] var6 = dpargs;
               int var7 = dpargs.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Object arg = var6[var8];
                  if (arg != null && arg instanceof WebservicesJAXRPCEventInfo) {
                     WebservicesJAXRPCEventInfo info = (WebservicesJAXRPCEventInfo)arg;
                     if (info.isSending() && !isAfter) {
                        if (deferredArguments == null) {
                           deferredArguments = new ArrayList();
                        }

                        deferredArguments.add(info);
                        target.setDeferredArguments(deferredArguments);
                     } else {
                        setFromValue(info, target);
                        if (target.getCurrentParty() != null && target.getAction() != null) {
                           return;
                        }
                     }
                  }
               }

            }
         }
      }
   }

   private static void setFromValue(WebservicesJAXRPCEventInfo input, WebservicesJAXRPCEventInfo target) {
      if (target.getAction() == null) {
         target.setAction(input.getAction());
      }

      if (target.getCurrentParty() == null) {
         target.setCurrentParty(input.getCurrentParty());
      }

   }
}
