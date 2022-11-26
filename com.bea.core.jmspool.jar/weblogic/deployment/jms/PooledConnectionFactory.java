package weblogic.deployment.jms;

import com.oracle.jms.jmspool.ReferenceManager;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.ReferenceQueue;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.utils.LocatorUtilities;

public class PooledConnectionFactory implements QueueConnectionFactory, TopicConnectionFactory, Externalizable, TimerListener {
   static final long serialVersionUID = 2432348962508530307L;
   private String poolName;
   private Map poolProps;
   private int wrapStyle;
   private boolean containerAuth;
   private boolean closed;
   private boolean connected;
   private WrappedClassManager wrapperManager;
   private TimerManager timerManager;
   private long connectDelay;
   private boolean clientIDSet;
   private static final int EXTERNAL_VERSION = 1;
   private static final long CLOSE_WAIT_TIME = 30000L;
   private static final long INITIAL_CONNECT_DELAY = 1L;
   private static final long RETRY_CONNECT_DELAY = 60000L;
   private static final long MAX_CONNECT_DELAY = 1800000L;
   private static final String IBM_JCA_CLASS = "com.ibm.mq.connector";
   private AuthenticatedSubject runAsSubject;
   private transient JMSSessionPoolManager poolManager = null;
   private final ReferenceManager referenceManager = (ReferenceManager)LocatorUtilities.getService(ReferenceManager.class);
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PooledConnectionFactory() {
      this.wrapperManager = new WrappedClassManager();
   }

   public PooledConnectionFactory(String poolName, int wrapStyle, boolean containerAuth, Map poolProps) throws JMSException {
      this.poolName = poolName;
      this.poolProps = poolProps;
      this.wrapStyle = wrapStyle;
      this.containerAuth = containerAuth;
      this.wrapperManager = new WrappedClassManager();
      this.poolManager = JMSSessionPoolManager.getSessionPoolManager();
      this.poolManager.incrementReferenceCount(poolName);
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Creating PooledConnectionFactory named " + poolName + " with wrap style " + wrapStyle + "[poolManager=" + this.poolManager + "]");
      }

