package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.j2ee.statistics.JCAStats;
import javax.management.j2ee.statistics.Stats;
import weblogic.management.j2ee.JCAResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class JCAResourceMBeanImpl extends StatsProviderMBeanImpl implements JCAResourceMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;
   private final ObjectName wlsObjectName;

   public JCAResourceMBeanImpl(String name, ObjectName wlsON) {
      super(name);
      this.wlsObjectName = wlsON;
   }

   public String[] getconnectionFactories() {
      String nameValue = JMOTypesHelper.getKeyValue(this.name, "name");
      String serverValue = JMOTypesHelper.getKeyValue(this.name, "J2EEServer");
      String objectName = domain + ":j2eeType=" + "JCAConnectionFactory" + "," + "J2EEServer" + "=" + serverValue + "," + "ResourceAdapterModule" + "=" + nameValue + "," + "JCAResource" + "=" + nameValue + ",*";

      try {
         ObjectName pattern = new ObjectName(objectName);
         return this.queryNames(pattern);
      } catch (MalformedObjectNameException var5) {
         throw new AssertionError(" Malformed ObjectName" + var5);
      }
   }

   public Stats getstats() {
      try {
         JCAStats stat = (JCAStats)MBeanServerConnectionProvider.getDomainMBeanServerConnection().getAttribute(this.wlsObjectName, "Stats");
         return stat;
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public boolean isstatisticsProvider() {
      return true;
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
   }
}
