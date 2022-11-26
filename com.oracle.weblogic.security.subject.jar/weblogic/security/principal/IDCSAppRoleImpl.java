package weblogic.security.principal;

public final class IDCSAppRoleImpl extends WLSAbstractPrincipal implements IDCSAppRole {
   private static final long serialVersionUID = 1L;
   private String appName;
   private String appId = null;

   public IDCSAppRoleImpl(String appRoleName, String id, String ref, String appName, String appId) {
      this.setName(appRoleName);
      this.setDn(ref);
      this.setGuid(id);
      this.appName = appName;
      this.appId = appId;
   }

   public IDCSAppRoleImpl(String appRoleName, String appName) {
      this.setName(appRoleName);
      this.appName = appName;
   }

   public byte[] getSignedData() {
      byte[] base = super.getSignedData();
      String decoratedName = "";
      if (this.appName != null && !this.appName.isEmpty()) {
         decoratedName = decoratedName + "::" + this.appName;
      }

      if (this.isEqualsCompareDnAndGuid() && this.appId != null && !this.appId.isEmpty()) {
         decoratedName = decoratedName + "::" + this.appId;
      }

      if (decoratedName.isEmpty()) {
         return base;
      } else {
         byte[] added = decoratedName.getBytes();
         byte[] combined = new byte[base.length + added.length];
         System.arraycopy(base, 0, combined, 0, base.length);
         System.arraycopy(added, 0, combined, base.length, added.length);
         return combined;
      }
   }

   public int hashCode() {
      int h = super.hashCode();
      if (this.appName != null && !this.appName.isEmpty()) {
         h = h * 31 + this.appName.hashCode();
      }

      return h;
   }

   public boolean equals(Object another) {
      if (another == null) {
         return false;
      } else if (this == another) {
         return true;
      } else if (this.getClass() != another.getClass()) {
         return false;
      } else if (!super.equals(another)) {
         return false;
      } else {
         IDCSAppRoleImpl anotherPrincipal = (IDCSAppRoleImpl)another;
         if ((this.appName == null || anotherPrincipal.appName == null) && this.appName != anotherPrincipal.appName) {
            return false;
         } else if (this.appName != null && !this.appName.equals(anotherPrincipal.appName)) {
            return false;
         } else {
            if (this.isEqualsCompareDnAndGuid()) {
               if ((this.appId == null || anotherPrincipal.appId == null) && this.appId != anotherPrincipal.appId) {
                  return false;
               }

               if (this.appId != null && !this.appId.equals(anotherPrincipal.appId)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public String toString() {
      String str = super.toString();
      if (this.appName != null) {
         str = str + " [" + this.appName + "]";
      }

      return str;
   }

   public String getId() {
      return this.getGuid();
   }

   public String getReference() {
      return this.getDn();
   }

   public String getAppName() {
      return this.appName;
   }

   public String getAppId() {
      return this.appId;
   }
}
