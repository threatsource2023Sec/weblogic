package weblogic.io.common.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.common.T3MiscLogger;
import weblogic.jndi.Environment;
import weblogic.logging.LogOutputStream;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.FileT3MBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.AssertionError;

@Service
@Named
@RunLevel(10)
public final class FileService extends AbstractServerService implements DeploymentHandler {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   private static FileService singleton = null;
   private Context context;
   private LogOutputStream log;

   public FileService() {
      if (singleton != null) {
         throw new AssertionError("File server module singleton already set");
      } else {
         singleton = this;
         this.log = new LogOutputStream("FileSystem");
      }
   }

   public void start() throws ServiceFailureException {
      DeploymentHandlerHome.addDeploymentHandler(this);
      this.context = this.getContext();
   }

   protected boolean isFileSystemMounted(String fileSystemName) {
      Context ctx = this.getContext();
      if (ctx == null) {
         return false;
      } else {
         try {
            ctx.lookup(fileSystemName);
            return true;
         } catch (NamingException var4) {
            return false;
         }
      }
   }

   private void mountFileSystem(FileT3MBean filesystem) {
      Context ctx = this.getContext();
      if (ctx != null) {
         String name = filesystem.getName();
         String path = filesystem.getPath();

         try {
            ctx.bind(name, new T3FileSystemProxyImpl(name, path));
         } catch (NamingException var6) {
            T3MiscLogger.logMount(filesystem.getName(), var6);
         }

      }
   }

   private void unmountFileSystem(FileT3MBean filesystem) {
      Context ctx = this.getContext();
      if (ctx != null) {
         try {
            ctx.unbind(filesystem.getName());
         } catch (NamingException var4) {
            T3MiscLogger.logUnmount(filesystem.getName(), var4);
         }

      }
   }

   private Context getContext() {
      if (this.context == null) {
         try {
            Environment env = new Environment();
            env.setCreateIntermediateContexts(true);
            env.setReplicateBindings(false);
            this.context = env.getInitialContext().createSubcontext("weblogic.fileSystem");
            return this.context;
         } catch (NamingException var2) {
            T3MiscLogger.logGetRoot(var2);
         }
      }

      return this.context;
   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext ctx) throws DeploymentException {
      if (deployment instanceof FileT3MBean) {
         try {
            this.mountFileSystem((FileT3MBean)deployment);
         } catch (Exception var4) {
            throw new DeploymentException("error creating connection pool", var4);
         }
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) {
      if (deployment instanceof FileT3MBean) {
         this.unmountFileSystem((FileT3MBean)deployment);
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
   }

   static FileService getFileService() {
      return singleton;
   }
}
