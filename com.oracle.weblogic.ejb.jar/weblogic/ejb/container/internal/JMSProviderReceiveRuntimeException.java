package weblogic.ejb.container.internal;

public class JMSProviderReceiveRuntimeException extends Exception {
   public JMSProviderReceiveRuntimeException(String msg) {
      super(msg);
   }

   public JMSProviderReceiveRuntimeException(String msg, Throwable th) {
      super(msg, th);
   }
}
