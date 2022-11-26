package weblogic.utils.compiler;

public interface JavaCompiler {
   String WITH_DEBUG_INFO = "debug";
   String SOURCE = "source";
   String TARGET = "target";
   String NO_WARNING = "nowarn";
   String CLASS_PATH = "classpath";
   String CLASS_DESTINATION_DIR = "d";
   String SOURCE_DESTINATION_DIR = "s";
   String PROC_NONE = "proc:none";
   String PROC_ONLY = "proc:only";
   String REPORT_DEPRECATION = "deprecation";

   JavaCompilerDiagnostics compile(JavaCompilationContext var1, JavaCompilerOptions var2);
}
