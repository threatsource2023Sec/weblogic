package weblogic.messaging.path;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.JMSService;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.utils.GenericManagedService;
import weblogic.management.utils.GenericServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class PathService extends AbstractServerService {
   @Inject
   @Named("MigrationManagerService")
   private ServerService dependencyOnMigrationManager;
   public static final String PATH_SERVICE = "PathService";
   public static final String HASH_POLICY = "Hash";
   private static HashMap pathServices = new HashMap();
   private ComponentInvocationContext componentInvocationContext;
   private HashMap pathServiceAdmins;
   private boolean registered;
   private GenericManagedService pathServiceService;
   public static final boolean TODO_REMOVE_DEBUG = false;
   static final String IMAGE_NAME = "PathService";
   private final ImageSource IMAGE_SOURCE;
   private final ComponentInvocationContext cic;

   public PathService() {
      this(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
   }

   private PathService(ComponentInvocationContext cic) {
      this.pathServiceAdmins = new HashMap();
      this.cic = cic;
      if ("DOMAIN".equals(this.getPartitionName())) {
         this.IMAGE_SOURCE = new PathServiceDiagnosticImageSource(this);
      } else {
         this.IMAGE_SOURCE = null;
      }

      Class var2 = PathService.class;
      synchronized(PathService.class) {
         pathServices.put(this.getPartitionName(), this);
      }
   }

   public static synchronized PathService getService(ComponentInvocationContext componentInvocationContext) throws ServiceFailureException {
      String pName = JMSService.getSafePartitionKey(componentInvocationContext);
      PathService pathService = (PathService)pathServices.get(pName);
      if (pathService == null) {
         pathService = new PathService(componentInvocationContext);
      }

      return pathService;
   }

   public static synchronized PathService getService() throws ServiceFailureException {
      ComponentInvocationContext componentInvocationContext = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return getService(componentInvocationContext);
   }

   Iterator getPathServiceAdminIterator() {
      HashMap cl = (HashMap)((HashMap)this.pathServiceAdmins.clone());
      return cl.values().iterator();
   }

   void addPathServiceAdmin(String jndiName, PathServiceAdmin pathServiceAdmin) {
      this.pathServiceAdmins.put(jndiName, pathServiceAdmin);
   }

   public boolean isRegistered() {
      return this.registered;
   }

   public synchronized void start() throws ServiceFailureException {
      if (!this.registered) {
         GenericServiceManager manager = GenericServiceManager.getManager();
         this.pathServiceService = manager.register(PathServiceMBean.class, PathServiceAdmin.class, true);
         this.registered = true;
      }

      this.pathServiceService.start();
      this.registerDiagnosticImageSource();
   }

   public synchronized void halt() throws ServiceFailureException {
      this.stop();
   }

   public synchronized void stop() throws ServiceFailureException {
      if ("DOMAIN".equals(this.getPartitionName())) {
         if (this.registered) {
            this.pathServiceService.stop();
            this.registered = false;
            this.unregisterDiagnosticImageSource();
            Class var1 = PathService.class;
            synchronized(PathService.class) {
               pathServices.clear();
            }
         }
      }
   }

   private String getPartitionName() {
      return JMSService.getSafePartitionKey(this.cic);
   }

   public ComponentInvocationContext getCIC() {
      return this.cic;
   }

   public static synchronized PathService removeService(String pName) {
      return (PathService)pathServices.remove(pName);
   }

   public void registerDiagnosticImageSource() {
      if (this.IMAGE_SOURCE != null) {
         ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         imageManager.registerImageSource("PathService", this.IMAGE_SOURCE);
      }
   }

   private void unregisterDiagnosticImageSource() {
      if (this.IMAGE_SOURCE != null) {
         ImageManager im = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);

         try {
            im.unregisterImageSource("PathService");
         } catch (ImageSourceNotFoundException var3) {
         }

      }
   }
}
