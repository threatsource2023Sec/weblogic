package weblogic.management.remote.wlx;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorProvider;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.kernel.KernelStatus;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.remote.common.WLSJMXConnector;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ClientProvider implements JMXConnectorProvider {
   private static final String WEBLOGIC_CONTEXT = "weblogic.context";
   private static final String JNDI_PREFIX = "/jndi/";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JMXConnector newJMXConnector(JMXServiceURL serviceURL, Map environmentIn) throws IOException {
      String[] tokens = this.getPartitionAndLookupName(serviceURL);
      String pNameFromJmxUrl = tokens[0];
      String lookupName = tokens[1];
      Context ctx = this.getNamingContext(environmentIn);
      String pName;
      if (isClient()) {
         pName = null;
         if (ctx == null) {
            throw new IllegalArgumentException("weblogic.context not set in env");
         }
      } else if (ctx != null) {
         pName = this.getPartitionFromJndiContext(ctx);
         this.validate(pNameFromJmxUrl, pName);
      } else {
         pName = pNameFromJmxUrl;
         ctx = this.setupNamingContext(environmentIn, pNameFromJmxUrl);
      }

      RMIServer rmiServer = this.findRMIServer(lookupName, ctx);
      Map environment = new HashMap(environmentIn);
      environment.put("jmx.remote.x.notification.fetch.timeout", new Long(1000L));
      environment.put("jmx.remote.x.client.connection.check.period", new Long(0L));
      return new WLXRMIConnectorWrapper(rmiServer, environment, pName);
   }

   private static boolean isServer() {
      return KernelStatus.isServer();
   }

   private static boolean isClient() {
      return !isServer();
   }

   private String getPartitionFromJndiContext(Context ctx) {
      Hashtable jndiEnv;
      try {
         jndiEnv = ctx.getEnvironment();
      } catch (NamingException var4) {
         throw new RuntimeException(var4);
      }

      String pNameFromJndi = (String)jndiEnv.get("weblogic.jndi.partitionInformation");
      if (null == pNameFromJndi) {
         pNameFromJndi = "DOMAIN";
      }

      return pNameFromJndi;
   }

   private void validate(String pNameFromJmxUrl, String pNameFromJndi) {
      if (pNameFromJmxUrl != null && !pNameFromJmxUrl.equals("DOMAIN")) {
         if (!pNameFromJmxUrl.equals(pNameFromJndi)) {
            throw new IllegalArgumentException("JNDI context is for partition " + pNameFromJndi + " where as JMX Service URL is for partition " + pNameFromJmxUrl);
         }
      }
   }

   private Context setupNamingContext(Map environment, String pName) throws IOException {
      assert this.getNamingContext(environment) == null;

      try {
         final Hashtable ht = new Hashtable(environment);
         String timeoutKey = "jmx.remote.x.request.waiting.timeout";
         Long o = (Long)environment.get(timeoutKey);
         if (o != null && o > 0L) {
            ht.put("weblogic.jndi.responseReadTimeout", o);
            ht.put("weblogic.jndi.connectTimeout", o);
         }

         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(pName);
         Context ctx = (Context)ComponentInvocationContextManager.runAs(KERNEL_ID, cic, new Callable() {
            public Context call() throws Exception {
               return new InitialContext(ht);
            }
         });
         return ctx;
      } catch (ExecutionException var8) {
         throw new IOException("Unable to create InitialContext: " + var8.getCause(), var8.getCause());
      }
   }

   private Context getNamingContext(Map environment) {
      return (Context)environment.get("weblogic.context");
   }

   private RMIServer findRMIServer(String lookupName, Context ctx) throws IOException {
      Object objref;
      try {
         objref = ctx.lookup(lookupName);
      } catch (NamingException var6) {
         IOException ioe = new IOException(var6.getMessage());
         ioe.initCause(var6);
         throw ioe;
      }

      return narrowServer(objref);
   }

   private String[] getPartitionAndLookupName(JMXServiceURL jmxServiceUrl) throws MalformedURLException {
      String path = jmxServiceUrl.getURLPath();
      int idx = path.indexOf("/jndi/");
      if (idx == -1) {
         throw new MalformedURLException("/jndi/ is missing in " + path + ". It must either at the beginning or right after the partition URI prefix in URL Path.");
      } else {
         String lookupName = path.substring(idx + "/jndi/".length());
         String partitionName = null;
         if (!isClient()) {
            String host = jmxServiceUrl.getHost();

            assert host != null;

            int port = jmxServiceUrl.getPort();
            String partitionUrl = jmxServiceUrl.getProtocol() + "://" + host + ":" + port + (idx > 0 ? "/partitions/" + path.substring(1, idx) : "");
            URI partitionUri = URI.create(partitionUrl);

            PartitionTableEntry partitionTableEntry;
            try {
               partitionTableEntry = PartitionTable.getInstance().lookup(partitionUri);
            } catch (IllegalArgumentException var12) {
               partitionTableEntry = null;
            }

            partitionName = partitionTableEntry != null ? partitionTableEntry.getPartitionName() : null;
         }

         return new String[]{partitionName, lookupName};
      }
   }

   private static RMIServer narrowServer(Object objectReference) {
      try {
         return (RMIServer)PortableRemoteObject.narrow(objectReference, RMIServer.class);
      } catch (ClassCastException var2) {
         return null;
      }
   }

   class WLXRMIConnectorWrapper extends RMIConnector implements WLSJMXConnector {
      private Locale locale_ = null;
      private final ComponentInvocationContext cic;

      public WLXRMIConnectorWrapper(RMIServer rmiServer, Map map, String pName) {
         super(rmiServer, map);
         if (pName != null) {
            this.cic = ComponentInvocationContextManager.getInstance().createComponentInvocationContext(pName);
         } else {
            this.cic = null;
         }

         this.locale_ = (Locale)map.get("weblogic.management.remote.locale");
      }

      public synchronized MBeanServerConnection getMBeanServerConnection() throws IOException {
         MBeanServerConnection connection = super.getMBeanServerConnection();
         if (this.locale_ != null) {
            connection = ClientProvider.this.new WLXMBeanServerConnectionWrapper((MBeanServerConnection)connection, this.locale_);
         }

         return this.wrap((MBeanServerConnection)connection);
      }

      public synchronized MBeanServerConnection getMBeanServerConnection(Subject delegationSubject) throws IOException {
         MBeanServerConnection connection = super.getMBeanServerConnection(delegationSubject);
         if (this.locale_ != null) {
            connection = ClientProvider.this.new WLXMBeanServerConnectionWrapper((MBeanServerConnection)connection, this.locale_);
         }

         return this.wrap((MBeanServerConnection)connection);
      }

      public synchronized MBeanServerConnection getMBeanServerConnection(Locale locale) throws IOException {
         MBeanServerConnection connection = super.getMBeanServerConnection();
         return this.wrap(ClientProvider.this.new WLXMBeanServerConnectionWrapper(connection, locale));
      }

      public synchronized MBeanServerConnection getMBeanServerConnection(Subject delegationSubject, Locale locale) throws IOException {
         MBeanServerConnection connection = super.getMBeanServerConnection(delegationSubject);
         return this.wrap(ClientProvider.this.new WLXMBeanServerConnectionWrapper(connection, locale));
      }

      private MBeanServerConnection wrap(final MBeanServerConnection connection) {
         return this.cic == null ? connection : (MBeanServerConnection)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{MBeanServerConnection.class}, new InvocationHandler() {
            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
               try {
                  return ComponentInvocationContextManager.runAs(ClientProvider.KERNEL_ID, WLXRMIConnectorWrapper.this.cic, new Callable() {
                     public Object call() throws Exception {
                        try {
                           return method.invoke(connection, args);
                        } catch (InvocationTargetException var3) {
                           Throwable t = var3.getCause();
                           if (t instanceof Exception) {
                              throw (Exception)t;
                           } else {
                              throw new Exception(t);
                           }
                        }
                     }
                  });
               } catch (ExecutionException var5) {
                  throw var5.getCause();
               }
            }
         });
      }
   }

   class WLXMBeanServerConnectionWrapper implements MBeanServerConnection {
      private MBeanServerConnection connection_;
      private Locale locale_;

      WLXMBeanServerConnectionWrapper(MBeanServerConnection connection, Locale locale) {
         this.connection_ = connection;
         this.locale_ = locale;
      }

      public ObjectInstance createMBean(String className, ObjectName name) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
         return this.connection_.createMBean(className, name);
      }

      public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
         return this.connection_.createMBean(className, name, loaderName);
      }

      public ObjectInstance createMBean(String className, ObjectName name, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
         return this.connection_.createMBean(className, name, params, signature);
      }

      public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
         return this.connection_.createMBean(className, name, loaderName, params, signature);
      }

      public void unregisterMBean(ObjectName name) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
         this.connection_.unregisterMBean(name);
      }

      public ObjectInstance getObjectInstance(ObjectName name) throws InstanceNotFoundException, IOException {
         return this.connection_.getObjectInstance(name);
      }

      public Set queryMBeans(ObjectName name, QueryExp query) throws IOException {
         return this.connection_.queryMBeans(name, query);
      }

      public Set queryNames(ObjectName name, QueryExp query) throws IOException {
         return this.connection_.queryNames(name, query);
      }

      public boolean isRegistered(ObjectName name) throws IOException {
         return this.connection_.isRegistered(name);
      }

      public Integer getMBeanCount() throws IOException {
         return this.connection_.getMBeanCount();
      }

      public Object getAttribute(ObjectName name, String attribute) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
         Object var3;
         try {
            this.initializeJMXContext();
            var3 = this.connection_.getAttribute(name, attribute);
         } finally {
            this.removeJMXContext();
         }

         return var3;
      }

      public AttributeList getAttributes(ObjectName name, String[] attributes) throws InstanceNotFoundException, ReflectionException, IOException {
         AttributeList var3;
         try {
            this.initializeJMXContext();
            var3 = this.connection_.getAttributes(name, attributes);
         } finally {
            this.removeJMXContext();
         }

         return var3;
      }

      public void setAttribute(ObjectName name, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
         try {
            this.initializeJMXContext();
            this.connection_.setAttribute(name, attribute);
         } finally {
            this.removeJMXContext();
         }

      }

      public AttributeList setAttributes(ObjectName name, AttributeList attributes) throws InstanceNotFoundException, ReflectionException, IOException {
         AttributeList var3;
         try {
            this.initializeJMXContext();
            var3 = this.connection_.setAttributes(name, attributes);
         } finally {
            this.removeJMXContext();
         }

         return var3;
      }

      public Object invoke(ObjectName name, String operationName, Object[] params, String[] signature) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
         Object var5;
         try {
            this.initializeJMXContext();
            var5 = this.connection_.invoke(name, operationName, params, signature);
         } finally {
            this.removeJMXContext();
         }

         return var5;
      }

      public String getDefaultDomain() throws IOException {
         return this.connection_.getDefaultDomain();
      }

      public String[] getDomains() throws IOException {
         return this.connection_.getDomains();
      }

      public MBeanInfo getMBeanInfo(ObjectName name) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
         MBeanInfo var2;
         try {
            this.initializeJMXContext();
            var2 = this.connection_.getMBeanInfo(name);
         } finally {
            this.removeJMXContext();
         }

         return var2;
      }

      public boolean isInstanceOf(ObjectName name, String className) throws InstanceNotFoundException, IOException {
         return this.connection_.isInstanceOf(name, className);
      }

      public void addNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, IOException {
         this.connection_.addNotificationListener(name, listener, filter, handback);
      }

      public void addNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, IOException {
         this.connection_.addNotificationListener(name, listener, filter, handback);
      }

      public void removeNotificationListener(ObjectName name, ObjectName listener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
         this.connection_.removeNotificationListener(name, listener);
      }

      public void removeNotificationListener(ObjectName name, ObjectName listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
         this.connection_.removeNotificationListener(name, listener, filter, handback);
      }

      public void removeNotificationListener(ObjectName name, NotificationListener listener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
         this.connection_.removeNotificationListener(name, listener);
      }

      public void removeNotificationListener(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
         this.connection_.removeNotificationListener(name, listener, filter, handback);
      }

      private void initializeJMXContext() {
         JMXContext jmxContext = JMXContextHelper.getJMXContext(true);
         jmxContext.setLocale(this.locale_);
         JMXContextHelper.putJMXContext(jmxContext);
      }

      private void removeJMXContext() {
         JMXContextHelper.removeJMXContext();
      }
   }
}
