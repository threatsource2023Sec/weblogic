package weblogic.deploy.service.datatransferhandlers;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.targetserver.datamanagement.ModuleRedeployDataTransferRequestImpl;
import weblogic.deploy.service.AppDataTransferRequest;
import weblogic.deploy.service.ConfigDataTransferRequest;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.ExtendLoaderDataTransferRequest;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.internal.DeploymentService;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DataHandlerManager {
   public static final String HTTP_FILE_BASED_HANDLER = "HTTP";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean canTransferAnyFile = false;
   private DataTransferHandler handler = null;

   public static DataHandlerManager getInstance() {
      return DataHandlerManager.Maker.singleton;
   }

   public DataTransferHandler getHttpDataTransferHandler() {
      synchronized(this) {
         if (this.handler == null) {
            this.handler = (DataTransferHandler)(ManagementService.getPropertyService(kernelId).isAdminServer() ? new LocalDataTransferHandler("HTTP") : new RemoteDataTransferHandler("HTTP"));
         }
      }

      return this.handler;
   }

   public static DataTransferHandler getHandler(String handlerType) throws IOException {
      if (handlerType == null) {
         handlerType = "HTTP";
      }

      DataTransferHandler dataTransferHandler = DeploymentService.getDeploymentService().getDataTransferHandler(handlerType);
      if (dataTransferHandler == null) {
         throw new IOException(DeploymentServiceLogger.logNoDataHandlerRegisteredLoggable(handlerType).getMessage());
      } else {
         return dataTransferHandler;
      }
   }

   protected static void validateRequestType(DataTransferRequest request) throws IOException {
      if (!(request instanceof AppDataTransferRequest) && !(request instanceof ConfigDataTransferRequest) && !(request instanceof ExtendLoaderDataTransferRequest)) {
         throw new IOException("Invalid request type : " + request.getClass().getName());
      }
   }

   private static MultiDataStream createDeploymentStream(BasicDeploymentMBean mbean, AppDataTransferRequest request) throws IOException {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      if (!ra.isAdminServer()) {
         throw new AssertionError("Cannot be invoked on managed server.");
      } else {
         List moduleIds;
         if (request instanceof ModuleRedeployDataTransferRequestImpl) {
            moduleIds = request.getFilePaths();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("createMultiStream for " + mbean.getName() + " moduleIds= " + moduleIds);
            }

            if (!(mbean instanceof AppDeploymentMBean)) {
               throw new AssertionError("ModuleRedeploy cannot be applied for SystemResources");
            } else {
               return SourceCache.getSourceCache(mbean).getAppDataLocationsForModuleIds(moduleIds, (AppDeploymentMBean)mbean);
            }
         } else {
            moduleIds = request.getFilePaths();
            boolean isPlanUpdate = request.isPlanUpdate();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("createMultiStream for " + mbean.getName() + " urisOrFiles= " + moduleIds + ", isPlanUpdate=" + isPlanUpdate);
            }

            return SourceCache.getSourceCache(mbean).getDataLocations(moduleIds, isPlanUpdate, mbean);
         }
      }
   }

   private static MultiDataStream createConfigDataStream(ConfigDataTransferRequest request) throws IOException {
      List filePaths = request.getFilePaths();
      if (filePaths != null && !filePaths.isEmpty()) {
         List targetPaths = request.getTargetPaths() == null ? new ArrayList() : request.getTargetPaths();
         File rootDir = new File(DomainDir.getRootDir());
         MultiDataStream streams = DataStreamFactory.createMultiDataStream();

         for(int i = 0; i < filePaths.size(); ++i) {
            String requestedPath = (String)filePaths.get(i);
            File requestedFile = new File(rootDir, requestedPath);
            if (!requestedFile.exists()) {
               DeploymentServiceLogger.logNoFile(requestedFile.getPath());
            } else {
               String rootDirPath = rootDir.getCanonicalPath();
               String filePath = requestedFile.getAbsolutePath();
               if (filePath.startsWith(rootDirPath) && (canTransferAnyFile || !filePath.contains("SerializedSystemIni.dat") && !filePath.contains("boot.properties") && !filePath.contains("SSI.dat") && !filePath.contains("security.xml") && !filePath.contains(".ldift"))) {
                  String target = ((List)targetPaths).size() > i ? (String)((List)targetPaths).get(i) : null;
                  streams.addFileDataStream(requestedPath, requestedFile, target, false);
               } else {
                  DeploymentServiceLogger.logFileUnavailable(requestedPath);
               }
            }
         }

         return streams;
      } else {
         throw new IOException("Got request with empty file paths");
      }
   }

   private static MultiDataStream createExtendLoaderDataStream(ExtendLoaderDataTransferRequest request) throws IOException {
      List filePaths = request.getFilePaths();
      if (filePaths != null && !filePaths.isEmpty()) {
         List targetPaths = request.getTargetPaths() == null ? new ArrayList() : request.getTargetPaths();
         MultiDataStream streams = DataStreamFactory.createMultiDataStream();

         for(int i = 0; i < filePaths.size(); ++i) {
            String requestedPath = (String)filePaths.get(i);
            File requestedFile = new File(requestedPath);
            if (requestedFile.exists()) {
               String filePath = requestedFile.getAbsolutePath();
               String target = ((List)targetPaths).size() > i ? (String)((List)targetPaths).get(i) : null;
               streams.addFileDataStream(requestedPath, requestedFile, target, false);
            } else {
               DeploymentServiceLogger.logNoFile(requestedFile.getPath());
            }
         }

         return streams;
      } else {
         throw new IOException("Got request with empty file paths");
      }
   }

   static {
      String prop = System.getProperty("weblogic.data.canTransferAnyFile", "false");
      if ("true".equalsIgnoreCase(prop)) {
         canTransferAnyFile = true;
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("canTransferAnyFile:  " + prop);
      }

   }

   private class RemoteDataTransferHandler implements DataTransferHandler {
      private DataTransferHandler delegate;

      private RemoteDataTransferHandler(String handlerType) {
         if ("HTTP".equals(handlerType)) {
            this.delegate = new HttpDataTransferHandler();
         } else {
            throw new IllegalArgumentException("Unsupported handler type " + handlerType);
         }
      }

      public final String getType() {
         return this.delegate.getType();
      }

      public MultiDataStream getDataAsStream(DataTransferRequest request) throws IOException {
         return this.delegate.getDataAsStream(request);
      }

      // $FF: synthetic method
      RemoteDataTransferHandler(String x1, Object x2) {
         this(x1);
      }
   }

   private class LocalDataTransferHandler extends RemoteDataTransferHandler {
      private LocalDataTransferHandler(String handlerType) {
         super(handlerType, null);
      }

      public MultiDataStream getDataAsStream(DataTransferRequest request) throws IOException {
         DataHandlerManager.validateRequestType(request);
         if (request instanceof AppDataTransferRequest) {
            AppDataTransferRequest atRequest = (AppDataTransferRequest)request;
            String depMBeanId = ApplicationVersionUtils.getApplicationId(atRequest.getAppName(), atRequest.getAppVersionIdentifier(), atRequest.getPartition());
            long reqId = atRequest.getRequestId();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(" LocalHandler.getDataAsStream() : handling application data request for application : " + atRequest.getAppName() + " : request id : " + reqId);
            }

            AppDeploymentMBean depMBean = AppDeploymentHelper.lookupAppDeployment(depMBeanId, reqId);
            if (depMBean == null) {
               throw new AssertionError("Could not find DeploymentMBean for '" + depMBeanId + "' for deployment request '" + reqId + "'");
            } else {
               return DataHandlerManager.createDeploymentStream(depMBean, atRequest);
            }
         } else if (request instanceof ConfigDataTransferRequest) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug(" LocalHandler.getDataAsStream() : handling config data request : " + request.getFilePaths() + " : request id : " + request.getRequestId());
            }

            return DataHandlerManager.createConfigDataStream((ConfigDataTransferRequest)request);
         } else {
            return DataHandlerManager.createExtendLoaderDataStream((ExtendLoaderDataTransferRequest)request);
         }
      }

      // $FF: synthetic method
      LocalDataTransferHandler(String x1, Object x2) {
         this(x1);
      }
   }

   static class Maker {
      private static final DataHandlerManager singleton = new DataHandlerManager();
   }
}
