package weblogic.utils.compiler;

import java.util.List;

public class JavaCompilerDiagnostics implements JavaCompilingResult {
   private List diags;
   private boolean hasError;

   public JavaCompilerDiagnostics(List diags, boolean hasError) {
      this.diags = diags;
      this.hasError = hasError;
   }

   public List getDisgnotics() {
      return this.diags;
   }

   public boolean getHasError() {
      return this.hasError;
   }
}
