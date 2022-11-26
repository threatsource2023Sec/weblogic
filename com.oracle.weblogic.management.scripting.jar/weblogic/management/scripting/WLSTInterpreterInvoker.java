package weblogic.management.scripting;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.core.PyList;
import org.python.core.PyString;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;
import weblogic.utils.StringUtils;

public class WLSTInterpreterInvoker {
   public static final String DISABLE_JAR_LOADING_PROPERTY = "wlst.disablePluginJarLoadingMode";
   private String propertiesFile;
   private String fileName;
   private String arguments = "";
   private String scriptTempFile;
   private String scriptDir;
   private boolean failOnError = true;
   private boolean executeScriptBeforeFile = true;
   private boolean scriptMode;
   private boolean onlineOnlyMode = false;
   private Boolean disablePluginJarLoadingMode = null;
   private boolean debug = false;
   private WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   private static final boolean IS_WINDOWS;

   public static void main(String[] arg) throws Throwable {
      WLSTInterpreterInvoker invoker = new WLSTInterpreterInvoker();
      invoker.parseArgs(arg);
      invoker.executePyScript();
   }

   private void parseArgs(String[] arg) {
      int i;
      if (IS_WINDOWS && arg != null && arg.length > 1) {
         for(i = 0; i < arg.length; ++i) {
            arg[i] = this.quoteTheString(arg[i]);
         }
      }

      for(i = 0; i < arg.length; ++i) {
         this.arguments = this.arguments + " " + arg[i];
      }

      this.fileName = System.getProperty("fileName");
      this.scriptTempFile = System.getProperty("scriptTempFile");
      String temp;
      if (this.scriptTempFile == null) {
         if (this.fileName == null) {
            temp = this.txtFmt.getErrrorFileNameRequired();
            this.printError(temp);
         }

         if (this.fileName != null && !(new File(this.fileName)).exists()) {
            temp = this.txtFmt.getErrorFileNotExist(this.fileName);
            this.printError(temp);
         }
      }

      this.scriptDir = System.getProperty("wlstScriptDir");
      this.failOnError = Boolean.getBoolean("failOnError");
      this.debug = Boolean.getBoolean("debug");
      this.executeScriptBeforeFile = Boolean.getBoolean("executeScriptBeforeFile");
      this.propertiesFile = System.getProperty("propertiesFile");
      this.scriptMode = Boolean.getBoolean("enableScriptMode");
      this.onlineOnlyMode = Boolean.getBoolean("onlineOnlyMode");
      temp = System.getProperty("wlst.disablePluginJarLoadingMode");
      if (temp != null) {
         if (temp.equalsIgnoreCase("true")) {
            this.disablePluginJarLoadingMode = new Boolean(true);
         } else if (temp.equalsIgnoreCase("false")) {
            this.disablePluginJarLoadingMode = new Boolean(false);
         }
      }

   }

   private String quoteTheString(String s) {
      if (s != null && s.length() != 0) {
         return s.indexOf(34) >= 0 ? s : "\"" + s + "\"";
      } else {
         return "";
      }
   }

   private void executePyScript() {
      try {
         Hashtable h = null;
         if (this.scriptDir != null) {
            h = new Hashtable();
            h.put("wlstScriptDir", this.scriptDir);
            this.printDebug("Set the script directory from " + this.scriptDir);
         }

         if (this.scriptMode) {
            if (h == null) {
               h = new Hashtable();
            }

            h.put("enableScriptMode", new Boolean(true));
         }

         if (this.onlineOnlyMode) {
            if (h == null) {
               h = new Hashtable();
            }

            h.put("onlineOnlyMode", new Boolean(true));
         }

         if (this.disablePluginJarLoadingMode != null) {
            if (h == null) {
               h = new Hashtable();
            }

            h.put("disablePluginJarLoadingMode", this.disablePluginJarLoadingMode);
         }

         WLSTInterpreter interp = new WLSTInterpreter(h);
         if (this.propertiesFile != null) {
            WLSTUtil.setProperties(this.propertiesFile, interp);
            this.printDebug("Loaded and set the properties from " + this.propertiesFile);
         }

         PyList sysArgs = new PyList();
         if (this.fileName != null) {
            sysArgs.append(new PyString(this.fileName));
         }

         if (this.arguments != null) {
            String[] args;
            if (this.arguments.indexOf("\"") == -1 && this.arguments.indexOf("'") == -1) {
               args = StringUtils.splitCompletely(this.arguments, " ");
            } else {
               args = this.splitQuotedString(this.arguments);
            }

            for(int i = 0; i < args.length; ++i) {
               this.printDebug("Adding " + args[i] + " to sys.argv");
               sysArgs.append(new PyString(args[i].trim()));
            }

            interp.exec("sys.argv=" + sysArgs);
            this.printDebug("sys.argv is " + sysArgs);
         }

         File f;
         if (this.fileName != null) {
            f = new File(this.fileName);
            if (!f.exists()) {
               String error = this.txtFmt.getErrorFileNotExist(this.fileName);
               this.printError(error);
            }
         }

         if (this.executeScriptBeforeFile) {
            if (this.scriptTempFile != null) {
               f = new File(this.scriptTempFile);
               interp.execfile(f.getPath());
            }

            if (this.fileName != null) {
               f = new File(this.fileName);
               interp.execfile(f.getPath());
            }
         } else {
            if (this.fileName != null) {
               f = new File(this.fileName);
               interp.execfile(f.getPath());
            }

            if (this.scriptTempFile != null) {
               f = new File(this.scriptTempFile);
               interp.execfile(f.getPath());
            }
         }
      } catch (Exception var6) {
         this.printError(var6.toString());
      }

   }

   private String[] splitQuotedString(String arguments) {
      ArrayList arrayList = new ArrayList();
      String regex = "\"([^\"]*)\"|'([^']*)'|(\\S+)";
      Matcher m = Pattern.compile(regex).matcher(arguments);

      while(m.find()) {
         if (m.group(1) != null) {
            arrayList.add(m.group(1));
         } else if (m.group(2) != null) {
            arrayList.add(m.group(2));
         } else {
            arrayList.add(m.group(3));
         }
      }

      return (String[])((String[])arrayList.toArray(new String[arrayList.size()]));
   }

   private void printError(String error) {
      System.out.println(error);
      if (this.failOnError) {
         throw new IllegalStateException(error);
      }
   }

   private void printDebug(String s) {
      if (this.debug) {
         System.out.println("<WLSTTask> " + s);
      }

   }

   static {
      String OS = System.getProperty("os.name").toLowerCase();
      IS_WINDOWS = OS.indexOf("win") >= 0;
   }
}
