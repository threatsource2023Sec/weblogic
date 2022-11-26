package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBeanAdapter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import weblogic.diagnostics.descriptor.WLDFScriptActionBean;

public class ScriptActionConfig extends ActionConfigBeanAdapter {
   private String workingDirectory;
   private String pathToScript;
   private Map environment = new HashMap();
   private List parameters = new LinkedList();

   public ScriptActionConfig() {
      super("ScriptAction");
   }

   public ScriptActionConfig(WLDFScriptActionBean action) {
      super("ScriptAction");
      this.setName(action.getName());
      this.workingDirectory = action.getWorkingDirectory();
      this.pathToScript = action.getPathToScript();
      Properties envProps = action.getEnvironment();
      if (envProps != null) {
         Iterator var3 = envProps.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            this.environment.put((String)entry.getKey(), (String)entry.getValue());
         }
      }

      this.parameters = Arrays.asList(action.getParameters());
   }

   public String getWorkingDirectory() {
      return this.workingDirectory;
   }

   public void setWorkingDirectory(String workingDirectory) {
      this.workingDirectory = workingDirectory;
   }

   public String getPathToScript() {
      return this.pathToScript;
   }

   public void setPathToScript(String pathToScript) {
      this.pathToScript = pathToScript;
   }

   public Map getEnvironment() {
      return this.environment;
   }

   public void setEnvironment(Map environment) {
      this.environment = environment;
   }

   public List getParameters() {
      return this.parameters;
   }

   public void setParameters(List parameters) {
      this.parameters = parameters;
   }
}
