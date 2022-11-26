package weblogic.diagnostics.flightrecorder.event;

import weblogic.diagnostics.instrumentation.DynamicJoinPoint;
import weblogic.diagnostics.instrumentation.GatheredArgument;

public final class ConnectorEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, DynamicJoinPoint djp, ConnectorEventInfo target) {
      if (args != null) {
         GatheredArgument[] gArgs = djp.getGatheredArguments();
         if (gArgs != null && gArgs.length == 1 && gArgs[0] != null) {
            int index = gArgs[0].getArgumentIndex();
            if (index < args.length && args[index] != null) {
               target.setPool(args[index].toString());
            }

         }
      }
   }
}
