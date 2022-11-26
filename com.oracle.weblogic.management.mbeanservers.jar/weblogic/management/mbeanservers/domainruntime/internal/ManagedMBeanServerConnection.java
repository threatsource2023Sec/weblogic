package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
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
import javax.management.MBeanServerNotification;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanNotification;

public class ManagedMBeanServerConnection implements MBeanServerConnection {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   ManagedMBeanServerObjectNameManager objectNameManager = null;
   MBeanServerConnection mbeanServerConnection;
   JMXExecutor executor;
   String location;
   boolean disableNotifications;
   boolean disableDelegateNotifications = Boolean.getBoolean("weblogic.disableDelegateNotifications");
   Map listenerToRelayListenerMap = Collections.synchronizedMap(new HashMap());

   public ManagedMBeanServerConnection(MBeanServerConnection mbs, String location, JMXExecutor executor, boolean disableNotif) {
      this.mbeanServerConnection = mbs;
      this.objectNameManager = new ManagedMBeanServerObjectNameManager(location);
      this.executor = executor;
      this.location = location;
      this.disableNotifications = disableNotif;
   }

   public void disconnected() {
      if (this.executor != null) {
         this.executor.cancel();
      }

   }

   public MBeanServerConnection getWrappedConnection() {
      return this.mbeanServerConnection;
   }

   private ObjectName addLocationToObjectName(ObjectName result) {
      if (result != null) {
         String loc = result.getKeyProperty("Location");
         if (loc != null && !loc.equalsIgnoreCase(this.location)) {
            return result;
         }
      }

      return this.objectNameManager.lookupScopedObjectName(result);
   }

   private ObjectInstance addLocationToObjectInstance(ObjectInstance result) {
      return new ObjectInstance(this.addLocationToObjectName(result.getObjectName()), result.getClassName());
   }

   private Object addLocationToResult(Object result) {
      if (result instanceof ObjectName) {
         return this.addLocationToObjectName((ObjectName)result);
      } else if (!(result instanceof ObjectName[])) {
         return result;
      } else {
         ObjectName[] resultArray = (ObjectName[])((ObjectName[])result);

         for(int i = 0; i < resultArray.length; ++i) {
            resultArray[i] = this.addLocationToObjectName(resultArray[i]);
         }

         return resultArray;
      }
   }

   private AttributeList addLocationToAttributeList(AttributeList attributes) {
      AttributeList resultList = new AttributeList();
      if (attributes == null) {
         return resultList;
      } else {
         ListIterator it = attributes.listIterator();

         while(it.hasNext()) {
            Attribute attr = (Attribute)it.next();
            Object result = this.addLocationToResult(attr.getValue());
            Attribute resultAttr = new Attribute(attr.getName(), result);
            resultList.add(resultAttr);
         }

         return resultList;
      }
   }

   private Set addLocationToObjectNameSet(Set result) {
      HashSet resultNoLocation = new HashSet(result.size());
      Iterator it = result.iterator();

      while(it.hasNext()) {
         ObjectName objectName = (ObjectName)it.next();
         resultNoLocation.add(this.addLocationToObjectName(objectName));
      }

      return resultNoLocation;
   }

   private Set addLocationToObjectInstances(Set result) {
      if (result != null && result.size() != 0) {
         HashSet resultNoLocation = new HashSet(result.size());
         Iterator it = result.iterator();

         while(it.hasNext()) {
            ObjectInstance objectInstance = (ObjectInstance)it.next();
            resultNoLocation.add(this.addLocationToObjectInstance(objectInstance));
         }

         return resultNoLocation;
      } else {
         return result;
      }
   }

   private ObjectName removeLocationFromObjectName(ObjectName oname) {
      if (oname == null) {
         return oname;
      } else {
         String loc = oname.getKeyProperty("Location");
         if (loc != null && loc.equalsIgnoreCase(this.location)) {
            ObjectName result = this.objectNameManager.lookupObjectName(oname);
            return result;
         } else {
            return oname;
         }
      }
   }

   private Attribute removeLocationFromAttribute(Attribute attribute) {
      return new Attribute(attribute.getName(), this.removeLocationFromParam(attribute.getValue()));
   }

