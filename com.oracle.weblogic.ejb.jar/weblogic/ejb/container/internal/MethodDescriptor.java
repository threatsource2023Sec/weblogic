package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import javax.ejb.TransactionAttributeType;
import javax.security.jacc.EJBMethodPermission;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.EJBResource;
import weblogic.utils.StackTraceUtilsClient;

public final class MethodDescriptor implements Cloneable {
   private static final DebugLogger debugLogger;
   private final Method method;
   private final String methodName;
   private final MethodInfo methodInfo;
   private final ClientViewDescriptor clientViewDesc;
   private final AuthenticatedSubject runAsSubject;
   private final ConcurrencyInfo concurrencyInfo;
   private final boolean isRemoveMethod;
   private final boolean isRetainIfException;
   private final boolean containsXPCRefs;
   private EJBResource ejbResource;
   private EJBMethodPermission ejbMethodPermission;
   private SecurityHelper securityHelper;
   private TransactionPolicy txPolicy;
   private String methodId;

   public MethodDescriptor(Method m, MethodInfo methodInfo, int txAttribute, boolean entityAlwaysUsesTransaction, AuthenticatedSubject runAsSubject, ConcurrencyInfo concurrencyInfo, boolean isRemoveMethod, boolean isRetainIfException, boolean containsExtendedPersistenceContextRefs, ClientViewDescriptor clientViewDesc) {
      this.clientViewDesc = clientViewDesc;
      this.method = m;
      this.methodName = m.getName();
      this.methodInfo = methodInfo;
      if (clientViewDesc.getBeanInfo().isEntityBean() && entityAlwaysUsesTransaction) {
         this.txPolicy = new EntityLocalTransactionPolicy(methodInfo.getSignature(), txAttribute, methodInfo.getTxIsolationLevel(), methodInfo.getSelectForUpdate(), clientViewDesc);
      } else {
         this.txPolicy = new TransactionPolicy(methodInfo.getSignature(), txAttribute, methodInfo.getTxIsolationLevel(), methodInfo.getSelectForUpdate(), clientViewDesc);
      }

      this.runAsSubject = runAsSubject;
      this.concurrencyInfo = concurrencyInfo;
      this.isRemoveMethod = isRemoveMethod;
      this.isRetainIfException = isRetainIfException;
      this.containsXPCRefs = containsExtendedPersistenceContextRefs;
   }

   public String getApplicationName() {
      return this.clientViewDesc.getBeanInfo().getDeploymentInfo().getApplicationId();
   }

   public String getModuleId() {
      return this.clientViewDesc.getBeanInfo().getDeploymentInfo().getModuleId();
   }

   public String getEjbName() {
      return this.clientViewDesc.getBeanInfo().getEJBName();
   }

   public TransactionPolicy getTransactionPolicy() {
      return this.txPolicy;
   }

   public boolean requiresTransaction() {
      TransactionAttributeType type = this.txPolicy.getTxAttribute();
      return type == TransactionAttributeType.REQUIRES_NEW || type == TransactionAttributeType.REQUIRED;
   }

   public String toString() {
      return "MethodDescriptor: Method: " + this.methodInfo.getSignature() + " TransactionAttribute: " + this.txPolicy.getTxAttribute();
   }

   public boolean isLocal() {
      return this.clientViewDesc.isLocal();
   }

   public boolean isRemoveMethod() {
      return this.isRemoveMethod;
   }

   public boolean isRetainIfException() {
      return this.isRetainIfException;
   }

   public Method getMethod() {
      return this.method;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public MethodInfo getMethodInfo() {
      return this.methodInfo;
   }

   public boolean containsExtendedPersistenceContextRefs() {
      return this.containsXPCRefs;
   }

   public void setEJBResource(EJBResource r) {
      this.ejbResource = r;
   }

   public EJBResource getEJBResource() {
      return this.ejbResource;
   }

   public void setEJBMethodPermission(EJBMethodPermission p) {
      this.ejbMethodPermission = p;
   }

   public EJBMethodPermission getEJBMethodPermission() {
      return this.ejbMethodPermission;
   }

   public int getTxTimeoutSeconds() {
      return this.clientViewDesc.getBeanInfo().getTransactionTimeoutSeconds();
   }

   public void setRetryOnRollbackCount(int i) {
      this.methodInfo.setRetryOnRollbackCount(i);
   }

   public int getRetryOnRollbackCount() {
      return this.methodInfo.getRetryOnRollbackCount();
   }

   public ClientViewDescriptor getClientViewDescriptor() {
      return this.clientViewDesc;
   }

   SecurityHelper getSecurityHelper() {
      return this.securityHelper;
   }

   public void setSecurityHelper(SecurityHelper s) {
      this.securityHelper = s;
   }

   public AuthenticatedSubject getRunAsSubject() {
      return this.runAsSubject;
   }

   public void setMethodId(String mid) {
      this.methodId = mid;
   }

   public String getMethodId() {
      return this.methodId;
   }

   public ConcurrencyInfo getConcurrencySettings() {
      return this.concurrencyInfo;
   }

   public Object clone() {
      try {
         MethodDescriptor copy = (MethodDescriptor)super.clone();
         copy.txPolicy = (TransactionPolicy)this.txPolicy.clone();
         return copy;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError("Unable to clone MethodDescriptor " + StackTraceUtilsClient.throwable2StackTrace(var2));
      }
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }

   public final class TxRequiredException extends RuntimeException {
      private static final long serialVersionUID = 1239800172031557020L;

      public TxRequiredException(String message) {
         super(message);
      }
   }
}
