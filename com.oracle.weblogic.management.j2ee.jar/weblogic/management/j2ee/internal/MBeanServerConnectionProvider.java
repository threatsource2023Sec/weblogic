package weblogic.management.j2ee.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

class MBeanServerConnectionProvider {
   private static MBeanServerConnection domainMBeanServerConnection = null;
   private static MBeanServerConnection editMBeanServerConnection = null;
   private static MBeanServerConnection runtimeMBeanServerConnection = null;
   private static final String JNDI = "/jndi/";
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MBeanServerConnectionProvider() {
      throw new AssertionError("This class cannot be instantiated");
   }

   public static void initialize() {
      domainMBeanServerConnection = createMBeanServerConnection("weblogic.management.mbeanservers.domainruntime");
      editMBeanServerConnection = createMBeanServerConnection("weblogic.management.mbeanservers.edit");
      runtimeMBeanServerConnection = createMBeanServerConnection("weblogic.management.mbeanservers.runtime");
   }

   public static MBeanServerConnection getDomainMBeanServerConnection() {
      return domainMBeanServerConnection;
   }

   public static MBeanServerConnection getEditMBeanServerConnection() {
      return editMBeanServerConnection;
   }

   public static MBeanServerConnection geRuntimeMBeanServerConnection() {
      return runtimeMBeanServerConnection;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private static MBeanServerConnection createMBeanServerConnection(String jndiName) {
      JMXServiceURL serviceURL;
      try {
         String adminServerName = ManagementService.getRuntimeAccess(kernelId).getAdminServerName();
         String adminServerURL = getURLManagerService().findAdministrationURL(adminServerName);
         ServerURL managedURL = new ServerURL(adminServerURL);
         serviceURL = new JMXServiceURL(managedURL.getProtocol(), managedURL.getHost(), managedURL.getPort(), "/jndi/" + jndiName);
      } catch (MalformedURLException var6) {
         throw new Error("Atempting to connect to the domain mbean server", var6);
      } catch (UnknownHostException var7) {
         return null;
      }

      try {
         Hashtable h = new Hashtable();
         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
         connector.addConnectionNotificationListener(createListener(), (NotificationFilter)null, (Object)null);
         return connector.getMBeanServerConnection();
      } catch (IOException var5) {
         throw new Error("Error while connecting to MBeanServer ", var5);
      }
   }

   private static NotificationListener createListener() {
      return new NotificationListener() {
         public void handleNotification(Notification notification, Object handback) {
         }
      };
   }
}
