package weblogic.jms.integration.injection;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSPasswordCredential;
import javax.jms.JMSRuntimeException;
import javax.jms.JMSSessionMode;
import javax.jms.XAConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.connector.external.RAUtil;
import weblogic.deployment.jms.ForeignOpaqueReference;
import weblogic.deployment.jms.PooledConnectionFactory;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.common.WLJMSRuntimeException;
import weblogic.logging.Loggable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.transaction.TransactionHelper;

public class InjectableJMSContext extends ForwardingJMSContextInternal implements Serializable {
   private static final long serialVersionUID = -805036399302074792L;
   private final String id;
   private RequestedJMSContextManager requestScopeJMSContextManager;
   @Inject
   private Instance requestScopeJMSContextManagerInstance;
   private TransactedJMSContextManager transactionScopeJMSContextManager;
   @Inject
   private Instance transactionScopeJMSContextManagerInstance;
   private final JMSContextMetadata metadata;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private transient ConnectionFactory connectionFactory;
   private static Method pcfCloseMethod = initializePCFCloseMethod();

   @Inject
   public InjectableJMSContext(InjectionPoint ip) {
      JMSConnectionFactory jmsConnectionFactoryAnnot = (JMSConnectionFactory)ip.getAnnotated().getAnnotation(JMSConnectionFactory.class);
      JMSSessionMode sessionModeAnnot = (JMSSessionMode)ip.getAnnotated().getAnnotation(JMSSessionMode.class);
      JMSPasswordCredential credentialAnnot = (JMSPasswordCredential)ip.getAnnotated().getAnnotation(JMSPasswordCredential.class);
      this.metadata = new JMSContextMetadata(jmsConnectionFactoryAnnot, sessionModeAnnot, credentialAnnot);
      this.id = this.metadata.getFingerPrint();
   }

   @PostConstruct
   public void postConstruct() {
      this.transactionScopeJMSContextManager = (TransactedJMSContextManager)this.transactionScopeJMSContextManagerInstance.get();
      this.requestScopeJMSContextManager = (RequestedJMSContextManager)this.requestScopeJMSContextManagerInstance.get();
      if (!(this.requestScopeJMSContextManager instanceof Serializable)) {
         throw new AssertionError("Injected proxy is not Serializable");
      }
   }

   protected JMSContext delegate() {
      Object manager;
      if (this.isInTransaction()) {
         manager = this.transactionScopeJMSContextManager;
      } else {
         manager = this.requestScopeJMSContextManager;
      }

      try {
         return ((AbstractJMSContextManager)manager).getContext(this.id, this.metadata, this.getConnectionFactory());
      } catch (ContextNotActiveException var4) {
         String message = JMSClientExceptionLogger.logNoValidScopeForInjectedJMSContextLoggable().getMessage();
         throw new JMSRuntimeException(message, (String)null, var4);
      }
   }

   public String toString() {
      boolean isInTransaction = this.isInTransaction();
      StringBuffer sb = new StringBuffer();
      sb.append("Injected JMSContext proxy with metadata [").append(this.metadata).append("]");
      JMSContext delegateContext;
      if (isInTransaction) {
         sb.append("transaction scoped with delegate ");

         try {
            delegateContext = this.transactionScopeJMSContextManager.getContext(this.id);
            if (delegateContext == null) {
               sb.append("not yet created");
            } else {
               sb.append("[").append(delegateContext.toString()).append("]");
            }
         } catch (ContextNotActiveException var6) {
            sb.append("unknown as no valid transaction scope");
         }
      } else {
         sb.append("request scoped with delegate ");

         try {
            delegateContext = this.requestScopeJMSContextManager.getContext(this.id);
            if (delegateContext == null) {
               sb.append("not yet created");
            } else {
               sb.append("[").append(delegateContext.toString()).append("]");
            }
         } catch (ContextNotActiveException var5) {
            sb.append("unknown as no valid request scope");
         }
      }

      return sb.toString();
   }

   private static Method initializePCFCloseMethod() {
      try {
         return PooledConnectionFactory.class.getMethod("close");
      } catch (SecurityException | NoSuchMethodException var1) {
         throw new JMSRuntimeException(var1.getMessage(), (String)null, var1);
      }
   }

   @PreDestroy
   public void cleanup() {
      this.cleanupManager(this.requestScopeJMSContextManager);
      this.cleanupManager(this.transactionScopeJMSContextManager);
      if (this.connectionFactory instanceof PooledConnectionFactory) {
         PooledConnectionFactory pcf = (PooledConnectionFactory)this.connectionFactory;

         try {
            pcfCloseMethod.invoke(pcf);
         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException var3) {
            throw new JMSRuntimeException(var3.getMessage(), (String)null, var3);
         }
      }

   }

