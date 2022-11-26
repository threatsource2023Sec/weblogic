package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.security.AccessController;
import java.util.HashSet;
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
import javax.management.MBeanServerConnection;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.security.auth.Subject;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.visibility.utils.MBeanNameUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class FederatedMBeanServerInterceptor extends WLSMBeanServerInterceptorBase {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   MBeanServerConnectionManager connectionManager;
   String domainName;

   public FederatedMBeanServerInterceptor(MBeanServerConnectionManager connectionManager, String domainName) {
      this.connectionManager = connectionManager;
      this.domainName = domainName;
   }

   private boolean isDelegate(ObjectName oname) {
      if (oname == null) {
         return false;
      } else {
         String location = oname.getKeyProperty("Location");
         return location != null && location.length() != 0 && !location.equals(this.domainName);
      }
   }

   MBeanServerConnection lookupMBeanServer(ObjectName oname) {
      if (oname == null) {
         return null;
      } else {
         String location = oname.getKeyProperty("Location");
         return location != null && location.length() != 0 && !location.equals(this.domainName) ? this.connectionManager.lookupMBeanServerConnection(location) : this.mbeanServerConnection;
      }
   }

   private void initializeJMXContext(ObjectName oname) {
      if (this.isDelegate(oname)) {
         this.initializeJMXContext();
      }
   }

   private String getPartitionName() {
      String pname = null;

      try {
         pname = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      } catch (Exception var3) {
         pname = "DOMAIN";
      }

      if (!ok(pname)) {
         pname = "DOMAIN";
      }

      return pname;
   }

   private static boolean ok(String s) {
      return s != null && s.length() > 0;
   }

   private void initializeJMXContext() {
      AuthenticatedSubject authSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (!KERNEL_ID.equals(authSubject)) {
         boolean isAnon = AuthenticatedSubject.ANON.equals(authSubject);
         String partitionName = this.getPartitionName();
         if (!isAnon || !"DOMAIN".equals(partitionName)) {
            JMXContext jmxContext = JMXContextHelper.getJMXContext(true);
            if (!isAnon) {
               jmxContext.setSubject(authSubject.getSubject());
            }

            jmxContext.setPartitionName(partitionName);
            JMXContextHelper.putJMXContext(jmxContext);
         }
      }
   }

   private void cleanupJMXContext(ObjectName oname) {
      if (this.isDelegate(oname)) {
         this.cleanupJMXContext();
      }
   }

   private void cleanupJMXContext() {
      JMXContext jmxContext = JMXContextHelper.getJMXContext(false);
      if (jmxContext != null) {
         jmxContext.setSubject((Subject)null);
         jmxContext.setPartitionName((String)null);
         JMXContextHelper.putJMXContext(jmxContext);
      }

   }

   MBeanServerConnection lookupMBeanServerForCreate(ObjectName object) throws IOException {
      MBeanServerConnection result = this.lookupMBeanServer(object);
      if (result == null) {
         throw new IOException("Unable to contact MBeanServer for " + object);
      } else {
         return result;
      }
   }

   MBeanServerConnection lookupMBeanServerRequired(ObjectName object) throws InstanceNotFoundException {
      MBeanServerConnection result = this.lookupMBeanServer(object);
      if (result == null) {
         throw new InstanceNotFoundException("Unable to contact MBeanServer for " + object);
      } else {
         return result;
      }
   }

   public ClassLoader getClassLoaderFor(ObjectName oName) throws InstanceNotFoundException {
      String location = oName.getKeyProperty("Location");
      return location != null && location.length() != 0 ? DescriptorClassLoader.getClassLoader() : super.getClassLoaderFor(oName);
   }

   public ObjectInstance createMBean(String className, ObjectName name) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.initializeJMXContext(name);

      ObjectInstance var3;
      try {
         var3 = this.lookupMBeanServerForCreate(name).createMBean(className, name);
      } finally {
         this.cleanupJMXContext(name);
      }

      return var3;
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.initializeJMXContext(name);

      ObjectInstance var4;
      try {
         var4 = this.lookupMBeanServerForCreate(name).createMBean(className, name, loaderName);
      } finally {
         this.cleanupJMXContext(name);
      }

      return var4;
   }

   public ObjectInstance createMBean(String className, ObjectName name, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.initializeJMXContext(name);

      ObjectInstance var5;
      try {
         var5 = this.lookupMBeanServerForCreate(name).createMBean(className, name, params, signature);
      } finally {
         this.cleanupJMXContext(name);
      }

      return var5;
   }

   public ObjectInstance createMBean(String className, ObjectName name, ObjectName loaderName, Object[] params, String[] signature) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.initializeJMXContext(name);

      ObjectInstance var6;
      try {
         var6 = this.lookupMBeanServerForCreate(name).createMBean(className, name, loaderName, params, signature);
      } finally {
         this.cleanupJMXContext(name);
      }

      return var6;
   }

   public void unregisterMBean(ObjectName objectname) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).unregisterMBean(objectname);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public ObjectInstance getObjectInstance(ObjectName objectname) throws InstanceNotFoundException, IOException {
      return this.lookupMBeanServerRequired(objectname).getObjectInstance(objectname);
   }

   public Set queryMBeans(ObjectName objectname, QueryExp queryexp) throws IOException {
      return this.queryMBeansOrNames(objectname, queryexp, false);
   }

   public Set queryNames(ObjectName objectname, QueryExp queryexp) throws IOException {
      return this.queryMBeansOrNames(objectname, queryexp, true);
   }

   public String getLocationFromObjectName(ObjectName objectname) {
      return objectname != null ? objectname.getKeyProperty("Location") : null;
   }

   public boolean isRegistered(ObjectName objectname) throws IOException {
      this.initializeJMXContext(objectname);

      boolean var2;
      try {
         var2 = this.lookupMBeanServerForCreate(objectname).isRegistered(objectname);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var2;
   }

   public Integer getMBeanCount() throws IOException {
      final int[] result = new int[]{this.mbeanServerConnection.getMBeanCount()};
      this.connectionManager.iterateConnections(new MBeanServerConnectionManager.ConnectionCallback() {
         public void connection(MBeanServerConnection connection) throws IOException {
            FederatedMBeanServerInterceptor.this.initializeJMXContext();

            try {
               int[] var10000 = result;
               var10000[0] += connection.getMBeanCount();
            } finally {
               FederatedMBeanServerInterceptor.this.cleanupJMXContext();
            }

         }
      }, false);
      return new Integer(result[0]);
   }

   public Object getAttribute(ObjectName objectname, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      Object var3;
      try {
         var3 = this.lookupMBeanServerRequired(objectname).getAttribute(objectname, s);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var3;
   }

   public AttributeList getAttributes(ObjectName objectname, String[] as) throws InstanceNotFoundException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      AttributeList var3;
      try {
         var3 = this.lookupMBeanServerRequired(objectname).getAttributes(objectname, as);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var3;
   }

   public void setAttribute(ObjectName objectname, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).setAttribute(objectname, attribute);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public AttributeList setAttributes(ObjectName objectname, AttributeList attributelist) throws InstanceNotFoundException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      AttributeList var3;
      try {
         var3 = this.lookupMBeanServerRequired(objectname).setAttributes(objectname, attributelist);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var3;
   }

   public Object invoke(ObjectName objectname, String s, Object[] aobj, String[] as) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      Object var5;
      try {
         var5 = this.lookupMBeanServerRequired(objectname).invoke(objectname, s, aobj, as);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var5;
   }

   public String getDefaultDomain() throws IOException {
      return this.mbeanServerConnection.getDefaultDomain();
   }

   private void addToSet(Set set, Object[] array) {
      for(int i = 0; i < array.length; ++i) {
         Object o = array[i];
         set.add(o);
      }

   }

   public String[] getDomains() throws IOException {
      final Set domains = new HashSet();
      this.addToSet(domains, this.mbeanServerConnection.getDomains());
      this.connectionManager.iterateConnections(new MBeanServerConnectionManager.ConnectionCallback() {
         public void connection(MBeanServerConnection connection) throws IOException {
            FederatedMBeanServerInterceptor.this.initializeJMXContext();

            try {
               FederatedMBeanServerInterceptor.this.addToSet(domains, connection.getDomains());
            } finally {
               FederatedMBeanServerInterceptor.this.cleanupJMXContext();
            }

         }
      }, false);
      return (String[])((String[])domains.toArray(new String[domains.size()]));
   }

   public void addNotificationListener(ObjectName objectname, NotificationListener notificationlistener, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, IOException {
      if (debug.isDebugEnabled()) {
         debug.debug("Add notification listener for " + objectname);
      }

      this.lookupMBeanServerRequired(objectname).addNotificationListener(objectname, notificationlistener, notificationfilter, obj);
   }

   public void addNotificationListener(ObjectName objectname, ObjectName objectname1, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, IOException {
      this.lookupMBeanServerRequired(objectname).addNotificationListener(objectname, objectname1, notificationfilter, obj);
   }

   public void removeNotificationListener(ObjectName objectname, ObjectName objectname1) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).removeNotificationListener(objectname, objectname1);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public void removeNotificationListener(ObjectName objectname, ObjectName objectname1, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).removeNotificationListener(objectname, objectname1, notificationfilter, obj);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public void removeNotificationListener(ObjectName objectname, NotificationListener notificationlistener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).removeNotificationListener(objectname, notificationlistener);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public void removeNotificationListener(ObjectName objectname, NotificationListener notificationlistener, NotificationFilter notificationfilter, Object obj) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      this.initializeJMXContext(objectname);

      try {
         this.lookupMBeanServerRequired(objectname).removeNotificationListener(objectname, notificationlistener, notificationfilter, obj);
      } finally {
         this.cleanupJMXContext(objectname);
      }

   }

   public MBeanInfo getMBeanInfo(ObjectName objectname) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      this.initializeJMXContext(objectname);

      MBeanInfo var2;
      try {
         var2 = this.lookupMBeanServerRequired(objectname).getMBeanInfo(objectname);
      } finally {
         this.cleanupJMXContext(objectname);
      }

      return var2;
   }

   public boolean isInstanceOf(ObjectName objectname, String s) throws InstanceNotFoundException, IOException {
      return this.lookupMBeanServerRequired(objectname).isInstanceOf(objectname, s);
   }

   private Set queryMBeansOrNames(final ObjectName objectname, final QueryExp queryexp, final boolean queryNames) throws IOException {
      String location = this.getLocationFromObjectName(objectname);
      if (location != null && location.length() != 0) {
         if (location.equals(this.domainName)) {
            ObjectName adjustedObjectName = MBeanNameUtil.removeLocation(objectname);
            Set set;
            if (queryNames) {
               set = this.mbeanServerConnection.queryNames(objectname, queryexp);
               return set != null && !set.isEmpty() ? set : this.mbeanServerConnection.queryNames(adjustedObjectName, queryexp);
            } else {
               set = this.mbeanServerConnection.queryMBeans(objectname, queryexp);
               return set != null && !set.isEmpty() ? set : this.mbeanServerConnection.queryMBeans(adjustedObjectName, queryexp);
            }
         } else {
            MBeanServerConnection mbs = this.connectionManager.lookupMBeanServerConnection(location);
            if (mbs == null) {
               return new HashSet(0);
            } else {
               return queryNames ? mbs.queryNames(objectname, queryexp) : mbs.queryMBeans(objectname, queryexp);
            }
         }
      } else {
         final Set nonFinalresult = null;
         if (queryNames) {
            nonFinalresult = this.mbeanServerConnection.queryNames(objectname, queryexp);
         } else {
            nonFinalresult = this.mbeanServerConnection.queryMBeans(objectname, queryexp);
         }

         this.connectionManager.iterateConnections(new MBeanServerConnectionManager.ConnectionCallback() {
            public void connection(MBeanServerConnection connection) throws IOException {
               FederatedMBeanServerInterceptor.this.initializeJMXContext();

               try {
                  if (queryNames) {
                     nonFinalresult.addAll(connection.queryNames(objectname, queryexp));
                  } else {
                     nonFinalresult.addAll(connection.queryMBeans(objectname, queryexp));
                  }
               } finally {
                  FederatedMBeanServerInterceptor.this.cleanupJMXContext();
               }

            }
         }, false);
         return nonFinalresult;
      }
   }
}
