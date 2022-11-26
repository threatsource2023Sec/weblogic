package weblogic.diagnostics.flightrecorder.event;

import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

public final class ConnectorEndpointEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, DynamicJoinPoint djp, ConnectorEndpointEventInfo target) {
      if (args != null && args.length == 7) {
         if (args[1] != null) {
            target.setEjbName(args[1].toString());
         }

         if (args[2] != null) {
            target.setJndiName(args[2].toString());
         }

         if (args[3] != null) {
            target.setMessageListener(args[3].toString());
         }

      }
   }
}
