package weblogic.management.logging;

import com.bea.logging.RotatingFileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import weblogic.logging.LogMgmtLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class LogRuntime extends RuntimeMBeanDelegate implements LogRuntimeMBean {
   private RotatingFileOutputStream rotatingFileOutputStream;
   private LogFileMBean logfileMBean;

   public LogRuntime(LogFileMBean logfileMBean, RuntimeMBean parent) throws ManagementException {
      this(logfileMBean.getName(), parent, (RotatingFileOutputStream)logfileMBean.getOutputStream());
      this.logfileMBean = logfileMBean;
   }

   public LogRuntime(String name, RuntimeMBean parent, RotatingFileOutputStream ros) throws ManagementException {
      super(name, parent);
      this.rotatingFileOutputStream = ros;
   }

   public void forceLogRotation() throws ManagementException {
      RotatingFileOutputStream ros = this.rotatingFileOutputStream;
      if (ros == null && this.logfileMBean != null) {
         ros = (RotatingFileOutputStream)this.logfileMBean.getOutputStream();
      }

      if (ros != null) {
         try {
            ros.forceRotation();
         } catch (IOException var3) {
            throw new ManagementException(var3.toString());
         }
      }

   }

   public void ensureLogOpened() throws ManagementException {
      if (this.rotatingFileOutputStream != null) {
         try {
            String logFile = this.rotatingFileOutputStream.getCurrentLogFile();
            if (logFile != null) {
               File file = new File(logFile);
               if (!file.exists()) {
                  this.rotatingFileOutputStream.forceRotation();
                  LogMgmtLogger.logForcedLogRotation();
               }
            }

            if (!this.rotatingFileOutputStream.isStreamOpened()) {
               this.rotatingFileOutputStream.open();
            }

         } catch (Exception var3) {
            throw new ManagementException(var3.toString());
         }
      }
   }

   public void close() throws ManagementException {
      if (this.rotatingFileOutputStream != null) {
         try {
            this.rotatingFileOutputStream.close();
         } catch (IOException var2) {
            throw new ManagementException(var2.toString());
         }
      }

   }

   public void flushLog() throws ManagementException {
      if (this.rotatingFileOutputStream != null) {
         try {
            this.rotatingFileOutputStream.flush();
         } catch (IOException var2) {
            throw new ManagementException(var2.toString());
         }
      }

   }

   public SortedSet getRotatedLogFiles() {
      return this.rotatingFileOutputStream != null ? this.rotatingFileOutputStream.getRotatedLogFiles() : null;
   }

   public String getCurrentLogFile() {
      return this.rotatingFileOutputStream != null ? this.rotatingFileOutputStream.getCurrentLogFile() : null;
   }

   public String getLogRotationDir() {
      return this.rotatingFileOutputStream != null ? this.rotatingFileOutputStream.getLogRotationDir() : null;
   }

   public boolean isLogFileStreamOpened() {
      return this.rotatingFileOutputStream != null ? this.rotatingFileOutputStream.isStreamOpened() : false;
   }
}
