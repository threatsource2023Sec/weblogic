package weblogic.logging;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileOutputStream;
import com.bea.logging.RotatingFileStreamHandler;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.jms.JMSMessageLoggerFactory;
import weblogic.management.configuration.JMSMessageLogFileMBean;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.PlatformConstants;

public final class FileStreamHandler extends RotatingFileStreamHandler implements BeanUpdateListener {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugLoggingConfiguration");
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String runtimeName;
   private ActiveBeanUtil activeBeanUtil;
   private final LogFileMBean bean;

   /** @deprecated */
   @Deprecated
   public FileStreamHandler(LogMBean config) throws IOException {
      this((LogFileMBean)config);
   }

   public FileStreamHandler(LogFileMBean logConfig) throws IOException {
      this(logConfig, LogFileConfigUtil.getLogFileConfig(logConfig));
   }

   public FileStreamHandler(LogFileMBean logConfigMBean, LogFileConfigBean logFileConfigBean) throws IOException {
      this(logConfigMBean, logFileConfigBean, (String)null);
   }

   public FileStreamHandler(LogFileMBean logConfigMBean, LogFileConfigBean logFileConfigBean, String runtimeName) throws IOException {
      super(logFileConfigBean);
      this.setErrorManager(new WLErrorManager(this));
      this.bean = logConfigMBean;
      this.bean.setOutputStream(this.getRotatingFileOutputStream());
      this.runtimeName = runtimeName;
      this.bean.addBeanUpdateListener(this);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Added " + this + " as BeanUpdateListener on " + this.bean);
      }

      this.activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);
   }

   public String toString() {
      RotatingFileOutputStream ros = this.getRotatingFileOutputStream();
      StringBuilder sb = new StringBuilder();
      sb.append("weblogic.logging.FileStreamHandler instance=").append(this.hashCode()).append(PlatformConstants.EOL);
      if (ros != null) {
         sb.append("Current log file=" + ros.getCurrentLogFile()).append(PlatformConstants.EOL);
         sb.append("Rotation dir=" + ros.getLogRotationDir()).append(PlatformConstants.EOL);
      }

      return sb.toString();
   }

   public void prepareUpdate(BeanUpdateEvent arg0) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(final BeanUpdateEvent arg0) throws BeanUpdateFailedException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Activating update to log configuration on " + arg0.getSource());
      }

      Object source = arg0.getSource();
      if (source instanceof LogFileMBean) {
         final LogFileMBean logMBean = (LogFileMBean)source;

         try {
            PartitionMBean partition = this.activeBeanUtil.findContainingPartition(logMBean);
            String partitionName = partition != null ? partition.getName() : "DOMAIN";
            ComponentInvocationContext ctx = ComponentInvocationContextManager.getInstance(KERNELID).createComponentInvocationContext(partitionName);
            ComponentInvocationContextManager.runAs(KERNELID, ctx, new Callable() {
               public Boolean call() throws Exception {
                  LogFileConfigBean configBean = LogFileConfigUtil.getLogFileConfig(logMBean);
                  if (FileStreamHandler.this.runtimeName != null && logMBean instanceof JMSMessageLogFileMBean) {
                     String logFilePath = configBean.getBaseLogFilePath();
                     logFilePath = JMSMessageLoggerFactory.decorateLogFilePath(logFilePath, FileStreamHandler.this.runtimeName);
                     configBean.setBaseLogFilePath(logFilePath);
                  }

                  FileStreamHandler.this.initialize(configBean, true, arg0);
                  return true;
               }
            });
         } catch (ExecutionException var7) {
            throw new BeanUpdateFailedException("Failed to update log configuration for " + logMBean.getName(), var7);
         }
      } else if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Updates to bean which is not a LogFileMBean: " + source);
      }

   }

   public void rollbackUpdate(BeanUpdateEvent arg0) {
   }

   public void close() {
      this.bean.removeBeanUpdateListener(this);
      super.close();
   }
}