   private void cleanupManager(AbstractJMSContextManager manager) {
      try {
         manager.cleanup();
      } catch (ContextNotActiveException var3) {
      }

   }

   private ConnectionFactory getConnectionFactory() {
      if (this.connectionFactory != null) {
         return this.connectionFactory;
      } else {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String jndiName;
         if (this.metadata.getLookup() == null) {
            jndiName = "java:comp/DefaultJMSConnectionFactory";
         } else {
            jndiName = this.metadata.getLookup();
         }

         InitialContext initialContext = null;

         Loggable loggable;
         try {
            initialContext = new InitialContext();
         } catch (NamingException var8) {
            loggable = JMSExceptionLogger.logExceptionCreatingInitialContextWhilstInjectingJMSContextLoggable(var8);
            throw new WLJMSRuntimeException(loggable, loggable.getId(), var8);
         }

         Object cfObject;
         try {
            cfObject = initialContext.lookup(jndiName);
         } catch (NamingException var7) {
            Loggable loggable = JMSExceptionLogger.logNamingExceptionLookingUpConnectionFactoryWhilstInjectingJMSContextLoggable(jndiName);
            throw new WLJMSRuntimeException(loggable, loggable.getId(), var7);
         }

         if (cfObject instanceof PooledConnectionFactory) {
            return (PooledConnectionFactory)cfObject;
         } else if (cfObject instanceof ConnectionFactory && isFromResourceAdapter(initialContext, cic, jndiName)) {
            this.connectionFactory = (ConnectionFactory)cfObject;
            return this.connectionFactory;
         } else if (!(cfObject instanceof ConnectionFactory) && !(cfObject instanceof XAConnectionFactory)) {
            loggable = JMSExceptionLogger.logClassCastExceptionLookingUpConnectionFactoryWhilstInjectingJMSContextLoggable(jndiName, cfObject.getClass().getName());
            throw new WLJMSRuntimeException(loggable, loggable.getId());
         } else {
            this.connectionFactory = this.createPooledConnectionFactory(cic, jndiName);
            return this.connectionFactory;
         }
      }
   }

   private PooledConnectionFactory createPooledConnectionFactory(ComponentInvocationContext cic, String jndiName) {
      int wrapStyle = 1;
      boolean containerAuth;
      if (this.metadata.getUserName() == null) {
         containerAuth = true;
      } else {
         containerAuth = false;
      }

      Map poolProps = new HashMap();
      poolProps.put("JNDIName", jndiName);
      String applicationName = cic.getApplicationId();
      poolProps.put("ApplicationName", applicationName);
      String componentName = cic.getComponentName();
      poolProps.put("ComponentName", componentName);
      poolProps.put("ComponentType", "EJB");
      String appId = ApplicationVersionUtils.getBindApplicationId();
      if (appId != null) {
         poolProps.put("weblogic.jndi.lookupApplicationId", appId);
      }

      String moduleName = cic.getModuleName();
      String poolName;
      if (jndiName.startsWith("java:app")) {
         poolName = applicationName + "-" + jndiName;
      } else if (jndiName.startsWith("java:module")) {
         poolName = applicationName + "-" + moduleName + "-" + jndiName;
      } else if (jndiName.startsWith("java:comp")) {
         poolName = applicationName + "-" + moduleName + "-" + componentName + "-" + jndiName;
      } else {
         poolName = jndiName;
      }

      AuthenticatedSubject currentSubject = SecurityManager.getCurrentSubject(KERNEL_ID);
      poolProps.put("RunAsSubject", currentSubject);

      try {
         return new PooledConnectionFactory(poolName, wrapStyle, containerAuth, poolProps);
      } catch (JMSException var13) {
         throw new JMSRuntimeException(var13.getMessage(), var13.getErrorCode(), var13);
      }
   }

   public static boolean isFromResourceAdapter(Context initialContext, ComponentInvocationContext cic, String jndiName) {
      String jndiNameOfCF = jndiName;

      Object link;
      do {
         try {
            link = initialContext.lookupLink(jndiNameOfCF);
         } catch (NamingException var6) {
            throw new WLJMSRuntimeException(var6);
         }

         if (link instanceof ForeignOpaqueReference) {
            if (((ForeignOpaqueReference)link).getJNDIEnvironment() != null) {
               return false;
            }

            jndiNameOfCF = ((ForeignOpaqueReference)link).getRemoteJNDIName();
         }
      } while(link instanceof ForeignOpaqueReference);

      return RAUtil.isConnectionFactory(jndiNameOfCF, cic);
   }

   private boolean isInTransaction() {
      boolean isInTransaction = false;
      Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
      if (tx != null) {
         isInTransaction = true;
      }

      return isInTransaction;
   }
}
