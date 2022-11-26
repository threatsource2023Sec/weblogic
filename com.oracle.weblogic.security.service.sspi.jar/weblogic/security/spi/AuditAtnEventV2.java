package weblogic.security.spi;

public interface AuditAtnEventV2 extends AuditEvent, AuditContext {
   String getUsername();

   AtnEventTypeV2 getAtnEventType();

   public static class AtnEventTypeV2 {
      private String type;
      public static final AtnEventTypeV2 AUTHENTICATE = new AtnEventTypeV2("AUTHENTICATE");
      public static final AtnEventTypeV2 ASSERTIDENTITY = new AtnEventTypeV2("ASSERTIDENTITY");
      public static final AtnEventTypeV2 IMPERSONATEIDENTITY = new AtnEventTypeV2("IMPERSONATEIDENTITY");
      public static final AtnEventTypeV2 VALIDATEIDENTITY = new AtnEventTypeV2("VALIDATEIDENTITY");
      public static final AtnEventTypeV2 USERLOCKED = new AtnEventTypeV2("USERLOCKED");
      public static final AtnEventTypeV2 USERUNLOCKED = new AtnEventTypeV2("USERUNLOCKED");
      public static final AtnEventTypeV2 USERLOCKOUTEXPIRED = new AtnEventTypeV2("USERLOCKOUTEXPIRED");
      public static final AtnEventTypeV2 CREATEPASSWORDDIGEST = new AtnEventTypeV2("CREATEPASSWORDDIGEST");
      public static final AtnEventTypeV2 CREATEDERIVEDKEY = new AtnEventTypeV2("CREATEDERIVEDKEY");

      private AtnEventTypeV2(String type) {
         this.type = type;
      }

      public String toString() {
         return this.type;
      }
   }
}
