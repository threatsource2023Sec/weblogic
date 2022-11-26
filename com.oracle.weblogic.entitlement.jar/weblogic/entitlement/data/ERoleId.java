package weblogic.entitlement.data;

public class ERoleId {
   public static final String GLOBAL_RESOURCE = "";
   private String mResourceName;
   private String mRoleName;

   public ERoleId(String roleName) {
      this("", roleName);
   }

   public ERoleId(String resourceName, String roleName) {
      if (resourceName == null || resourceName.length() == 0) {
         resourceName = "";
      }

      this.mResourceName = resourceName;
      if (roleName == null) {
         throw new NullPointerException("null role name");
      } else {
         this.mRoleName = roleName;
      }
   }

   public final String getResourceName() {
      return this.mResourceName;
   }

   public final String getRoleName() {
      return this.mRoleName;
   }

   public final boolean isGlobal() {
      return this.mResourceName == "";
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof ERoleId)) {
         return false;
      } else {
         ERoleId e = (ERoleId)obj;
         return e.getRoleName().equals(this.mRoleName) && e.getResourceName().equals(this.mResourceName);
      }
   }

   public int hashCode() {
      int hashCode = this.mResourceName == null ? 0 : this.mResourceName.hashCode();
      hashCode += this.mRoleName.hashCode();
      return hashCode;
   }

   public String toString() {
      return this.mResourceName + ":" + this.mRoleName;
   }
}
