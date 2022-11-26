package weblogic.jdbc.common.internal;

import com.bea.logging.LogFileConfigBean;
import com.bea.logging.RotatingFileStreamHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.management.configuration.CommonLogMBean;
import weblogic.management.configuration.DataSourceLogFileMBean;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DataSourceServiceFullImpl extends DataSourceServiceImpl implements BeanUpdateListener {
   private static final AuthenticatedSubject KERNELID = getKernelID();
   static RotatingFileStreamHandler rotatingFileStreamHandler = null;
   private static final String DEFAULT_ROTATION_TIME = "00:00";

   private static AuthenticatedSubject getKernelID() {
      return (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   public OutputStream getLogFileOutputStream() throws Exception {
      if (rotatingFileStreamHandler == null) {
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(KERNELID).getServer();
         final LogFileMBean logConfig = serverMBean.getDataSource().getDataSourceLogFile();
         ComponentInvocationContext ctx = ComponentInvocationContextManager.getInstance(KERNELID).createComponentInvocationContext("DOMAIN");
         final Exception[] exWrapper = new Exception[1];
         ComponentInvocationContextManager.runAs(KERNELID, ctx, new Runnable() {
            public void run() {
               try {
                  LogFileConfigBean logFileConfigBean = DataSourceServiceFullImpl.getLogFileConfig(logConfig);
                  DataSourceServiceFullImpl.rotatingFileStreamHandler = new RotatingFileStreamHandler(logFileConfigBean);
               } catch (Exception var2) {
                  exWrapper[0] = var2;
               }

            }
         });
         if (exWrapper[0] != null) {
            throw exWrapper[0];
         }

         logConfig.addBeanUpdateListener(this);
      }

      return rotatingFileStreamHandler.getRotatingFileOutputStream();
   }

   public void prepareUpdate(BeanUpdateEvent arg0) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent arg0) throws BeanUpdateFailedException {
      Object source = arg0.getSource();
      if (source instanceof DataSourceLogFileMBean) {
         DataSourceLogFileMBean logMBean = (DataSourceLogFileMBean)source;

         try {
            LogFileConfigBean configBean = getLogFileConfig(logMBean);
            rotatingFileStreamHandler.initialize(configBean, true, arg0);
         } catch (IOException var5) {
            throw new BeanUpdateFailedException("Failed to update log configuration for " + logMBean.getName(), var5);
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent arg0) {
   }

   private static LogFileConfigBean getLogFileConfig(LogFileMBean logFileMBean) {
      LogFileConfigBean logFileConfig = new LogFileConfigBean();
      logFileConfig.setBaseLogFileName(logFileMBean.getLogFilePath());
      logFileConfig.setRotateLogOnStartupEnabled(logFileMBean.getRotateLogOnStartup());
      String logFileRotationDir;
      if (logFileMBean instanceof CommonLogMBean) {
         logFileRotationDir = ((CommonLogMBean)logFileMBean).getLogFileSeverity();
         if (logFileRotationDir != null) {
            logFileConfig.setLogFileSeverity(logFileRotationDir);
         }
      }

      logFileConfig.setRotatedFileCount(logFileMBean.getFileCount());
      logFileConfig.setRotationSize(logFileMBean.getFileMinSize());
      logFileRotationDir = logFileMBean.getLogFileRotationDir();
      if (logFileRotationDir != null && logFileRotationDir.length() > 0) {
         logFileConfig.setLogFileRotationDir(logFileRotationDir);
      }

      logFileConfig.setNumberOfFilesLimited(logFileMBean.isNumberOfFilesLimited());
      String rotationTime = logFileMBean.getRotationTime();
      if (rotationTime == null || rotationTime.equals("")) {
         rotationTime = "00:00";
      }

      logFileConfig.setRotationTime(rotationTime);
      String rotationType = logFileMBean.getRotationType();
      if (rotationType == null || rotationType.equals("")) {
         rotationType = "bySize";
      }

      logFileConfig.setRotationType(rotationType);
      logFileConfig.setRotationTimeSpan(logFileMBean.getFileTimeSpan());
      logFileConfig.setRotationTimeSpanFactor(logFileMBean.getFileTimeSpanFactor());
      logFileConfig.setBufferSizeKB(logFileMBean.getBufferSizeKB());
      return logFileConfig;
   }

   public ConnectionPoolManager getConnectionPoolManager() {
      return JDBCService.getConnectionPoolManager();
   }

   public DataSourceManager getDataSourceManager() {
      return JDBCService.getDataSourceManager();
   }

   public void createHADataSourceRuntimeMBean(HAConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
      JDBCService.createHADataSourceRuntimeMBean(pool, appName, moduleName, compName, dsBean);
   }

   public void createDataSourceRuntimeMBean(ConnectionPool pool, String appName, String moduleName, String compName, JDBCDataSourceBean dsBean) throws Exception {
      JDBCService.createDataSourceRuntimeMBean(pool, appName, moduleName, compName, dsBean);
   }

   public void destroyDataSourceRuntimeMBean(String driverName, String appName, String moduleName, String compName, String poolName, JDBCDataSourceBean dsBean) throws Exception {
      JDBCService.destroyDataSourceRuntimeMBean(driverName, appName, moduleName, compName, poolName, (String)null, dsBean);
   }
}
