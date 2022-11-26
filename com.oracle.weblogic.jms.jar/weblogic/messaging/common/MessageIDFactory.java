package weblogic.messaging.common;

public final class MessageIDFactory {
   private int seed = MessagingUtilities.getSeed();
   private long timestamp = System.currentTimeMillis();
   private int counter;

   final void initMessageId(MessageIDImpl messageID) {
      long idMessageTimestamp;
      int idCounter;
      synchronized(this) {
         idMessageTimestamp = System.currentTimeMillis();
         if (idMessageTimestamp == this.timestamp) {
            idCounter = ++this.counter;
         } else {
            this.timestamp = idMessageTimestamp;
            idCounter = this.counter = 0;
         }
      }

      messageID.init(this.seed, idMessageTimestamp, idCounter);
   }
}
