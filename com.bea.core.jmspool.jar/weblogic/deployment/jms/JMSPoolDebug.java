package weblogic.deployment.jms;

import javax.jms.JMSException;
import weblogic.diagnostics.debug.DebugLogger;

public class JMSPoolDebug {
   static final DebugLogger logger = DebugLogger.getDebugLogger("DebugJMSWrappers");

   public static String getWholeJMSException(JMSException jmse) {
      return jmse.getLinkedException() != null ? jmse.toString() + ". Linked exception: " + jmse.getLinkedException() : jmse.toString();
   }

   public static String getWholeJMSRuntimeException(JMSException jmsre) {
      return jmsre.getLinkedException() != null ? jmsre.toString() + ". Linked exception: " + jmsre.getLinkedException() : jmsre.toString();
   }
}
