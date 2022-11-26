package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerNotification;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.mbeanservers.internal.LocalNotificationHandback;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class FederatedMBeanServerDelegate extends MBeanServerDelegate {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   MBeanServerConnectionManager connectionManager;
   MBeanServerDelegate wrappedDelegate;
   int registeredListeners;
   boolean managingListeners;
   private final Map listenersByServerMap = new ConcurrentHashMap();
   private WeakHashMap localListeners = new WeakHashMap();
   static final ObjectName ALLMBEANS;
   static final ObjectName MBEANSERVERDELEGATE;

   public FederatedMBeanServerDelegate(MBeanServerDelegate wrappedDelegate, MBeanServerConnectionManager connectionManager) {
      this.wrappedDelegate = wrappedDelegate;
      this.connectionManager = connectionManager;
      connectionManager.addCallback(this.createConnectionListener());
   }

   private MBeanServerConnectionManager.MBeanServerConnectionListener createConnectionListener() {
      return new MBeanServerConnectionManager.MBeanServerConnectionListener() {
         public void connect(final String serverName, final MBeanServerConnection connection) {
            FederatedMBeanServerDelegate.kernelId.doAs(FederatedMBeanServerDelegate.kernelId, new PrivilegedAction() {
               public Object run() {
                  _connect(serverName, connection);
                  return null;
               }
            });
         }

         private void _connect(String serverName, MBeanServerConnection connection) {
            try {
               if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                  FederatedMBeanServerDelegate.debug.debug("MBeanServerDelegate: Querying Managed Server");
               }

               if (FederatedMBeanServerDelegate.this.isManagingListeners()) {
                  if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                     FederatedMBeanServerDelegate.debug.debug("MBeanServerDelegate: There are registered listeners");
                  }

                  FederatedNotificationListener listener = FederatedMBeanServerDelegate.this.createRegistrationListener(connection, FederatedMBeanServerDelegate.this.connectionManager.isManagedServerNotificationsDisabled());
                  FederatedMBeanServerDelegate.this.listenersByServerMap.put(serverName, listener);
                  if (!FederatedMBeanServerDelegate.this.connectionManager.isManagedServerNotificationListenersDisabled()) {
                     Set result = connection.queryNames(FederatedMBeanServerDelegate.ALLMBEANS, (QueryExp)null);
                     Iterator it = result.iterator();

                     while(it.hasNext()) {
                        ObjectName objectName = (ObjectName)it.next();
                        Notification notif = new MBeanServerNotification("JMX.mbean.registered", FederatedMBeanServerDelegate.MBEANSERVERDELEGATE, 0L, objectName);
                        FederatedMBeanServerDelegate.this.sendNotification(notif);
                        if (!FederatedMBeanServerDelegate.this.connectionManager.isManagedServerNotificationsDisabled()) {
                           listener.addRegisteredObject(objectName);
                        }
                     }
                  }

                  try {
                     connection.addNotificationListener(FederatedMBeanServerDelegate.MBEANSERVERDELEGATE, listener, (NotificationFilter)null, (Object)null);
                  } catch (InstanceNotFoundException var8) {
                     throw new Error(var8);
                  }
               }
            } catch (IOException var9) {
               if (var9.getCause() != null && "weblogic.socket.MaxMessageSizeExceededException".equals(var9.getCause().getClass().getName())) {
                  JMXLogger.logErrorAllMBeansQueryNames(var9.getCause().getMessage());
               }

               if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                  FederatedMBeanServerDelegate.debug.debug("Failed Connection to Managed MBean Server ", var9);
               }
            }

         }

         public void disconnect(String serverName) {
            if (FederatedMBeanServerDelegate.this.hasRegisteredListeners()) {
               FederatedNotificationListener listener = (FederatedNotificationListener)FederatedMBeanServerDelegate.this.listenersByServerMap.remove(serverName);
               if (listener != null) {
                  listener.unregisterAll();
               }
            }

         }
      };
   }

   private FederatedNotificationListener createRegistrationListener(final MBeanServerConnection MBSConnection, final boolean disableManagedNotifications) {
      return new FederatedNotificationListener() {
         Set registeredObjectNames = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap(1024)));
         Set unmatchedObjectNames = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap(1024)));
         int numNotifications = 0;
         final MBeanServerConnection connection = MBSConnection;
         static final int REFRESH_LEVEL = 10000;
         final boolean disableNotifications = disableManagedNotifications;

         public void handleNotification(Notification notification, Object handback) {
            if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
               FederatedMBeanServerDelegate.debug.debug("MBeanServerDelegate: handleNotification" + notification);
            }

            MBeanServerNotification mbsn;
            String type;
            if (!this.disableNotifications && notification instanceof MBeanServerNotification) {
               mbsn = (MBeanServerNotification)notification;
               type = mbsn.getType();
               if (type.equals("JMX.mbean.registered")) {
                  if (this.unmatchedObjectNames.remove(mbsn.getMBeanName())) {
                     if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                        FederatedMBeanServerDelegate.debug.debug("Skipping registration for previous unregistered notifications " + mbsn.getMBeanName());
                     }
                  } else {
                     ++this.numNotifications;
                     this.registeredObjectNames.add(mbsn.getMBeanName());
                     if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                        FederatedMBeanServerDelegate.debug.debug("Registered " + mbsn.getMBeanName());
                     }
                  }
               } else if (type.equals("JMX.mbean.unregistered")) {
                  if (this.registeredObjectNames.remove(mbsn.getMBeanName())) {
                     --this.numNotifications;
                  } else {
                     this.unmatchedObjectNames.add(mbsn.getMBeanName());
                  }

                  if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                     FederatedMBeanServerDelegate.debug.debug("Unregistered " + mbsn.getMBeanName());
                  }
               }
            }

            if (notification instanceof MBeanServerNotification) {
               mbsn = (MBeanServerNotification)notification;
               type = mbsn.getType();
               if (type.equals("JMX.mbean.unregistered")) {
                  try {
                     this.connection.removeNotificationListener(mbsn.getMBeanName(), this);
                  } catch (Exception var8) {
                  }
               }
            }

            notification.setSequenceNumber(0L);
            FederatedMBeanServerDelegate.this.sendNotification(notification);
            if (!this.disableNotifications) {
               if (this.numNotifications > 10000) {
                  synchronized(this.registeredObjectNames) {
                     if (this.numNotifications > 10000) {
                        this.numNotifications = 0;
                        this.registeredObjectNames.clear();
                        this.unmatchedObjectNames.clear();
                        if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                           FederatedMBeanServerDelegate.debug.debug("Clearing out registered object names and refreshing cache.");
                        }

                        try {
                           Set result = this.connection.queryNames(FederatedMBeanServerDelegate.ALLMBEANS, (QueryExp)null);
                           Iterator it = result.iterator();

                           while(it.hasNext()) {
                              ObjectName objectName = (ObjectName)it.next();
                              this.registeredObjectNames.add(objectName);
                           }
                        } catch (Exception var9) {
                           if (var9.getCause() != null && "weblogic.socket.MaxMessageSizeExceededException".equals(var9.getCause().getClass().getName())) {
                              JMXLogger.logErrorAllMBeansQueryNames(var9.getCause().getMessage());
                           }

                           if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                              FederatedMBeanServerDelegate.debug.debug("Failed query to Managed MBean Server ", var9);
                           }
                        }
                     }
                  }
               }

            }
         }

         public void addRegisteredObject(ObjectName name) {
            if (!this.disableNotifications) {
               this.registeredObjectNames.add(name);
            }
         }

         public void unregisterAll() {
            if (!this.disableNotifications) {
               synchronized(this.registeredObjectNames) {
                  Iterator it = this.registeredObjectNames.iterator();

                  while(it.hasNext()) {
                     ObjectName objectName = (ObjectName)it.next();
                     Notification notif = new MBeanServerNotification("JMX.mbean.unregistered", FederatedMBeanServerDelegate.MBEANSERVERDELEGATE, 0L, objectName);
                     FederatedMBeanServerDelegate.this.sendNotification(notif);
                  }

               }
            }
         }
      };
   }

   public String getMBeanServerId() {
      return this.wrappedDelegate.getMBeanServerId();
   }

   public String getSpecificationName() {
      return this.wrappedDelegate.getSpecificationName();
   }

   public String getSpecificationVersion() {
      return this.wrappedDelegate.getSpecificationVersion();
   }

   public String getSpecificationVendor() {
      return this.wrappedDelegate.getSpecificationVendor();
   }

   public String getImplementationName() {
      return this.wrappedDelegate.getImplementationName();
   }

   public String getImplementationVersion() {
      return this.wrappedDelegate.getImplementationVersion();
   }

   public String getImplementationVendor() {
      return this.wrappedDelegate.getImplementationVendor();
   }

   public synchronized void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
      super.removeNotificationListener(listener, filter, handback);
      this.decrementRegisteredListeners();
   }

   private synchronized boolean isManagingListeners() {
      return this.managingListeners;
   }

   private synchronized boolean hasRegisteredListeners() {
      return this.registeredListeners != 0;
   }

   private synchronized void incrementRegisteredListeners() {
      ++this.registeredListeners;
      if (this.registeredListeners == 1 && !this.isManagingListeners()) {
         kernelId.doAs(kernelId, new PrivilegedAction() {
            public Object run() {
               FederatedMBeanServerDelegate.this.registerManagedListeners();
               return null;
            }
         });
      }

      this.managingListeners = true;
   }

   private synchronized void decrementRegisteredListeners() {
      --this.registeredListeners;
   }

   public synchronized void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
      super.addNotificationListener(listener, filter, handback);
      if (handback instanceof LocalNotificationHandback) {
         this.localListeners.put(listener, handback);
      } else {
         this.incrementRegisteredListeners();
      }
   }

   private void registerManagedListeners() {
      try {
         this.connectionManager.iterateConnections(new MBeanServerConnectionManager.ConnectionCallback() {
            public void connection(MBeanServerConnection connection) throws IOException {
               try {
                  FederatedNotificationListener listener = FederatedMBeanServerDelegate.this.createRegistrationListener(connection, FederatedMBeanServerDelegate.this.connectionManager.isManagedServerNotificationsDisabled());
                  if (!FederatedMBeanServerDelegate.this.connectionManager.isManagedServerNotificationsDisabled()) {
                     try {
                        Set result = connection.queryNames(FederatedMBeanServerDelegate.ALLMBEANS, (QueryExp)null);
                        Iterator it = result.iterator();

                        while(it.hasNext()) {
                           ObjectName objectName = (ObjectName)it.next();
                           listener.addRegisteredObject(objectName);
                        }
                     } catch (Exception var6) {
                        if (FederatedMBeanServerDelegate.debug.isDebugEnabled()) {
                           FederatedMBeanServerDelegate.debug.debug("Failed query to Managed MBean Server ", var6);
                        }
                     }
                  }

                  connection.addNotificationListener(FederatedMBeanServerDelegate.MBEANSERVERDELEGATE, listener, (NotificationFilter)null, (Object)null);
               } catch (InstanceNotFoundException var7) {
                  throw new AssertionError(var7);
               }
            }
         }, false);
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   public synchronized void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
      super.removeNotificationListener(listener);
      if (this.localListeners.containsKey(listener)) {
         this.localListeners.remove(listener);
      } else {
         this.decrementRegisteredListeners();
      }
   }

   public MBeanNotificationInfo[] getNotificationInfo() {
      return this.wrappedDelegate.getNotificationInfo();
   }

   static {
      try {
         ALLMBEANS = new ObjectName("*:*");
         MBEANSERVERDELEGATE = new ObjectName("JMImplementation:type=MBeanServerDelegate");
      } catch (MalformedObjectNameException var1) {
         throw new Error(var1);
      }
   }

   interface FederatedNotificationListener extends NotificationListener {
      void addRegisteredObject(ObjectName var1);

      void unregisterAll();
   }
}
