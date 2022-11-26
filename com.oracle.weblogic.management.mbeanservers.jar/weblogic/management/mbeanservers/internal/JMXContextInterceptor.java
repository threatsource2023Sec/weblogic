package weblogic.management.mbeanservers.internal;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Locale;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerDelegate;
import javax.management.MBeanServerNotification;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.utils.MBeanInfoLocalizationHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class JMXContextInterceptor extends WLSMBeanServerInterceptorBase {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugJMXContext");
   private boolean isDomainRuntime;
   private String domainName;
   private final Map mbeansContextLoaderCache_ = new ConcurrentWeakHashMap();
   private ReferenceQueue referenceQueue_ = new ReferenceQueue();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JMXContextInterceptor() {
      this.isDomainRuntime = false;
   }

   public JMXContextInterceptor(String domainName) {
      this.isDomainRuntime = true;
      this.domainName = domainName;
   }

   public void setNextMBeanServerConnection(MBeanServerConnection next) {
      super.setNextMBeanServerConnection(next);

      try {
         next.addNotificationListener(MBeanServerDelegate.DELEGATE_NAME, new RegistrationUnRegistrationMBeanServerNotificationListener(), new RegistrationUnRegistrationMBeanServerNotificationFilter(), new LocalNotificationHandback());
      } catch (Exception var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextInterceptor.setNextMBeanServerConnection(MBeanServerConnection next). Error registering listener with MBeanServerDelegate: " + var3.getMessage());
         }

         throw new RuntimeException(var3);
      }
   }

   public Object getAttribute(ObjectName objectName, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      ClassLoader origContextLoader = null;
      if (this.isLocalizable(objectName)) {
         ClassLoader contextLoader = this.getMBeanContextLoader(objectName);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }
      }

      Object var8;
      try {
         var8 = super.getAttribute(objectName, s);
      } finally {
         if (origContextLoader != null) {
            Thread.currentThread().setContextClassLoader(origContextLoader);
         }

      }

      return var8;
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      ClassLoader origContextLoader = null;
      if (this.isLocalizable(objectName)) {
         ClassLoader contextLoader = this.getMBeanContextLoader(objectName);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }
      }

      AttributeList var8;
      try {
         var8 = super.getAttributes(objectName, strings);
      } finally {
         if (origContextLoader != null) {
            Thread.currentThread().setContextClassLoader(origContextLoader);
         }

      }

      return var8;
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      ClassLoader origContextLoader = null;
      if (this.isLocalizable(objectName)) {
         ClassLoader contextLoader = this.getMBeanContextLoader(objectName);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }
      }

      try {
         super.setAttribute(objectName, attribute);
      } finally {
         if (origContextLoader != null) {
            Thread.currentThread().setContextClassLoader(origContextLoader);
         }

      }

   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      ClassLoader origContextLoader = null;
      if (this.isLocalizable(objectName)) {
         ClassLoader contextLoader = this.getMBeanContextLoader(objectName);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }
      }

      Object var10;
      try {
         var10 = super.invoke(objectName, s, objects, strings);
      } finally {
         if (origContextLoader != null) {
            Thread.currentThread().setContextClassLoader(origContextLoader);
         }

      }

      return var10;
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      ClassLoader origContextLoader = null;
      if (this.isLocalizable(objectName)) {
         ClassLoader contextLoader = this.getMBeanContextLoader(objectName);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }
      }

      AttributeList var5;
      try {
         var5 = super.setAttributes(objectName, attributeList);
      } finally {
         if (origContextLoader != null) {
            Thread.currentThread().setContextClassLoader(origContextLoader);
         }

      }

      return var5;
   }

   public MBeanInfo getMBeanInfo(ObjectName objectname) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      if (!this.isLocalizable(objectname)) {
         return super.getMBeanInfo(objectname);
      } else {
         ClassLoader origContextLoader = null;
         ClassLoader contextLoader = this.getMBeanContextLoader(objectname);
         if (contextLoader != null) {
            origContextLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(contextLoader);
         }

         MBeanInfo var6;
         try {
            MBeanInfo mbeanInfo = super.getMBeanInfo(objectname);
            Locale locale = this.getLocale();
            if (locale != null) {
               var6 = MBeanInfoLocalizationHelper.localizeMBeanInfo(mbeanInfo, locale);
               return var6;
            }

            var6 = mbeanInfo;
         } finally {
            if (origContextLoader != null) {
               Thread.currentThread().setContextClassLoader(origContextLoader);
            }

         }

         return var6;
      }
   }

   private boolean isLocalizable(ObjectName objectName) {
      return objectName == null || !"com.bea".equals(objectName.getDomain()) && !"Security".equals(objectName.getDomain()) && !this.isDelegate(objectName);
   }

   private boolean isDelegate(ObjectName objectname) {
      if (this.isDomainRuntime && objectname != null) {
         String location = objectname.getKeyProperty("Location");
         if (location != null && location.length() > 0 && !location.equals(this.domainName)) {
            return true;
         }
      }

      return false;
   }

   private Locale getLocale() {
      Locale locale = null;

      try {
         locale = getThreadLocale();
      } catch (PrivilegedActionException var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextInterceptor.getLocale(): Privileged action violation: " + var3.getMessage());
         }
      }

      return locale;
   }

   public static Locale getThreadLocale() throws PrivilegedActionException {
      JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
      return jmxContext != null ? jmxContext.getLocale() : null;
   }

   public ObjectInstance registerMBean(Object object, ObjectName name) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
      ObjectInstance ret = super.registerMBean(object, name);
      this.cleanupCache();
      return ret;
   }

   public void unregisterMBean(ObjectName objectName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      super.unregisterMBean(objectName);
   }

   private ClassLoader getMBeanContextLoader(ObjectName mbeanName) {
      ClassLoader mbeanContextLoader = null;
      if (mbeanName == null) {
         return null;
      } else {
         InternalWeakReference contextLoaderRef = (InternalWeakReference)this.mbeansContextLoaderCache_.get(mbeanName);
         if (contextLoaderRef != null) {
            mbeanContextLoader = (ClassLoader)contextLoaderRef.get();
            if (mbeanContextLoader == null) {
               this.mbeansContextLoaderCache_.remove(mbeanName);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("JMXContextInterceptor.getMBeanContextLoader(objectName mbeanName): MBean associated ClassLoader was recycled, prior to MBean named: \"" + mbeanName + "\" was unregistered. This mostly indicates improper unregistration life-cycle.");
               }
            }
         } else if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextInterceptor.getMBeanContextLoader(objectName mbeanName): Could not find ClassLoader associated with MBean named: \"" + mbeanName + "\"");
         }

         return mbeanContextLoader;
      }
   }

   private synchronized void cleanupCache() {
      InternalWeakReference ref = (InternalWeakReference)InternalWeakReference.class.cast(this.referenceQueue_.poll());

      while(ref != null) {
         ObjectName associatedMBean = ref.getObjectName();
         if (associatedMBean != null) {
            this.mbeansContextLoaderCache_.remove(associatedMBean);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("JMXContextInterceptor.cleanupCache: MBean associated ClassLoader was recycled, prior to MBean named: \"" + associatedMBean + "\" was unregistered. This mostly indicates improper MBean unregistration life-cycle.");
            }

            ref = (InternalWeakReference)InternalWeakReference.class.cast(this.referenceQueue_.poll());
         }
      }

   }

   private class RegistrationUnRegistrationMBeanServerNotificationListener implements NotificationListener {
      private RegistrationUnRegistrationMBeanServerNotificationListener() {
      }

      public void handleNotification(Notification notification, Object handback) {
         MBeanServerNotification notif = (MBeanServerNotification)MBeanServerNotification.class.cast(notification);
         if ("JMX.mbean.registered".equals(notif.getType())) {
            InternalWeakReference ref = JMXContextInterceptor.this.new InternalWeakReference(Thread.currentThread().getContextClassLoader(), notif.getMBeanName());
            JMXContextInterceptor.this.mbeansContextLoaderCache_.put(notif.getMBeanName(), ref);
         }

         if ("JMX.mbean.unregistered".equals(notif.getType())) {
            JMXContextInterceptor.this.mbeansContextLoaderCache_.remove(notif.getMBeanName());
         }

      }

      // $FF: synthetic method
      RegistrationUnRegistrationMBeanServerNotificationListener(Object x1) {
         this();
      }
   }

   private class RegistrationUnRegistrationMBeanServerNotificationFilter implements NotificationFilter {
      private RegistrationUnRegistrationMBeanServerNotificationFilter() {
      }

      public boolean isNotificationEnabled(Notification notification) {
         if (notification instanceof MBeanServerNotification && ("JMX.mbean.registered".equals(notification.getType()) || "JMX.mbean.unregistered".equals(notification.getType()))) {
            ObjectName oname = ((MBeanServerNotification)MBeanServerNotification.class.cast(notification)).getMBeanName();
            if (JMXContextInterceptor.this.isLocalizable(oname)) {
               return true;
            }
         }

         return false;
      }

      // $FF: synthetic method
      RegistrationUnRegistrationMBeanServerNotificationFilter(Object x1) {
         this();
      }
   }

   private class InternalWeakReference extends WeakReference {
      private WeakReference associatedMBean_;

      InternalWeakReference(ClassLoader referent, ObjectName associatedMBean) {
         super(referent, JMXContextInterceptor.this.referenceQueue_);
         this.associatedMBean_ = new WeakReference(associatedMBean);
      }

      public ObjectName getObjectName() {
         return (ObjectName)this.associatedMBean_.get();
      }
   }
}
