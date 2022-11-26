package weblogic.server.embed.internal;

import com.bea.logging.StdoutHandler;
import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.CIEDomainGenerator;
import weblogic.management.internal.DomainGenerator;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class EmbeddedServerProvider {
   private static final int EDIT_ACCESS_TIMEOUT = 120000;
   protected static final Logger LOGGER = createLogger();

   private static EmbeddedServerProvider createInstance() {
      return new FullEmbeddedServerProvider();
   }

   public static EmbeddedServerProvider get() {
      return EmbeddedServerProvider.SingletonMaker.INSTANCE;
   }

   public static MBeanServerConnection getMBeanServerConnection(String type, String u, String p) throws NamingException, IOException {
      Hashtable ht = new Hashtable();
      ht.put("java.naming.security.principal", u);
      ht.put("java.naming.security.credentials", p);
      Context ic = new InitialContext(ht);
      JMXServiceURL serviceURL = new JMXServiceURL("wlx", (String)null, 0, "/jndi/weblogic.management.mbeanservers." + type);
      Hashtable h = new Hashtable();
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      h.put("weblogic.context", ic);
      JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
      ic.close();
      return connector.getMBeanServerConnection();
   }

   public Logger getLogger() {
      return LOGGER;
   }

   private static Logger createLogger() {
      Logger l = Logger.getLogger("EmbeddedServer");
      Handler h = new StdoutHandler();
      h.setLevel(Level.INFO);
      l.addHandler(h);
      l.setUseParentHandlers(false);
      return l;
   }

   public final DomainMBean createDefaultDomain(String user, String pass) throws Exception {
      DomainGenerator domainGen = this.lookupDomainGenerator();
      String root = DomainDir.getRootDir();
      if (root == null) {
         root = ".";
      }

      root = (new File(root)).getAbsolutePath();
      domainGen.generateDefaultDomain(root, user, pass);
      String configFile = DomainDir.getConfigDir() + File.separator + "config.xml";
      return (DomainMBean)DescriptorManagerHelper.loadDescriptor(configFile, true, false, (List)null).getRootBean();
   }

   protected DomainGenerator lookupDomainGenerator() throws ManagementException {
      return new CIEDomainGenerator();
   }

   public void initializeManagementServiceClientBeanInfoAccess() {
   }

   public DomainMBean beginEdit(String u, String p) throws Exception {
      EditAccess ea = ManagementServiceRestricted.getEditAccess(this.getKernelId());
      return ea.startEdit(-1, 120000);
   }

   public void saveEdit() throws Exception {
      EditAccess ea = ManagementServiceRestricted.getEditAccess(this.getKernelId());
      ea.saveChanges();
      ea.activateChangesAndWaitForCompletion(120000L);
   }

   public void cancelEdit() {
   }

   public void registerServerStateNotifier(NotificationListener listener) throws Exception {
      ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
      MBeanServerConnection conn = ManagementService.getRuntimeMBeanServer(this.getKernelId());
      ObjectName sr = (ObjectName)conn.getAttribute(rs, "ServerRuntime");
      conn.addNotificationListener(sr, listener, (NotificationFilter)null, (Object)null);
   }

   public abstract DeploymentManagerMBean getDeploymentManager() throws Exception;

   private AuthenticatedSubject getKernelId() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static final class SingletonMaker {
      private static final EmbeddedServerProvider INSTANCE = EmbeddedServerProvider.createInstance();
   }
}
