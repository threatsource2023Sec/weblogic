package weblogic.logging.jms;

import com.bea.logging.LogFileConfigBean;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Logger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.FileStreamHandler;
import weblogic.logging.LogFileConfigUtil;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JMSMessageLogFileMBean;
import weblogic.management.configuration.JMSSAFMessageLogFileMBean;
import weblogic.management.logging.LogRuntime;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.SAFAgentRuntimeMBean;

public class JMSMessageLoggerFactory {
   private static Map jmsMessageLoggers = new HashMap();
   private static Map jmsSAFMessageLoggers = new HashMap();

   public static synchronized JMSMessageLogger findOrCreateJMSMessageLogger(JMSMessageLogFileMBean jmsMessageLogFileMBean, JMSServerRuntimeMBean jmsServerRuntime) throws IOException {
      String key = getKey(jmsServerRuntime.getName());
      if (!jmsMessageLoggers.containsKey(key)) {
         JMSMessageLogger jmsMessageLogger = new JMSMessageLogger(key);
         LogFileConfigBean logFileConfigBean = LogFileConfigUtil.getLogFileConfig(jmsMessageLogFileMBean);
         String logFilePath = logFileConfigBean.getBaseLogFileName();
         logFilePath = decorateLogFilePath(logFilePath, jmsServerRuntime.getName());
         logFileConfigBean.setBaseLogFileName(logFilePath);
         FileStreamHandler fsh = new FileStreamHandler(jmsMessageLogFileMBean, logFileConfigBean, jmsServerRuntime.getName());
         fsh.setFormatter(new JMSMessageLogFileFormatter(jmsMessageLogFileMBean));

         try {
            LogRuntime logRuntime = new LogRuntime(jmsServerRuntime.getName(), jmsServerRuntime, fsh.getRotatingFileOutputStream());
            jmsServerRuntime.setLogRuntime(logRuntime);
         } catch (ManagementException var8) {
            throw new IOException(var8);
         }

         jmsMessageLogger.addHandler(fsh);
         jmsMessageLoggers.put(key, jmsMessageLogger);
      }

      return (JMSMessageLogger)jmsMessageLoggers.get(key);
   }

   public static String decorateLogFilePath(String logFilePath, String jmsServerInstanceName) throws IOException {
      File file = new File(logFilePath);
      File parent = file.getParentFile();
      String decoratedFileName = jmsServerInstanceName.replaceAll("@", "_") + "-" + file.getName();
      file = new File(parent, decoratedFileName);
      return file.getCanonicalPath();
   }

   public static synchronized void removeJMSMessageLogger(JMSServerRuntimeMBean jmsServerRuntime) throws IOException {
      String key = getKey(jmsServerRuntime.getName());
      JMSMessageLogger jmsMessageLogger = (JMSMessageLogger)jmsMessageLoggers.remove(key);
      if (jmsMessageLogger != null) {
         resetLogger(jmsMessageLogger);
      }

   }

   public static synchronized JMSSAFMessageLogger findOrCreateJMSSAFMessageLogger(JMSSAFMessageLogFileMBean jmsSAFMessageLogFileConfig, SAFAgentRuntimeMBean safAgentRuntime) throws IOException {
      String key = getKey(safAgentRuntime.getName());
      if (!jmsSAFMessageLoggers.containsKey(key)) {
         JMSSAFMessageLogger jmsSAFMessageLogger = new JMSSAFMessageLogger(key);
         LogFileConfigBean logFileConfigBean = LogFileConfigUtil.getLogFileConfig(jmsSAFMessageLogFileConfig);
         String logFilePath = logFileConfigBean.getBaseLogFileName();
         logFilePath = decorateLogFilePath(logFilePath, safAgentRuntime.getName());
         logFileConfigBean.setBaseLogFileName(logFilePath);
         FileStreamHandler fsh = new FileStreamHandler(jmsSAFMessageLogFileConfig, logFileConfigBean);
         fsh.setFormatter(new JMSMessageLogFileFormatter(jmsSAFMessageLogFileConfig));

         try {
            LogRuntime logRuntime = new LogRuntime(safAgentRuntime.getName(), safAgentRuntime, fsh.getRotatingFileOutputStream());
            safAgentRuntime.setLogRuntime(logRuntime);
         } catch (ManagementException var8) {
            throw new IOException(var8);
         }

         jmsSAFMessageLogger.addHandler(fsh);
         jmsSAFMessageLoggers.put(key, jmsSAFMessageLogger);
      }

      return (JMSSAFMessageLogger)jmsSAFMessageLoggers.get(key);
   }

   public static synchronized void removeJMSSAFMessageLogger(SAFAgentRuntimeMBean safAgentRuntime) throws IOException {
      String key = getKey(safAgentRuntime.getName());
      JMSSAFMessageLogger jmsSAFMessageLogger = (JMSSAFMessageLogger)jmsSAFMessageLoggers.remove(key);
      if (jmsSAFMessageLogger != null) {
         resetLogger(jmsSAFMessageLogger);
      }

   }

   private static void resetLogger(Logger logger) {
      Handler[] handlers = logger.getHandlers();
      if (handlers != null) {
         Handler[] var2 = handlers;
         int var3 = handlers.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Handler h = var2[var4];
            logger.removeHandler(h);
            h.flush();
            h.close();
         }
      }

   }

   private static String getKey(String name) {
      ComponentInvocationContextManager compCtxMgr = ComponentInvocationContextManager.getInstance();
      String partitionId = "";
      if (compCtxMgr != null) {
         ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
         if (compCtx != null) {
            partitionId = compCtx.getPartitionId();
         }
      }

      return partitionId != null && !partitionId.isEmpty() ? name + "$" + partitionId : name;
   }
}
