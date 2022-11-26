package weblogic.connector.lifecycle;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkException;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import weblogic.application.ApplicationContextInternal;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.configuration.validation.BeanValidator;
import weblogic.connector.extensions.ExtendedBootstrapContext;
import weblogic.connector.external.AdapterListener;
import weblogic.connector.utils.PartitionUtils;
import weblogic.connector.work.WorkContextProcessorFactory;
import weblogic.connector.work.WorkManager;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;
import weblogic.diagnostics.context.CorrelationHelper;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.context.InvalidDyeException;

public class BootstrapContext implements javax.resource.spi.BootstrapContext, ExtendedBootstrapContext, Serializable {
   private static final long serialVersionUID = -6084262294645664250L;
   private String moduleName;
   private WorkManager workManager;
   private Vector listeners;
   private transient BeanValidator beanValidator;
   private transient RAInstanceManager raIM;
   private BeanManager beanManager;

   protected BootstrapContext() {
   }

   public BootstrapContext(RAInstanceManager raIM, ApplicationContextInternal appCtx, String moduleName, weblogic.work.WorkManager workManager, BeanValidator beanValidator, WorkContextProcessorFactory workContextProcessorFactory) throws WorkException {
      Debug.enter(this, "( " + raIM + ", " + appCtx + ", " + moduleName + " )");

      try {
         this.raIM = raIM;
         this.workManager = null;
         this.moduleName = moduleName;
         this.beanValidator = beanValidator;
         this.listeners = new Vector(10);
         if (moduleName == null || moduleName.trim().equals("")) {
            Debug.throwAssertionError("moduleName is null or blank");
         }

         if (workManager == null) {
            Debug.throwAssertionError("Couldn't obtain WorkManager for resource adapter '" + moduleName + "'");
         }

         if (Debug.isWorkEnabled()) {
            Debug.work("BootStrapContext() creating WorkManager for RA=" + moduleName);
         }

         this.workManager = WorkManager.create(appCtx.getApplicationId(), moduleName, raIM.getPartitionName(), raIM.getAdapterLayer(), workContextProcessorFactory, raIM.getClassloader(), workManager, raIM.getRAInfo().getConnectorWorkManager(), raIM.getResourceAdapter());
      } finally {
         Debug.exit(this, "()");
      }

   }

   public Timer createTimer() {
      this.checkPartition();
      Debug.logTimerWarning();
      return new Timer();
   }

   public javax.resource.spi.work.WorkManager getWorkManager() {
      this.checkPartition();
      return this.workManager;
   }

   public WorkManager getConnectorWorkManager() {
      this.checkPartition();
      return this.workManager;
   }

   public XATerminator getXATerminator() {
      this.checkPartition();
      return weblogic.connector.inbound.XATerminator.getXATerminator();
   }

   public void setDiagnosticContextID(String id) {
      this.checkPartition();
      CorrelationHelper.setPayload(id);
   }

   public String getDiagnosticContextID() {
      this.checkPartition();
      return CorrelationHelper.getPayload();
   }

   public void setDyeBits(byte bits) throws ResourceException {
      this.checkPartition();
      String exMsg;
      if (!CorrelationManager.isCorrelationEnabled()) {
         Debug.println("BootstrapContext.setDyeBits failed because Diagnostic Contexts are not enabled");
         exMsg = Debug.getExceptionSetDyeBitsFailedDiagCtxNotEnabled();
         throw new ResourceException(exMsg);
      } else if (bits >= 0 && bits <= 15) {
         boolean bit4 = (bits & 8) != 0;
         boolean bit3 = (bits & 4) != 0;
         boolean bit2 = (bits & 2) != 0;
         boolean bit1 = (bits & 1) != 0;
         Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
         if (ctx == null) {
            Debug.println("<" + this.moduleName + "> Failed to obtain DiagnosticContext for BootstrapContext.setDyeBits call");
            String exMsg = Debug.getExceptionFailedToGetDiagCtx(this.moduleName);
            throw new ApplicationServerInternalException(exMsg);
         } else {
            try {
               ctx.setDye(30, bit4);
               ctx.setDye(29, bit3);
               ctx.setDye(28, bit2);
               ctx.setDye(27, bit1);
            } catch (InvalidDyeException var10) {
               String msgId = Debug.logInvalidDye(this.moduleName, var10.toString());
               Debug.logStackTrace(msgId, var10);
               String exMsg = Debug.getExceptionInvalidDye(this.moduleName, var10.toString());
               throw new ApplicationServerInternalException(exMsg, var10);
            }
         }
      } else {
         exMsg = Debug.getExceptionInvalidDyeValue(String.valueOf(bits));
         throw new ResourceException(exMsg);
      }
   }

