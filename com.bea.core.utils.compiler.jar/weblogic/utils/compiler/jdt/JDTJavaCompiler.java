package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.Compiler;
import com.bea.core.repackaged.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import com.bea.core.repackaged.jdt.internal.compiler.ICompilerRequestor;
import com.bea.core.repackaged.jdt.internal.compiler.IErrorHandlingPolicy;
import com.bea.core.repackaged.jdt.internal.compiler.IProblemFactory;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblemFactory;
import java.util.Locale;
import weblogic.utils.compiler.JavaCompilationContext;
import weblogic.utils.compiler.JavaCompiler;
import weblogic.utils.compiler.JavaCompilerDiagnostics;
import weblogic.utils.compiler.JavaCompilerOptions;

public class JDTJavaCompiler implements JavaCompiler {
   public JavaCompilerDiagnostics compile(JavaCompilationContext context, JavaCompilerOptions options) {
      INameEnvironment nameEnviron = null;
      if (context.getClassPath() != null) {
         String[] initialFileNames = new String[0];
         nameEnviron = JDTJavaCompilerFactory.getInstance().createFileSystem(context.getClassPath(), initialFileNames, (String)null);
      } else {
         nameEnviron = ((JDTCompilationContext)context).getFileSystem();
      }

      IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies.proceedWithAllProblems();
      ICompilerRequestor requestor = new CompilerRequestor(context.getOutputRoot(), context.getSmap(), context.getReportWaning());
      IProblemFactory problemFactory = new DefaultProblemFactory(Locale.getDefault());
      ICompilationUnit[] compilationUnits = new ICompilationUnit[context.getFileNames().length];
      String[] fileNames = context.getFileNames();
      String[] classNames = context.getClassNames();
      char[][] contents = context.getContents();

      for(int i = 0; i < fileNames.length; ++i) {
         compilationUnits[i] = new CompilationUnit(fileNames[i], classNames[i], contents[i]);
      }

      CompilerOptions compilerOptions = new CompilerOptions(options.getOptionsMap());
      compilerOptions.parseLiteralExpressionsAsConstants = true;
      Compiler compiler = new Compiler(nameEnviron, policy, compilerOptions, requestor, problemFactory);
      compiler.compile(compilationUnits);
      return new JavaCompilerDiagnostics(((CompilerRequestor)requestor).getProblems(), ((CompilerRequestor)requestor).isResultHasError());
   }
}
