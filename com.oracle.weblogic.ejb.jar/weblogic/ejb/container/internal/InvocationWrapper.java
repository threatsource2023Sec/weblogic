package weblogic.ejb.container.internal;

import java.rmi.AccessException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import javax.ejb.AccessLocalException;
import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.WLSessionBean;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.persistence.ExtendedPersistenceContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TimedOutException;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionSystemException;

public final class InvocationWrapper implements InvocationContext {
   private static final DebugLogger debugLogger;
   private static final AuthenticatedSubject KERNEL_ID;
   private Transaction callerTx;
   private Transaction invokeTx;
   private Thread thread = Thread.currentThread();
   private Object bean;
   private Object primaryKey;
   private final MethodDescriptor md;
   private AtomicBoolean cancelRunning;
   private boolean isLocal;
   private boolean hasSystemExceptionOccured;
   private boolean isRemoteInvocation;
   private boolean shouldLogException = true;
   private int retryCount;
   private Lock acquiredLock = null;
   private Set pushedXPCWrappers = null;
   private boolean environmentPushed = false;
   private boolean callerPrincipalPushed = false;
   private boolean methodObjectPushed = false;
   private boolean runAsPushed = false;
   private AuthenticatedSubject altRunAsSubject = null;
   private ClassLoader savedContextClassLoader = null;
   private boolean callerTxCaptured = false;
   private Map contextData = null;
   private boolean securityContextPushed = false;
   private ManagedInvocationContext mic;

   private InvocationWrapper(MethodDescriptor md) {
      this.md = md;
      if (md != null) {
         this.isLocal = md.isLocal();
         this.retryCount = md.getRetryOnRollbackCount();
      }

   }

   public static InvocationWrapper newInstance() {
      return newInstance((MethodDescriptor)null);
   }

   public static InvocationWrapper newInstance(MethodDescriptor md) {
      return new InvocationWrapper(md);
   }

   public static InvocationWrapper newInstance(MethodDescriptor md, Object pk) {
      InvocationWrapper wrap = newInstance(md);
      wrap.setPrimaryKey(pk);
      return wrap;
   }

   public ManagedInvocationContext setCIC(BeanInfo bi) {
      this.mic = bi.setCIC();
      return this.mic;
   }

   public void unsetCIC() {
      this.mic.close();
      this.mic = null;
   }

   public Object getBean() {
      return this.bean;
   }

   public void setBean(Object b) {
      this.bean = b;
   }

   public Transaction getCallerTx() {
      return this.callerTx;
   }

   public Transaction getInvokeTx() {
      return this.invokeTx;
   }

   public Object getInvokeTxOrThread() {
      return this.invokeTx != null ? this.invokeTx : this.thread;
   }

   public Object getPrimaryKey() {
      return this.primaryKey;
   }

   void setPrimaryKey(Object pk) {
      this.primaryKey = pk;
   }

   public MethodDescriptor getMethodDescriptor() {
      return this.md;
   }

   AtomicBoolean getCancelRunning() {
      return this.cancelRunning;
   }

   void setCancelRunning(AtomicBoolean cancelRunning) {
      this.cancelRunning = cancelRunning;
   }

   public boolean isLocal() {
      return this.isLocal;
   }

   int getNextTxRetryCount() {
      if (--this.retryCount < 0) {
         this.retryCount = 0;
      }

      return this.retryCount;
   }

   public void perhapsPushXPCWrappers() {
      if (this.md != null && this.md.containsExtendedPersistenceContextRefs()) {
         this.pushedXPCWrappers = ExtendedPersistenceContextManager.pushMissingWrappers(((WLSessionBean)this.bean).__WL_getExtendedPersistenceContexts());
      }

   }

   public void popXPCWrappers() {
      if (this.md != null && this.md.containsExtendedPersistenceContextRefs() && this.pushedXPCWrappers != null) {
         ExtendedPersistenceContextManager.popWrappers(this.pushedXPCWrappers);
         this.pushedXPCWrappers = null;
      }

   }

   public Lock getAcquiredLock() {
      return this.acquiredLock;
   }

   public void setAcquiredLock(Lock acquiredLock) {
      this.acquiredLock = acquiredLock;
   }

   public boolean hasSystemExceptionOccured() {
      return this.hasSystemExceptionOccured;
   }

   void setSystemExceptionOccured() {
      this.hasSystemExceptionOccured = true;
   }

   void setIsRemoteInvocation() {
      this.isRemoteInvocation = true;
   }

   boolean isRemoteInvocation() {
      return this.isRemoteInvocation;
   }

   public boolean shouldLogException() {
      return this.shouldLogException;
   }

   public void skipLoggingException() {
      this.shouldLogException = false;
   }

