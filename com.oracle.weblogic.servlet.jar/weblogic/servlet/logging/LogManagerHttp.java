package weblogic.servlet.logging;

import com.bea.logging.RotatingFileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.FileStreamHandler;
import weblogic.logging.LogFileFormatter;
import weblogic.management.configuration.WebServerMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;

public final class LogManagerHttp {
   private static final DebugLogger DEBUG_LOGGING = DebugLogger.getDebugLogger("DebugHttpLogging");
   private final WebServerMBean mbean;
   private RotatingFileOutputStream outputStream;
   private Logger logger;
   private boolean isExtendedFormat = false;
   private boolean rotateLog = false;
   private boolean logInternalAppAccess = Boolean.getBoolean("weblogic.servlet.logging.LogInternalAppAccess");
   private FileStreamHandler fileHandler = null;

   public LogManagerHttp(WebServerMBean mb) {
      this.mbean = mb;
      if (!this.mbean.getWebServerLog().isLoggingEnabled()) {
         HTTPLogger.logHttpLoggingDisabled(this.mbean.getName());
      }
   }

   public void start() {
      this.initLoggers();
   }

   private void initLoggers() {
      if (!"extended".equals(this.mbean.getWebServerLog().getLogFileFormat())) {
         if (DEBUG_LOGGING.isDebugEnabled()) {
            DEBUG_LOGGING.debug("Creating CLFLogger for the webserver: " + this.mbean.getName());
         }

         this.logger = new CLFLogger(this, this.mbean);
      } else {
         if (DEBUG_LOGGING.isDebugEnabled()) {
            DEBUG_LOGGING.debug("Creating ELFLogger for the webserver: " + this.mbean.getName());
         }

         this.isExtendedFormat = true;
         this.logger = new ELFLogger(this, this.mbean);
      }

      try {
         this.fileHandler = new FileStreamHandler(this.mbean.getWebServerLog());
         this.fileHandler.setFormatter(new LogFileFormatter(this.mbean.getWebServerLog()));
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      this.outputStream = (RotatingFileOutputStream)this.mbean.getWebServerLog().getOutputStream();
      this.outputStream.setRotateImmediately(true);

      try {
         if (this.rotateLog) {
            this.forceRotation();
            this.rotateLog = false;
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   protected void rotateLog() throws IOException {
      this.rotateLog = true;
   }

   public void close() {
      try {
         if (this.outputStream != null) {
            this.outputStream.close();
         }

         if (this.fileHandler != null) {
            this.fileHandler.close();
         }
      } catch (IOException var2) {
         if (DEBUG_LOGGING.isDebugEnabled()) {
            DEBUG_LOGGING.debug("Failed to close HttpRotatingFileOutputStream for the webserver: " + this.mbean.getName(), var2);
         }

         var2.printStackTrace();
      }

   }

   public void log(ServletRequestImpl req, ServletResponseImpl res) {
      if (this.mbean.getWebServerLog().isLoggingEnabled() && this.logger != null && this.outputStream != null && (req.getContext() == null || !req.getContext().getConfigManager().isAccessLoggingDisabled() || this.logInternalAppAccess)) {
         if (this.isExtendedFormat && this.outputStream.isFileRotated()) {
            this.outputStream.setFileRotated(false);
            this.logger.needToWriteELFHeaders();
         }

         this.logger.log(req, res);
      }
   }

   OutputStream getLogStream() {
      return this.outputStream;
   }

   public boolean isExtendedFormat() {
      return this.isExtendedFormat;
   }

   private synchronized void forceRotation() throws IOException {
      this.outputStream.forceRotation();
   }

   public String getLogFilePath() {
      return this.outputStream.getCurrentLogFile();
   }

   public String getLogRotationDir() {
      return this.outputStream.getLogRotationDir();
   }
}
