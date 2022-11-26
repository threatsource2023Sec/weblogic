package weblogic.deployment.jms;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.XAConnectionFactory;
import javax.jms.XAJMSContext;
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

public class PrimaryContextHelper implements PrimaryContextHelperService {
   static final String DEFAULT_JMS_CONNECTION_FACTORY = "java:comp/DefaultJMSConnectionFactory";
   private final String DEFAULT_JMS_CONNECTION_FACTORY_INTERNAL = "weblogic.jms.DefaultConnectionFactory";
   private JMSContext primaryContext;
   private XAJMSContext primaryXAContext;
   private boolean xaSupported;
   private WrappedTransactionalSecondaryContext xaWrappedTransactionalSecondaryContext;
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

   protected PrimaryContextHelper(String poolName, Map poolProps, boolean containerAuth, WrappedClassManager wrapperManager) throws JMSException {
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
         JMSPoolDebug.logger.debug("CR315579: PrimaryContextHelper() : subject=" + this.subject + " currentSubject=" + currentSubject + " localSubject " + this.localSubject + " username=" + userName + " password=XXXXX");
      }

      this.openConnection(hasCreds, userName, passwd, poolProps);
   }

   protected PrimaryContextHelper(String poolName, Map poolProps, String userName, String passwd, WrappedClassManager wrapperManager) throws JMSException {
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
         JMSPoolDebug.logger.debug("CR315579: PrimaryContextHelper() 2: subject=" + this.subject + " currentSubject=" + currentSubject + " localSubject=" + this.localSubject + " username=" + userName + " password=XXXXX");
      }

      this.openConnection(hasCreds, userName, passwd, poolProps);
   }

   public synchronized void close() {
      JMSRuntimeException savedException = null;
      if (this.primaryContext != null) {
         this.unregisterXAResource();
         AbstractSubject currentSubject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
         if (this.subject == null && currentSubject.equals(KERNEL_ID) || this.subject != null && this.subject.equals(KERNEL_ID)) {
            this.subject = SubjectManager.getSubjectManager().getAnonymousSubject();
         }

         this.pushSubject();

         try {
            this.primaryContext.close();
         } catch (JMSRuntimeException var7) {
            savedException = var7;
         } finally {
            this.popSubject();
         }

         JMSPoolDebug.logger.debug("Closed a primary JMSContext in PrimaryContextHelper");
         this.primaryContext = null;
         this.primaryXAContext = null;
         this.xaWrappedTransactionalSecondaryContext = null;
         if (savedException != null) {
            throw savedException;
         }
      }
   }

   protected void start() {
      this.checkClosed();
      this.pushSubject();

      try {
         this.primaryContext.start();
      } finally {
         this.popSubject();
      }

   }

   protected void stop() {
      this.checkClosed();
      this.pushSubject();

      try {
         this.primaryContext.stop();
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
            JMSPoolDebug.logger.debug("Failed to open a new connection in PrimaryContextHelper for pool " + this.poolName + ", the JMS connection factory with JNDI name " + jndiName + " is object-based-security enabled and is not supported by wrappers.");
         }

         throw JMSExceptions.getJMSException(JMSPoolLogger.logUnsupportedOBSEnabledCFLoggable(jndiName));
      } else {
         this.pushSubject();

         try {
            if (factory instanceof XAConnectionFactory) {
               JMSPoolDebug.logger.debug("Creating XAJMSContext");
               XAConnectionFactory xacf = (XAConnectionFactory)factory;
               Class cls = null;

               try {
                  cls = Class.forName("weblogic.jms.client.JMSXAConnectionFactory");
               } catch (ClassNotFoundException var12) {
                  var12.printStackTrace();
               }

               if (cls != null && cls.isInstance(factory)) {
                  if (hasCreds) {
                     this.primaryXAContext = xacf.createXAContext(userName, passwd);
                  } else {
                     this.primaryXAContext = xacf.createXAContext();
                  }
               } else {
                  this.primaryXAContext = new PsuedoXAJMSContext(xacf, hasCreds, userName, passwd);
               }

               this.xaSupported = true;
               this.primaryContext = this.primaryXAContext;
            } else {
               JMSPoolDebug.logger.debug("Creating JMSContext");
               ConnectionFactory cf = (ConnectionFactory)factory;
               JMSContext c;
               if (hasCreds) {
                  c = cf.createContext(userName, passwd);
               } else {
                  c = cf.createContext();
               }

               this.primaryContext = c;
            }

            if (this.primaryContext.getClientID() != null) {
               this.factorySetClientID = true;
            }
         } finally {
            this.popSubject();
         }

         this.checkForNativeTransactions();
      }
   }

   public void markAsPooled() {
      JMSContext ctx = this.primaryContext;
      if (ctx != null) {
         Class c = ctx.getClass();
         if (c.getName().startsWith("weblogic.jms")) {
            try {
               Method m = c.getMethod("markAsJMSSessionPooledInWrapper", (Class[])null);
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("PrimaryContextHelper[poolName=" + this.poolName + "].markAsPooled(): invoke WLJMS JMSContext method markAsJMSSessionPooledInWrapper()");
               }

               m.invoke(ctx, (Object[])null);
            } catch (Exception var4) {
               throw new JMSRuntimeException(var4.getMessage(), (String)null, var4);
            }
         }
      }
   }

   private void checkForNativeTransactions() {
      this.hasNativeTransactions = hasNativeTransactions(this.primaryContext);
   }

   protected static boolean hasNativeTransactions(JMSContext primaryContextArg) {
      boolean result = false;
      if (primaryContextArg instanceof PsuedoXAJMSContext) {
         result = ((PsuedoXAJMSContext)primaryContextArg).isHasNativeTransactions();
      }

      try {
         Class cls = Class.forName("weblogic.jms.client.JMSContextInternal");
         if (cls.isInstance(primaryContextArg)) {
            Method meth = cls.getMethod("getUserTransactionsEnabled", (Class[])null);
            Object retobj = meth.invoke(primaryContextArg, (Object[])null);
            Boolean retval = (Boolean)retobj;
            boolean boolval = retval;
            if (boolval) {
               result = true;
            } else if (KernelStatus.isServer()) {
               meth = cls.getMethod("isXAServerEnabled", (Class[])null);
               retobj = meth.invoke(primaryContextArg, (Object[])null);
               retval = (Boolean)retobj;
               boolval = retval;
               if (boolval) {
                  result = true;
               }
            }

            Class[] partypes = new Class[]{String.class};
            meth = cls.getMethod("setReconnectPolicy", partypes);
            Object[] args = new Object[]{"none"};
            meth.invoke(primaryContextArg, args);
         }
      } catch (ClassNotFoundException var9) {
      } catch (NoSuchMethodException var10) {
      } catch (IllegalAccessException var11) {
      } catch (InvocationTargetException var12) {
      }

      return result;
   }

   protected Object lookupConnectionFactory(Map poolProps) {
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
                  throw new JMSRuntimeException(JMSPoolLogger.logNotAConnectionFactoryLoggable(this.poolName, jndiName, factory.getClass().getName()).getMessage());
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
                  throw new JMSRuntimeException(JMSPoolLogger.logNotAConnectionFactoryLoggable(this.poolName, jndiName, factory.getClass().getName()).getMessage());
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Found connection factory of type " + factory.getClass().getName());
               }
            } catch (NameNotFoundException var36) {
               boolean isForeignConnectionFactory = false;
               if ("ReturnDefault".equalsIgnoreCase(cfUnmappedResourceReferenceMode) && isResRefNameJNDIName) {
                  try {
                     factory = context.lookupLink(jndiName);
                     if (factory instanceof ForeignOpaqueTag) {
                        isForeignConnectionFactory = true;
                     }
                  } catch (NamingException var35) {
                  }
               }

               if (isForeignConnectionFactory || !"ReturnDefault".equalsIgnoreCase(cfUnmappedResourceReferenceMode) || !isResRefNameJNDIName) {
                  throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var36);
               }

               try {
                  JMSPoolLogger.logLookupDefaultJMSConnectionFactory(jndiName, (String)poolProps.get("ApplicationName"));
                  factory = context.lookup("weblogic.jms.DefaultConnectionFactory");
               } catch (NamingException var34) {
                  throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var34);
               }
            } catch (NamingException var37) {
               throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionFactoryLookupFailedLoggable(jndiName), var37);
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

   static String getJNDIName(Map poolProps) {
      String jndiName = (String)poolProps.get("JNDIName");
      if (jndiName == null) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSSessionPoolPropertyMissingLoggable("JNDIName"));
      } else {
         return jndiName;
      }
   }

   static Hashtable getJNDIEnvironment(Map poolProps) {
      Hashtable jndiEnv = new Hashtable(3);
      Iterator i = poolProps.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         String propName;
         do {
            if (!i.hasNext()) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug(" PrimaryContextHelper.getJNDIEnvironment() jndiEnv " + jndiEnv);
               }

               return jndiEnv;
            }

            entry = (Map.Entry)i.next();
            propName = (String)entry.getKey();
         } while(!propName.startsWith("java.naming") && !propName.startsWith("weblogic.jndi"));

         jndiEnv.put(propName, entry.getValue());
      }
   }

   private boolean getCredentials(Map poolProps, StringBuffer userNameBuf, StringBuffer passwdBuf) {
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
            } catch (NamingException var17) {
            } finally {
               try {
                  if (context != null) {
                     context.close();
                  }
               } catch (NamingException var16) {
               }

            }
         }

         return true;
      } else {
         PlatformHelper.ForeignRefReturn ret;
         try {
            ret = PlatformHelper.getPlatformHelper().checkForeignRef(poolProps);
         } catch (JMSException var19) {
            throw new JMSRuntimeException(var19.getMessage(), var19.getErrorCode(), var19);
         }

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

   public JMSContext getPrimaryContext() {
      return this.primaryContext;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   public SecondaryContextHolder getNewSecondaryContext(int sessionMode, boolean ignoreXA) {
      this.checkClosed();
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Creating a new secondary context:  sessionMode = " + sessionMode + " ignoreXA = " + ignoreXA);
      }

      return !ignoreXA && this.primaryXAContext != null ? this.getXASecondaryContextHolder(sessionMode) : this.getSecondaryContextHolder(sessionMode);
   }

   public PooledSecondaryContext createPooledWrapper(SecondaryContextHolder holder, ResourcePool pool, ReferenceQueue queue) {
      int type = 26;

      PooledSecondaryContext retVal;
      try {
         retVal = (PooledSecondaryContext)this.wrapperManager.getWrappedInstance(type, holder.getSecondaryContext());
      } catch (JMSException var7) {
         throw new JMSRuntimeException(var7.getMessage(), var7.getErrorCode(), var7);
      }

      SecondaryContextHolder.HolderReference holderRef = holder.makePhantomReference(retVal, pool, queue);
      retVal.init(this.poolName, holderRef, this.hasNativeTransactions, this.wrapperManager);
      return retVal;
   }

   protected WrappedTransactionalSecondaryContext createNonPooledWrapper(SecondaryContextHolder holder) {
      int type = 27;

      WrappedTransactionalSecondaryContext retVal;
      try {
         retVal = (WrappedTransactionalSecondaryContext)this.wrapperManager.getWrappedInstance(type, holder.getSecondaryContext());
      } catch (JMSException var5) {
         throw new JMSRuntimeException(var5.getMessage(), var5.getErrorCode(), var5);
      }

      retVal.init(this.poolName, holder, this.hasNativeTransactions, this.wrapperManager);
      return retVal;
   }

   private SecondaryContextHolder getSecondaryContextHolder(int sessionModeArg) {
      int sessionMode = sessionModeArg;
      long startTime = System.currentTimeMillis();
      this.pushSubject();

      SecondaryContextHolder var54;
      try {
         JMSPoolDebug.logger.debug("Opening a new Session");
         JMSContext context;
         if (this.primaryContext instanceof PsuedoXAJMSContext) {
            PsuedoXAJMSContext psuedoJMSContext = (PsuedoXAJMSContext)this.primaryXAContext;
            XAJMSContext tempContext;
            Throwable var9;
            if (psuedoJMSContext.isHasCreds()) {
               tempContext = psuedoJMSContext.getConnectionFactory().createXAContext(psuedoJMSContext.getUserName(), psuedoJMSContext.getPassword());
               var9 = null;

               try {
                  context = tempContext.createContext(sessionMode);
               } catch (Throwable var48) {
                  var9 = var48;
                  throw var48;
               } finally {
                  if (tempContext != null) {
                     if (var9 != null) {
                        try {
                           tempContext.close();
                        } catch (Throwable var45) {
                           var9.addSuppressed(var45);
                        }
                     } else {
                        tempContext.close();
                     }
                  }

               }
            } else {
               tempContext = psuedoJMSContext.getConnectionFactory().createXAContext();
               var9 = null;

               try {
                  context = tempContext.createContext(sessionMode);
               } catch (Throwable var47) {
                  var9 = var47;
                  throw var47;
               } finally {
                  if (tempContext != null) {
                     if (var9 != null) {
                        try {
                           tempContext.close();
                        } catch (Throwable var46) {
                           var9.addSuppressed(var46);
                        }
                     } else {
                        tempContext.close();
                     }
                  }

               }
            }
         } else {
            context = this.primaryContext.createContext(sessionMode);
         }

         XAJMSContext xas = null;
         if (context instanceof XAJMSContext) {
            xas = (XAJMSContext)context;
         }

         SecondaryContextHolder retVal = new SecondaryContextHolder(this, context, xas, sessionMode, startTime);
         var54 = retVal;
      } catch (ClassCastException var51) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable(), var51);
      } finally {
         this.popSubject();
      }

      return var54;
   }

   private SecondaryContextHolder getXASecondaryContextHolder(int sessionModeArg) {
      if (sessionModeArg != 1 && sessionModeArg != 2 && sessionModeArg != 3 && sessionModeArg != 0 && sessionModeArg != 4 && sessionModeArg != 128) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logInvalidSessionModeLoggable(sessionModeArg));
      } else {
         long startTime = System.currentTimeMillis();
         int sessionMode = sessionModeArg;
         this.pushSubject();

         SecondaryContextHolder var29;
         try {
            JMSPoolDebug.logger.debug("Creating a new secondary XAJMSContext");
            Object context;
            XAJMSContext xaJMSContext;
            if (this.primaryXAContext instanceof PsuedoXAJMSContext) {
               PsuedoXAJMSContext psuedoJMSContext = (PsuedoXAJMSContext)this.primaryXAContext;
               if (psuedoJMSContext.isHasCreds()) {
                  xaJMSContext = psuedoJMSContext.getConnectionFactory().createXAContext(psuedoJMSContext.getUserName(), psuedoJMSContext.getPassword());
               } else {
                  xaJMSContext = psuedoJMSContext.getConnectionFactory().createXAContext();
               }

               context = xaJMSContext;
            } else {
               Class cls = Class.forName("weblogic.jms.client.XAJMSContextImpl");
               Method meth = cls.getMethod("createXAContext", (Class[])null);
               Object retobj = meth.invoke(this.primaryXAContext, (Object[])null);
               context = (JMSContext)retobj;
               xaJMSContext = (XAJMSContext)context;
            }

            SecondaryContextHolder retVal = new SecondaryContextHolder(this, (JMSContext)context, xaJMSContext, sessionMode, startTime);
            var29 = retVal;
         } catch (ClassCastException var20) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSConnectionFactoryWrongTypeLoggable(), var20);
         } catch (ClassNotFoundException var21) {
            throw new JMSRuntimeException(var21.getMessage(), (String)null, var21);
         } catch (NoSuchMethodException var22) {
            throw new JMSRuntimeException(var22.getMessage(), (String)null, var22);
         } catch (SecurityException var23) {
            throw new JMSRuntimeException(var23.getMessage(), (String)null, var23);
         } catch (IllegalAccessException var24) {
            throw new JMSRuntimeException(var24.getMessage(), (String)null, var24);
         } catch (IllegalArgumentException var25) {
            throw new JMSRuntimeException(var25.getMessage(), (String)null, var25);
         } catch (InvocationTargetException var26) {
            throw new JMSRuntimeException(var26.getMessage(), (String)null, var26);
         } finally {
            this.popSubject();
         }

         return var29;
      }
   }

   private void unregisterXAResource() {
      this.pushSubject();

      try {
         if (this.xaWrappedTransactionalSecondaryContext != null) {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            if (tm != null) {
               tm.unregisterResource(this.getXAResourceName(), true);
            }

            this.xaWrappedTransactionalSecondaryContext.close();
            this.xaWrappedTransactionalSecondaryContext = null;
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

   public void firstTimeInit() {
      this.checkClosed();
      String compType = (String)this.poolProps.get("ComponentType");
      if (compType != null && compType.equalsIgnoreCase("EJB") && !this.xaSupported && !this.hasNativeTransactions) {
         JMSPoolLogger.logNoXAOnJMSResource(this.poolName, (String)this.poolProps.get("ComponentName"), (String)this.poolProps.get("ApplicationName"));
      }

      if (this.xaSupported && !this.hasNativeTransactions && this.poolName != null) {
         SecondaryContextHolder holder = this.getNewSecondaryContext(1, false);
         this.xaWrappedTransactionalSecondaryContext = this.createNonPooledWrapper(holder);

         try {
            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            if (tm != null) {
               tm.registerResource(this.getXAResourceName(), this.xaWrappedTransactionalSecondaryContext.getXAResource(), PlatformHelper.getPlatformHelper().getResNameEqualProp());
            }
         } catch (SystemException var4) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var4);
         }
      }

   }

   public String getXAResourceName() {
      return PlatformHelper.getPlatformHelper().getXAResourceName(this.poolName);
   }

   public boolean hasNativeTransactions() {
      return this.hasNativeTransactions;
   }

   private synchronized void checkClosed() {
      if (this.primaryContext == null) {
         throw JMSExceptions.getIllegalStateRuntimeException(JMSPoolLogger.logJMSObjectClosedLoggable());
      }
   }

   public boolean getFactorySetClientID() {
      return this.factorySetClientID;
   }

   String getPoolName() {
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
   private static class PrimaryContextHelperServiceGeneratorImpl implements PrimaryContextHelperServiceGenerator {
      public PrimaryContextHelperService createPrimaryContextHelperService(String poolName, Map poolProps, boolean containerAuth, WrappedClassManager wrapperManager) throws JMSException {
         return new PrimaryContextHelper(poolName, poolProps, containerAuth, wrapperManager);
      }

      public PrimaryContextHelperService createPrimaryContextHelperService(String poolName, Map poolProps, String userName, String passwd, WrappedClassManager wrapperManager) throws JMSException {
         return new PrimaryContextHelper(poolName, poolProps, userName, passwd, wrapperManager);
      }

      public boolean hasNativeTransactions(JMSContext primaryContextArg) {
         return PrimaryContextHelper.hasNativeTransactions(primaryContextArg);
      }
   }
}