   public void pushEnvironment(Context envCtx) {
      EJBRuntimeUtils.pushEnvironment(envCtx);
      this.environmentPushed = true;
   }

   public void popEnvironment() {
      if (this.environmentPushed) {
         EJBRuntimeUtils.popEnvironment();
         this.environmentPushed = false;
      }

   }

   void pushCallerPrincipal() {
      SecurityHelper.pushCallerPrincipal();
      this.callerPrincipalPushed = true;
   }

   void popCallerPrincipal() {
      if (this.callerPrincipalPushed) {
         try {
            SecurityHelper.popCallerPrincipal();
            this.callerPrincipalPushed = false;
         } catch (PrincipalNotFoundException var2) {
            EJBLogger.logErrorPoppingCallerPrincipal(var2);
         }
      }

   }

   void setAlternativeRunAsSubject(AuthenticatedSubject altRunAsSubject) {
      this.altRunAsSubject = altRunAsSubject;
   }

   void pushRunAsIdentity() {
      if (this.altRunAsSubject != null) {
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.altRunAsSubject);
         this.runAsPushed = true;
      } else if (this.md.getRunAsSubject() != null) {
         SecurityHelper.pushRunAsSubject(KERNEL_ID, this.md.getRunAsSubject());
         this.runAsPushed = true;
      }

   }

   void popRunAsIdentity() {
      if (this.runAsPushed) {
         SecurityHelper.popRunAsSubject(KERNEL_ID);
         this.runAsPushed = false;
      }

   }

   void pushSecurityContext(ContextHandler ch) {
      this.getMethodDescriptor().getSecurityHelper().pushSecurityContext(ch);
      this.securityContextPushed = true;
   }

   void popSecurityContext() {
      if (this.securityContextPushed) {
         this.getMethodDescriptor().getSecurityHelper().popSecurityContext();
         this.securityContextPushed = false;
      }

   }

   public void pushMethodObject(BeanInfo beanInfo) {
      MethodInvocationHelper.pushMethodObject(beanInfo);
      this.methodObjectPushed = true;
   }

   public boolean popMethodObject(BeanInfo beanInfo) {
      if (this.methodObjectPushed) {
         this.methodObjectPushed = false;
         return MethodInvocationHelper.popMethodObject(beanInfo);
      } else {
         return false;
      }
   }

   public void setContextClassLoader(ClassLoader cl) {
      if (this.savedContextClassLoader != null && this.savedContextClassLoader != cl) {
         throw new AssertionError("Unexpected setContextClassLoader call!");
      } else {
         Thread currentThread = Thread.currentThread();
         ClassLoader oldCL = currentThread.getContextClassLoader();
         if (oldCL != cl) {
            currentThread.setContextClassLoader(cl);
            this.savedContextClassLoader = oldCL;
         }

      }
   }

   public void resetContextClassLoader() {
      if (this.savedContextClassLoader != null) {
         Thread.currentThread().setContextClassLoader(this.savedContextClassLoader);
         this.savedContextClassLoader = null;
      }

   }

   public void enforceTransactionPolicy() throws InternalException {
      if (this.md == null) {
         throw new AssertionError("No transaction policy specified!");
      } else {
         this.enforceTransactionPolicy(this.md.getTransactionPolicy());
      }
   }

   public void enforceTransactionPolicy(TransactionPolicy txPolicy) throws InternalException {
      if (!this.callerTxCaptured) {
         this.callerTx = TransactionService.getWeblogicTransaction();
         this.callerTxCaptured = true;
      }

      this.invokeTx = txPolicy.enforceTransactionPolicy(this.callerTx);
   }

   public boolean runningInOurTx() {
      return this.invokeTx != null && !this.invokeTx.equals(this.callerTx);
   }

   public boolean runningInCallerTx() {
      return this.invokeTx != null && this.invokeTx.equals(this.callerTx);
   }

   public void resumeCallersTransaction() throws InternalException {
      TransactionService.resumeCallersTransaction(this.callerTx, this.invokeTx);
   }

   public Map getContextData() {
      if (this.contextData == null) {
         if ("ServiceEndpoint".equals(this.md.getClientViewDescriptor().getIntfType())) {
            try {
               WebServiceContext wsCtx = (WebServiceContext)InitialContext.doLookup("java:comp/WebServiceContext");
               this.contextData = wsCtx.getMessageContext();
            } catch (NamingException var2) {
               throw new IllegalStateException(var2);
            }
         } else {
            this.contextData = new HashMap();
         }
      }

      return this.contextData;
   }

   boolean hasRolledBack() {
      return this.bean != null && TransactionService.isRolledback(this.invokeTx);
   }

   boolean isSystemRollback() {
      Throwable t = this.invokeTx.getRollbackReason();
      return t instanceof TimedOutException || t instanceof TransactionSystemException;
   }

   public String getInvocationDetail() {
      return " Method '" + this.md.getMethodInfo().getSignature() + "' on bean class : " + this.md.getClientViewDescriptor().getBeanInfo().getBeanClass().getName();
   }

   public boolean checkMethodPermissionsRemote(ContextHandler ch) throws AccessException {
      Loggable l;
      if (this.md == null) {
         l = EJBLogger.logAccessDeniedOnNonexistentMethodExceptionLoggable();
         throw new AccessException(l.getMessage());
      } else if (!this.checkMethodPermissions(ch)) {
         if (this.md.getMethodInfo().getIsExcluded()) {
            l = EJBLogger.logaccessExceptionLoggable(this.md.getMethodName());
            throw new AccessException(l.getMessage());
         } else {
            throw new AccessException(this.getAccessDeniedMessage());
         }
      } else {
         return true;
      }
   }

   public boolean checkMethodPermissionsLocal(ContextHandler ch) throws AccessLocalException {
      Loggable l;
      if (this.md == null) {
         l = EJBLogger.logAccessDeniedOnNonexistentMethodExceptionLoggable();
         throw new AccessLocalException(l.getMessage());
      } else if (!this.checkMethodPermissions(ch)) {
         if (this.md.getMethodInfo().getIsExcluded()) {
            l = EJBLogger.logaccessExceptionLoggable(this.md.getMethodName());
            throw new AccessLocalException(l.getMessage());
         } else {
            throw new AccessLocalException(this.getAccessDeniedMessage());
         }
      } else {
         return true;
      }
   }

   public boolean checkMethodPermissionsBusiness(ContextHandler ch) throws EJBAccessException {
      Loggable l;
      if (this.md == null) {
         l = EJBLogger.logAccessDeniedOnNonexistentMethodExceptionLoggable();
         throw new EJBAccessException(l.getMessage());
      } else if (!this.checkMethodPermissions(ch)) {
         if (this.md.getMethodInfo().getIsExcluded()) {
            l = EJBLogger.logaccessExceptionLoggable(this.md.getMethodName());
            throw new EJBAccessException(l.getMessage());
         } else {
            throw new EJBAccessException(this.getAccessDeniedMessage());
         }
      } else {
         return true;
      }
   }

   private boolean checkMethodPermissions(ContextHandler ch) {
      if ("Timer".equals(this.md.getMethodInfo().getMethodInterfaceType()) && this.md.getMethodInfo().getUnchecked()) {
         return true;
      } else {
         if (!this.md.getSecurityHelper().fullyDelegateSecurityCheck()) {
            if (this.md.getMethodInfo().getIsExcluded()) {
               return false;
            }

            if (this.md.getMethodInfo().getUnchecked()) {
               return true;
            }
         }

         return this.checkAccess(ch);
      }
   }

   private boolean checkAccess(ContextHandler ch) {
      if (debugLogger.isDebugEnabled()) {
         debug(this.md + ", hasRoles: " + this.md.getMethodInfo().hasRoles() + ", subject: " + SecurityHelper.getCurrentSubject());
      }

      if (!this.md.getSecurityHelper().fullyDelegateSecurityCheck() && !this.md.getMethodInfo().hasRoles()) {
         return true;
      } else {
         boolean allowed = false;

         try {
            this.pushSecurityContext(ch);
            allowed = this.md.getSecurityHelper().isAccessAllowed(this.md.getEJBResource(), this.md.getEJBMethodPermission(), ch);
         } finally {
            this.popSecurityContext();
         }

         if (debugLogger.isDebugEnabled()) {
            debug(allowed ? "  Access Allowed." : "  Access Denied !");
         }

         return allowed;
      }
   }

   private String getAccessDeniedMessage() {
      String principalName = SecurityHelper.getCurrentPrincipal().getName();
      if (principalName == null) {
         principalName = "UNKNOWN";
      }

      String infoString = null;
      if (this.md.getEJBResource() != null) {
         infoString = this.md.getEJBResource().toString();
      } else if (this.md.getEJBMethodPermission() != null) {
         infoString = this.md.getEJBMethodPermission().toString();
      }

      if (infoString == null) {
         infoString = this.md.getMethodName();
      }

      return EJBLogger.logaccessDeniedOnEJBResourceLoggable(principalName, infoString).getMessage();
   }

   public boolean isCallerTxCaptured() {
      return this.callerTxCaptured;
   }

   private static void debug(String s) {
      debugLogger.debug("[InvocationWrapper] " + s);
   }

   public final String toString() {
      return "[InvocationWrapper] callerTx: " + this.callerTx + " invokeTx: " + this.invokeTx + " bean:" + this.bean + " primaryKey: " + this.primaryKey + "MethodDescriptor: " + this.md;
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