   public byte getDyeBits() throws ResourceException {
      this.checkPartition();
      if (!CorrelationManager.isCorrelationEnabled()) {
         Debug.println("BootstrapContext.getDyeBits failed because Diagnostic Contexts are not enabled");
         String exMsg = Debug.getExceptionGetDyeBitsFailedDiagCtxNotEnabled();
         throw new ResourceException(exMsg);
      } else {
         byte dye = 0;
         Correlation ctx = CorrelationFactory.findOrCreateCorrelation();

         try {
            if (ctx.isDyedWith(30)) {
               dye = (byte)(dye | 8);
            }

            if (ctx.isDyedWith(29)) {
               dye = (byte)(dye | 4);
            }

            if (ctx.isDyedWith(28)) {
               dye = (byte)(dye | 2);
            }

            if (ctx.isDyedWith(27)) {
               dye = (byte)(dye | 1);
            }

            return dye;
         } catch (InvalidDyeException var6) {
            String msgId = Debug.logInvalidDye(this.moduleName, var6.toString());
            Debug.logStackTrace(msgId, var6);
            String exMsg = Debug.getExceptionInvalidDye(this.moduleName, var6.toString());
            throw new ApplicationServerInternalException(exMsg, var6);
         }
      }
   }

   public void complete() {
      this.checkPartition();
      ConnectorLogger.logCompleteCalled(this.moduleName, this.raIM.getVersionId());
      this.raIM.clearWaitingStartVersioningComplete();
      this.signalShutdown();
   }

   public void signalShutdown() {
      this.checkPartition();
      if (!this.raIM.isWaitingStartVersioningComplete()) {
         Iterator iterator = this.listeners.iterator();

         while(iterator.hasNext()) {
            AdapterListener adapterListener = (AdapterListener)iterator.next();
            adapterListener.completed();
         }
      }

   }

   public void addListener(AdapterListener adapterListener) {
      this.checkPartition();
      if (adapterListener != null) {
         this.listeners.add(adapterListener);
      }

   }

   public void removeListener(AdapterListener adapterListener) {
      this.checkPartition();
      if (adapterListener != null) {
         this.listeners.remove(adapterListener);
      }

   }

   public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
      this.checkPartition();
      InitialContext ctx = null;

      TransactionSynchronizationRegistry var2;
      try {
         ctx = new InitialContext();
         var2 = (TransactionSynchronizationRegistry)ctx.lookup("javax.transaction.TransactionSynchronizationRegistry");
      } catch (NamingException var11) {
         String errMsg = "Failed to lookup TransactionSynchronizationRegistry: " + var11;
         Debug.println((String)errMsg, (Throwable)var11);
         throw new RuntimeException(errMsg, var11);
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var10) {
               Debug.println("Exception when closing InitialContext, ignored: " + var10);
            }
         }

      }

      return var2;
   }

   public boolean isContextSupported(Class inflowContextClass) {
      boolean isSupport = this.workManager.getWorkContextManager().getValidator().isContextSupported(inflowContextClass);
      this.checkPartition();
      if (Debug.isWorkEnabled()) {
         Debug.work("BootStrapContext(): isContextSupported(" + inflowContextClass.getName() + "):" + isSupport);
      }

      return isSupport;
   }

   public Validator getValidator() {
      this.checkPartition();
      return CheckPartitionProxy.wrapValidator(this.beanValidator.getValidator(), this.raIM.getPartitionName());
   }

   public ValidatorFactory getValidatorFactory() {
      this.checkPartition();
      return CheckPartitionProxy.wrapValidatorFactory(this.beanValidator.getValidatorFactory(), this.raIM.getPartitionName());
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public void setBeanManager(BeanManager beanManager) {
      this.checkPartition();
      this.beanManager = CheckPartitionProxy.wrapBeanManager(beanManager, this.raIM.getPartitionName());
   }

   public String getModuleName() {
      return this.moduleName;
   }

   private void checkPartition() {
      PartitionUtils.checkPartition(this.raIM.getPartitionName());
   }
}
