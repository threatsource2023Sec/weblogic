package weblogic.application;

import java.io.Serializable;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.invocation.ComponentInvocationContext;

final class ComponentInvocationContextImpl implements ComponentInvocationContext, Serializable {
   private static final long serialVersionUID = -4645339834209086871L;
   private final String partitionName;
   private final String partitionId;
   private final String applicationName;
   private final String applicationVersion;
   private final String moduleName;
   private final String componentName;

   ComponentInvocationContextImpl(String partitionName, String applicationName, String applicationVersion, String moduleName, String componentName) {
      this.partitionName = partitionName == null ? "DOMAIN" : partitionName;
      this.partitionId = ComponentInvocationContextManagerImpl.PartitionIdNameMapper.mapPartitionNameToId(this.partitionName);
      this.applicationName = applicationName;
      this.applicationVersion = applicationVersion;
      this.moduleName = moduleName;
      this.componentName = componentName;
   }

   ComponentInvocationContextImpl(ComponentInvocationContext other) {
      this.partitionName = other.getPartitionName();
      this.partitionId = other.getPartitionId();
      this.applicationName = other.getApplicationName();
      this.applicationVersion = other.getApplicationVersion();
      this.moduleName = other.getModuleName();
      this.componentName = other.getComponentName();
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getApplicationId() {
      return ApplicationVersionUtils.getApplicationId(this.applicationName, this.applicationVersion, this.partitionName);
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getApplicationVersion() {
      return this.applicationVersion;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public boolean isGlobalRuntime() {
      return "DOMAIN".equals(this.getPartitionName());
   }

   public String toString() {
      return "(pId = " + this.getPartitionId() + ", pName = " + this.getPartitionName() + ", appId = " + this.getApplicationId() + ", appName = " + this.getApplicationName() + ", appVersion = " + this.getApplicationVersion() + ", mId = " + this.getModuleName() + ", compName = " + this.getComponentName() + ")";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.applicationName == null ? 0 : this.applicationName.hashCode());
      result = 31 * result + (this.applicationVersion == null ? 0 : this.applicationVersion.hashCode());
      result = 31 * result + (this.componentName == null ? 0 : this.componentName.hashCode());
      result = 31 * result + (this.moduleName == null ? 0 : this.moduleName.hashCode());
      result = 31 * result + (this.partitionName == null ? 0 : this.partitionName.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof ComponentInvocationContextImpl)) {
         return false;
      } else {
         ComponentInvocationContextImpl other = (ComponentInvocationContextImpl)obj;
         if (this.applicationName == null) {
            if (other.applicationName != null) {
               return false;
            }
         } else if (!this.applicationName.equals(other.applicationName)) {
            return false;
         }

         if (this.applicationVersion == null) {
            if (other.applicationVersion != null) {
               return false;
            }
         } else if (!this.applicationVersion.equals(other.applicationVersion)) {
            return false;
         }

         if (this.componentName == null) {
            if (other.componentName != null) {
               return false;
            }
         } else if (!this.componentName.equals(other.componentName)) {
            return false;
         }

         if (this.moduleName == null) {
            if (other.moduleName != null) {
               return false;
            }
         } else if (!this.moduleName.equals(other.moduleName)) {
            return false;
         }

         if (this.partitionName == null) {
            if (other.partitionName != null) {
               return false;
            }
         } else if (!this.partitionName.equals(other.partitionName)) {
            return false;
         }

         return true;
      }
   }
}
