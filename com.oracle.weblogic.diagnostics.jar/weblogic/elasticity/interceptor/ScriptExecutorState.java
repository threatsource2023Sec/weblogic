package weblogic.elasticity.interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ScriptExecutorState implements Serializable {
   private String scriptMBeanName;
   private String workingDirectory;
   private boolean isScaleUp;
   private String dynamicClusterName;
   private boolean isPreProcessor;
   private String pathToScript;
   private String[] arguments = new String[0];
   private Map environment = new HashMap();
   private boolean ignoreFailures;
   private int numberOfRetriesAllowed;
   private long retryDelayInMillis;
   private String pathToErrorHandlerScript;
   private int timeoutInSeconds;

   public String getWorkingDirectory() {
      return this.workingDirectory;
   }

   public void setWorkingDirectory(String workingDirectory) {
      this.workingDirectory = workingDirectory;
   }

   public boolean isScaleUp() {
      return this.isScaleUp;
   }

   public void setIsScaleUp(boolean isScaleUp) {
      this.isScaleUp = isScaleUp;
   }

   public String getDynamicClusterName() {
      return this.dynamicClusterName;
   }

   public void setDynamicClusterName(String dynamicClusterName) {
      this.dynamicClusterName = dynamicClusterName;
   }

   public boolean isPreProcessor() {
      return this.isPreProcessor;
   }

   public void setIsPreProcessor(boolean isPreProcessor) {
      this.isPreProcessor = isPreProcessor;
   }

   public String getPathToScript() {
      return this.pathToScript;
   }

   public void setPathToScript(String pathToScript) {
      this.pathToScript = pathToScript;
   }

   public String[] getArguments() {
      return this.arguments;
   }

   public void setArguments(String[] arguments) {
      this.arguments = arguments;
   }

   public Map getEnvironment() {
      return this.environment;
   }

   public String getScriptMBeanName() {
      return this.scriptMBeanName;
   }

   public void setScriptMBeanName(String scriptMBeanName) {
      this.scriptMBeanName = scriptMBeanName;
   }

   public void setEnvironment(Properties props) {
      this.environment = new HashMap();
      if (props != null) {
         Iterator var2 = props.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry e = (Map.Entry)var2.next();
            this.environment.put(e.getKey().toString(), e.getValue().toString());
         }
      }

   }

   public boolean isIgnoreFailures() {
      return this.ignoreFailures;
   }

   public void setIgnoreFailures(boolean ignoreFailures) {
      this.ignoreFailures = ignoreFailures;
   }

   public int getNumberOfRetriesAllowed() {
      return this.numberOfRetriesAllowed;
   }

   public void setNumberOfRetriesAllowed(int numberOfRetriesAllowed) {
      this.numberOfRetriesAllowed = numberOfRetriesAllowed;
   }

   public long getRetryDelayInMillis() {
      return this.retryDelayInMillis;
   }

   public void setRetryDelayInMillis(long retryDelayInMillis) {
      this.retryDelayInMillis = retryDelayInMillis;
   }

   public String getPathToErrorHandlerScript() {
      return this.pathToErrorHandlerScript;
   }

   public void setPathToErrorHandlerScript(String pathToErrorHandlerScript) {
      this.pathToErrorHandlerScript = pathToErrorHandlerScript;
   }

   public int getTimeoutInSeconds() {
      return this.timeoutInSeconds;
   }

   public void setTimeoutInSeconds(int timeoutSeconds) {
      this.timeoutInSeconds = timeoutSeconds;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("\n*************** ScriptExecutorCommand ***************");
      sb.append("\nscriptMBean name        : ").append(this.getScriptMBeanName());
      sb.append("\nmain script             : ").append(this.getPathToScript());
      sb.append("\nerror handler           : ").append(this.getPathToErrorHandlerScript());
      sb.append("\nargs                    : ");
      String prefix = "";
      String[] var3 = this.getArguments();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String s = var3[var5];
         sb.append(prefix).append(s);
         prefix = ", ";
      }

      sb.append("\nworking directory       : ").append(this.getWorkingDirectory());
      sb.append("\nisScaleUp               : ").append(this.isScaleUp());
      sb.append("\ndynamicClusterName      : ").append(this.getDynamicClusterName());
      sb.append("\nisPreProcessor          : ").append(this.isPreProcessor());
      sb.append("\nisIgnoreFailures        : ").append(this.isIgnoreFailures());
      sb.append("\nnum retries             : ").append(this.getNumberOfRetriesAllowed());
      sb.append("\nretry delay (in millis) : ").append(this.getRetryDelayInMillis());
      sb.append("\ntimeout (seconds)       : ").append(this.getTimeoutInSeconds());
      sb.append("\nenvironment             : ");
      Iterator var7 = this.getEnvironment().entrySet().iterator();

      while(var7.hasNext()) {
         Map.Entry e = (Map.Entry)var7.next();
         sb.append("\n\t").append(((String)e.getKey()).toString()).append(" :").append(((String)e.getValue()).toString());
      }

      return sb.toString();
   }
}
