package weblogic.ejb.container.ejbc.javac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavacCompilerOptions {
   private List setting = new ArrayList();
   private Map options = new HashMap();

   JavacCompilerOptions() {
      this.setting.add("-nowarn");
      this.setting.add("-proc:none");
   }

   public void addOption(String optionName, String optionValue) {
      this.options.put(optionName, optionValue);
   }

   public void addOptions(Map options) {
      this.options.putAll(options);
   }

   public List getOptions() {
      String debug = (String)this.options.get("debug");
      if (debug != null && Boolean.getBoolean(debug)) {
         this.setting.add("-g");
      }

      String classPath = (String)this.options.get("classpath");
      if (classPath != null) {
         this.setting.add("-cp");
         this.setting.add(classPath);
      }

      String source = (String)this.options.get("source");
      if (source != null) {
         this.setting.add("-sourcepath");
         this.setting.add(source);
      }

      String target = (String)this.options.get("target");
      if (target != null) {
         this.setting.add("-target");
         this.setting.add(target);
      }

      String classDestinationDir = (String)this.options.get("d");
      if (classDestinationDir != null) {
         this.setting.add("-d");
         this.setting.add(classDestinationDir);
      }

      return this.setting;
   }
}
