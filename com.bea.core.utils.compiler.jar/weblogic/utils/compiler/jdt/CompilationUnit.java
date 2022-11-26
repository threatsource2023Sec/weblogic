package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import java.util.StringTokenizer;

class CompilationUnit implements ICompilationUnit {
   String className;
   String sourceFile;
   char[] content;

   CompilationUnit(String sourceFile, String className, char[] content) {
      this.className = className;
      this.sourceFile = sourceFile;
      this.content = content;
   }

   public char[] getFileName() {
      return this.sourceFile.toCharArray();
   }

   public char[] getContents() {
      return this.content;
   }

   public char[] getMainTypeName() {
      int dot = this.className.lastIndexOf(46);
      return dot > 0 ? this.className.substring(dot + 1).toCharArray() : this.className.toCharArray();
   }

   public char[][] getPackageName() {
      StringTokenizer izer = new StringTokenizer(this.className, ".");
      char[][] result = new char[izer.countTokens() - 1][];

      for(int i = 0; i < result.length; ++i) {
         String tok = izer.nextToken();
         result[i] = tok.toCharArray();
      }

      return result;
   }

   public boolean ignoreOptionalProblems() {
      return false;
   }
}
