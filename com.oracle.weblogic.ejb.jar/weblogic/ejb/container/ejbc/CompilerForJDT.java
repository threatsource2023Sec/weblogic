package weblogic.ejb.container.ejbc;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.utils.compiler.JavaCompilerDiagnostic;
import weblogic.utils.compiler.JavaCompilerDiagnostics;
import weblogic.utils.compiler.JavaCompilerUtils;

public class CompilerForJDT {
   private final String outputRoot;
   private final String classPath;
   private String result;

   public CompilerForJDT(String outputRoot, String classPath) {
      this.outputRoot = outputRoot;
      this.classPath = classPath;
   }

   public void compile(Map nameToSource) throws IOException {
      String[] classNames = (String[])nameToSource.keySet().toArray(new String[nameToSource.size()]);
      String[] fileNames = new String[classNames.length];
      char[][] contents = new char[classNames.length][];

      for(int i = 0; i < classNames.length; ++i) {
         String clsName = classNames[i];
         fileNames[i] = clsName.substring(clsName.lastIndexOf(46) + 1) + ".java";
         contents[i] = ((String)nameToSource.get(clsName)).toCharArray();
      }

      JavaCompilerDiagnostics outputResult = JavaCompilerUtils.compile(true, this.classPath, this.outputRoot, fileNames, classNames, contents, false);
      List diagnoticList = outputResult.getDisgnotics();
      StringBuilder sb = new StringBuilder();

      JavaCompilerDiagnostic diagnotic;
      for(Iterator var8 = diagnoticList.iterator(); var8.hasNext(); sb.append(diagnotic.getOriginatingFileName()).append(": ").append(diagnotic.getMessage())) {
         diagnotic = (JavaCompilerDiagnostic)var8.next();
         sb.append("\n");
         if (diagnotic.isError()) {
            sb.append("<Compilation Error> ");
         } else {
            sb.append("<Compilation Warn> ");
         }
      }

      this.result = sb.toString();
      if (outputResult.getHasError()) {
         throw new IOException("JDT compilation error!" + this.result);
      }
   }

   public String getCompilerErrors() {
      return this.result;
   }
}
