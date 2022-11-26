package weblogic.jms.forwarder.dd.internal;

import weblogic.jms.forwarder.DestinationName;
import weblogic.jms.forwarder.dd.DDInfo;
import weblogic.jms.forwarder.internal.DestinationNameImpl;

public class DDInfoImpl implements DDInfo {
   private DestinationName destinationName;
   private int ddType;
   private String applicationName;
   private String moduleName;
   private int ddLoadBalancingPolicy;
   private int forwardingPolicy;
   private int forwardDelay;
   private String pathServiceJNDIName;
   private String safExportPolicy;
   private String uuoRouting;

   public DDInfoImpl(String ddJNDIName, String ddConfigName, int ddType, String applicationName, String moduleName, int ddLoadBalancingPolicy, int msgForwardingPolicy) {
      this(ddJNDIName, ddConfigName, ddType, applicationName, moduleName, ddLoadBalancingPolicy, msgForwardingPolicy, -1, (String)null, (String)null, (String)null);
   }

   public DDInfoImpl(String ddJNDIName, String ddConfigName, int ddType, String applicationName, String moduleName, int ddLoadBalancingPolicy, int msgForwardingPolicy, int forwardDelay, String pathServiceJNDIName, String safExportPolicy, String uuoRouting) {
      this.destinationName = new DestinationNameImpl(ddConfigName, ddJNDIName);
      this.ddType = ddType;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.ddLoadBalancingPolicy = ddLoadBalancingPolicy;
      this.forwardingPolicy = msgForwardingPolicy;
      this.forwardDelay = forwardDelay;
      this.pathServiceJNDIName = pathServiceJNDIName;
      this.safExportPolicy = safExportPolicy;
      this.uuoRouting = uuoRouting;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DDInfoImpl)) {
         return false;
      } else {
         DDInfoImpl ddInfo = (DDInfoImpl)o;
         if (this.destinationName != null) {
            if (!this.destinationName.equals(ddInfo.destinationName)) {
               return false;
            }
         } else if (ddInfo.destinationName != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      return this.destinationName != null ? this.destinationName.hashCode() : 0;
   }

   public DestinationName getDestinationName() {
      return this.destinationName;
   }

   public int getType() {
      return this.ddType;
   }

   public String getPathServiceJNDIName() {
      return this.pathServiceJNDIName;
   }

   public String getSAFExportPolicy() {
      return this.safExportPolicy;
   }

   public String getUnitOfOrderRouting() {
      return this.uuoRouting;
   }

   public int getLoadBalancingPolicy() {
      return this.ddLoadBalancingPolicy;
   }

   public int getForwardingPolicy() {
      return this.forwardingPolicy;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public int getForwardDelay() {
      return this.forwardDelay;
   }
}
