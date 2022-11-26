package weblogic.security.spi;

public interface AuditLifecycleEvent extends AuditEvent {
   AuditLifecycleEventType getAuditLifecycleEventType();

   public static class AuditLifecycleEventType {
      private String type;
      public static final AuditLifecycleEventType START_AUDIT = new AuditLifecycleEventType("Start Audit");
      public static final AuditLifecycleEventType STOP_AUDIT = new AuditLifecycleEventType("Stop Audit");

      private AuditLifecycleEventType(String type) {
         this.type = type;
      }

      public String toString() {
         return this.type;
      }
   }
}
