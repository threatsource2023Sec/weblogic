package weblogic.utils.compiler.jdt;

import java.util.HashMap;
import java.util.Map;
import weblogic.utils.compiler.AbstractJavaCompilerOptions;

public class JDTCompilerOptions extends AbstractJavaCompilerOptions {
   private static Map defaultOptions = new HashMap();
   private Map customizedOptions;

   public JDTCompilerOptions() {
   }

   public JDTCompilerOptions(boolean withDebug) {
      super(withDebug);
   }

   public JDTCompilerOptions(Map customizedOptions) {
      this.customizedOptions = customizedOptions;
   }

   public Map getDefaultOptions() {
      return defaultOptions;
   }

   public void add(String optionName, String optionValue) {
      if (this.customizedOptions != null) {
         this.customizedOptions.put(optionName, optionValue);
      }
   }

   public Map getOptionsMap() {
      if (this.useDefault) {
         return defaultOptions;
      } else {
         Map ret = new HashMap();
         if (this.customizedOptions == null) {
            if (this.withDebugInfo) {
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
            }

            ret.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "ignore");
            ret.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
            ret.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.6");
            ret.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.6");
            return ret;
         } else {
            String debug = (String)this.customizedOptions.get("debug");
            if (debug != null && Boolean.valueOf(debug)) {
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
               ret.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
            }

            ret.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "ignore");
            String version = (String)this.customizedOptions.get("target");
            if (version.equals("1.6")) {
               ret.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
            }

            version = (String)this.customizedOptions.get("source");
            if (version.equals("1.6")) {
               ret.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.6");
               ret.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.6");
            }

            return ret;
         }
      }
   }

   static {
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", "generate");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", "generate");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", "generate");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "ignore");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", "1.6");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.source", "1.6");
      defaultOptions.put("com.bea.core.repackaged.jdt.core.compiler.compliance", "1.6");
   }
}
