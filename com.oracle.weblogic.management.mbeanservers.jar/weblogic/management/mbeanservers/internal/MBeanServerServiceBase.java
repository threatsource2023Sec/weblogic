package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.mbeanservers.MBeanTypeService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.ServerURL;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

public class MBeanServerServiceBase extends AbstractServerService {
   @Inject
   @Named("IIOPClientService")
   private ServerService dependencyOnIIOPClientService;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   protected static final String CURRENT_VERSION = "99.0.0.0";
   private static final String PROTOCOL_IIOP = "iiop";
   private static final String JNDI = "/jndi/";
   private static final ObjectName MBEANTYPESERVICE;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MBeanServer mbeanServer;
   private JMXServiceURL serviceURL;
   private JMXConnectorServer connectorServer = null;
   private MBeanTypeServiceImpl mbeanTypeService = null;

   protected MBeanServer getMBeanServer() {
      return this.mbeanServer;
   }

   protected synchronized void initialize(String jndiName, MBeanServer mbeanServerIn) throws ServiceFailureException {
      this.initialize(jndiName, mbeanServerIn, (MBeanServer)null);
   }

   protected synchronized void registerTypeService(WLSModelMBeanContext context) throws OperationsException, MBeanRegistrationException {
      this.mbeanTypeService = new MBeanTypeServiceImpl(context);
      WLSModelMBeanFactory.registerWLSModelMBean(this.mbeanTypeService, MBEANTYPESERVICE, context);
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   protected synchronized void unregisterTypeService(WLSModelMBeanContext context) {
      if (this.mbeanTypeService != null) {
         WLSModelMBeanFactory.unregisterWLSModelMBean(this.mbeanTypeService, context);
      }

   }

   protected synchronized void initialize(String jndiName, MBeanServer mbeanServerIn, MBeanServer wrappedServer) throws ServiceFailureException {
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      this.mbeanServer = mbeanServerIn;
      if (wrappedServer != null && wrappedServer instanceof WLSMBeanServer) {
         this.mbeanServer = wrappedServer;
      } else if (this.mbeanServer == null) {
         WLSMBeanServerBuilder builder = new WLSMBeanServerBuilder();
         String defaultDomain = runtime.getDomainName();
         this.mbeanServer = builder.newMBeanServer(defaultDomain, (MBeanServer)null, (MBeanServerDelegate)null, wrappedServer);
      }

      try {
         String adminURLString = getURLManagerService().findAdministrationURL(runtime.getServerName());
         ServerURL adminURL = new ServerURL(adminURLString);
         this.serviceURL = new JMXServiceURL("iiop", adminURL.getHost(), adminURL.getPort(), "/jndi/" + jndiName);
      } catch (MalformedURLException var7) {
         throw new AssertionError(" Malformed URL" + var7);
      } catch (UnknownHostException var8) {
         if (debug.isDebugEnabled()) {
            debug.debug("Unable to obtain URL for managed server " + runtime.getServerName());
         }

         return;
      }

      if (debug.isDebugEnabled()) {
         debug.debug("Registering JMXConnectorServer at " + this.serviceURL);
      }

      try {
         Hashtable h = new Hashtable();
         h.put("weblogic.jndi.replicateBindings", Boolean.FALSE.toString());
         h.put("weblogic.jndi.createIntermediateContexts", Boolean.TRUE.toString());
         h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         h.put("weblogic.jndi.createUnderSharable", "true");
         h.put("weblogic.jndi.crossPartitionAware", "true");
         int timeout = ManagementService.getRuntimeAccess(kernelId).getDomain().getJMX().getInvocationTimeoutSeconds();
         if (timeout > 0) {
            h.put("weblogic.jndi.connectTimeout", new Long((long)(timeout * 1000)));
            h.put("weblogic.jndi.responseReadTimeout", new Long((long)(timeout * 1000)));
         }

         h.put("jmx.remote.x.server.connection.timeout", new Long(Long.MAX_VALUE));
         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         h.put("jmx.remote.authenticator", new JMXAuthenticator());
         this.connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(this.serviceURL, h, this.mbeanServer);
         this.connectorServer.setMBeanServerForwarder(new JMXConnectorSubjectForwarder());
      } catch (IOException var9) {
         throw new AssertionError(" Failed to start JMXConnector Server" + var9);
      }

      if (debug.isDebugEnabled()) {
         debug.debug("JMXConnectorServer registered at " + this.serviceURL);
      }

   }

   private String getServiceURLString() {
      return this.serviceURL == null ? "<not established>" : this.serviceURL.toString();
   }

   public void start() throws ServiceFailureException {
      try {
         this.connectorServer.start();
         JMXLogger.logStartedJMXConnectorServer(this.getServiceURLString());
      } catch (IOException var2) {
         throw new ServiceFailureException("Unable to start JMX Connector Server", var2);
      }
   }

   public void stop() throws ServiceFailureException {
      try {
         if (this.connectorServer != null) {
            if (debug.isDebugEnabled()) {
               debug.debug("Stopping connectionServer: ConnectionIds: " + this.connectorServer.getConnectionIds());
            }

            this.connectorServer.stop();
            JMXLogger.logStoppedJMXConnectorServer(this.getServiceURLString());
         }
      } catch (IOException var2) {
         throw new ServiceFailureException("Unable to stop JMX Connector Server", var2);
      }
   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   static {
      try {
         MBEANTYPESERVICE = new ObjectName(MBeanTypeService.OBJECT_NAME);
      } catch (MalformedObjectNameException var1) {
         throw new Error(var1);
      }
   }
}
