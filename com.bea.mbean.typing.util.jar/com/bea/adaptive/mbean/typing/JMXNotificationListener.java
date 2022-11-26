package com.bea.adaptive.mbean.typing;

import java.io.IOException;
import java.security.AccessController;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

final class JMXNotificationListener extends RegistrationEventHandler implements NotificationListener {
   static final String MBEANSERVER_DELEGATE_OBJECTNAME = "JMImplementation:type=MBeanServerDelegate";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private MBeanServerConnection mbeanServer;
   private MBeanCategorizer categorizer;

   public JMXNotificationListener(String name, MBeanTypeUtil.RegHandler regHandler, String[] patterns, Executor exec, MBeanServerConnection mbs, MBeanCategorizer cat) {
      super(name, regHandler, patterns, exec);
      this.mbeanServer = mbs;
      this.categorizer = cat;
   }

   public void initialize() throws JMException, IOException {
      this.runInDomainCIC(new Callable() {
         public Throwable call() {
            NotificationFilterSupport filter = new NotificationFilterSupport();
            filter.enableType("JMX.mbean.registered");
            filter.enableType("JMX.mbean.unregistered");
            Throwable error = null;

            try {
               JMXNotificationListener.this.mbeanServer.addNotificationListener(new ObjectName("JMImplementation:type=MBeanServerDelegate"), JMXNotificationListener.this, filter, (Object)null);
            } catch (Throwable var4) {
               error = var4;
            }

            return error;
         }
      });
      Set currentMBeans = this.mbeanServer.queryNames(new ObjectName("*:*"), (QueryExp)null);
      if (currentMBeans != null && currentMBeans.size() != 0) {
         this.initQueue(currentMBeans);
      }
   }

   public void handleNotification(Notification notif, Object handback) {
      if (notif instanceof MBeanServerNotification) {
         MBeanServerNotification n = (MBeanServerNotification)notif;
         ObjectName oName = n.getMBeanName();
         String type = n.getType();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getMessagePrefix() + this.notificationDebugString(n, oName, type));
         }

         try {
            InstanceRegistrationEvent.EventType eventType = InstanceRegistrationEvent.EventType.createEventType(type);
            this.queueEvent(oName, eventType);
         } catch (Throwable var7) {
            this.handleNotificationException(oName, type, var7);
         }
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Received non-MBeanServerNotification type, " + notif.getClass().getCanonicalName());
      }

   }

   void removeRegistrationHandler(final Object id) throws JMException, IOException {
      this.runInDomainCIC(new Callable() {
         public Throwable call() {
            Throwable error = null;

            try {
               JMXNotificationListener.this.mbeanServer.removeNotificationListener(new ObjectName("JMImplementation:type=MBeanServerDelegate"), (NotificationListener)id);
            } catch (Throwable var3) {
               error = var3;
            }

            return error;
         }
      });
   }

   private void handleNotificationException(ObjectName oName, String type, Throwable x) {
      if (type.equals("JMX.mbean.registered")) {
         MBeanTypingUtilLogger.logErrorHandlingMBeanNotification(oName.getCanonicalName(), this.getMessagePrefix(), DebugHelper.mtf.getRegistrationLabel(), x);
      } else if (type.equals("JMX.mbean.unregistered")) {
         MBeanTypingUtilLogger.logErrorHandlingMBeanNotification_Warning(oName.getCanonicalName(), this.getMessagePrefix(), DebugHelper.mtf.getDeregistrationLabel(), x.getMessage());
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getMessagePrefix() + "Encounterd error during MBeanServer notification. Type: " + type + ".  MBean: " + oName + ".");
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getMessagePrefix() + printStackTrace(x));
      }

   }

   protected void newInstance(ObjectName currentMBean) throws Exception {
      String canonicalName = currentMBean.getCanonicalName();
      if (!this.handlesMBean(currentMBean)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getMessagePrefix() + "MBean " + canonicalName + " rejected by categorizer");
         }

      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getMessagePrefix() + "Processing new MBean: " + canonicalName);
         }

         MBeanCategorizer.TypeInfo ti = this.getTypeName(currentMBean);
         String typeName = ti == null ? null : ti.getTypeName();
         String categoryName = ti == null ? null : ti.getCategoryName();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getMessagePrefix() + "TypeName: " + typeName);
         }

         this.getHandler().newInstance(typeName, canonicalName, categoryName);
      }
   }

   private boolean handlesMBean(ObjectName currentMBean) {
      return this.categorizer != null && this.categorizer.handles(this.mbeanServer, currentMBean);
   }

   private MBeanCategorizer.TypeInfo getTypeName(ObjectName currentMBean) {
      MBeanCategorizer.TypeInfo ti = null;
      if (this.categorizer != null) {
         ti = this.categorizer.getTypeInfo(this.mbeanServer, currentMBean);
      }

      return ti;
   }

   protected String notificationDebugString(MBeanServerNotification n, ObjectName oName, String type) {
      StringBuilder b = new StringBuilder(256);
      b.append(this.getHandlerName());
      b.append(" Notif - type = ");
      b.append(type);
      b.append(", sequence = ");
      b.append(n.getSequenceNumber());
      b.append(", on = ");
      b.append(oName.getCanonicalName());
      return b.toString();
   }

   private void runInDomainCIC(Callable c) throws JMException, IOException {
      ComponentInvocationContextManager cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      ComponentInvocationContext cic = cicManager.createComponentInvocationContext("DOMAIN");
      Throwable t = null;

      try {
         t = (Throwable)ComponentInvocationContextManager.runAs(KERNEL_ID, cic, c);
      } catch (ExecutionException var9) {
         this.rethrowCheckedException(var9);
         throw new RuntimeException(var9);
      } finally {
         this.rethrowCheckedException(t);
      }

   }

   private void rethrowCheckedException(Throwable t) throws JMException, IOException {
      if (t != null) {
         if (t instanceof JMException) {
            throw (JMException)t;
         }

         if (t instanceof IOException) {
            throw (IOException)t;
         }
      }

   }
}
