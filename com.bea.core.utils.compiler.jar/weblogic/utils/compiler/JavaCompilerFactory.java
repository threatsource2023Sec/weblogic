package weblogic.utils.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import java.util.Map;

public interface JavaCompilerFactory {
   JavaCompiler createCompiler();

   JavaCompilerOptions createOptions();

   JavaCompilerOptions createOptions(boolean var1);

   JavaCompilerOptions createOptions(Map var1);

   JavaCompilationContext createCompliationContext();

   JavaCompilationContext createCompliationContext(String var1, String var2, String[] var3, String[] var4, char[][] var5, boolean var6);

   JavaCompilationContext createCompliationContext(INameEnvironment var1, String var2, String[] var3, String[] var4, char[][] var5, String var6, boolean var7);
}
