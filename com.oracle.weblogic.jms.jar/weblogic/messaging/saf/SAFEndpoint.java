package weblogic.messaging.saf;

public interface SAFEndpoint {
   void deliver(SAFConversationInfo var1, SAFRequest var2) throws SAFException;

   boolean isAvailable();

   String getTargetQueue();
}