      this.runAsSubject = (AuthenticatedSubject)poolProps.get("RunAsSubject");
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("PooledConnectionFactory(): runasSubject = " + this.runAsSubject + " currentSubject = " + SecurityServiceManager.getCurrentSubject(KERNEL_ID) + "[poolManager=" + this.poolManager + "]");
      }

      if (containerAuth) {
         this.pushRunAsSubject();

         try {
            this.timerManager = this.poolManager.getTimerManager();
            this.timerManager.schedule(this, 1L);
         } finally {
            this.popRunAsSubject();
         }
      }

   }

   public synchronized void pushRunAsSubject() {
      if (this.runAsSubject != null) {
         SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, this.runAsSubject);
      }

   }

   public synchronized void popRunAsSubject() {
      if (this.runAsSubject != null) {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
      }

   }

   public void close() throws JMSException {
      this.close(false);
   }

   public synchronized void close(boolean force) throws JMSException {
      this.closed = true;
      this.poolManager.decrementReferenceCount(this.poolName, 30000L, force);
   }

   public QueueConnection createQueueConnection() throws JMSException {
      try {
         return (QueueConnection)this.createConnectionInternal(1, (String)null, (String)null);
      } catch (ClassCastException var2) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSUnableToCreateQueueConnectionLoggable(), var2);
      }
   }

   public QueueConnection createQueueConnection(String userName, String password) throws JMSException {
      try {
         return (QueueConnection)this.createConnectionInternal(1, userName, password);
      } catch (ClassCastException var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSUnableToCreateQueueConnectionLoggable(), var4);
      }
   }

   public TopicConnection createTopicConnection() throws JMSException {
      try {
         return (TopicConnection)this.createConnectionInternal(2, (String)null, (String)null);
      } catch (ClassCastException var2) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSUnableToCreateTopicConnectionLoggable(), var2);
      }
   }

   public TopicConnection createTopicConnection(String userName, String password) throws JMSException {
      try {
         return (TopicConnection)this.createConnectionInternal(2, userName, password);
      } catch (ClassCastException var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSUnableToCreateTopicConnectionLoggable(), var4);
      }
   }

   public Connection createConnection() throws JMSException {
      return (Connection)this.createConnectionInternal(3, (String)null, (String)null);
   }

   public Connection createConnection(String userName, String password) throws JMSException {
      return (Connection)this.createConnectionInternal(3, userName, password);
   }

   public JMSContext createContext() {
      try {
         return this.createContextInternal((String)null, (String)null, 1);
      } catch (AbstractMethodError var2) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createContext()", "javax.jms.ConnectionFactory"), var2);
      }
   }

   public JMSContext createContext(int sessionMode) {
      try {
         return this.createContextInternal((String)null, (String)null, sessionMode);
      } catch (AbstractMethodError var3) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createContext(int sessionMode)", "javax.jms.ConnectionFactory"), var3);
      }
   }

   public JMSContext createContext(String userName, String password) {
      try {
         return this.createContextInternal(userName, password, 1);
      } catch (AbstractMethodError var4) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createContext(String userName, String password)", "javax.jms.ConnectionFactory"), var4);
      }
   }

   public JMSContext createContext(String userName, String password, int sessionMode) {
      try {
         return this.createContextInternal(userName, password, sessionMode);
      } catch (AbstractMethodError var5) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logCalledMissingJMS20MethodLoggable("createContext(String userName, String password, int sessionMode)", "javax.jms.ConnectionFactory"), var5);
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      JMSPoolDebug.logger.debug("In PooledConnectionFactory.writeExternal()+[poolManager=" + this.poolManager + "]");
      out.writeInt(1);
      out.writeInt(this.wrapStyle);
      out.writeUTF(this.poolName);
      out.writeBoolean(this.containerAuth);
      out.writeInt(this.poolProps.size());
      Iterator keys = this.poolProps.keySet().iterator();

      while(keys.hasNext()) {
         String propName = (String)keys.next();
         out.writeUTF(propName);
         out.writeUTF((String)this.poolProps.get(propName));
      }

   }

   public void readExternal(ObjectInput in) throws IOException {
      int extVersion = in.readInt();
      if (extVersion != 1) {
         throw new IOException(JMSPoolLogger.logInvalidExternalVersionLoggable(extVersion).getMessage());
      } else {
         this.wrapStyle = in.readInt();
         this.poolName = in.readUTF();
         this.containerAuth = in.readBoolean();
         int numProps = in.readInt();
         this.poolProps = new HashMap(numProps);

         for(int inc = 0; inc < numProps; ++inc) {
            String name = in.readUTF();
            String value = in.readUTF();
            this.poolProps.put(name, value);
         }

         this.poolManager = JMSSessionPoolManager.getSessionPoolManager();
         this.poolManager.incrementReferenceCount(this.poolName);
         JMSPoolDebug.logger.debug("In PooledConnectionFactory.readExternal()[poolManager=" + this.poolManager + "]");
      }
   }

   private JMSSessionPool getSessionPool() throws JMSException {
      return this.poolManager.findOrCreate(this.poolName, this.poolProps, this.wrapperManager);
   }

   private WrappedConnection createConnectionInternal(int sessionType, String userName, String password) throws JMSException {
      if (!this.containerAuth || userName == null && password == null) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Looking up session pool " + this.poolName + "[poolManager=" + this.poolManager + "]");
         }

         WrappedConnection ret = null;
         JMSSessionPool pool = this.getSessionPool();
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("createConnectionInternal(): subject = " + SecurityServiceManager.getCurrentSubject(KERNEL_ID) + "[poolManager=" + this.poolManager + "]");
         }

         boolean isPooled = true;
         JMSConnectionHelperService helper = null;
         synchronized(this) {
            if (pool.getClientIDPoolingEnabled()) {
               helper = pool.getConnectionHelper(this.containerAuth, userName, password);
            } else if (this.clientIDSet) {
               isPooled = false;
            } else {
               helper = pool.getConnectionHelper(this.containerAuth, userName, password);
               if (helper.getFactorySetClientID()) {
                  helper.close();
                  this.clientIDSet = true;
                  isPooled = false;
               }
            }

            this.connected = true;
         }

         if (isPooled) {
            ret = this.createPooledConnection(sessionType, userName, password, pool, helper);
         } else {
            ret = this.createNonPooledConnection(sessionType, userName, password);
         }

         return (WrappedConnection)ret;
      } else {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSConnectionBadAppAuthLoggable());
      }
   }

   private JMSContext createContextInternal(String userName, String password, int sessionMode) {
      if (this.containerAuth && (userName != null || password != null)) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionBadAppAuthLoggable());
      } else {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Looking up session pool " + this.poolName + "[poolManager=" + this.poolManager + "]");
         }

         JMSSessionPool pool;
         try {
            pool = this.getSessionPool();
         } catch (JMSException var14) {
            throw new JMSRuntimeException(var14.getMessage(), var14.getErrorCode(), var14);
         }

         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("createConnectionInternal(): subject = " + SecurityServiceManager.getCurrentSubject(KERNEL_ID) + "[poolManager=" + this.poolManager + "]");
         }

         boolean isPooled = true;
         PrimaryContextHelperService helper = null;
         synchronized(this) {
            if (pool.getClientIDPoolingEnabled()) {
               helper = pool.getPrimaryContextHelper(this.containerAuth, userName, password);
            } else if (this.clientIDSet) {
               isPooled = false;
            } else {
               helper = pool.getPrimaryContextHelper(this.containerAuth, userName, password);
               if (helper.getFactorySetClientID()) {
                  helper.close();
                  this.clientIDSet = true;
                  isPooled = false;
               }
            }

            this.connected = true;
         }

         if (helper != null && helper.getPrimaryContext() != null && helper.getPrimaryContext().getClass().getName().startsWith("com.ibm.mq.connector")) {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("Calling createContext from inside IBM adapter, return unwrapped context");
            }

            return helper.getPrimaryContext();
         } else {
            JMSContext primaryContext;
            if (isPooled) {
               primaryContext = (JMSContext)this.createPooledPrimaryContext(userName, password, pool, helper);
            } else {
               primaryContext = (JMSContext)this.createNonPooledPrimaryContext(userName, password);
            }

            JMSContext secondaryContext;
            try {
               secondaryContext = primaryContext.createContext(sessionMode);
            } finally {
               if (!isPooled) {
                  primaryContext.close();
               }

            }

            return secondaryContext;
         }
      }
   }

   private WrappedConnection createNonPooledConnection(int sessionType, String userName, String password) throws JMSException {
      JMSConnectionHelperServiceGenerator generator = (JMSConnectionHelperServiceGenerator)LocatorUtilities.getService(JMSConnectionHelperServiceGenerator.class);
      JMSConnectionHelperService helper;
      if (this.containerAuth) {
         helper = generator.createJMSConnectionHelperService(this.poolName, this.poolProps, true, this.wrapperManager);
      } else {
         helper = generator.createJMSConnectionHelperService(this.poolName, this.poolProps, userName, password, this.wrapperManager);
      }

      byte type;
      switch (sessionType) {
         case 1:
            type = 21;
            break;
         case 2:
            type = 22;
            break;
         default:
            type = 20;
      }

      WrappedConnection ret = (WrappedConnection)this.wrapperManager.getWrappedInstance(type, helper.getConnection());
      ret.init(this.wrapStyle, this.wrapperManager, helper);
      return ret;
   }

   private WrappedPrimaryContext createNonPooledPrimaryContext(String userName, String password) {
      PrimaryContextHelperServiceGenerator generator = (PrimaryContextHelperServiceGenerator)LocatorUtilities.getService(PrimaryContextHelperServiceGenerator.class);

      PrimaryContextHelperService helper;
      try {
         if (this.containerAuth) {
            helper = generator.createPrimaryContextHelperService(this.poolName, this.poolProps, true, this.wrapperManager);
         } else {
            helper = generator.createPrimaryContextHelperService(this.poolName, this.poolProps, userName, password, this.wrapperManager);
         }
      } catch (JMSException var9) {
         throw new JMSRuntimeException(var9.getMessage(), var9.getErrorCode(), var9);
      }

      int type = 25;

      WrappedPrimaryContext ret;
      try {
         ret = (WrappedPrimaryContext)this.wrapperManager.getWrappedInstance(type, helper.getPrimaryContext());
      } catch (JMSException var8) {
         throw new JMSRuntimeException(var8.getMessage(), var8.getErrorCode(), var8);
      }

      ret.init(this.wrapStyle, this.wrapperManager, helper);
      return ret;
   }

   private PooledConnection createPooledConnection(int sessionType, String userName, String password, JMSSessionPool pool, JMSConnectionHelperService helper) throws JMSException {
      byte type;
      switch (sessionType) {
         case 1:
            type = 18;
            break;
         case 2:
            type = 19;
            break;
         default:
            type = 17;
      }

      PooledConnection pooled = (PooledConnection)this.wrapperManager.getWrappedInstance(type, helper.getConnection());
      ReferenceQueue refQ = this.referenceManager.getReferenceQueue();
      PooledConnection.ConnectionReference phantomRef = new PooledConnection.ConnectionReference(pooled, pool, helper, userName, password, this.containerAuth, refQ, this.referenceManager);
      pooled.init(phantomRef, this.wrapStyle, this.wrapperManager);
      return pooled;
   }

   private PooledPrimaryContext createPooledPrimaryContext(String userName, String password, JMSSessionPool pool, PrimaryContextHelperService helper) {
      int type = 24;

      PooledPrimaryContext pooled;
      try {
         pooled = (PooledPrimaryContext)this.wrapperManager.getWrappedInstance(type, helper.getPrimaryContext());
      } catch (JMSException var9) {
         throw new JMSRuntimeException(var9.getMessage(), var9.getErrorCode(), var9);
      }

      ReferenceQueue refQ = this.referenceManager.getReferenceQueue();
      PooledPrimaryContext.PrimaryContextReference phantomRef = new PooledPrimaryContext.PrimaryContextReference(pooled, pool, helper, userName, password, this.containerAuth, refQ, this.referenceManager);
      pooled.init(phantomRef, this.wrapStyle, this.wrapperManager);
      return pooled;
   }

   public synchronized long schedule(long time) {
      return !this.closed && !this.connected ? time + this.connectDelay : 0L;
   }

   public synchronized void timerExpired(Timer t) {
      this.pushRunAsSubject();

      try {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("timerExpired(): runAsSubject = " + this.runAsSubject + " subject = " + SecurityServiceManager.getCurrentSubject(KERNEL_ID) + "[poolManager=" + this.poolManager + "]");
         }

         if (!this.closed && !this.connected) {
            try {
               JMSSessionPool pool = this.getSessionPool();
               JMSConnectionHelperService helper = pool.getConnectionHelper(this.containerAuth, (String)null, (String)null);
               if (helper.getFactorySetClientID() && !pool.getClientIDPoolingEnabled()) {
                  this.clientIDSet = true;
                  helper.close();
               }

               this.connected = true;
            } catch (JMSException var7) {
               if (this.connectDelay == 0L) {
                  this.connectDelay = 1L;
               } else if (this.connectDelay == 1L) {
                  this.connectDelay = 60000L;
               } else if (this.connectDelay < 1800000L) {
                  this.connectDelay *= 2L;
                  if (this.connectDelay > 1800000L) {
                     this.connectDelay = 1800000L;
                  }
               }

               if (this.connectDelay >= 60000L) {
                  String compType = (String)this.poolProps.get("ComponentType");
                  if (compType != null && compType.equalsIgnoreCase("EJB")) {
                     JMSPoolLogger.logJMSInitialConnectionFailedEJB(this.poolName, (String)this.poolProps.get("ComponentName"), (String)this.poolProps.get("ApplicationName"), var7.toString());
                  } else {
                     JMSPoolLogger.logJMSInitialConnectionFailed(this.poolName, var7.toString());
                  }
               }

               this.timerManager.schedule(this, this.connectDelay);
            }
         }
      } finally {
         this.popRunAsSubject();
      }

   }
}
