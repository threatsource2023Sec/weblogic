package weblogic.tools.ui.progress;

public class ProgressEvent {
   private String message;
   private int messageType;
   private int percentageComplete;
   public static final int WARN = 0;
   public static final int NORM = 1;
   public static final int ERR = 2;
   public static final int DONE_SUCCESS = 3;
   public static final int DONE_FAILURE = 4;

   public ProgressEvent() {
      this.messageType = 1;
   }

   public ProgressEvent(String message, int messageType) {
      this.message = message;
      this.messageType = messageType;
   }

   public String getMessage() {
      return this.message;
   }

   public int getMessageType() {
      return this.messageType;
   }

   public int getPercentageComplete() {
      return this.percentageComplete;
   }

   public void setEventInfo(String message, int messageType) {
      this.messageType = messageType;
      this.message = message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public void setPercentageComplete(int value) {
      this.percentageComplete = value;
   }
}
