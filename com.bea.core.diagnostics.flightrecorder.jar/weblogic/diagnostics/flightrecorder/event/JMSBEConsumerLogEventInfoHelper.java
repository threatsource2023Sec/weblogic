package weblogic.diagnostics.flightrecorder.event;

public final class JMSBEConsumerLogEventInfoHelper {
   public static void populateExtensions(Object returnValue, Object[] args, JMSBEConsumerLogEventInfo target) {
      if (target != null && args != null && args.length >= 2) {
         if (args[0] != null && args[0] instanceof JMSBEConsumerLogEventInfo) {
            JMSBEConsumerLogEventInfo info = (JMSBEConsumerLogEventInfo)args[0];
            target.setConsumer(info.getConsumer());
            target.setSubscription(info.getSubscription());
            target.setDestination(info.getDestination());
         }

         if (args[1] != null) {
            target.setConsumerLifecycle(args[1].toString());
         }

      }
   }
}
