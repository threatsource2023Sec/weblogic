package weblogic.servlet.logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.WebServerMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.utils.StringUtils;

public final class ELFLogger implements Logger {
   private static final String LINE_SEP = System.getProperty("line.separator");
   private static final String ELF_HEADERS_0;
   private static final String ELF_HEADERS_2;
   private static final String ELF_HEADERS_4 = "\t";
   private static final String ELF_HEADERS_6;
   private static final String[] ELF_HEADERS;
   private final String fieldsDirective;
   private final LogFormat logFormat;
   private final boolean logTimeInGMT;
   private final LogManagerHttp logManager;
   private static final DebugLogger DEBUG_LOGGING;
   private volatile boolean needToWriteELFHeaders;
   private boolean logMillis;

   public ELFLogger(LogManagerHttp lmh, WebServerMBean mbean) {
      this.logManager = lmh;
      this.logTimeInGMT = mbean.getWebServerLog().isLogTimeInGMT();
      this.logMillis = mbean.getWebServerLog().isLogMilliSeconds();
      this.fieldsDirective = this.findFieldsDirective(mbean);
      this.logFormat = new LogFormat(this.fieldsDirective);
      if (mbean.getWebServerLog().getRotateLogOnStartup()) {
         this.needToWriteELFHeaders = true;
      }

   }

   private String findFieldsDirective(WebServerMBean mbean) {
      try {
         String[] elfFields = readELFFields(mbean.getWebServerLog().getLogFilePath());
         if (elfFields == null) {
            if (DEBUG_LOGGING.isDebugEnabled()) {
               DEBUG_LOGGING.debug("No previous access.log for webserver: " + mbean.getName());
            }

            this.needToWriteELFHeaders = true;
            return mbean.getWebServerLog().getELFFields();
         }

         if (elfFields[0] != null && !elfFields[0].startsWith("1.0")) {
            HTTPLogger.logELFLogNotFormattedProperly();
         } else if (elfFields[1] != null && elfFields[1].length() > 0) {
            if (elfFields[1].equals(mbean.getWebServerLog().getELFFields())) {
               if (DEBUG_LOGGING.isDebugEnabled()) {
                  DEBUG_LOGGING.debug("Fields in access.log match with webserver: " + mbean.getName());
               }

               return elfFields[1];
            }

            if (!mbean.getWebServerLog().isSet("ELFFields")) {
               if (DEBUG_LOGGING.isDebugEnabled()) {
                  DEBUG_LOGGING.debug("Found modified fields: " + elfFields[1] + " in access.log for webserver: " + mbean.getName());
               }

               return elfFields[1];
            }

            if (DEBUG_LOGGING.isDebugEnabled()) {
               DEBUG_LOGGING.debug("WebServerMBean's ELF-Fields have been modified for : " + mbean.getName() + " access.log will be rotated");
            }

            HTTPLogger.logELFFieldsChanged(mbean.getName(), elfFields[1], mbean.getWebServerLog().getELFFields());
            this.logManager.rotateLog();
            this.needToWriteELFHeaders = true;
            return mbean.getWebServerLog().getELFFields();
         }
      } catch (IOException var4) {
         HTTPLogger.logELFReadHeadersException(var4);
      }

      if (DEBUG_LOGGING.isDebugEnabled()) {
         DEBUG_LOGGING.debug("Rotating access.log for : " + mbean.getName() + " since the old format doesn't seem to ELF");
      }

      try {
         this.logManager.rotateLog();
      } catch (IOException var3) {
         HTTPLogger.logFailedToRollLogFile(mbean.getName(), var3);
      }

      this.needToWriteELFHeaders = true;
      return mbean.getWebServerLog().getELFFields();
   }

