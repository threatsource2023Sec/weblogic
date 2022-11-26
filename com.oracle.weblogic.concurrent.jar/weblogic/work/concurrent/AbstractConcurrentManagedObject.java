package weblogic.work.concurrent;

import javax.enterprise.concurrent.ContextService;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jndi.OpaqueReference;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.context.ApplicationContextProcessor;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public abstract class AbstractConcurrentManagedObject implements ConcurrentManagedObject {
   private ContextProvider contextSetupProcessor;
   protected final String name;
   protected final String appId;
   protected String moduleId;
   protected final String partitionName;
   protected final GenericClassLoader parCL;
   protected volatile int cmoType;
   private final ConcurrentOpaqueReference jsr236Delegator = new ConcurrentOpaqueReference(this);
   private transient volatile ConcurrentOpaqueReference applicationDelegator;
   protected volatile boolean warnIfUserObjectCheckSkipped = true;

   public AbstractConcurrentManagedObject(ConcurrentManagedObjectBuilder builder) {
      this.name = builder.getName();
      this.partitionName = builder.getPartitionName();
      this.appId = builder.getAppId();
      this.moduleId = builder.getModuleId();
      this.parCL = builder.getPartitionClassLoader();
      this.setContextSetupProcessor(builder.getContextProcessor());
   }

   protected AbstractConcurrentManagedObject(AbstractConcurrentManagedObject target, ContextProvider provider) {
      if (provider == null) {
         throw new NullPointerException();
      } else {
         this.name = target.name;
         this.partitionName = target.partitionName;
         this.appId = target.appId;
         this.moduleId = target.moduleId;
         this.parCL = target.parCL;
         this.setContextSetupProcessor(provider);
      }
   }

   public void terminate() {
   }

   public String getName() {
      return this.name;
   }

   public ContextProvider getContextSetupProcessor() {
      return this.contextSetupProcessor;
   }

   public void setContextSetupProcessor(ContextProvider provider) {
      this.contextSetupProcessor = provider;
      this.cmoType = this.appId == null ? 1 : (this.contextSetupProcessor instanceof ApplicationContextProcessor ? 2 : 0);
   }

   public GenericClassLoader getPartitionClassLoader() {
      return this.parCL;
   }

   public String getAppId() {
      return this.appId;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getName());
      sb.append("(name=");
      sb.append(this.name);
      sb.append(",moduleId=");
      sb.append(this.moduleId);
      sb.append(",appId=");
      sb.append(this.appId);
      String extraToString = this.getExtraToString();
      if (extraToString != null) {
         sb.append(extraToString);
      }

      sb.append(")");
      return sb.toString();
   }

   protected String getExtraToString() {
      return null;
   }

   public Object getOrCreateApplicationDelegator(ClassLoader classLoader, Context jndiContext) {
      if (this.applicationDelegator == null) {
         this.applicationDelegator = this.createApplicationDelegator(classLoader, jndiContext);
      }

      return this.applicationDelegator;
   }

   abstract ConcurrentOpaqueReference createApplicationDelegator(ClassLoader var1, Context var2);

   public Object getOrCreateJSR236Delegator(String module, String comp, ClassLoader classLoader, Context jndiContext) {
      return this.jsr236Delegator;
   }

   public void updateContexts(String module, String comp, ClassLoader classLoader) {
   }

   public int getCMOType() {
      return this.cmoType;
   }

   protected void rejectIfOutOfScope() throws RejectException {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      RejectException e;
      if (!ConcurrentUtils.isSameString(this.partitionName, cic.getPartitionName())) {
         e = new RejectException();
         e.setMessage(ConcurrencyLogger.logOutOfPartitionLoggable(this.toString(), cic.toString(), e).getMessage());
         throw e;
      } else if (this.cmoType == 2 && !ConcurrentUtils.isSameString(this.appId, cic.getApplicationId())) {
         e = new RejectException();
         e.setMessage(ConcurrencyLogger.logOutOfApplicationLoggable(this.toString(), cic.toString(), e).getMessage());
         throw e;
      }
   }

   protected void rejectIfSubmittingCompNotStarted() throws RejectException {
      if (this.cmoType == 0) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String submittingApp = cic.getApplicationId();
         if (submittingApp != null && !ConcurrentUtils.isSameString(this.appId, submittingApp)) {
            if (!ConcurrentManagedObjectCollection.isStarted(submittingApp)) {
               RejectException e = new RejectException();
               e.setMessage(ConcurrencyLogger.logSubmittingComponentNotStartLoggable(this.toString(), cic.toString(), e).getMessage());
               throw e;
            } else {
               if (!ContextService.class.getName().equals(this.getJSR236Class())) {
                  ConcurrentManagedObjectCollection.connectRelatedApp(this.appId, submittingApp);
               }

            }
         }
      }
   }

   public WorkManager getWorkManager() {
      return null;
   }

   public void shutdownThreadsSubmittedBy(String applicationId) {
   }

   public static class ConcurrentOpaqueReference implements OpaqueReference {
      private final ConcurrentManagedObject cmo;

      public ConcurrentOpaqueReference(ConcurrentManagedObject cmo) {
         if (cmo == null) {
            throw new IllegalArgumentException("cmo can not be null when ConcurrentOpaqueReference is created.");
         } else {
            this.cmo = cmo;
         }
      }

      public Object getReferent(Name name, Context ctx) throws NamingException {
         if (this.cmo.isStarted()) {
            return this.cmo;
         } else {
            throw new NamingException("The concurrent mananged object being looked up is not started: " + this.cmo);
         }
      }

      public String toString() {
         return "ConcurrentOpaqueReference of " + this.cmo.toString();
      }

      public ConcurrentManagedObject getObject() {
         return this.cmo;
      }
   }
}
