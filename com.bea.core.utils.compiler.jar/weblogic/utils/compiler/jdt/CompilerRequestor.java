package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.IProblem;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.ICompilerRequestor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weblogic.utils.compiler.FileSystemUtil;
import weblogic.utils.compiler.WeblogicSmapUtil;

class CompilerRequestor implements ICompilerRequestor {
   private String outputRoot;
   private String smap;
   private List problems = new ArrayList();
   private boolean hasError = false;
   private boolean reportWarning;

   public CompilerRequestor(String outputRoot, String smap, boolean reportWarning) {
      this.outputRoot = outputRoot;
      this.smap = smap;
      this.reportWarning = reportWarning;
   }

   public void acceptResult(CompilationResult result) {
      int var4;
      int var5;
      if (result.hasProblems()) {
         IProblem[] resProblems = result.getProblems();
         CategorizedProblem[] var3 = resProblems;
         var4 = resProblems.length;

         for(var5 = 0; var5 < var4; ++var5) {
            IProblem iProblem = var3[var5];
            boolean isError = iProblem.isError();
            if (isError && !this.hasError) {
               this.hasError = true;
            }

            if (this.reportWarning || isError) {
               this.problems.add(new JDTDiagnosticAdapter(iProblem));
            }
         }
      }

      if (!this.hasError) {
         ClassFile[] classFiles = result.getClassFiles();
         ClassFile[] var12 = classFiles;
         var4 = classFiles.length;

         for(var5 = 0; var5 < var4; ++var5) {
            ClassFile classFile = var12[var5];
            String className = new String(CharOperation.concatWith(classFile.getCompoundName(), File.separatorChar));
            StringBuilder outputDir = new StringBuilder(this.outputRoot);
            outputDir.append(outputDir.charAt(outputDir.length() - 1) == File.separatorChar ? "" : File.separatorChar);
            outputDir.append(className);
            outputDir.append(".class");
            String outputFile = outputDir.toString();
            File parentDir = (new File(outputFile)).getParentFile();
            if (!parentDir.exists()) {
               FileSystemUtil.mkdirs(parentDir);
            }

            WeblogicSmapUtil.installSmap(classFile.getBytes(), this.smap, outputFile);
         }

      }
   }

   public List getProblems() {
      return this.problems;
   }

   public boolean isResultHasError() {
      return this.hasError;
   }
}
