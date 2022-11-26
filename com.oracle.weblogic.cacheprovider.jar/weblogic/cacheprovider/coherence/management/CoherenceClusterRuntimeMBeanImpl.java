package weblogic.cacheprovider.coherence.management;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import weblogic.management.ManagementException;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class CoherenceClusterRuntimeMBeanImpl extends RuntimeMBeanDelegate implements CoherenceClusterRuntimeMBean {
   private ObjectName objectName;
   private MBeanServer mbeanSrvr;

   public CoherenceClusterRuntimeMBeanImpl(ObjectName objName, MBeanServer srvr, RuntimeMBean parent) throws ManagementException {
      super(objName.getDomain(), parent, true, "CoherenceClusterRuntime");
      this.objectName = objName;
      this.mbeanSrvr = srvr;
   }

   public MBeanServer getMBeanServer() {
      return this.mbeanSrvr;
   }

   public String getClusterName() {
      return (String)this.get("ClusterName");
   }

   public Integer getClusterSize() {
      return (Integer)this.get("ClusterSize");
   }

   public String getLicenseMode() {
      return (String)this.get("LicenseMode");
   }

   public String[] getMembers() {
      Object obj = this.get("Members");
      return (String[])((String[])obj);
   }

   public String getVersion() {
      return (String)this.get("Version");
   }

   private Object get(String attribute) {
      try {
         return this.mbeanSrvr.getAttribute(this.objectName, attribute);
      } catch (JMException var3) {
         return null;
      }
   }
}
