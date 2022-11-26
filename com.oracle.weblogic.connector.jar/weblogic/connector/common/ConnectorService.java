package weblogic.connector.common;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.utils.ManagementUtils;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.deploy.ConnectorDeploymentFactory;
import weblogic.connector.deploy.ConnectorModuleFactory;
import weblogic.connector.deploy.TransportableJNDIHandler;
import weblogic.connector.exception.RAException;
import weblogic.connector.monitoring.ServiceRuntimeMBeanImpl;
import weblogic.connector.utils.PartitionUtils;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.jndi.internal.WLNamingManager;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ActivatedService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.cmm.MemoryPressureService;

@Service
@Singleton
public final class ConnectorService extends ActivatedService {
   private static ServiceRuntimeMBeanImpl globalConnectorServiceRuntimeMBean;
   private static boolean alreadyInitialized = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean imageSourceRegistered = false;
   @Inject
   private MemoryPressureService memoryPressureService;

   public void stopService() throws ServiceFailureException {
      Debug.service("stopService() called on ConnectorService ");

      try {
         RACollectionManager.stop();
         this.haltService();
      } catch (RAException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public void haltService() throws ServiceFailureException {
      Debug.service("haltService() called on ConnectorService ");
      ErrorCollectionException e = null;

      try {
         RACollectionManager.halt();
      } catch (RAException var5) {
         Debug.logConnectorServiceShutdownError(var5.toString());
         if (e == null) {
            e = new ErrorCollectionException();
         }

         e.addError(var5);
      }

      try {
         globalConnectorServiceRuntimeMBean.unregister();
         ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setConnectorServiceRuntime((ConnectorServiceRuntimeMBean)null);
         globalConnectorServiceRuntimeMBean = null;
      } catch (ManagementException var4) {
         Debug.logConnectorServiceShutdownError(var4.toString());
         if (e == null) {
            e = new ErrorCollectionException();
         }

         e.addError(var4);
      }

      if (imageSourceRegistered) {
         try {
            Debug.service("Unregistering the Connector diagnostic image source");
            ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
            imageManager.unregisterImageSource("CONNECTOR");
            imageSourceRegistered = false;
         } catch (Throwable var3) {
            Debug.logDiagImageUnregisterFailure(var3);
            if (e == null) {
               e = new ErrorCollectionException();
            }

            e.addError(var3);
         }
      }

      if (e != null) {
         throw new ServiceFailureException(e);
      }
   }

   public boolean startService() throws ServiceFailureException {
      Debug.service("startService() called on ConnectorService ");
      Debug.logConnectorServiceInitializing();
      if (alreadyInitialized) {
         return true;
      } else {
         try {
            Debug.service("Creating ServiceRuntimeMBeanImpl");
            globalConnectorServiceRuntimeMBean = new ServiceRuntimeMBeanImpl();
            ManagementService.getRuntimeAccess(kernelId).getServerRuntime().setConnectorServiceRuntime(globalConnectorServiceRuntimeMBean);
         } catch (ManagementException var6) {
            Debug.logConnectorServiceInitError(var6.toString());
            throw new ServiceFailureException(var6);
         }

         ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
         ConnectorDeploymentFactory cdf = new ConnectorDeploymentFactory();
         afm.addDeploymentFactory(cdf);
         afm.addModuleFactory(new ConnectorModuleFactory());
         alreadyInitialized = true;
         Debug.logConnectorServiceInitialized();

         try {
            Debug.service("Initializing the Connector diagnostic image source");
            ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
            ImageSource imageSource = new ConnectorDiagnosticImageSource();
            imageManager.registerImageSource("CONNECTOR", imageSource);
            imageSourceRegistered = true;
         } catch (Throwable var5) {
            Debug.logDiagImageRegisterFailure(var5);
         }

         Debug.service("Registering the connector TransportableJNDIHandler as a TransportableFactory");
         WLNamingManager.addTransportableFactory(new TransportableJNDIHandler());
         this.memoryPressureService.registerListener("connector", new ConnectorMemoryPressureListener());
         return true;
      }
   }

   public static ServiceRuntimeMBeanImpl getConnectorServiceRuntimeMBean() {
      return globalConnectorServiceRuntimeMBean;
   }

   public static ServiceRuntimeMBeanImpl getConnectorServiceRuntimeMBean(String partitionName) throws ManagementException {
      if (PartitionUtils.isDomainScope(partitionName)) {
         return globalConnectorServiceRuntimeMBean;
      } else {
         PartitionRuntimeMBean partitionRuntime = ManagementUtils.getPartitionRuntime(partitionName);
         ServiceRuntimeMBeanImpl connectorMbean = null;
         if (partitionRuntime == null) {
            Loggable loggable = ConnectorLogger.logPartitionRuntimeMBeanNotFoundExceptionLoggable(partitionName);
            throw new ManagementException(loggable.getMessage());
         } else {
            synchronized(partitionRuntime) {
               connectorMbean = (ServiceRuntimeMBeanImpl)partitionRuntime.getConnectorServiceRuntime();
               if (connectorMbean == null) {
                  throw new ManagementException("ConnectorServiceRuntimeMBean for partition " + partitionName + " has not been set");
               } else {
                  return connectorMbean;
               }
            }
         }
      }
   }
}
