package weblogic.common;

public final class DisconnectEvent {
   Object source;
   String reason;

   DisconnectEvent(Object source, String reason) {
      this.source = source;
      this.reason = reason;
   }

   public Object getSource() {
      return this.source;
   }

   public String getReason() {
      return this.reason;
   }

   public String toString() {
      return this.getClass().getName() + "[source=" + this.source + ", reason=" + this.reason + "]";
   }
}