   static String[] readELFFields(String logFile) throws IOException {
      BufferedReader logReader = getLogReader(logFile);
      if (logReader == null) {
         return null;
      } else {
         String[] elfFields = new String[2];

         String line;
         try {
            while((line = logReader.readLine()) != null && line.startsWith("#")) {
               String[] keyval = StringUtils.split(line.substring(1), ':');
               keyval[0] = keyval[0].trim();
               if ("Version".equals(keyval[0])) {
                  elfFields[0] = keyval[1].trim();
               } else if ("Fields".equals(keyval[0])) {
                  elfFields[1] = keyval[1].trim();
               }
            }
         } finally {
            logReader.close();
         }

         return elfFields;
      }
   }

   public int log(ServletRequestImpl req, ServletResponseImpl res) {
      if (this.needToWriteELFHeaders) {
         OutputStream httpdlog = this.logManager.getLogStream();
         synchronized(httpdlog) {
            this.writeELFHeaders(httpdlog);
         }
      }

      FormatStringBuffer buf = new FormatStringBuffer(128);
      buf.setUseGMT(this.logTimeInGMT);
      buf.setLogMillis(this.logMillis);
      int fn = this.logFormat.countFields();
      HttpAccountingInfo accountInfo = req.getHttpAccountingInfo();

      for(int i = 0; i < fn; ++i) {
         this.logFormat.getFieldAt(i).logField(accountInfo, buf);
         if (i != fn - 1) {
            buf.append('\t');
         }
      }

      buf.append(LINE_SEP);
      OutputStream httpdlog = this.logManager.getLogStream();

      try {
         httpdlog.write(buf.getBytes(), 0, buf.size());
      } catch (IOException var8) {
      }

      return buf.size();
   }

   private static BufferedReader getLogReader(String logFileName) {
      File logfile = null;

      try {
         if (logFileName == null) {
            return null;
         } else {
            logfile = new File(logFileName);
            String parentPathName = logfile.getParent();
            if (parentPathName != null) {
               File parentPath = new File(parentPathName);
               if (!parentPath.exists()) {
                  parentPath.mkdirs();
               }
            }

            BufferedReader reader = new BufferedReader(new FileReader(logfile));
            return reader;
         }
      } catch (IOException var5) {
         return null;
      }
   }

   private String getELFHeaders() {
      StringBuffer elfHeader = new StringBuffer();

      for(int i = 0; i < ELF_HEADERS.length; ++i) {
         if (ELF_HEADERS[i] == null) {
            FormatStringBuffer buf;
            switch (i) {
               case 1:
                  elfHeader.append(this.fieldsDirective);
               case 2:
               case 4:
               default:
                  break;
               case 3:
                  buf = (new FormatStringBuffer()).appendDate();
                  elfHeader.append(new String(buf.getBytes(), 0, buf.size()));
                  break;
               case 5:
                  buf = (new FormatStringBuffer()).appendTime();
                  elfHeader.append(new String(buf.getBytes(), 0, buf.size()));
            }
         } else {
            elfHeader.append(ELF_HEADERS[i]);
         }
      }

      return elfHeader.toString();
   }

   public void markRotated() {
      OutputStream httpdlog = this.logManager.getLogStream();
      synchronized(httpdlog) {
         this.needToWriteELFHeaders = true;
         this.writeELFHeaders(httpdlog);
      }
   }

   public void needToWriteELFHeaders() {
      this.needToWriteELFHeaders = true;
   }

   private void writeELFHeaders(OutputStream httpdlog) {
      if (this.needToWriteELFHeaders) {
         byte[] bytes = this.getELFHeaders().getBytes();

         try {
            httpdlog.write(bytes, 0, bytes.length);
         } catch (IOException var4) {
         }

         this.needToWriteELFHeaders = false;
      }
   }

   static {
      ELF_HEADERS_0 = "#Version:\t1.0" + LINE_SEP + "#Fields:\t";
      ELF_HEADERS_2 = LINE_SEP + "#Software:\tWebLogic" + LINE_SEP + "#Start-Date:\t";
      ELF_HEADERS_6 = LINE_SEP;
      ELF_HEADERS = new String[]{ELF_HEADERS_0, null, ELF_HEADERS_2, null, "\t", null, ELF_HEADERS_6};
      DEBUG_LOGGING = DebugLogger.getDebugLogger("DebugHttpLogging");
   }
}
