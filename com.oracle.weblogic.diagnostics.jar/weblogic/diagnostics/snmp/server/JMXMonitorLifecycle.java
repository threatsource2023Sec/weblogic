package weblogic.diagnostics.snmp.server;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.management.Attribute;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

public abstract class JMXMonitorLifecycle {
   static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final String LOCATION_KEY = "Location";
   static final String SNMP_DOMAIN = "com.bea.wls.snmp";
   static final String OBJECT_NAME_PREFIX = "com.bea.wls.snmp:";
   private static BeanInfoAccess beanInfoAccess;
   boolean adminServer;
   SNMPAgent snmpAgent;
   String serverName;
   MBeanServerConnection mbeanServerConnection;
   List monitorListenerList = new LinkedList();
   Map monitorListenerRegistry = new HashMap();
   protected boolean deregisterMonitorListener = false;

   public JMXMonitorLifecycle(boolean adminServer, String serverName, SNMPAgent snmpAgent, MBeanServerConnection mbeanServerConnection) {
      this.adminServer = adminServer;
      this.snmpAgent = snmpAgent;
      this.serverName = serverName;
      this.mbeanServerConnection = mbeanServerConnection;
   }

   protected ObjectName getMonitorObjectName(ObjectName objName, JMXMonitorListener listener, String monitorType) throws MalformedObjectNameException {
      Hashtable keys = objName.getKeyPropertyList();
      String location = objName.getKeyProperty("Location");
      String monitorName = listener.getName() + "-" + listener.getAttributeName();
      if (location != null && location.length() > 0) {
         keys.remove("Location");
         monitorName = monitorName + "-" + location;
      }

      keys.put(monitorType, monitorName);
      ObjectName monitor = new ObjectName("com.bea.wls.snmp", keys);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Creating MBean " + monitor);
      }

