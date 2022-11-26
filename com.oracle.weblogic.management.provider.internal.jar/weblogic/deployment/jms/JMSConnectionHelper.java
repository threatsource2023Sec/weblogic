package weblogic.deployment.jms;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicConnection;
import javax.jms.XATopicConnectionFactory;
import javax.jms.XATopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public class JMSConnectionHelper implements JMSConnectionHelperService {
   static final String DEFAULT_JMS_CONNECTION_FACTORY = "java:comp/DefaultJMSConnectionFactory";
   private final String DEFAULT_JMS_CONNECTION_FACTORY_INTERNAL = "weblogic.jms.DefaultConnectionFactory";
   static final String WEBLOGIC_JMS_PACKAGE = "weblogic.jms";
   static final String CONNECT_TIMEOUT = "weblogic.jndi.connectTimeout";
   static final String RESPONSE_READ_TIMEOUT = "weblogic.jndi.responseReadTimeout";
   private static final int JNDI_CONNECT_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.connectTimeout", "0"));
   private static final int JNDI_RESPONSE_READ_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.responseReadTimeout", "0"));
   private Connection connection;
   private XAConnection xaConnection;
   private boolean xaSupported;
   private WrappedTransactionalSession xaSession;
   private boolean hasNativeTransactions;
   private String poolName;
   private WrappedClassManager wrapperManager;
   private int referenceCount;
   private boolean factorySetClientID;
   private Map poolProps;
   private boolean isUsedForPooltesting = false;
   private static String RESOURCE_REFERENCE_POLICY = System.getProperty("weblogic.jms.JMSConnectionFactoryUnmappedResRefMode", "");
   private static boolean isValidUnmappedResRefMode;
   private AbstractSubject subject = null;
   private AbstractSubject localSubject = null;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected JMSConnectionHelper(String poolName, Map poolProps, boolean containerAuth, WrappedClassManager wrapperManager) throws JMSException {
      this.poolName = poolName;
      this.wrapperManager = wrapperManager;
      this.poolProps = poolProps;
      String userName = null;
      String passwd = null;
      boolean hasCreds = false;
      if (containerAuth) {
         StringBuffer userNameBuf = new StringBuffer();
         StringBuffer passwdBuf = new StringBuffer();
         hasCreds = this.getCredentials(poolProps, userNameBuf, passwdBuf);
         if (hasCreds) {
            userName = userNameBuf.toString();
            passwd = passwdBuf.toString();
         }
      }

      AbstractSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         Iterator i = poolProps.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            String propName = (String)entry.getKey();
            JMSPoolDebug.logger.debug("CR315579: poolProps.name = " + propName + " value= " + entry.getValue());
         }
      }

      if (this.subject == null && currentSubject.equals(KERNEL_ID) || this.subject != null && this.subject.equals(KERNEL_ID)) {
         this.subject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      this.localSubject = currentSubject;
      if (this.localSubject.equals(KERNEL_ID)) {
         this.localSubject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("CR315579: JMSConnectionHelper() : subject=" + this.subject + " currentSubject=" + currentSubject + " localSubject " + this.localSubject + " username=" + userName + " password=XXXXX");
      }

      this.openConnection(hasCreds, userName, passwd, poolProps);
   }

   protected JMSConnectionHelper(String poolName, Map poolProps, String userName, String passwd, WrappedClassManager wrapperManager) throws JMSException {
      this.poolName = poolName;
      this.wrapperManager = wrapperManager;
      this.poolProps = poolProps;
      boolean hasCreds = false;
      if (userName != null || passwd != null) {
         hasCreds = true;
      }

      AbstractSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      if (this.subject == null && currentSubject.equals(KERNEL_ID) || this.subject != null && this.subject.equals(KERNEL_ID)) {
         this.subject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      this.localSubject = currentSubject;
      if (this.localSubject.equals(KERNEL_ID)) {
         this.localSubject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("CR315579: JMSConnectionHelper() 2: subject=" + this.subject + " currentSubject=" + currentSubject + " localSubject=" + this.localSubject + " username=" + userName + " password=XXXXX");
      }

      this.openConnection(hasCreds, userName, passwd, poolProps);
   }

   public synchronized void close() throws JMSException {
      JMSException savedException = null;
      if (this.connection != null) {
         this.unregisterXAResource();
         AbstractSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
         if (this.subject == null && currentSubject.equals(KERNEL_ID) || this.subject != null && this.subject.equals(KERNEL_ID)) {
            this.subject = SubjectManager.getSubjectManager().getAnonymousSubject();
         }

         this.pushSubject();

         try {
            this.connection.close();
         } catch (JMSException var7) {
            savedException = var7;
         } finally {
            this.popSubject();
         }

         JMSPoolDebug.logger.debug("Closed a JMS connection in JMSConnectionHelper");
         this.connection = null;
         this.xaConnection = null;
         this.xaSession = null;
         if (savedException != null) {
            throw savedException;
         } else {
            this.setUsedForPooltesting(false);
         }
      }
   }

   protected void start() throws JMSException {
      this.checkClosed();
      this.pushSubject();

      try {
         this.connection.start();
      } finally {
         this.popSubject();
      }

   }

   protected void stop() throws JMSException {
      this.checkClosed();
      this.pushSubject();

      try {
         this.connection.stop();
      } finally {
         this.popSubject();
      }

   }

   private void openConnection(boolean hasCreds, String userName, String passwd, Map poolProps) throws JMSException {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Opening a new connection for pool " + this.poolName + " with userName " + userName + " hasCreds = " + hasCreds);
      }

      Object factory = this.lookupConnectionFactory(poolProps);
      if (factory instanceof ObjectBasedSecurityAware && ((ObjectBasedSecurityAware)factory).isOBSEnabled()) {
         String jndiName = getJNDIName(poolProps);
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Failed to open a new connection for pool " + this.poolName + ", the JMS connection factory with JNDI name " + jndiName + " is object-based-security enabled and is not supported by wrappers.");
         }

         throw JMSExceptions.getJMSException(JMSPoolLogger.logUnsupportedOBSEnabledCFLoggable(jndiName));
      } else {
         this.pushSubject();

         try {
            if (factory instanceof XAConnectionFactory) {
               if (factory instanceof XAQueueConnectionFactory) {
                  JMSPoolDebug.logger.debug("Creating XAQueueConnection");
                  XAQueueConnectionFactory xaqcf = (XAQueueConnectionFactory)factory;
                  if (hasCreds) {
                     this.xaConnection = xaqcf.createXAQueueConnection(userName, passwd);
                  } else {
                     this.xaConnection = xaqcf.createXAQueueConnection();
                  }

                  this.xaSupported = true;
               } else if (factory instanceof XATopicConnectionFactory) {
                  JMSPoolDebug.logger.debug("Creating XATopicConnection");
                  XATopicConnectionFactory xatcf = (XATopicConnectionFactory)factory;
                  if (hasCreds) {
                     this.xaConnection = xatcf.createXATopicConnection(userName, passwd);
                  } else {
                     this.xaConnection = xatcf.createXATopicConnection();
                  }

                  this.xaSupported = true;
               } else {
                  JMSPoolDebug.logger.debug("Creating XAConnection");
                  XAConnectionFactory xacf = (XAConnectionFactory)factory;
                  if (hasCreds) {
                     this.xaConnection = xacf.createXAConnection(userName, passwd);
                  } else {
                     this.xaConnection = xacf.createXAConnection();
                  }

                  this.xaSupported = true;
               }

               this.connection = getXAConnectionToUse(this.xaConnection);
            } else if (factory instanceof QueueConnectionFactory) {
               JMSPoolDebug.logger.debug("Creating QueueConnection");
               QueueConnectionFactory qcf = (QueueConnectionFactory)factory;
               QueueConnection qc;
               if (hasCreds) {
                  qc = qcf.createQueueConnection(userName, passwd);
               } else {
                  qc = qcf.createQueueConnection();
               }

               this.connection = qc;
            } else if (factory instanceof TopicConnectionFactory) {
               JMSPoolDebug.logger.debug("Creating TopicConnection");
               TopicConnectionFactory tcf = (TopicConnectionFactory)factory;
               TopicConnection tc;
               if (hasCreds) {
                  tc = tcf.createTopicConnection(userName, passwd);
               } else {
                  tc = tcf.createTopicConnection();
               }

               this.connection = tc;
            } else {
               JMSPoolDebug.logger.debug("Creating Connection");
               ConnectionFactory cf = (ConnectionFactory)factory;
               Connection c;
               if (hasCreds) {
                  c = cf.createConnection(userName, passwd);
               } else {
                  c = cf.createConnection();
               }

               this.connection = c;
            }

            if (this.connection.getClientID() != null) {
               this.factorySetClientID = true;
            }
         } finally {
            this.popSubject();
         }

         this.checkForNativeTransactions();
      }
   }

   public void markAsPooled() throws JMSException {
      Connection conn = this.connection;
      if (conn != null) {
         Class c = conn.getClass();
         if (c.getName().startsWith("weblogic.jms")) {
            try {
               Method m = c.getMethod("markAsJMSSessionPooledInWrapper", (Class[])null);
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("JMSConnectionHelper[poolName=" + this.poolName + "].markAsPooled(): invoke WLJMS Connection method markAsJMSSessionPooledInWrapper()");
               }

               m.invoke(conn, (Object[])null);
            } catch (Exception var5) {
               JMSException jmse = new JMSException(var5.getMessage());
               jmse.setLinkedException(var5);
               throw jmse;
            }
         }
      }
   }

   private void checkForNativeTransactions() throws JMSException {
      this.hasNativeTransactions = false;

      try {
         Class cls = Class.forName("weblogic.jms.client.ConnectionInternal");
         if (cls.isInstance(this.connection)) {
            Method meth = cls.getMethod("getUserTransactionsEnabled", (Class[])null);
            Object retobj = meth.invoke(this.connection, (Object[])null);
            Boolean retval = (Boolean)retobj;
            boolean boolval = retval;
            if (boolval) {
               this.hasNativeTransactions = true;
            } else if (KernelStatus.isServer()) {
               meth = cls.getMethod("isXAServerEnabled", (Class[])null);
               retobj = meth.invoke(this.connection, (Object[])null);
               retval = (Boolean)retobj;
               boolval = retval;
               if (boolval) {
                  this.hasNativeTransactions = true;
               }
            }

            Class[] partypes = new Class[]{String.class};
            meth = cls.getMethod("setReconnectPolicy", partypes);
            Object[] args = new Object[]{"none"};
            meth.invoke(this.connection, args);
         }
      } catch (ClassNotFoundException var8) {
      } catch (NoSuchMethodException var9) {
      } catch (IllegalAccessException var10) {
      } catch (InvocationTargetException var11) {
      }

   }

   protected Object lookupConnectionFactory(Map poolProps) throws JMSException {
      ConnectionFactory cf = (ConnectionFactory)poolProps.get("ConnectionFactory");
      if (cf != null) {
         return cf;
      } else {
         String jndiName = getJNDIName(poolProps);
         if (jndiName.equals("java:comp/DefaultJMSConnectionFactory")) {
            jndiName = "weblogic.jms.DefaultConnectionFactory";
         }

         Context jmsCtx = null;
         InitialContext context = null;
         Object factory = null;
         String cfUnmappedResourceReferenceMode = null;
         if (isValidUnmappedResRefMode) {
            cfUnmappedResourceReferenceMode = RESOURCE_REFERENCE_POLICY;
         } else {
            ServerMBean serverMBean = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
            if (serverMBean != null) {
               cfUnmappedResourceReferenceMode = serverMBean.getJMSConnectionFactoryUnmappedResRefMode();
            } else {
               cfUnmappedResourceReferenceMode = "ReturnDefault";
            }
         }

         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Connection Factory unmapped resource reference mode " + cfUnmappedResourceReferenceMode);
         }

         boolean isResRefNameJNDIName = false;
         if ("true".equalsIgnoreCase((String)poolProps.get("ResourceRefNameAsJNDIName"))) {
            isResRefNameJNDIName = true;
         }

         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Looking up JMS connection factory with JNDI name " + jndiName + " currentSubject " + SecurityServiceManager.getCurrentSubject(KERNEL_ID) + " subject " + this.subject + " localSubject " + this.localSubject);
         }

         if ((jmsCtx = (Context)poolProps.get("JmsApplicationContext")) != null) {
            this.pushLocalSubject();

            try {
               factory = jmsCtx.lookup(jndiName);
               if (!(factory instanceof ConnectionFactory) && !(factory instanceof XAConnectionFactory)) {
                  throw new JMSException(JMSPoolLogger.logNotAConnectionFactoryLoggable(this.poolName, jndiName, factory.getClass().getName()).getMessage());
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Found an application scoped connection factory of type " + factory.getClass().getName());
               }
            } catch (NamingException var39) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("CR315579: Failed to find an application scoped connection factory of type " + var39);
               }
            } finally {
               this.popLocalSubject();
            }
         }

         if (factory == null) {
            Hashtable jndiEnv = getJNDIEnvironment(poolProps);

            try {
               if (jndiEnv.isEmpty()) {
                  context = new InitialContext();
               } else {
                  context = new InitialContext(jndiEnv);
               }

               if (jndiEnv.isEmpty()) {
                  this.pushLocalSubject();
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("CR315579: lookup: local subject = " + SecurityServiceManager.getCurrentSubject(KERNEL_ID));
               }

               factory = context.lookup(jndiName);
               if (!(factory instanceof ConnectionFactory) && !(factory instanceof XAConnectionFactory)) {
                  throw new JMSException(JMSPoolLogger.logNotAConnectionFactoryLoggable(this.poolName, jndiName, factory.getClass().getName()).getMessage());
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Found connection factory of type " + factory.getClass().getName());
               }
            } catch (NameNotFoundException var36) {
               boolean isForeignConnectionFactory = false;
               if (isResRefNameJNDIName && "ReturnDefault".equalsIgnoreCase(cfUnmappedResourceReferenceMode)) {
                  try {
                     factory = context.lookupLink(jndiName);
                     if (factory instanceof ForeignOpaqueTag) {
                        isForeignConnectionFactory = true;
                     }
                  } catch (NamingException var35) {
                  }
               }

               if (isForeignConnectionFactory || !"ReturnDefault".equalsIgnoreCase(cfUnmappedResourceReferenceMode) || !isResRefNameJNDIName) {
                  throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var36);
               }

               try {
                  JMSPoolLogger.logLookupDefaultJMSConnectionFactory(jndiName, (String)poolProps.get("ApplicationName"));
                  factory = context.lookup("weblogic.jms.DefaultConnectionFactory");
               } catch (NamingException var34) {
                  throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var34);
               }
            } catch (NamingException var37) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var37);
            } finally {
               if (jndiEnv.isEmpty()) {
                  this.popLocalSubject();
               }

               try {
                  if (context != null) {
                     context.close();
                  }
               } catch (NamingException var33) {
               }

            }
         }

         return factory;
      }
   }

   public Destination lookupDestination(Map poolProps, String destName) throws NamingException {
      Hashtable jndiEnv = getJNDIEnvironment(poolProps);
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("lookupDestination() Looking up JMS destination at JNDI name " + destName);
      }

      InitialContext context;
      if (jndiEnv.isEmpty()) {
         context = new InitialContext();
      } else {
         context = new InitialContext(jndiEnv);
      }

      if (jndiEnv.isEmpty()) {
         this.pushLocalSubject();
      }

      Destination destination;
      try {
         destination = (Destination)context.lookup(destName);
      } finally {
         if (jndiEnv.isEmpty()) {
            this.popLocalSubject();
         }

         context.close();
      }

      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Found JMS destination of type " + destination.getClass().getName());
      }

      return destination;
   }

   static String getJNDIName(Map poolProps) throws JMSException {
      String jndiName = (String)poolProps.get("JNDIName");
      if (jndiName == null) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSSessionPoolPropertyMissingLoggable("JNDIName"));
      } else {
         return jndiName;
      }
   }

   static Hashtable getJNDIEnvironment(Map poolProps) {
      boolean hasConnectTimeout = false;
      boolean hasResponseReadTimeout = false;
      Hashtable jndiEnv = new Hashtable(3);
      Iterator i = poolProps.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         String propName;
         do {
            if (!i.hasNext()) {
               if (!hasConnectTimeout && JNDI_CONNECT_TIMEOUT > 0) {
                  jndiEnv.put("weblogic.jndi.connectTimeout", new Long((long)JNDI_CONNECT_TIMEOUT));
               }

               if (!hasResponseReadTimeout && JNDI_RESPONSE_READ_TIMEOUT > 0) {
                  jndiEnv.put("weblogic.jndi.responseReadTimeout", new Long((long)JNDI_RESPONSE_READ_TIMEOUT));
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug(" JMSConnectionHelper.getJNDIEnvironment() jndiEnv " + jndiEnv);
               }

               return jndiEnv;
            }

            entry = (Map.Entry)i.next();
            propName = (String)entry.getKey();
         } while(!propName.startsWith("java.naming") && !propName.startsWith("weblogic.jndi"));

         if (propName.equals("weblogic.jndi.connectTimeout")) {
            hasConnectTimeout = true;
         }

         if (propName.equals("weblogic.jndi.responseReadTimeout")) {
            hasResponseReadTimeout = true;
         }

         jndiEnv.put(propName, entry.getValue());
      }
   }

   private boolean getCredentials(Map poolProps, StringBuffer userNameBuf, StringBuffer passwdBuf) throws JMSException {
      Context context = null;
      boolean foundCreds = false;
      if (poolProps.containsKey("UserName")) {
         userNameBuf.append((String)poolProps.get("UserName"));
         foundCreds = true;
      }

      if (poolProps.containsKey("Password")) {
         passwdBuf.append((String)poolProps.get("Password"));
         foundCreds = true;
      }

      if (foundCreds) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Found credentials for connection factory with username " + userNameBuf.toString());
         }

         Hashtable jndiEnv = getJNDIEnvironment(poolProps);
         if (jndiEnv != null) {
            try {
               context = new InitialContext(jndiEnv);
               this.subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
            } catch (NamingException var16) {
            } finally {
               try {
                  if (context != null) {
                     context.close();
                  }
               } catch (NamingException var15) {
               }

            }
         }

         return true;
      } else {
         PlatformHelper.ForeignRefReturn ret = PlatformHelper.getPlatformHelper().checkForeignRef(poolProps);
         if (ret.foundCreds) {
            foundCreds = true;
            if (ret.userNameBuf != null && ret.userNameBuf.length() > 0) {
               userNameBuf.append(ret.userNameBuf);
            }

            if (ret.passwdBuf != null && ret.passwdBuf.length() > 0) {
               passwdBuf.append(ret.passwdBuf);
            }
         }

         if (ret.subject != null) {
            this.subject = ret.subject;
         }

         return foundCreds;
      }
   }

   public static Connection getXAConnectionToUse(Connection origConnection) throws JMSException {
      Connection realConnection = origConnection;
      Class connectionClass = origConnection.getClass();
      if (connectionClass.getName().startsWith("progress.message")) {
         ConnectionMetaData metaData = origConnection.getMetaData();
         if (metaData.getProviderMajorVersion() > 4) {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("This is not Sonic 4.*.* release. This is : " + metaData.getProviderVersion() + " . Hence Sonic specific propritary XA calls are not required in this version.");
            }

            return origConnection;
         }

         Method getTopicConn;
         if (origConnection instanceof QueueConnection) {
            try {
               getTopicConn = connectionClass.getMethod("getQueueConnection", (Class[])null);
               realConnection = (QueueConnection)getTopicConn.invoke(origConnection, (Object[])null);
               JMSPoolDebug.logger.debug("Invoked SonicMQ's proprietary getQueueConnection");
            } catch (Throwable var6) {
            }
         } else if (origConnection instanceof TopicConnection) {
            try {
               getTopicConn = connectionClass.getMethod("getTopicConnection", (Class[])null);
               realConnection = (TopicConnection)getTopicConn.invoke(origConnection, (Object[])null);
               JMSPoolDebug.logger.debug("Invoked SonicMQ's proprietary getTopicConnection");
            } catch (Throwable var5) {
            }
         }
      }

      return (Connection)realConnection;
   }

   public Connection getConnection() {
      return this.connection;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   private int adjustSessionType(int origType) {
      if (origType == 4) {
         if (this.connection instanceof QueueConnection) {
            return 1;
         } else {
            return this.connection instanceof TopicConnection ? 2 : 3;
         }
      } else {
         return origType;
      }
   }

   public JMSSessionHolder getNewSession(int origSessionType, boolean transacted, int acknowledgeMode, boolean ignoreXA) throws JMSException {
      this.checkClosed();
      int sessionType = this.adjustSessionType(origSessionType);
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Creating a new JMS session. sessionType = " + sessionType + " transacted = " + transacted + " acknowledgeMode = " + acknowledgeMode + " ignoreXA = " + ignoreXA);
      }

      return !ignoreXA && this.xaConnection != null ? this.getXASessionHolder(sessionType, transacted, acknowledgeMode) : this.getSessionHolder(sessionType, transacted, acknowledgeMode);
   }

   public PooledSession createPooledWrapper(JMSSessionHolder holder, ResourcePool pool, ReferenceQueue queue) throws JMSException {
      byte type;
      switch (holder.getSessionType()) {
         case 1:
            type = 1;
            break;
         case 2:
            type = 2;
            break;
         default:
            type = 0;
      }

      PooledSession retVal = (PooledSession)this.wrapperManager.getWrappedInstance(type, holder.getSession());
      JMSSessionHolder.HolderReference holderRef = holder.makePhantomReference(retVal, pool, queue);
      retVal.init(this.poolName, holderRef, this.hasNativeTransactions, this.wrapperManager);
      return retVal;
   }

   protected WrappedTransactionalSession createNonPooledWrapper(JMSSessionHolder holder) throws JMSException {
      byte type;
      switch (holder.getSessionType()) {
         case 1:
            type = 7;
            break;
         case 2:
            type = 8;
            break;
         default:
            type = 6;
      }

      WrappedTransactionalSession retVal = (WrappedTransactionalSession)this.wrapperManager.getWrappedInstance(type, holder.getSession());
      retVal.init(this.poolName, holder, this.hasNativeTransactions, this.wrapperManager);
      return retVal;
   }

   private JMSSessionHolder getSessionHolder(int sessionType, boolean transacted, int am) throws JMSException {
      int acknowledgeMode = am;
      long startTime = System.currentTimeMillis();
      if (am == 0) {
         acknowledgeMode = 1;
      }

      this.pushSubject();

      JMSSessionHolder var21;
      try {
         JMSSessionHolder retVal;
         switch (sessionType) {
            case 1:
               JMSPoolDebug.logger.debug("Opening a new QueueSession");
               QueueConnection qc = (QueueConnection)this.connection;
               QueueSession qs = qc.createQueueSession(transacted, acknowledgeMode);
               XASession xaqs = null;
               if (qs instanceof XASession) {
                  xaqs = (XASession)qs;
               }

               retVal = new JMSSessionHolder(this, qs, xaqs, sessionType, acknowledgeMode, transacted, startTime);
               break;
            case 2:
               JMSPoolDebug.logger.debug("Opening a new TopicSession");
               TopicConnection tc = (TopicConnection)this.connection;
               TopicSession ts = tc.createTopicSession(transacted, acknowledgeMode);
               XASession xats = null;
               if (ts instanceof XASession) {
                  xats = (XASession)ts;
               }

               retVal = new JMSSessionHolder(this, ts, xats, sessionType, acknowledgeMode, transacted, startTime);
               break;
            case 3:
               JMSPoolDebug.logger.debug("Opening a new Session");
               Session session = this.connection.createSession(transacted, acknowledgeMode);
               XASession xas = null;
               if (session instanceof XASession) {
                  xas = (XASession)session;
               }

               retVal = new JMSSessionHolder(this, session, xas, sessionType, acknowledgeMode, transacted, startTime);
               break;
            default:
               throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable());
         }

         var21 = retVal;
      } catch (ClassCastException var19) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable(), var19);
      } finally {
         this.popSubject();
      }

      return var21;
   }

   private JMSSessionHolder getXASessionHolder(int sessionType, boolean transacted, int am) throws JMSException {
      long startTime = System.currentTimeMillis();
      int acknowledgeMode = am;
      if (am == 0) {
         acknowledgeMode = 1;
      }

      this.pushSubject();

      JMSSessionHolder var21;
      try {
         JMSSessionHolder retVal;
         switch (sessionType) {
            case 1:
               JMSPoolDebug.logger.debug("Opening a new XAQueueSession");
               XAQueueConnection qc = (XAQueueConnection)this.xaConnection;
               XAQueueSession xaqs = qc.createXAQueueSession();
               QueueSession qs = xaqs.getQueueSession();
               retVal = new JMSSessionHolder(this, qs, xaqs, sessionType, acknowledgeMode, transacted, startTime);
               break;
            case 2:
               JMSPoolDebug.logger.debug("Opening a new XATopicSession");
               XATopicConnection tc = (XATopicConnection)this.xaConnection;
               XATopicSession xats = tc.createXATopicSession();
               TopicSession ts = xats.getTopicSession();
               retVal = new JMSSessionHolder(this, ts, xats, sessionType, acknowledgeMode, transacted, startTime);
               break;
            case 3:
               JMSPoolDebug.logger.debug("Opening a new XASession");
               XASession xas = this.xaConnection.createXASession();
               Session session = xas.getSession();
               retVal = new JMSSessionHolder(this, session, xas, sessionType, acknowledgeMode, transacted, startTime);
               break;
            default:
               throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable());
         }

         var21 = retVal;
      } catch (ClassCastException var19) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable(), var19);
      } finally {
         this.popSubject();
      }

      return var21;
   }

   private void unregisterXAResource() {
      this.pushSubject();

      try {
         if (this.xaSession != null) {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            if (tm != null) {
               tm.unregisterResource(this.getXAResourceName(), true);
            }

            this.xaSession.close();
            this.xaSession = null;
         }
      } catch (SystemException var6) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Exception while unregistering XA resource", var6);
         }
      } catch (JMSException var7) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Exception while unregistering XA resource", var7);
         }
      } finally {
         this.popSubject();
      }

   }

   public void firstTimeInit() throws JMSException {
      this.checkClosed();
      String compType = (String)this.poolProps.get("ComponentType");
      if (compType != null && compType.equalsIgnoreCase("EJB") && !this.xaSupported && !this.hasNativeTransactions) {
         JMSPoolLogger.logNoXAOnJMSResource(this.poolName, (String)this.poolProps.get("ComponentName"), (String)this.poolProps.get("ApplicationName"));
      }

      if (this.xaSupported && !this.hasNativeTransactions && this.poolName != null) {
         JMSSessionHolder holder = this.getNewSession(4, false, 1, false);
         this.xaSession = this.createNonPooledWrapper(holder);

         try {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            if (tm != null) {
               tm.registerResource(this.getXAResourceName(), this.xaSession.getXAResource(), PlatformHelper.getPlatformHelper().getResNameEqualProp());
            }
         } catch (SystemException var4) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var4);
         }
      }

   }

   public String getXAResourceName() {
      return PlatformHelper.getPlatformHelper().getXAResourceName(this.poolName);
   }

   public boolean hasNativeTransactions() {
      return this.hasNativeTransactions;
   }

   private synchronized void checkClosed() throws JMSException {
      if (this.connection == null) {
         throw JMSExceptions.getIllegalStateException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public boolean getFactorySetClientID() {
      return this.factorySetClientID;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public synchronized int incrementReferenceCount() {
      return ++this.referenceCount;
   }

   public synchronized int decrementReferenceCount() {
      return --this.referenceCount;
   }

   public synchronized int getReferenceCount() {
      return this.referenceCount;
   }

   public synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, this.subject);
      }

   }

   public synchronized void pushLocalSubject() {
      if (this.localSubject != null) {
         SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, this.localSubject);
      }

   }

   public synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
      }

   }

   public synchronized void popLocalSubject() {
      if (this.localSubject != null) {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
      }

   }

   public static synchronized void pushAnonymousSubject() {
      SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, SubjectManager.getSubjectManager().getAnonymousSubject());
   }

   public static synchronized void popAnonymousSubject() {
      SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
   }

   public boolean isUsedForPooltesting() {
      return this.isUsedForPooltesting;
   }

   public void setUsedForPooltesting(boolean isUsedForPooltesting) {
      this.isUsedForPooltesting = isUsedForPooltesting;
   }

   static {
      if (!"".equals(RESOURCE_REFERENCE_POLICY)) {
         if (!"FailSafe".equalsIgnoreCase(RESOURCE_REFERENCE_POLICY) && !"ReturnDefault".equalsIgnoreCase(RESOURCE_REFERENCE_POLICY)) {
            JMSPoolLogger.logInvalidConnectionFactoryUnmappedResRefMode("FailSafe", "ReturnDefault", RESOURCE_REFERENCE_POLICY);
         } else {
            JMSPoolLogger.logJMSConnectionFactoryUnmappedResRefMode(RESOURCE_REFERENCE_POLICY);
            isValidUnmappedResRefMode = true;
         }
      }

   }

   @Service
   public static class JMSConnectionHelperServiceGeneratorImpl implements JMSConnectionHelperServiceGenerator {
      public JMSConnectionHelperService createJMSConnectionHelperService(String poolName, Map poolProps, boolean containerAuth, WrappedClassManager wrapperManager) throws JMSException {
         return new JMSConnectionHelper(poolName, poolProps, containerAuth, wrapperManager);
      }

      public JMSConnectionHelperService createJMSConnectionHelperService(String poolName, Map poolProps, String userName, String passwd, WrappedClassManager wrapperManager) throws JMSException {
         return new JMSConnectionHelper(poolName, poolProps, userName, passwd, wrapperManager);
      }
   }
}
