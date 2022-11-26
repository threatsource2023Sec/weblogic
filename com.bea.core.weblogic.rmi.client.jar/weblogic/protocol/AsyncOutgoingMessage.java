package weblogic.protocol;

public interface AsyncOutgoingMessage extends OutgoingMessage {
   void enqueue();

   void cleanup();
}
