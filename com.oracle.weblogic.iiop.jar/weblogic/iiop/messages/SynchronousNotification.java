package weblogic.iiop.messages;

public class SynchronousNotification implements ReplyNotification {
   private SequencedMessage reply;
   private SequencedRequestMessage owner;

   public SynchronousNotification(SequencedRequestMessage owner) {
      this.owner = owner;
   }

   public SequencedMessage getReply() {
      return this.reply;
   }

   public void notify(SequencedMessage reply) {
      this.reply = reply;
      this.owner.notify();
   }
}
