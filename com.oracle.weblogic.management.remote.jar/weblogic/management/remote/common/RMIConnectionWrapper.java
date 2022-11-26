package weblogic.management.remote.common;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.NotificationResult;
import javax.management.remote.TargetedNotification;
import javax.management.remote.rmi.RMIConnection;
import javax.security.auth.Subject;
import weblogic.iiop.RequestUrl;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jndi.ThreadLocalMap;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.context.JMXContext;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.jmx.mbeanserver.WLSMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanNotification;
import weblogic.management.remote.util.MBeanVisibilityUtil;
import weblogic.security.Security;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class RMIConnectionWrapper implements RMIConnection {
   private RMIConnection connection;
   private boolean disconnected = false;
   private Subject subject = null;
   private Locale locale;
   private HashMap localeMap = new HashMap();
   private boolean defaultLocale = false;
   private RMIServerWrapper rmiServerWrapper = null;
   private Hashtable env = null;
   private String partition;

   RMIConnectionWrapper(RMIConnection connection, Subject subject, Locale locale, RMIServerWrapper rmiServerWrapper, Hashtable env) {
      this.subject = subject;
      this.connection = connection;
      this.rmiServerWrapper = rmiServerWrapper;
      this.env = env;
      this.partition = (String)env.get("weblogic.partitionName");
      if (locale == null) {
         this.locale = Locale.getDefault();
         this.defaultLocale = true;
      } else {
         this.locale = locale;
      }

   }

   public void disconnected() {
      this.disconnected = true;
   }

   public void close() throws IOException {
      if (!this.disconnected) {
         Hashtable orig = null;

         try {
            orig = this.pushEnvironment();
            this.connection.close();
         } finally {
            this.popEnvironment(orig);
         }
      }

      this.rmiServerWrapper.clearClientConnection(this);
   }

   public void setLocale(Locale locale) {
      this.setLocale((Subject)null, locale);
   }

   public void setLocale(Subject subject, Locale locale) {
      this.localeMap.put(subject, locale);
   }

   public String getConnectionId() throws IOException {
      String connID = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         connID = (String)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getConnectionId();
            }
         });
      } catch (PrivilegedActionException var8) {
         Exception resultEx = var8.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var8 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return connID;
   }

   public ObjectInstance createMBean(final String className, final ObjectName name, final Subject delegationSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (ObjectInstance)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.createMBean(className, name, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)resultEx;
         }

         if (resultEx instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)resultEx;
         }

         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public ObjectInstance createMBean(final String className, final ObjectName name, final ObjectName loaderName, final Subject delegationSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectInstance result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (ObjectInstance)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.createMBean(className, name, loaderName, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var12) {
         Exception resultEx = var12.getException();
         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)resultEx;
         }

         if (resultEx instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)resultEx;
         }

         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var12 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public ObjectInstance createMBean(final String className, final ObjectName name, final MarshalledObject params, final String[] signature, final Subject delegationSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      ObjectInstance result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (ObjectInstance)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.createMBean(className, name, params, signature, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var13) {
         Exception resultEx = var13.getException();
         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)resultEx;
         }

         if (resultEx instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)resultEx;
         }

         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var13 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public ObjectInstance createMBean(final String className, final ObjectName name, final ObjectName loaderName, final MarshalledObject params, final String[] signature, final Subject delegationSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      ObjectInstance result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (ObjectInstance)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.createMBean(className, name, loaderName, params, signature, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var14) {
         Exception resultEx = var14.getException();
         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof InstanceAlreadyExistsException) {
            throw (InstanceAlreadyExistsException)resultEx;
         }

         if (resultEx instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)resultEx;
         }

         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof NotCompliantMBeanException) {
            throw (NotCompliantMBeanException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var14 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public void unregisterMBean(final ObjectName name, final Subject delegationSubject) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.unregisterMBean(name, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception resultEx = var9.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof MBeanRegistrationException) {
            throw (MBeanRegistrationException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var9 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

   }

   public ObjectInstance getObjectInstance(final ObjectName name, final Subject delegationSubject) throws InstanceNotFoundException, IOException {
      ObjectInstance result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (ObjectInstance)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getObjectInstance(name, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public Set queryMBeans(final ObjectName name, final MarshalledObject query, final Subject delegationSubject) throws IOException {
      Set result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Set)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.queryMBeans(name, query, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public Set queryNames(final ObjectName name, final MarshalledObject query, final Subject delegationSubject) throws IOException {
      Set result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Set)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.queryNames(name, query, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public boolean isRegistered(final ObjectName name, final Subject delegationSubject) throws IOException {
      Boolean result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Boolean)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.isRegistered(name, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public Integer getMBeanCount(final Subject delegationSubject) throws IOException {
      Integer result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Integer)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getMBeanCount(delegationSubject);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception resultEx = var9.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var9 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public Object getAttribute(final ObjectName name, final String attribute, final Subject delegationSubject) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      Object result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         result = Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getAttribute(name, attribute, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof AttributeNotFoundException) {
            throw (AttributeNotFoundException)resultEx;
         }

         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

      return result;
   }

   public AttributeList getAttributes(final ObjectName name, final String[] attributes, final Subject delegationSubject) throws InstanceNotFoundException, ReflectionException, IOException {
      AttributeList result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         result = (AttributeList)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getAttributes(name, attributes, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

      return result;
   }

   public void setAttribute(final ObjectName name, final MarshalledObject attribute, final Subject delegationSubject) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.setAttribute(name, attribute, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof AttributeNotFoundException) {
            throw (AttributeNotFoundException)resultEx;
         }

         if (resultEx instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)resultEx;
         }

         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         if (resultEx instanceof NoAccessRuntimeException) {
            throw (NoAccessRuntimeException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

   }

   public AttributeList setAttributes(final ObjectName name, final MarshalledObject attributes, final Subject delegationSubject) throws InstanceNotFoundException, ReflectionException, IOException {
      AttributeList result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         result = (AttributeList)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.setAttributes(name, attributes, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

      return result;
   }

   public Object invoke(final ObjectName name, final String operationName, final MarshalledObject params, final String[] signature, final Subject delegationSubject) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      Object result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         result = Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.invoke(name, operationName, params, signature, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var13) {
         Exception resultEx = var13.getException();
         if (resultEx instanceof MBeanException) {
            throw (MBeanException)resultEx;
         }

         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         if (resultEx instanceof NoAccessRuntimeException) {
            throw (NoAccessRuntimeException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var13 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

      return result;
   }

   public String getDefaultDomain(final Subject delegationSubject) throws IOException {
      String result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (String)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getDefaultDomain(delegationSubject);
            }
         });
      } catch (PrivilegedActionException var9) {
         Exception resultEx = var9.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var9 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public String[] getDomains(final Subject delegationSubject) throws IOException {
      String[] result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (String[])((String[])Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getDomains(delegationSubject);
            }
         }));
      } catch (PrivilegedActionException var9) {
         Exception resultEx = var9.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var9 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   private void initializeJMXContext(Subject delegationSubject) {
      Locale locale = (Locale)this.localeMap.get(delegationSubject);
      boolean overideJMXContextLocal = !this.defaultLocale || locale != null;
      if (locale == null) {
         locale = this.locale;
      }

      JMXContext jmxContext = JMXContextHelper.getJMXContext(true);
      if (jmxContext.getLocale() == null || overideJMXContextLocal) {
         jmxContext.setLocale(locale);
      }

      JMXContextHelper.putJMXContext(jmxContext);
   }

   private void removeJMXContext() {
      JMXContextHelper.removeJMXContext();
   }

   private String getPartitionName() {
      String pname = null;

      try {
         AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         pname = ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext().getPartitionName();
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

   public MBeanInfo getMBeanInfo(final ObjectName name, final Subject delegationSubject) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
      MBeanInfo result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         this.initializeJMXContext(delegationSubject);
         result = (MBeanInfo)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.getMBeanInfo(name, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof IntrospectionException) {
            throw (IntrospectionException)resultEx;
         }

         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ReflectionException) {
            throw (ReflectionException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.removeJMXContext();
         this.popEnvironment(orig);
      }

      return result;
   }

   public boolean isInstanceOf(final ObjectName name, final String className, final Subject delegationSubject) throws InstanceNotFoundException, IOException {
      Boolean result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Boolean)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.isInstanceOf(name, className, delegationSubject);
            }
         });
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public void addNotificationListener(final ObjectName name, final ObjectName listener, final MarshalledObject filter, final MarshalledObject handback, final Subject delegationSubject) throws InstanceNotFoundException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.addNotificationListener(name, listener, filter, handback, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var12) {
         Exception resultEx = var12.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var12 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

   }

   public void removeNotificationListener(final ObjectName name, final ObjectName listener, final Subject delegationSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.removeNotificationListener(name, listener, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

   }

   public void removeNotificationListener(final ObjectName name, final ObjectName listener, final MarshalledObject filter, final MarshalledObject handback, final Subject delegationSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.removeNotificationListener(name, listener, filter, handback, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var12) {
         Exception resultEx = var12.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var12 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

   }

   public Integer[] addNotificationListeners(final ObjectName[] names, final MarshalledObject[] filters, final Subject[] delegationSubjects) throws InstanceNotFoundException, IOException {
      Integer[] result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (Integer[])((Integer[])Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.addNotificationListeners(names, filters, delegationSubjects);
            }
         }));
      } catch (PrivilegedActionException var11) {
         Exception resultEx = var11.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var11 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   public void removeNotificationListeners(final ObjectName name, final Integer[] listenerIDs, final Subject delegationSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               RMIConnectionWrapper.this.connection.removeNotificationListeners(name, listenerIDs, delegationSubject);
               return null;
            }
         });
      } catch (PrivilegedActionException var10) {
         Exception resultEx = var10.getException();
         if (resultEx instanceof InstanceNotFoundException) {
            throw (InstanceNotFoundException)resultEx;
         }

         if (resultEx instanceof ListenerNotFoundException) {
            throw (ListenerNotFoundException)resultEx;
         }

         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var10 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

   }

   public NotificationResult fetchNotifications(final long clientSequenceNumber, final int maxNotifications, final long timeout) throws IOException {
      NotificationResult result = null;
      Hashtable orig = null;

      try {
         orig = this.pushEnvironment();
         result = (NotificationResult)Security.runAs(this.subject, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return RMIConnectionWrapper.this.connection.fetchNotifications(clientSequenceNumber, maxNotifications, timeout);
            }
         });
         if (result != null && !this.partition.equals("DOMAIN")) {
            result = this.modifyResult(result, this.partition);
         }
      } catch (PrivilegedActionException var13) {
         Exception resultEx = var13.getException();
         if (resultEx instanceof IOException) {
            throw (IOException)resultEx;
         }

         throw new RuntimeException((Throwable)(resultEx == null ? var13 : resultEx));
      } finally {
         this.popEnvironment(orig);
      }

      return result;
   }

   private NotificationResult modifyResult(NotificationResult result, String partition) {
      List targetedNotifications = new ArrayList();
      TargetedNotification[] notifications = result.getTargetedNotifications();
      int var5 = notifications.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetedNotification tnotification = notifications[var6];
         Notification notification = tnotification.getNotification();

         try {
            boolean isAttributeVisible;
            if (notification instanceof WLSMBeanNotification) {
               isAttributeVisible = MBeanVisibilityUtil.isMBeanVisibleToPartition(partition, (WLSMBeanNotification)notification);
               if (isAttributeVisible) {
                  targetedNotifications.add(tnotification);
               }
            } else if (notification instanceof WLSMBeanAttributeChangeNotification) {
               isAttributeVisible = MBeanVisibilityUtil.isAttributeVisibleToPartition(partition, (WLSMBeanAttributeChangeNotification)notification);
               if (isAttributeVisible) {
                  targetedNotifications.add(tnotification);
               }
            }
         } catch (Exception var10) {
            System.out.println("Exception occurred while analyzing notification: " + notification);
         }
      }

      notifications = new TargetedNotification[targetedNotifications.size()];
      targetedNotifications.toArray(notifications);
      NotificationResult modifiedResult = new NotificationResult(result.getEarliestSequenceNumber(), result.getNextSequenceNumber(), notifications);
      return modifiedResult;
   }

   private Hashtable pushEnvironment() {
      return pushEnvironment(this.env);
   }

   static Hashtable pushEnvironment(Hashtable env) {
      if (env != null && env.size() != 0) {
         RequestUrl.set((String)env.get("java.naming.provider.url"));
         Hashtable orig = ThreadLocalMap.pop();
         ThreadLocalMap.push(env);
         return orig;
      } else {
         return null;
      }
   }

   private void popEnvironment(Hashtable orig) {
      popEnvironment(orig, this.env);
   }

   static void popEnvironment(Hashtable orig, Hashtable env) {
      RequestUrl.clear();
      if (env != null && env.size() != 0) {
         ThreadLocalMap.pop();
         if (orig != null) {
            ThreadLocalMap.push(orig);
         }

      }
   }
}
