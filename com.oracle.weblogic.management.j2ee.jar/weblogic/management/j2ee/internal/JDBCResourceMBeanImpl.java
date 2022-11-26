package weblogic.management.j2ee.internal;

import java.security.AccessController;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.management.j2ee.JDBCResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class JDBCResourceMBeanImpl extends J2EEResourceMBeanImpl implements JDBCResourceMBean {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String domain;
   private String serverName = null;

   public JDBCResourceMBeanImpl(String name, String server) {
      super(name);
      this.serverName = server;
   }

   public String[] getjdbcDataSources() {
      try {
         String sObjectName = domain + ":j2eeType=" + "JDBCDataSource" + "," + "J2EEServer" + "=" + this.serverName + ",*";
         ObjectName pattern = new ObjectName(sObjectName);
         return this.queryNames(pattern);
      } catch (MalformedObjectNameException var3) {
         throw new AssertionError(" Malformed ObjectName" + var3);
      }
   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
   }
}
