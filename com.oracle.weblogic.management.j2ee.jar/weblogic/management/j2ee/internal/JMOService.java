package weblogic.management.j2ee.internal;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServerNotification;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.j2ee.ListenerRegistry;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.ObjectSecurityManager;
import weblogic.management.jmx.modelmbean.WLSModelMBean;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JMOService implements ListenerRegistry {
   private final Map wlsToJ2EEObjectNames;
   private String defaultDomain;
   private MBeanServerConnection domainRuntimeMBeanServer;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJ2EEManagement");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ObjectName RUNTIME_QUERY;
   private final MBeanServer server;

   private JMOService() {
      this.wlsToJ2EEObjectNames = new HashMap(39);
      this.defaultDomain = null;
      this.defaultDomain = ManagementService.getRuntimeAccess(kernelId).getDomainName();
      this.server = MBeanServerFactory.createMBeanServer();
      MBeanServerConnectionProvider.initialize();
      this.domainRuntimeMBeanServer = MBeanServerConnectionProvider.getDomainMBeanServerConnection();
      this.registerDomainRuntimeListener();
      this.initializePreexistingBeans();
   }

   public static JMOService getJMOService() {
      return JMOService.SINGLETON.service;
   }

   public String getDefaultDomain() {
      return this.defaultDomain;
   }

   public Object getAttribute(ObjectName objectName, String attribute) throws InvalidObjectNameException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException {
      this.validate(objectName);
      return this.server.getAttribute(objectName, attribute);
   }

   public Set queryNames(ObjectName objectName, QueryExp queryExp) {
      return this.server.queryNames(objectName, queryExp);
   }

   public boolean isRegistered(ObjectName objectName) {
      return this.server.isRegistered(objectName);
   }

   public Integer getMBeanCount() {
      return this.server.getMBeanCount();
   }

   public MBeanInfo getMBeanInfo(ObjectName objectName) throws IntrospectionException, InstanceNotFoundException, ReflectionException {
      return this.server.getMBeanInfo(objectName);
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException {
      return this.server.getAttributes(objectName, strings);
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, ReflectionException, MBeanException, InvalidObjectNameException {
      this.validate(objectName);
      this.server.setAttribute(objectName, attribute);
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException {
      return this.server.setAttributes(objectName, attributeList);
   }

   public Object invoke(ObjectName objectName, String string, Object[] objects, String[] strings) throws InstanceNotFoundException, ReflectionException, MBeanException, InvalidObjectNameException {
      this.validate(objectName);
      return this.server.invoke(objectName, string, objects, strings);
   }

   public void addListener(ObjectName objectName, NotificationListener notificationListener, NotificationFilter notificationFilter, Object object) throws InstanceNotFoundException, RemoteException {
      this.server.addNotificationListener(objectName, notificationListener, notificationFilter, object);
   }

   public void removeListener(ObjectName objectName, NotificationListener notificationListener) throws InstanceNotFoundException, ListenerNotFoundException, RemoteException {
      this.server.removeNotificationListener(objectName, notificationListener);
   }

   private void registerDomainRuntimeListener() {
      try {
         this.domainRuntimeMBeanServer.addNotificationListener(new ObjectName("JMImplementation:type=MBeanServerDelegate"), new MBeanServerListener(), new MBeanServerFilter(), (Object)null);
      } catch (MalformedObjectNameException var2) {
         throw new Error("Malformed ObjectName", var2);
      } catch (InstanceNotFoundException var3) {
         throw new Error("DomainRuntime MBeanServer not found", var3);
      } catch (IOException var4) {
         throw new Error("IOException while registering with DomainRuntime MBeanServer", var4);
      }
   }

   private void initializePreexistingBeans() {
      try {
         Set objectNames = this.domainRuntimeMBeanServer.queryNames(RUNTIME_QUERY, (QueryExp)null);
         Iterator iterator = objectNames.iterator();

         while(iterator.hasNext()) {
            try {
               ObjectName objectName = (ObjectName)iterator.next();
               String type = objectName.getKeyProperty("Type");
               if (type != null && Types.isValidWLSType(type)) {
                  NotificationHandler handler = new NotificationHandler(objectName, this.defaultDomain);
                  if (!handler.registerThisObject()) {
                     if (debug.isDebugEnabled()) {
                        debug.debug("Skipping Bean !registerThisObject" + objectName);
                     }
                  } else {
                     this.registerMBean(handler);
                  }
               } else if (debug.isDebugEnabled()) {
                  debug.debug("Skipping Bean: no type " + objectName);
               }
            } catch (Throwable var6) {
               debug.debug("Unable to register a pre-existing mean" + var6);
               ManagementLogger.logCouldNotRegisterMBeanForJSR77(var6);
            }
         }
      } catch (IOException var7) {
         debug.debug("Failure to connect to domain runtime service", var7);
      }

   }

   MBeanServer getJMOMBeanServer() {
      return this.server;
   }

   private void validate(ObjectName objectName) throws InvalidObjectNameException {
      JMOValidator validator = new JMOValidator(objectName);
      validator.validate();
   }

   private void registerMBean(NotificationHandler handler) {
      Map objectMap = handler.getJ2EEObjectNameToImplMap();
      Set keySet = objectMap.keySet();
      Iterator it = keySet.iterator();

      while(it.hasNext()) {
         ObjectName j2eeObjectName = (ObjectName)it.next();

         try {
            Object bean = objectMap.get(j2eeObjectName);
            WLSModelMBeanContext context = new WLSModelMBeanContext(this.server, (ObjectNameManager)null, (ObjectSecurityManager)null);
            WLSModelMBean beanObject = new WLSModelMBean(bean, context);
            if (debug.isDebugEnabled()) {
               debug.debug("Registering:    " + j2eeObjectName.toString());
            }

            this.server.registerMBean(beanObject, j2eeObjectName);
         } catch (NotCompliantMBeanException var9) {
            throw new AssertionError("Not a Compliant MBean" + var9);
         } catch (MBeanRegistrationException var10) {
            throw new AssertionError("MBean Registration failed" + var10);
         } catch (InstanceAlreadyExistsException var11) {
            if (debug.isDebugEnabled()) {
               debug.debug("Attempt to register an MBean again" + var11.toString());
            }
            continue;
         } catch (OperationsException var12) {
            throw new AssertionError("Failure in creating MBean Adaptor" + var12);
         }

         this.wlsToJ2EEObjectNames.put(handler.getWLSObjectName(), keySet.toArray(new ObjectName[keySet.size()]));
      }

   }

   private void unregisterMBean(ObjectName wlsObjectName) {
      if (this.wlsToJ2EEObjectNames.containsKey(wlsObjectName)) {
         ObjectName[] cachedj2eeObjectNames = (ObjectName[])((ObjectName[])this.wlsToJ2EEObjectNames.remove(wlsObjectName));

         for(int i = 0; i < cachedj2eeObjectNames.length; ++i) {
            if (debug.isDebugEnabled()) {
               debug.debug("Un-Registering:    " + cachedj2eeObjectNames[i]);
            }

            try {
               this.server.unregisterMBean(cachedj2eeObjectNames[i]);
            } catch (MBeanRegistrationException var5) {
               throw new AssertionError("MBean Registration failed" + var5);
            } catch (OperationsException var6) {
               throw new AssertionError("Failure in creating MBean Adaptor" + var6);
            }
         }

      }
   }

   public String[] getQueriedNames(ObjectName pattern) {
      Set returnObjects = this.server.queryNames(pattern, (QueryExp)null);
      return toStringArray(returnObjects);
   }

   public String[] getQueriedNames(QueryExp query) {
      Set returnObjects = this.server.queryNames((ObjectName)null, query);
      return toStringArray(returnObjects);
   }

   public static String[] toStringArray(Set objectNameSet) {
      Set stringNames = new HashSet();
      ObjectName objectName = null;
      Iterator it = objectNameSet.iterator();

      while(it.hasNext()) {
         objectName = (ObjectName)it.next();
         stringNames.add(objectName.getCanonicalName());
      }

      return (String[])((String[])stringNames.toArray(new String[0]));
   }

   // $FF: synthetic method
   JMOService(Object x0) {
      this();
   }

   static {
      try {
         RUNTIME_QUERY = new ObjectName("*:*");
      } catch (MalformedObjectNameException var1) {
         throw new Error(var1);
      }
   }

   class MBeanServerListener implements NotificationListener {
      public void handleNotification(Notification notification, Object handback) {
         try {
            this._handleNotification(notification, handback);
         } catch (Throwable var4) {
            ManagementLogger.logCouldNotRegisterMBeanForJSR77(var4);
         }

      }

      private void _handleNotification(Notification notification, Object handback) {
         MBeanServerNotification serverNotification = null;
         ObjectName wlsObjectName = null;
         if (notification instanceof MBeanServerNotification) {
            serverNotification = (MBeanServerNotification)notification;
            wlsObjectName = serverNotification.getMBeanName();
            boolean isRegister = serverNotification.getType().equals("JMX.mbean.registered");
            if (isRegister) {
               NotificationHandler handler = new NotificationHandler(wlsObjectName, JMOService.this.defaultDomain);
               if (!handler.registerThisObject()) {
                  if (JMOService.debug.isDebugEnabled()) {
                     JMOService.debug.debug("Not Registering:    " + serverNotification.getMBeanName().getCanonicalName());
                  }

                  return;
               }

               JMOService.this.registerMBean(handler);
            } else {
               JMOService.this.unregisterMBean(wlsObjectName);
            }
         }

      }
   }

   private static class SINGLETON {
      static JMOService service = new JMOService();
   }
}
