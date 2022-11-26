package weblogic.elasticity.interceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.util.ElasticityUtils;
import weblogic.elasticity.util.ScriptPathValidator;
import weblogic.management.configuration.ScriptMBean;

public class ScriptExecutor {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugInterceptors");
   public static final String ACTION_NAME = "ScriptExecutor";
   public static final String EXIT_VALUE_KEY = "exit-value";
   public static final String EXCEPTION_KEY = "exception";
   private Throwable th;
   private volatile Process proc;
   private volatile int exitCode;
   private final ScriptMBean scriptMBean;
   private final Properties outputProperties;
   private final boolean handleError;

   public ScriptExecutor(ScriptMBean scriptMBean) {
      this(scriptMBean, false);
   }

   public ScriptExecutor(ScriptMBean scriptMBean, boolean handleError) {
      this.exitCode = -1;
      this.outputProperties = new Properties();
      this.scriptMBean = scriptMBean;
      this.handleError = handleError;
   }

   private String[] buildCommand(ScriptMBean scriptMBean) {
      String path = this.handleError ? ScriptPathValidator.buildScriptPath(scriptMBean.getPathToErrorHandlerScript()) : ScriptPathValidator.buildScriptPath(scriptMBean.getPathToScript());
      if (path != null && path.trim().length() != 0) {
         String[] args = scriptMBean.getArguments();
         int argCnt = args != null ? args.length : 0;
         String[] command = new String[argCnt + 1];
         command[0] = path;

         for(int i = 0; i < argCnt; ++i) {
            command[i + 1] = args[i];
         }

         return command;
      } else {
         return null;
      }
   }

   public void execute(Properties inputProperties) {
      if (this.scriptMBean == null) {
         throw new IllegalArgumentException("Invalid scriptMBean: " + this.scriptMBean);
      } else {
         String[] command = this.buildCommand(this.scriptMBean);
         if (command != null) {
            ProcessBuilder pb = new ProcessBuilder(new String[0]);
            if (this.scriptMBean.getWorkingDirectory() != null) {
               pb.directory(new File(this.scriptMBean.getWorkingDirectory()));
            }

            pb.command(command);
            ElasticityUtils.initScriptProcessEnvironment(pb);
            if (this.scriptMBean.getEnvironment() != null) {
               Iterator var4 = this.scriptMBean.getEnvironment().entrySet().iterator();

               while(var4.hasNext()) {
                  Map.Entry e = (Map.Entry)var4.next();
                  pb.environment().put(e.getKey().toString(), e.getValue().toString());
               }
            }

            try {
               this.proc = pb.start();
               if (inputProperties != null) {
               }

               BufferedReader br = new BufferedReader(new InputStreamReader(this.proc.getInputStream()));
               Throwable var19 = null;

               try {
                  for(String line = br.readLine(); line != null; line = br.readLine()) {
                  }
               } catch (Throwable var15) {
                  var19 = var15;
                  throw var15;
               } finally {
                  if (br != null) {
                     if (var19 != null) {
                        try {
                           br.close();
                        } catch (Throwable var14) {
                           var19.addSuppressed(var14);
                        }
                     } else {
                        br.close();
                     }
                  }

               }

               this.exitCode = this.proc.waitFor();
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("**>> " + command[0] + " exited with exit code: " + this.exitCode);
               }
            } catch (Exception var17) {
               this.th = var17;
            }

         }
      }
   }

   public void cancel() {
      try {
         this.proc.destroy();
         this.proc.waitFor();
      } catch (Exception var2) {
      }

   }

   public int getExitCode() {
      return this.exitCode;
   }

   public Properties getOutputProperties() {
      return this.outputProperties;
   }
}
