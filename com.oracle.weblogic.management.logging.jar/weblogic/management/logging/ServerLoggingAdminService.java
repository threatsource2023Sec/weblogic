package weblogic.management.logging;

import com.bea.logging.LogFileRotator;
import com.bea.logging.LoggingService;
import com.bea.logging.LoggingServiceManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.debug.PartitionContextProvider;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.JDKLoggerFactory;
import weblogic.logging.LogMgmtLogger;
import weblogic.logging.ServerLoggingInitializer;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.PartitionLogMBean;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.ArrayUtils;

@Service
@Named
@RunLevel(15)
public class ServerLoggingAdminService extends AbstractServerService {
   private static final boolean DEBUG = false;
   private static final ThreadLocal RECURSIVE_CHECK = new ThreadLocal() {
      protected Boolean initialValue() {
         return Boolean.FALSE;
      }
   };

   public void start() throws ServiceFailureException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      final RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);

      try {
         LogMBean logMBean = runtimeAccess.getServer().getLog();
         JDKLoggerFactory loggerFactory = ServerLoggingInitializer.getLoggerFactory();
         if (loggerFactory != null) {
            loggerFactory.initializeServerLoggingBridge(logMBean);
            LogMgmtLogger.logInitializedServerLoggingBridge();
         }
      } catch (Throwable var10) {
         LogMgmtLogger.logErrorRegisteringServerLoggingBridge(var10);
      }

      ServerDebugMBean serverDebug = runtimeAccess.getServer().getServerDebug();
      this.initializeDebugPartitionContextProvider(serverDebug.isPartitionDebugLoggingEnabled());
      serverDebug.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("PartitionDebugLoggingEnabled")) {
               Boolean newValue = (Boolean)evt.getNewValue();
               ServerLoggingAdminService.this.initializeDebugPartitionContextProvider(newValue);
            }

         }
      });
      PartitionRuntimeMBean[] partitions = runtimeAccess.getServerRuntime().getPartitionRuntimes();
      if (partitions != null) {
         PartitionRuntimeMBean[] var5 = partitions;
         int var6 = partitions.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PartitionRuntimeMBean p = var5[var7];
            PartitionLogMBean plog = runtimeAccess.getDomain().lookupPartition(p.getName()).getPartitionLog();
            this.initializePartitionLogManager(plog);
            this.initializePartitionDebugConfig(plog);
         }
      }

      runtimeAccess.getServerRuntime().addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("PartitionRuntimes")) {
               PartitionRuntimeMBean[] oldValue = (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])evt.getOldValue());
               PartitionRuntimeMBean[] newValue = (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])evt.getNewValue());
               ArrayUtils.computeDiff(oldValue, newValue, new ArrayUtils.DiffHandler() {
                  public void addObject(Object added) {
                     if (added != null) {
                        PartitionRuntimeMBean prt = (PartitionRuntimeMBean)added;
                        PartitionLogMBean plog = runtimeAccess.getDomain().lookupPartition(prt.getName()).getPartitionLog();
                        ServerLoggingAdminService.this.initializePartitionLogManager(plog);
                        ServerLoggingAdminService.this.initializePartitionDebugConfig(plog);
                     }

                  }

                  public void removeObject(Object removed) {
                     if (removed != null) {
                        PartitionRuntimeMBean prt = (PartitionRuntimeMBean)removed;
                        ServerLoggingAdminService.this.removePartitionLogManager(prt.getName());
                        ServerLoggingAdminService.this.removePartitionDebugConfig(prt.getName());
                     }

                  }
               });
            }

         }
      });
      this.initializeLogFileRotator(kernelId);
   }

   private void initializeDebugPartitionContextProvider(boolean enable) {
      if (enable) {
         DebugLogger.setPartitionContextProvider(ServerLoggingAdminService.PartitionContextProviderImpl.SINGLETON);
      } else {
         DebugLogger.setPartitionContextProvider((PartitionContextProvider)null);
      }

   }

   private void initializePartitionDebugConfig(PartitionLogMBean plog) {
      String[] debugAttrs = plog.getEnabledServerDebugAttributes();
      if (debugAttrs != null) {
         Map debugConfig = new ConcurrentHashMap();
         String[] var4 = debugAttrs;
         int var5 = debugAttrs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String attr = var4[var6];
            debugConfig.put(attr, true);
         }

         DebugLogger.initializePartitionDebugConfig(plog.getName(), debugConfig);
      } else {
         this.removePartitionDebugConfig(plog.getName());
      }

   }

   private void removePartitionDebugConfig(String name) {
      DebugLogger.removePartitionDebugConfig(name);
   }

   private void initializePartitionLogManager(PartitionLogMBean partitionLog) {
      if (partitionLog != null && !ServerLoggingInitializer.isODLLoggingEnabled()) {
         String name = partitionLog.getName();
         Properties props = partitionLog.getPlatformLoggerLevels();
         if (props != null && !props.isEmpty()) {
            LoggingServiceManager plm = LoggingService.getInstance().findOrCreatePartitionLogManager(name);
            plm.superReset();
            Iterator var5 = props.entrySet().iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               Object key = entry.getKey();
               Object value = entry.getValue();
               String logger = key != null ? key.toString() : "";
               String levelName = value != null ? value.toString() : "";

               try {
                  Level level = Level.parse(levelName);
                  plm.getLogger(logger).setLevel(level);
               } catch (IllegalArgumentException var12) {
               }
            }
         } else {
            LoggingService.getInstance().removePartitionLogManager(name);
         }

         partitionLog.addPropertyChangeListener(new PartitionLogMBeanPropertyListener());
      }
   }

   private void removePartitionLogManager(String pname) {
      LoggingService.getInstance().removePartitionLogManager(pname);
   }

   private void initializeLogFileRotator(AuthenticatedSubject kernelId) {
      LogFileRotator.setGlobalAccountRunner(new LogFileRotator.GlobalAccountRunner() {
         public void accountAsGlobal(Runnable runnable) throws IOException {
            boolean startedAccountAsGlobal = false;

            try {
               runnable.run();
            } catch (Throwable var7) {
               throw new IOException(var7);
            } finally {
               if (startedAccountAsGlobal) {
                  ServerLoggingAdminService.RECURSIVE_CHECK.set(Boolean.FALSE);
               }

            }

         }
      });
   }

   public void stop() throws ServiceFailureException {
   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   private final class PartitionLogMBeanPropertyListener implements PropertyChangeListener {
      private PartitionLogMBeanPropertyListener() {
      }

      public void propertyChange(PropertyChangeEvent evt) {
         Object source = evt.getSource();
         if (source != null && source instanceof PartitionLogMBean) {
            PartitionLogMBean partitionLog = (PartitionLogMBean)source;
            if (evt.getPropertyName().equals("PlatformLoggerLevels")) {
               ServerLoggingAdminService.this.initializePartitionLogManager(partitionLog);
            } else if (evt.getPropertyName().equals("EnabledServerDebugAttributes")) {
               ServerLoggingAdminService.this.initializePartitionDebugConfig(partitionLog);
            }
         }

      }

      // $FF: synthetic method
      PartitionLogMBeanPropertyListener(Object x1) {
         this();
      }
   }

   private static final class PartitionContextProviderImpl implements PartitionContextProvider {
      private static final PartitionContextProvider SINGLETON = new PartitionContextProviderImpl();

      public String getCurrentPartitionName() {
         ComponentInvocationContextManager compCtxMgr = ComponentInvocationContextManager.getInstance();
         if (compCtxMgr != null) {
            ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
            if (compCtx != null) {
               return compCtx.getPartitionName();
            }
         }

         return null;
      }
   }
}
