package weblogic.management.scripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import weblogic.management.VersionConstants;
import weblogic.management.scripting.core.WLScriptCoreConstants;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.utils.io.TerminalIO;

public class WLSTUtils extends WLScriptCoreConstants {
   private WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();

   boolean isExecutingFromDomainDir() {
      boolean saltFileExists = false;
      boolean configFileExists = false;
      File saltFile = new File("./security/SerializedSystemIni.dat");
      if (saltFile.exists()) {
         saltFileExists = true;
      }

      File configFile = new File("./config/config.xml");
      if (configFile.exists()) {
         configFileExists = true;
      }

      return saltFileExists && configFileExists;
   }

   String getRightType(String mbeanType) {
      if (mbeanType.endsWith("ies")) {
         mbeanType = mbeanType.substring(0, mbeanType.length() - 3) + "y";
      } else if (mbeanType.endsWith("sses")) {
         mbeanType = mbeanType.substring(0, mbeanType.length() - 2);
      } else if (mbeanType.endsWith("s") && !mbeanType.endsWith("ss") && !mbeanType.equals("WTCResources")) {
         mbeanType = mbeanType.substring(0, mbeanType.length() - 1);
      }

      return mbeanType;
   }

   String getPlural(String mbeanType) {
      if (mbeanType.endsWith("y")) {
         mbeanType = mbeanType.substring(0, mbeanType.length() - 1) + "ies";
         return mbeanType;
      } else if (mbeanType.endsWith("ss")) {
         mbeanType = mbeanType + "es";
         return mbeanType;
      } else {
         return !mbeanType.endsWith("s") ? mbeanType + "s" : mbeanType;
      }
   }

   boolean isPlural(String mbeanType) {
      if (mbeanType.endsWith("ies")) {
         return true;
      } else if (mbeanType.endsWith("sses")) {
         return true;
      } else {
         return mbeanType.endsWith("s") && !mbeanType.endsWith("ss");
      }
   }

   boolean isNewFormat(InputStream is) throws ScriptException {
      boolean isNewFormat = false;

      try {
         String line = null;
         BufferedReader in = new BufferedReader(new InputStreamReader(is));

         while(true) {
            while((line = in.readLine()) != null && !isNewFormat) {
               for(int i = 0; i < VersionConstants.KNOWN_NAMESPACE_PREFIXES.length; ++i) {
                  if (line.indexOf(VersionConstants.KNOWN_NAMESPACE_PREFIXES[i]) != -1) {
                     isNewFormat = true;
                     break;
                  }
               }
            }

            return isNewFormat;
         }
      } catch (IOException var6) {
         this.throwWLSTException(this.txtFmt.getUnableToDetermineConfig(), var6);
         return isNewFormat;
      }
   }

   public String promptValue(String text_prompt, boolean echo) {
      String returnValue = null;

      try {
         this.print(text_prompt);
         if (!echo && TerminalIO.isNoEchoAvailable()) {
            try {
               returnValue = TerminalIO.readTerminalNoEcho();
               if (returnValue != null) {
                  byte[] b = returnValue.getBytes();
                  if (b.length == 0 || b[0] == 13 && b[1] == 10) {
                     returnValue = "";
                  }
               }
            } catch (Error var5) {
               System.err.println(this.txtFmt.getFailedToGetValue(var5.getMessage()));
            }
         } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            returnValue = reader.readLine();
         }
      } catch (Exception var6) {
         System.err.println(this.txtFmt.getFailedToGetValue(var6.getMessage()));
      }

