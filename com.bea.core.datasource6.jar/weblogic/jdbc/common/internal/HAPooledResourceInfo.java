package weblogic.jdbc.common.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.jdbc.common.rac.RACInstance;

public class HAPooledResourceInfo extends LabelingConnectionInfo implements PooledResourceInfo {
   private RACInstance racInstance;
   private String url;
   private Properties additionalProperties;
   private boolean refreshToSpecificInstance;
   private Set serviceInstances;

   public HAPooledResourceInfo(String url, RACInstance racInstance, Properties labels, Properties additionalProperties) {
      this(url, racInstance, (String)null, (String)null, (Set)null, labels, additionalProperties);
   }

   public HAPooledResourceInfo(String url, RACInstance racInstance, String pdbName, Properties labels, Properties additionalProperties) {
      this(url, racInstance, pdbName, (String)null, (Set)null, labels, additionalProperties);
   }

   public HAPooledResourceInfo(String pdbName, String serviceName, RACInstance racInstance) {
      super(pdbName, serviceName, (Properties)null);
      this.racInstance = racInstance;
   }

   public HAPooledResourceInfo(String pdbName, String serviceName, List list) {
      super(pdbName, serviceName, (Properties)null);
      this.serviceInstances = new HashSet(list);
   }

   public HAPooledResourceInfo(String url, RACInstance racInstance, String pdbName, String serviceName, Set serviceInstances, Properties labels, Properties additionalProperties) {
      super(pdbName, serviceName, labels);
      this.racInstance = racInstance;
      this.additionalProperties = additionalProperties;
      this.serviceInstances = serviceInstances;
      if (url != null && racInstance != null) {
         this.url = this.qualifyURLWithInstanceName(url, racInstance.getInstance());
      } else {
         this.url = url;
      }

   }

   public boolean equals(PooledResourceInfo info) {
      return this.isEqual(info);
   }

   public boolean equals(Object obj) {
      return this.isEqual(obj);
   }

   private boolean isEqual(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof HAPooledResourceInfo)) {
         return false;
      } else {
         HAPooledResourceInfo that = (HAPooledResourceInfo)obj;
         if (!super.equals(obj)) {
            return false;
         } else if (this.racInstance == null && that.racInstance == null) {
            return true;
         } else {
            String thisInstance = null;
            String thatInstance = null;
            if (this.racInstance != null) {
               thisInstance = this.racInstance.getInstance();
            }

            if (that.racInstance != null) {
               thatInstance = that.racInstance.getInstance();
            }

            if (thisInstance == null && thatInstance == null) {
               return true;
            } else {
               return thisInstance != null ? thisInstance.equals(thatInstance) : false;
            }
         }
      }
   }

   public int hashCode() {
      return this.racInstance == null ? 1 : this.racInstance.hashCode();
   }

   private String qualifyURLWithInstanceName(String url, String instance) {
      if (url == null) {
         return null;
      } else if (instance == null) {
         return url;
      } else {
         String nurl = url.replaceAll("CONNECT_DATA\\s*=", "CONNECT_DATA=(INSTANCE_NAME=" + instance + ")");
         return nurl;
      }
   }

   RACInstance getRACInstance() {
      return this.racInstance;
   }

   String getUrl() {
      return this.url;
   }

   Properties getAdditionalProperties() {
      return this.additionalProperties;
   }

   void setRefreshToSpecificInstance(boolean b) {
      this.refreshToSpecificInstance = b;
   }

   Set getServiceInstances() {
      return this.serviceInstances;
   }

   boolean isRefreshToSpecificInstance() {
      return this.refreshToSpecificInstance;
   }

   String getInstanceName() {
      return this.racInstance == null ? null : this.racInstance.getInstance();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("(");
      sb.append(super.toString());
      sb.append(", ");
      sb.append("instance=" + this.racInstance);
      sb.append(", ");
      sb.append("serviceInstances=" + this.serviceInstances);
      sb.append(", ");
      sb.append("url=" + this.url);
      sb.append(")");
      return sb.toString();
   }
}
