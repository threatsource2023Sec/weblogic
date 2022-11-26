package weblogic.deployment.jms;

import javax.jms.IllegalStateException;
import javax.jms.IllegalStateRuntimeException;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import weblogic.logging.Loggable;

public class JMSExceptions {
   public static JMSException getJMSException(Loggable logMessage) {
      return new JMSException(constructMessage(logMessage, (Throwable)null), logMessage.getId());
   }

   public static JMSException getJMSException(Loggable logMessage, Exception linked) {
      JMSException jmse = new JMSException(constructMessage(logMessage, linked), logMessage.getId());
      jmse.setLinkedException(linked);
      return jmse;
   }

   public static IllegalStateException getIllegalStateException(Loggable logMessage) {
      return new IllegalStateException(constructMessage(logMessage, (Throwable)null), logMessage.getId());
   }

   public static IllegalStateException getIllegalStateException(Loggable logMessage, Exception linked) {
      IllegalStateException jmse = new IllegalStateException(constructMessage(logMessage, linked), logMessage.getId());
      jmse.setLinkedException(linked);
      return jmse;
   }

   public static JMSRuntimeException getJMSRuntimeException(Loggable logMessage) {
      return new JMSRuntimeException(constructMessage(logMessage, (Throwable)null), logMessage.getId());
   }

   public static JMSRuntimeException getJMSRuntimeException(Loggable logMessage, Throwable linked) {
      JMSRuntimeException jmse = new JMSRuntimeException(constructMessage(logMessage, linked), logMessage.getId());
      jmse.initCause(linked);
      return jmse;
   }

   public static IllegalStateRuntimeException getIllegalStateRuntimeException(Loggable logMessage) {
      return new IllegalStateRuntimeException(constructMessage(logMessage, (Throwable)null), logMessage.getId());
   }

   public static IllegalStateRuntimeException getIllegalStateRuntimeException(Loggable logMessage, Exception linked) {
      IllegalStateRuntimeException jmse = new IllegalStateRuntimeException(constructMessage(logMessage, linked), logMessage.getId());
      jmse.initCause(linked);
      return jmse;
   }

   private static String constructMessage(Loggable logMessage, Throwable linked) {
      return linked != null ? logMessage.getMessage() + ": " + linked.toString() : logMessage.getMessage();
   }
}
