package com.oracle.batch.connector.impl;

import com.ibm.jbatch.spi.BatchSPIManager;
import com.ibm.jbatch.spi.DatabaseAlreadyInitializedException;
import java.security.AccessController;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

@Service
@RunLevel(20)
public class WLSBatchConnector {
   private static final DebugLogger _debugLogger = DebugLogger.getDebugLogger("DebugBatchConnector");
   private RuntimeAccess runtimeAccess;
   private WLSDatabaseConfigurationBean wlsDatabaseConfigurationBean;
   private BatchJobExecutorService batchJobExecutorService;
   private WLSBatchSecurityHelper wlsBatchSecurityHelper;
   private BatchJobRepositoryRuntimeMBeanImpl batchJobRepositoryRuntimeMBean = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public WLSBatchConnector() {
      try {
         if (ManagementService.getRuntimeAccess(kernelId) != null) {
            this.runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
            BatchConfigBeanHelper.setDomainMBean(this.runtimeAccess.getDomain());
         } else {
            _debugLogger.debug("** [WLSBatchConnector]: RuntimeAccess is NULL");
         }

         this.wlsDatabaseConfigurationBean = WLSDatabaseConfigurationBean.getInstance();
         this.batchJobExecutorService = new BatchJobExecutorService();
         this.wlsBatchSecurityHelper = new WLSBatchSecurityHelper();
         _debugLogger.debug("** [WLSBatchConnector]: in postInit() done.");
         BatchSPIManager batchSPIManager = BatchSPIManager.getInstance();
         batchSPIManager.registerDatabaseConfigurationBean(this.wlsDatabaseConfigurationBean);
         batchSPIManager.registerBatchSecurityHelper(this.wlsBatchSecurityHelper);
         batchSPIManager.registerExecutorServiceProvider(this.batchJobExecutorService);

         try {
            if (this.runtimeAccess != null && this.runtimeAccess.getServerRuntime() != null) {
               this.batchJobRepositoryRuntimeMBean = new BatchJobRepositoryRuntimeMBeanImpl(this.runtimeAccess.getServerRuntime());
               if (_debugLogger.isDebugEnabled()) {
                  _debugLogger.debug("** [WLSBatchConnector]: created (domain) BatchJobRepositoryRuntimeMBeanImpl ...");
               }
            }
         } catch (ManagementException var3) {
            var3.printStackTrace();
         }
      } catch (DatabaseAlreadyInitializedException var4) {
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      _debugLogger.debug("** [WLSBatchConnector] successfully initialized...");
   }
}
