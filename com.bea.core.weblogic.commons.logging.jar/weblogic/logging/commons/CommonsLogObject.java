package weblogic.logging.commons;

public final class CommonsLogObject implements Constants {
   private String messageId;
   private String subsystemId;
   private String message;
   private String messageIdPrefix;

   public CommonsLogObject(String msgId, String subsystem, String msg) {
      this(msgId, "", subsystem, msg);
   }

   public CommonsLogObject(String msgId, String prefix, String subsystem, String msg) {
      this.messageId = null;
      this.subsystemId = null;
      this.message = null;
      this.messageIdPrefix = null;
      this.messageId = msgId;
      this.messageIdPrefix = prefix;
      this.subsystemId = subsystem;
      this.message = msg;
   }

   public String getMessageId() {
      return this.messageId;
   }

   public String getMessageIdPrefix() {
      return this.messageIdPrefix;
   }

   public String getSubsystemId() {
      return this.subsystemId;
   }

   public String getMessage() {
      return this.message;
   }

   public String toString() {
      return this.getMessage();
   }
}
