package weblogic.management.deploy.internal;

import java.io.Serializable;
import weblogic.utils.Debug;

/** @deprecated */
@Deprecated
public class ComponentTarget implements Serializable {
   private static final long serialVersionUID = 3862450250430200114L;
   public static final int COMPONENT_TARGET_SERVER = 1;
   public static final int COMPONENT_TARGET_CLUSTER = 2;
   public static final int COMPONENT_TARGET_VIRTUALHOST = 3;
   private String componentTarget = null;
   private String clusterTarget = null;
   private String physicalTarget = null;
   private int targetType;
   private String signature = null;
   private int hashCodeValue;

   public ComponentTarget(String componentTarget, String physicalTarget, int targetType) {
      this.componentTarget = componentTarget;
      this.physicalTarget = physicalTarget;
      this.targetType = targetType;
      this.signature = this.computeSignature();
      this.hashCodeValue = this.signature.hashCode();
   }

   public ComponentTarget(String componentTarget, String clusterTarget, String physicalTarget, int targetType) {
      if (targetType == 3) {
         this.clusterTarget = clusterTarget;
      } else {
         Debug.assertion(false, "ClusterTarget can be set only for VirtualHost");
      }

      this.componentTarget = componentTarget;
      this.physicalTarget = physicalTarget;
      this.targetType = targetType;
      this.signature = this.computeSignature();
      this.hashCodeValue = this.signature.hashCode();
   }

   public String getComponentTarget() {
      return this.componentTarget;
   }

   public String getClusterTarget() {
      return this.clusterTarget;
   }

   public String getPhysicalTarget() {
      return this.physicalTarget;
   }

   public boolean isVirtualHostClustered() {
      return this.clusterTarget != null;
   }

   public int getTargetType() {
      return this.targetType;
   }

   public boolean equals(Object target) {
      boolean status = false;
      if (target == this) {
         status = true;
      } else if (target instanceof ComponentTarget) {
         ComponentTarget compTarget = (ComponentTarget)target;
         if (compTarget.toString().equals(this.signature)) {
            status = true;
         }
      }

      return status;
   }

   public String toString() {
      return this.signature;
   }

   public int hashCode() {
      return this.hashCodeValue;
   }

   private String computeSignature() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("ComponentTarget:");
      strBuf.append(this.componentTarget);
      strBuf.append(":");
      if (this.clusterTarget != null) {
         strBuf.append(this.getTargetTypeString(2));
         strBuf.append(this.clusterTarget);
         strBuf.append(":");
      }

      strBuf.append(this.getTargetTypeString(1));
      strBuf.append(this.physicalTarget);
      return strBuf.toString();
   }

   private String getTargetTypeString(int type) {
      switch (type) {
         case 1:
            return "Server:";
         case 2:
            return "Cluster:";
         case 3:
            return "VirtualHost:";
         default:
            Debug.assertion(false, "Illegal TargetType");
            return null;
      }
   }
}
