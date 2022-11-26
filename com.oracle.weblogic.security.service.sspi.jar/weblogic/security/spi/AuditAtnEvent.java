package weblogic.security.spi;

/** @deprecated */
@Deprecated
public interface AuditAtnEvent extends AuditEvent {
   String getUsername();

   AtnEventType getAtnEventType();

   public static class AtnEventType {
      private String type;
      public static final AtnEventType AUTHENTICATE = new AtnEventType("AUTHENTICATE");
      public static final AtnEventType ASSERTIDENTITY = new AtnEventType("ASSERTIDENTITY");
      public static final AtnEventType IMPERSONATEIDENTITY = new AtnEventType("IMPERSONATEIDENTITY");
      public static final AtnEventType VALIDATEIDENTITY = new AtnEventType("VALIDATEIDENTITY");
      public static final AtnEventType USERLOCKED = new AtnEventType("USERLOCKED");
      public static final AtnEventType USERUNLOCKED = new AtnEventType("USERUNLOCKED");
      public static final AtnEventType USERLOCKOUTEXPIRED = new AtnEventType("USERLOCKOUTEXPIRED");

      private AtnEventType(String type) {
         this.type = type;
      }

      public String toString() {
         return this.type;
      }
   }
}
