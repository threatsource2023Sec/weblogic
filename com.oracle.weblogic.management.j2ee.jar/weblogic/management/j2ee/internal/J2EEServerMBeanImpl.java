package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.management.j2ee.J2EEServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class J2EEServerMBeanImpl extends J2EEManagedObjectMBeanImpl implements J2EEServerMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String serverVersion;
   private static final String serverVendor = "Oracle";
   private static final String domain;
   private static String serverName;

   public J2EEServerMBeanImpl(String name) {
      super(name);
   }

   public String getserverVersion() {
      return serverVersion;
   }

   public String getserverVendor() {
      return "Oracle";
   }

   public String[] getjavaVMs() {
      try {
         String stringPattern = domain + ":j2eeType=" + "JVM" + "," + "J2EEServer" + "=\"" + this.getServerName() + "\",*";
         ObjectName pattern = new ObjectName(stringPattern);
         return this.queryNames(pattern);
      } catch (MalformedObjectNameException var3) {
         throw new AssertionError(" Malformed ObjectName" + var3);
      }
   }

   public String[] getdeployedObjects() {
      QueryExp query = new QueryExp() {
         public boolean apply(ObjectName name) {
            String type = name.getKeyProperty("j2eeType");
            if (type.equals("J2EEApplication")) {
               return true;
            } else if (!type.equals("WebModule") && !type.equals("ResourceAdapterModule") && !type.equals("EJBModule")) {
               return false;
            } else {
               return name.getKeyProperty("J2EEApplication").equals("null");
            }
         }

         public void setMBeanServer(MBeanServer server) {
         }
      };
      return this.queryNames(query);
   }

   public String[] getresources() {
      QueryExp query = new QueryExp() {
         public boolean apply(ObjectName name) {
            return JMOTypesHelper.resourceList.contains(name.getKeyProperty("j2eeType"));
         }

         public void setMBeanServer(MBeanServer server) {
         }
      };
      return this.queryNames(query);
   }

   private String getServerName() {
      if (serverName != null) {
         return serverName;
      } else {
         try {
            ObjectName server = new ObjectName(this.name);
            serverName = server.getKeyProperty("name");
         } catch (MalformedObjectNameException var2) {
            throw new AssertionError("Malformed ObjectName detected");
         }

         return serverName;
      }
   }

   static {
      serverVersion = ManagementService.getRuntimeAccess(kernelId).getServer().getServerVersion();
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
      serverName = null;
   }
}
