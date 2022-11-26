package weblogic.iiop.messages;

public interface ReplyNotification {
   SequencedMessage getReply();

   void notify(SequencedMessage var1);
}
