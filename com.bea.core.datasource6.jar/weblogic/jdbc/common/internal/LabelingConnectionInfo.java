package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.common.resourcepool.PooledResourceInfo;

public class LabelingConnectionInfo implements PooledResourceInfo {
   Properties labels;
   String pdbName;
   String serviceName;

   public LabelingConnectionInfo(Properties labels) {
      this.labels = labels;
   }

   public LabelingConnectionInfo(String pdbName, String serviceName, Properties labels) {
      this(labels);
      this.pdbName = pdbName;
      this.serviceName = serviceName;
   }

   Properties getLabels() {
      return this.labels;
   }

   String getPDBName() {
      return this.pdbName;
   }

   void setPDBName(String pdbName) {
      this.pdbName = pdbName;
   }

   String getServiceName() {
      return this.serviceName;
   }

   void setServiceName(String serviceName) {
      this.serviceName = serviceName;
   }

   public int hashCode() {
      if (this.pdbName == null) {
         return 1;
      } else {
         int hashcode = 0;
         if (this.pdbName != null) {
            hashcode += this.pdbName.hashCode();
         }

         if (this.serviceName != null) {
            hashcode += this.serviceName.hashCode();
         }

         return hashcode;
      }
   }

   public boolean equals(Object obj) {
      return this.isEqual(obj);
   }

   public boolean equals(PooledResourceInfo pri) {
      return this.isEqual(pri);
   }

   private boolean isEqual(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof LabelingConnectionInfo)) {
         return false;
      } else {
         LabelingConnectionInfo that = (LabelingConnectionInfo)obj;
         return !JDBCUtil.isEqualIgnoringCase(this.pdbName, that.pdbName) ? false : JDBCUtil.isEqualIgnoringCase(this.serviceName, that.serviceName);
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("(");
      sb.append("pdbName=" + this.pdbName);
      sb.append(", ");
      sb.append("serviceName=" + this.serviceName);
      sb.append(", ");
      sb.append("labels=" + this.labels);
      sb.append(")");
      return sb.toString();
   }
}