      return monitor;
   }

   void registerMonitorListener(ObjectName objectName, JMXMonitorListener listener, Object handback) throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Registering listener: this=" + this + " objectName=" + objectName + " listener=" + listener + " handback=" + handback);
      }

      this.mbeanServerConnection.addNotificationListener(objectName, listener, listener, handback);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Registered listener succesfully: this=" + this + " objectName=" + objectName + " listener=" + listener + " handback=" + handback);
      }

      List listeners = null;
      synchronized(this.monitorListenerRegistry) {
         listeners = (List)this.monitorListenerRegistry.get(objectName);
         if (listeners == null) {
            listeners = new LinkedList();
            this.monitorListenerRegistry.put(objectName, listeners);
         }
      }

      synchronized(listeners) {
         ((List)listeners).add(listener);
      }
   }

   void serverStarted(String serverName) {
   }

   void serverStopped(String serverName) {
   }

   Iterator getJMXMonitorListeners() {
      List result = new LinkedList();
      synchronized(this.monitorListenerList) {
         result.addAll(this.monitorListenerList);
      }

      return result.iterator();
   }

   abstract void initializeMonitorListenerList(SNMPAgentMBean var1) throws Exception;

   void registerMonitorListeners(ObjectName on) {
      synchronized(this.monitorListenerList) {
         Iterator i = this.monitorListenerList.iterator();

         while(i.hasNext()) {
            JMXMonitorListener jm = (JMXMonitorListener)i.next();
            ObjectName query = jm.getQueryExpression();
            if (query != null && query.apply(on)) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Registering JMXMonitor for " + on + " as it matches " + jm.getQueryExpression());
               }

               this.registerMonitor(on, jm);
            }
         }

      }
   }

   abstract void registerMonitor(ObjectName var1, JMXMonitorListener var2);

   void deregisterMonitorListeners() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("De-registering monitor listeners on " + this);
      }

      synchronized(this.monitorListenerRegistry) {
         Iterator it = this.monitorListenerRegistry.keySet().iterator();

         while(true) {
            if (!it.hasNext()) {
               break;
            }

            ObjectName on = (ObjectName)it.next();
            this.deregisterMonitorListeners(on, false);
            it.remove();
         }
      }

      this.monitorListenerList.clear();
   }

   void deregisterMonitorListeners(ObjectName on) {
      this.deregisterMonitorListeners(on, true);
   }

   protected void deregisterMonitorListener(ObjectName on, JMXMonitorListener listener) {
      if (this.deregisterMonitorListener) {
         try {
            if (this.mbeanServerConnection.isRegistered(on)) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Unregistering NotificationListener " + listener + " on " + on);
               }

               this.mbeanServerConnection.removeNotificationListener(on, listener);
            }
         } catch (Throwable var4) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Exception deregistering listener", var4);
            }
         }

      }
   }

   private List getMonitorsForObservedInstance(ObjectName on) {
      List list = new ArrayList();
      Iterator it = this.monitorListenerRegistry.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         List listeners = (List)this.monitorListenerRegistry.get(key);
         boolean doInclude = false;
         Iterator it1 = listeners.iterator();

         while(!doInclude && it1.hasNext()) {
            JMXMonitorListener jm = (JMXMonitorListener)it1.next();
            ObjectName query = jm.getQueryExpression();
            if (jm.getMonitor() != null && query != null && query.apply(on)) {
               doInclude = true;
            }
         }

         if (doInclude) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Monitor " + key + " observes an attribute on " + on);
            }

            list.add(key);
         }
      }

      return list;
   }

   private void deregisterMonitorListeners(ObjectName on, boolean removeKey) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("deregisterMonitorListeners " + on);
      }

      List listeners = null;
      List observingMonitors = null;
      synchronized(this.monitorListenerRegistry) {
         listeners = (List)this.monitorListenerRegistry.get(on);
         if (listeners == null) {
            observingMonitors = this.getMonitorsForObservedInstance(on);
         }
      }

      if (listeners == null) {
         if (observingMonitors != null) {
            Iterator it = observingMonitors.iterator();

            while(it.hasNext()) {
               ObjectName mon = (ObjectName)it.next();
               this.deregisterMonitorListeners(mon, removeKey);
            }
         }

      } else {
         synchronized(listeners) {
            Iterator i = listeners.iterator();

            label85:
            while(true) {
               JMXMonitorListener l;
               ObjectName m;
               do {
                  if (!i.hasNext()) {
                     break label85;
                  }

                  l = (JMXMonitorListener)i.next();
                  this.deregisterMonitorListener(on, l);
                  m = l.getMonitor();
               } while(m == null);

               try {
                  if (this.mbeanServerConnection.isRegistered(m)) {
                     this.mbeanServerConnection.invoke(m, "stop", new Object[0], new String[0]);
                  }
               } catch (Throwable var13) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Exception stopping monitor", var13);
                  }
               }

               try {
                  if (this.mbeanServerConnection.isRegistered(m)) {
                     if (DEBUG.isDebugEnabled()) {
                        DEBUG.debug("Unregistering NotificationListener " + l + " for " + m);
                     }

                     this.mbeanServerConnection.removeNotificationListener(m, l);
                     this.mbeanServerConnection.unregisterMBean(m);
                  }
               } catch (Throwable var14) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Exception deregistering listener", var14);
                  }
               }
            }
         }

         if (removeKey) {
            synchronized(this.monitorListenerRegistry) {
               this.monitorListenerRegistry.remove(on);
            }
         }

      }
   }

   private void removeObservedInstances(ObjectName monitor) {
      try {
         ObjectName[] observedInstances = (ObjectName[])((ObjectName[])this.mbeanServerConnection.invoke(monitor, "getObservedObjects", new Object[]{monitor}, new String[]{"javax.management.ObjectName"}));
         int size = observedInstances != null ? observedInstances.length : 0;

         for(int i = 0; i < size; ++i) {
            this.mbeanServerConnection.invoke(monitor, "removeObservedObject", new Object[]{observedInstances[i]}, new String[]{"javax.management.ObjectName"});
         }

         this.mbeanServerConnection.setAttribute(monitor, new Attribute("ObservedAttribute", (Object)null));
      } catch (Exception var5) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception while removing observed instance", var5);
         }
      }

   }

   protected Number getNumber(String mbeanType, String attributeName, double value) throws IllegalArgumentException {
      try {
         Number number = new Double(value);
         PropertyDescriptor pd = getPropertyDescriptor(mbeanType, attributeName);
         Class clz = pd.getPropertyType();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Class = " + clz + " for " + mbeanType + "." + attributeName);
         }

         if (clz == Double.TYPE) {
            return number.doubleValue();
         } else if (clz == Float.TYPE) {
            return number.floatValue();
         } else {
            return (Number)(clz == Long.TYPE ? number.longValue() : number.intValue());
         }
      } catch (Throwable var8) {
         throw new IllegalArgumentException(var8);
      }
   }

   private static PropertyDescriptor getPropertyDescriptor(String type, String attr) {
      BeanInfo bi = getBeanInfo(type);
      if (bi == null) {
         throw new IllegalArgumentException();
      } else {
         PropertyDescriptor[] propDescriptors = bi.getPropertyDescriptors();
         if (propDescriptors != null) {
            for(int i = 0; i < propDescriptors.length; ++i) {
               PropertyDescriptor pd = propDescriptors[i];
               String property = pd.getName();
               if (property != null && property.equals(attr)) {
                  return pd;
               }
            }
         }

         throw new IllegalArgumentException();
      }
   }

   private static BeanInfo getBeanInfo(String type) {
      if (beanInfoAccess == null) {
         beanInfoAccess = ManagementService.getBeanInfoAccess();
      }

      String version = VersionInfo.theOne().getReleaseVersion();
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(type, true, version);
      String rtType;
      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.configuration." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, true, version);
      }

      if (beanInfo == null && type.indexOf(".") == -1) {
         rtType = "weblogic.management.runtime." + type + "MBean";
         beanInfo = beanInfoAccess.getBeanInfoForInterface(rtType, true, version);
      }

      return beanInfo;
   }
}
