package weblogic.messaging.saf;

public interface SAFErrorAwareTransport extends SAFTransport {
   boolean isPermanentError(Throwable var1);
}