   private AttributeList removeLocationFromAttributeList(AttributeList attributeList) {
      AttributeList resultList = new AttributeList();
      if (attributeList == null) {
         return resultList;
      } else {
         ListIterator it = attributeList.listIterator();

         while(it.hasNext()) {
            Attribute attr = (Attribute)it.next();
            resultList.add(this.removeLocationFromAttribute(attr));
         }

         return resultList;
      }
   }

   private Object removeLocationFromParam(Object param) {
      if (param instanceof ObjectName) {
         return this.removeLocationFromObjectName((ObjectName)param);
      } else if (!(param instanceof ObjectName[])) {
         return param;
      } else {
         ObjectName[] resultArray = (ObjectName[])((ObjectName[])param);

         for(int j = 0; j < resultArray.length; ++j) {
            resultArray[j] = this.removeLocationFromObjectName(resultArray[j]);
         }

         return resultArray;
      }
   }

   private Object[] removeLocationFromParams(Object[] params) {
      if (params == null) {
         return null;
      } else {
         for(int i = 0; i < params.length; ++i) {
            Object param = params[i];
            params[i] = this.removeLocationFromParam(param);
         }

         return params;
      }
   }

   private QueryExp processQueryExp(QueryExp queryexp) {
      return queryexp;
   }

   public ObjectInstance createMBean(String className, ObjectName name) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance result = this.mbeanServerConnection.createMBean(className, this.removeLocationFromObjectName(name));
      return new ObjectInstance(this.addLocationToObjectName(result.getObjectName()), result.getClassName());
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectInstance result = this.mbeanServerConnection.createMBean(className, this.removeLocationFromObjectName(name), loaderName);
      return new ObjectInstance(this.addLocationToObjectName(result.getObjectName()), result.getClassName());
   }

   public ObjectInstance createMBean(String className, ObjectName name, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance result = this.mbeanServerConnection.createMBean(className, this.removeLocationFromObjectName(name), params, signature);
      return new ObjectInstance(this.addLocationToObjectName(result.getObjectName()), result.getClassName());
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectInstance result = this.mbeanServerConnection.createMBean(className, this.removeLocationFromObjectName(name), loaderName, params, signature);
      return this.addLocationToObjectInstance(result);
   }

   public void unregisterMBean(ObjectName objectname) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      this.mbeanServerConnection.unregisterMBean(this.removeLocationFromObjectName(objectname));
      this.objectNameManager.unregisterObjectInstance(objectname);
   }

   public ObjectInstance getObjectInstance(ObjectName objectname) throws InstanceNotFoundException, IOException {
      return this.addLocationToObjectInstance(this.mbeanServerConnection.getObjectInstance(this.removeLocationFromObjectName(objectname)));
   }

   public Set queryMBeans(ObjectName objectname, QueryExp queryexp) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("queryMBeans called with canonical name: " + (objectname == null ? null : objectname.getCanonicalName()));
      }

      Set result = this.mbeanServerConnection.queryMBeans(this.removeLocationFromObjectName(objectname), this.processQueryExp(queryexp));
      return this.addLocationToObjectInstances(result);
   }

   public Set queryNames(ObjectName objectname, QueryExp queryexp) throws IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("queryNames called with canonical name: " + (objectname == null ? null : objectname.getCanonicalName()));
      }

      Set result = this.mbeanServerConnection.queryNames(this.removeLocationFromObjectName(objectname), this.processQueryExp(queryexp));
      return this.addLocationToObjectNameSet(result);
   }

   public boolean isRegistered(ObjectName objectname) throws IOException {
      return this.mbeanServerConnection.isRegistered(this.removeLocationFromObjectName(objectname));
   }

   public Integer getMBeanCount() throws IOException {
      return this.mbeanServerConnection.getMBeanCount();
   }

   public Object getAttribute(ObjectName objectname, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      return this.addLocationToResult(this.mbeanServerConnection.getAttribute(this.removeLocationFromObjectName(objectname), s));
   }

   public AttributeList getAttributes(ObjectName objectname, String[] as) throws InstanceNotFoundException, ReflectionException, IOException {
      return this.addLocationToAttributeList(this.mbeanServerConnection.getAttributes(this.removeLocationFromObjectName(objectname), as));
   }

   public void setAttribute(ObjectName objectname, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      this.mbeanServerConnection.setAttribute(this.removeLocationFromObjectName(objectname), this.removeLocationFromAttribute(attribute));
   }

   public AttributeList setAttributes(ObjectName objectname, AttributeList attributelist) throws InstanceNotFoundException, ReflectionException, IOException {
      AttributeList result = this.mbeanServerConnection.setAttributes(this.removeLocationFromObjectName(objectname), this.removeLocationFromAttributeList(attributelist));
      return this.addLocationToAttributeList(result);
   }

   public Object invoke(ObjectName objectname, String s, Object[] aobj, String[] as) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      Object result = this.mbeanServerConnection.invoke(this.removeLocationFromObjectName(objectname), s, this.removeLocationFromParams(aobj), as);
      return this.addLocationToResult(result);
   }

   public String getDefaultDomain() throws IOException {
      return this.mbeanServerConnection.getDefaultDomain();
   }

   public String[] getDomains() throws IOException {
      return this.mbeanServerConnection.getDomains();
   }

   private NotificationListener createRelayNotificationListener(NotificationListener listener) {
      RelayNotificationListener result = new RelayNotificationListener(listener);
      this.listenerToRelayListenerMap.put(listener, result);
      return result;
   }

   private NotificationListener removeRelayNotificationListener(NotificationListener notificationlistener) {
      return (NotificationListener)this.listenerToRelayListenerMap.remove(notificationlistener);
   }

   public void addNotificationListener(ObjectName objectname, NotificationListener notificationlistener, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, IOException {
      if (!this.disableNotifications || FederatedMBeanServerDelegate.MBEANSERVERDELEGATE.equals(objectname) && !this.disableDelegateNotifications) {
         this.mbeanServerConnection.addNotificationListener(this.removeLocationFromObjectName(objectname), this.createRelayNotificationListener(notificationlistener), notificationfilter, obj);
      }
   }

   public void addNotificationListener(ObjectName objectname, ObjectName objectname1, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, IOException {
      if (this.disableNotifications && (!FederatedMBeanServerDelegate.MBEANSERVERDELEGATE.equals(objectname) || this.disableDelegateNotifications)) {
         this.mbeanServerConnection.addNotificationListener(this.removeLocationFromObjectName(objectname), this.removeLocationFromObjectName(objectname1), notificationfilter, obj);
      }

   }

   public void removeNotificationListener(ObjectName objectname, ObjectName objectname1) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.mbeanServerConnection.removeNotificationListener(this.removeLocationFromObjectName(objectname), this.removeLocationFromObjectName(objectname1));
   }

   public void removeNotificationListener(ObjectName objectname, ObjectName objectname1, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.mbeanServerConnection.removeNotificationListener(this.removeLocationFromObjectName(objectname), this.removeLocationFromObjectName(objectname1), notificationfilter, obj);
   }

   public void removeNotificationListener(ObjectName objectname, NotificationListener notificationlistener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      NotificationListener listener = this.removeRelayNotificationListener(notificationlistener);
      this.mbeanServerConnection.removeNotificationListener(this.removeLocationFromObjectName(objectname), listener);
   }

   public void removeNotificationListener(ObjectName objectname, NotificationListener notificationlistener, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      NotificationListener listener = this.removeRelayNotificationListener(notificationlistener);
      this.mbeanServerConnection.removeNotificationListener(this.removeLocationFromObjectName(objectname), listener, notificationfilter, obj);
   }

   public MBeanInfo getMBeanInfo(ObjectName objectname) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      return this.mbeanServerConnection.getMBeanInfo(this.removeLocationFromObjectName(objectname));
   }

   public boolean isInstanceOf(ObjectName objectname, String s) throws InstanceNotFoundException, IOException {
      return this.mbeanServerConnection.isInstanceOf(this.removeLocationFromObjectName(objectname), s);
   }

   private class RelayNotificationListener implements NotificationListener {
      private NotificationListener listener;

      RelayNotificationListener(NotificationListener listener) {
         this.listener = listener;
      }

      public void handleNotification(Notification notification, Object handback) {
         ObjectName remoteName;
         if (notification instanceof MBeanServerNotification) {
            MBeanServerNotification registrationx = (MBeanServerNotification)notification;
            remoteName = ManagedMBeanServerConnection.this.addLocationToObjectName(registrationx.getMBeanName());
            if (notification instanceof WLSCoherenceOrVTMBeanNotification) {
               WLSCoherenceOrVTMBeanNotification wlsNotificationxxx = (WLSCoherenceOrVTMBeanNotification)notification;
               notification = new WLSCoherenceOrVTMBeanNotification(wlsNotificationxxx.getType(), wlsNotificationxxx.getSource(), wlsNotificationxxx.getSequenceNumber(), remoteName, wlsNotificationxxx.getVisibility(), wlsNotificationxxx.getMBeanType(), wlsNotificationxxx.getMBeanInfo(), new HashSet());
            } else if (notification instanceof WLSMBeanNotification) {
               WLSMBeanNotification wlsNotification = (WLSMBeanNotification)notification;
               notification = new WLSMBeanNotification(wlsNotification.getType(), wlsNotification.getSource(), wlsNotification.getSequenceNumber(), remoteName, wlsNotification.getVisibility(), wlsNotification.getMBeanType(), wlsNotification.getMBeanInfo());
            } else {
               notification = new MBeanServerNotification(((Notification)notification).getType(), ((Notification)notification).getSource(), ((Notification)notification).getSequenceNumber(), remoteName);
            }
         } else if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification registration = (AttributeChangeNotification)notification;
            remoteName = ManagedMBeanServerConnection.this.addLocationToObjectName((ObjectName)registration.getSource());
            if (notification instanceof WLSCoherenceOrVTMBeanAttributeChangeNotification) {
               WLSCoherenceOrVTMBeanAttributeChangeNotification wlsNotificationx = (WLSCoherenceOrVTMBeanAttributeChangeNotification)notification;
               notification = new WLSCoherenceOrVTMBeanAttributeChangeNotification(remoteName, wlsNotificationx.getSequenceNumber(), wlsNotificationx.getTimeStamp(), wlsNotificationx.getMessage(), wlsNotificationx.getAttributeName(), wlsNotificationx.getAttributeType(), wlsNotificationx.getOldValue(), wlsNotificationx.getNewValue(), wlsNotificationx.getVisibility(), wlsNotificationx.getMBeanType(), wlsNotificationx.isAttributeVisible(), wlsNotificationx.getMBeanInfo(), new HashSet());
            } else if (notification instanceof WLSMBeanAttributeChangeNotification) {
               WLSMBeanAttributeChangeNotification wlsNotificationxx = (WLSMBeanAttributeChangeNotification)notification;
               notification = new WLSMBeanAttributeChangeNotification(remoteName, wlsNotificationxx.getSequenceNumber(), wlsNotificationxx.getTimeStamp(), wlsNotificationxx.getMessage(), wlsNotificationxx.getAttributeName(), wlsNotificationxx.getAttributeType(), wlsNotificationxx.getOldValue(), wlsNotificationxx.getNewValue(), wlsNotificationxx.getVisibility(), wlsNotificationxx.getMBeanType(), wlsNotificationxx.getMBeanInfo(), wlsNotificationxx.isAttributeVisible());
            } else {
               notification = new AttributeChangeNotification(remoteName, registration.getSequenceNumber(), registration.getTimeStamp(), registration.getMessage(), registration.getAttributeName(), registration.getAttributeType(), registration.getOldValue(), registration.getNewValue());
            }
         } else {
            ((Notification)notification).setSource(ManagedMBeanServerConnection.this.addLocationToResult(((Notification)notification).getSource()));
         }

         if (ManagedMBeanServerConnection.debug.isDebugEnabled()) {
            ManagedMBeanServerConnection.debug.debug("RelayNotificationListener handles Notification: \nType: " + ((Notification)notification).getType() + "\nSource: " + ((Notification)notification).getSource() + "\nMessage: " + ((Notification)notification).getMessage());
         }

         this.listener.handleNotification((Notification)notification, handback);
      }
   }
}
