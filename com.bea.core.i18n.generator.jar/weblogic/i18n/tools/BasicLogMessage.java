package weblogic.i18n.tools;

public abstract class BasicLogMessage extends BasicMessage {
   protected MessageDetail messageDetail;
   protected Cause cause;
   protected Action action;

   public void addMessageDetail(MessageDetail detail) {
      this.messageDetail = detail;
   }

   public MessageDetail getMessageDetail() {
      return this.messageDetail;
   }

   public void addCause(Cause cause) {
      this.cause = cause;
   }

   public Cause getCause() {
      return this.cause;
   }

   public void addAction(Action action) {
      this.action = action;
   }

   public Action getAction() {
      return this.action;
   }
}
