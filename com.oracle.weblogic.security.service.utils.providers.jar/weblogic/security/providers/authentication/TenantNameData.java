package weblogic.security.providers.authentication;

public class TenantNameData {
   private final String userName;
   private final String tenant;

   public TenantNameData(String userName, String tenant) {
      if (userName != null && !userName.isEmpty()) {
         this.userName = userName;
         this.tenant = tenant;
      } else {
         throw new IllegalArgumentException("user name must not be null or empty");
      }
   }

   public String getUserName() {
      return this.userName;
   }

   public String getTenant() {
      return this.tenant;
   }
}