      return returnValue;
   }

   String getListenAddress(String url) {
      int i = url.indexOf("//");
      int j = url.lastIndexOf(":");
      String addr = url.substring(i + 2, j);
      return addr;
   }

   String getListenPort(String url) {
      int j = url.lastIndexOf(":");
      int slashIdx = url.indexOf(47, j);
      if (slashIdx == -1) {
         slashIdx = url.length();
      }

      String port = url.substring(j + 1, slashIdx);
      return port;
   }

   String getURL(String url) {
      int i = url.indexOf("//");
      if (i > 0 && url.charAt(i - 1) == ':') {
         return url;
      } else {
         return i == 0 ? "t3:" + url : "t3://" + url;
      }
   }

   String getProtocol(String url) {
      int i = url.indexOf("://");
      return i > 0 ? url.substring(0, i) : "t3";
   }

   String getPath(String url) {
      int j = url.lastIndexOf(":");
      int slashIdx = url.indexOf(47, j);
      if (slashIdx == -1) {
         return null;
      } else {
         String host = url.substring(slashIdx);
         return host;
      }
   }

   void printError(String s) {
      try {
         if (this.errOutputMedium == null) {
            System.err.println(s);
            return;
         }

         if (this.errOutputMedium instanceof OutputStream) {
            ((OutputStream)this.errOutputMedium).write(s.getBytes());
            ((OutputStream)this.errOutputMedium).flush();
         } else if (this.errOutputMedium instanceof Writer) {
            if (this.errOutputMedium instanceof PrintWriter) {
               ((PrintWriter)this.errOutputMedium).println(s);
               ((PrintWriter)this.errOutputMedium).flush();
            } else {
               ((Writer)this.errOutputMedium).write(s);
               ((Writer)this.errOutputMedium).flush();
            }
         }
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void printDebug(String s) {
      if (this.debug) {
         this.println("<wlst-debug> " + this.commandType + " : " + s);
      }

   }

   public void print(String s) {
      try {
         if (this.stdOutputMedium == null && this.logToStandardOut) {
            System.out.print(s);
            return;
         }

         if (this.logToStandardOut) {
            System.out.print(s);
         }

         if (this.stdOutputMedium instanceof OutputStream) {
            ((OutputStream)this.stdOutputMedium).write(s.getBytes());
            ((OutputStream)this.stdOutputMedium).flush();
         } else if (this.stdOutputMedium instanceof Writer) {
            if (this.stdOutputMedium instanceof PrintWriter) {
               ((PrintWriter)this.stdOutputMedium).print(s);
               ((PrintWriter)this.stdOutputMedium).flush();
            } else {
               ((Writer)this.stdOutputMedium).write(s);
               ((Writer)this.stdOutputMedium).flush();
            }
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      } finally {
         try {
            if (this.stdOutputMedium != null) {
               if (this.stdOutputMedium instanceof OutputStream) {
                  ((OutputStream)this.stdOutputMedium).flush();
               } else if (this.stdOutputMedium instanceof Writer) {
                  if (this.stdOutputMedium instanceof PrintWriter) {
                     ((PrintWriter)this.stdOutputMedium).flush();
                  } else {
                     ((Writer)this.stdOutputMedium).flush();
                  }
               }
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   public void println(String s) {
      try {
         if (this.stdOutputMedium == null && this.logToStandardOut) {
            System.out.println(s);
            return;
         }

         if (this.logToStandardOut) {
            System.out.println(s);
         }

         if (this.stdOutputMedium instanceof OutputStream) {
            ((OutputStream)this.stdOutputMedium).write(s.getBytes());
            ((OutputStream)this.stdOutputMedium).write("\n".getBytes());
            ((OutputStream)this.stdOutputMedium).flush();
         } else if (this.stdOutputMedium instanceof Writer) {
            if (this.stdOutputMedium instanceof PrintWriter) {
               ((PrintWriter)this.stdOutputMedium).println(s);
               ((PrintWriter)this.stdOutputMedium).flush();
            } else {
               ((Writer)this.stdOutputMedium).write(s);
               ((Writer)this.stdOutputMedium).write("\n");
               ((Writer)this.stdOutputMedium).flush();
            }
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      } finally {
         try {
            if (this.stdOutputMedium != null) {
               if (this.stdOutputMedium instanceof OutputStream) {
                  ((OutputStream)this.stdOutputMedium).flush();
               } else if (this.stdOutputMedium instanceof Writer) {
                  if (this.stdOutputMedium instanceof PrintWriter) {
                     ((PrintWriter)this.stdOutputMedium).flush();
                  } else {
                     ((Writer)this.stdOutputMedium).flush();
                  }
               }
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   public boolean getBoolean(String value) {
      if (value == null) {
         return false;
      } else {
         return value.toLowerCase(Locale.US).equals("true");
      }
   }

   public void throwWLSTException(String msg, Throwable th) throws ScriptException {
      this.errorMsg = msg;
      this.errorInfo = new ErrorInformation(th, this.errorMsg);
      this.exceptionHandler.handleException(this.errorInfo);
   }

   public void throwWLSTException(String msg) throws ScriptException {
      this.errorMsg = msg;
      this.errorInfo = new ErrorInformation(this.errorMsg);
      this.exceptionHandler.handleException(this.errorInfo);
   }

   public boolean isConnected() {
      return false;
   }

   public void setDumpStackThrowable(Throwable th) {
      this.stackTrace = th;
   }

   public Throwable getStackTrace() {
      return this.stackTrace;
   }

   public byte[] encodeUserAndIDDBytes(byte[] username_bytes, byte[] idd_bytes) {
      if (idd_bytes != null && idd_bytes.length != 0) {
         String user = new String(username_bytes);
         String idd = new String(idd_bytes);
         return IdentityDomainNamesEncoder.encodeNames(user, idd).getBytes();
      } else {
         return username_bytes;
      }
   }
}
