package weblogic.ant.taskdefs.antline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.Task;
import weblogic.utils.Debug;
import weblogic.utils.Getopt2;

public class AntTool {
   static boolean debug = System.getProperty(DEBUG_PROPERTY()) != null;
   private Class antTask;
   protected Getopt2 cmdOpts = new Getopt2();
   private Map antOpts = new HashMap();
   private List antArgs = new ArrayList();
   private int optCount = 0;

   static String DEBUG_PROPERTY() {
      return "anttool.debug";
   }

   public AntTool(String taskName) throws AntLineException {
      this.antTask = loadClass(taskName, Task.class);
      this.cmdOpts.setFailOnUnrecognizedOpts(true);
   }

   public void addOption(String optName, String antName, String arg, String desc, ArgConverter converter) {
      if (debug) {
         Debug.assertion(antName != null);
         Debug.assertion(antName.length() > 0);
      }

      if (optName == null) {
         this.antArgs.add(new AntOpt(antName, converter));
      } else {
         this.antOpts.put(optName, new AntOpt(antName, converter));
         if (arg != null) {
            this.cmdOpts.addOption(optName, arg, desc);
         } else {
            this.cmdOpts.addFlag(optName, desc);
         }
      }

   }

   public void run(String[] args) throws Exception {
      this.cmdOpts.grok(args);
      Iterator i = this.antOpts.keySet().iterator();

      while(i.hasNext()) {
         String cmdOpt = (String)i.next();
         String optVal = this.cmdOpts.getOption(cmdOpt);
         if (optVal != null) {
            AntOpt antOpt = (AntOpt)this.antOpts.get(cmdOpt);
            antOpt.setValue(optVal);
         }
      }

      String[] cmdArgs = this.cmdOpts.args();

      for(int i = 0; i < cmdArgs.length; ++i) {
         ((AntOpt)this.antArgs.get(i)).setValue(cmdArgs[i]);
      }

      List taskParams = new ArrayList(this.antArgs);
      taskParams.addAll(this.antOpts.values());
      AntLauncher launcher = new AntLauncher(this.antTask, taskParams);
      launcher.launch();
   }

   public void usageAndExit(String progName) {
      this.cmdOpts.usageAndExit(progName);
   }

   protected static Class loadClass(String className, Class superClassOrInterface) throws AntLineException {
      Class clazz = null;

      try {
         clazz = Class.forName(className);
      } catch (Exception var4) {
         throw new AntLineException("Could not load class " + className + ". Is it in the classpath?");
      }

      if (superClassOrInterface != null && !superClassOrInterface.isAssignableFrom(clazz)) {
         throw new AntLineException(className + " must be implement/extend " + superClassOrInterface.getName());
      } else {
         return clazz;
      }
   }
}
