package weblogic.deploy.api.spi.config;

import javax.enterprise.deploy.shared.ModuleType;

public class DeployableObjectKey {
   private String name;
   private ModuleType type;
   private String contextRoot;

   public DeployableObjectKey(String argName, ModuleType argType) {
      this.name = argName;
      this.type = argType;
   }

   public DeployableObjectKey(String argName, ModuleType argType, String root) {
      this.name = argName;
      this.type = argType;
      this.contextRoot = root;
   }

   public boolean equals(Object obj) {
      if (obj instanceof DeployableObjectKey) {
         DeployableObjectKey otherKey = (DeployableObjectKey)obj;
         if (this.name.equals(otherKey.getName()) && this.type.getValue() == otherKey.getType().getValue()) {
            if (this.contextRoot == null) {
               if (otherKey.getContextRoot() == null) {
                  return true;
               }

               return false;
            }

            if (this.contextRoot.equals(otherKey.getContextRoot())) {
               return true;
            }

            return false;
         }
      }

      return false;
   }

   public final int hashCode() {
      int i = this.name.hashCode() ^ this.type.getValue();
      if (this.contextRoot != null) {
         i ^= this.contextRoot.hashCode();
      }

      return i;
   }

   public final String getName() {
      return this.name;
   }

   public final void setName(String argName) {
      this.name = argName;
   }

   public final ModuleType getType() {
      return this.type;
   }

   public final void setType(ModuleType argType) {
      this.type = argType;
   }

   public final String getContextRoot() {
      return this.contextRoot;
   }

   public final void setContextRoot(String root) {
      this.contextRoot = root;
   }

   public String toString() {
      int sbSize = true;
      String variableSeparator = "  ";
      StringBuffer sb = new StringBuffer(1000);
      sb.append("name=").append(this.name);
      sb.append("  ");
      sb.append("type=").append(this.type);
      sb.append("  ");
      if (this.contextRoot != null) {
         sb.append("contextRoot=").append(this.contextRoot);
      }

      return sb.toString();
   }
}
