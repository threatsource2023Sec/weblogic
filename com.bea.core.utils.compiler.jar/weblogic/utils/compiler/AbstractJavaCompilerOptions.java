package weblogic.utils.compiler;

import java.util.Map;

public abstract class AbstractJavaCompilerOptions implements JavaCompilerOptions {
   protected boolean useDefault = false;
   protected boolean withDebugInfo = true;

   public AbstractJavaCompilerOptions() {
      this.useDefault = true;
   }

   public AbstractJavaCompilerOptions(boolean withDebug) {
      this.withDebugInfo = withDebug;
   }

   public boolean getUseDefaultOptions() {
      return this.useDefault;
   }

   public abstract void add(String var1, String var2);

   public abstract Map getOptionsMap();
}
