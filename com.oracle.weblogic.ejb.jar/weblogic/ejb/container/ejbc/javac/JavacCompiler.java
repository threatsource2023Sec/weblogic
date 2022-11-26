package weblogic.ejb.container.ejbc.javac;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.Diagnostic.Kind;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;

public class JavacCompiler {
   private static final DebugLogger debugLogger;
   private final String outputRoot;
   private final String classPath;
   private String result;

   public JavacCompiler(String outputRoot, String classPath) {
      this.outputRoot = outputRoot;
      this.classPath = classPath;
   }

   public void compile(Map nameToSource) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debug("Compiling Bean Sources");
      }

      DiagnosticCollector diagnostics = new DiagnosticCollector();
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
      StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, (Locale)null, (Charset)null);
      JavaFileManager delegateManager = new ForwardingJavaFileManager(fileManager) {
      };
      String[] classNames = (String[])nameToSource.keySet().toArray(new String[nameToSource.size()]);
      JavaFileObject[] javaJavaFileObjects = new JavaFileObject[classNames.length];

      for(int i = 0; i < classNames.length; ++i) {
         String clsName = classNames[i];
         JavaFileObject javaFileObject = new InMemoryJavaFileObject(clsName, (String)nameToSource.get(clsName));
         javaJavaFileObjects[i] = javaFileObject;
      }

      JavacCompilerOptions options = new JavacCompilerOptions();
      options.addOption("classpath", this.classPath);
      options.addOption("d", this.outputRoot);
      boolean success = compiler.getTask((Writer)null, delegateManager, diagnostics, options.getOptions(), (Iterable)null, Arrays.asList(javaJavaFileObjects)).call();
      if (!success) {
         StringBuilder sb = new StringBuilder();

         Diagnostic diagnostic;
         for(Iterator var11 = diagnostics.getDiagnostics().iterator(); var11.hasNext(); sb.append(diagnostic.getSource()).append(": ").append(diagnostic.getMessage(Locale.getDefault()))) {
            diagnostic = (Diagnostic)var11.next();
            if (diagnostic.getKind() != Kind.ERROR) {
               sb.append("<Compilation Error> ");
            } else {
               sb.append("<Compilation Warn> ");
            }
         }

         this.result = sb.toString();
      }

      delegateManager.close();
   }

   public String getCompilerErrors() {
      return this.result;
   }

   private static void debug(String s) {
      debugLogger.debug("[JavacCompiler] " + s);
   }

   static {
      debugLogger = EJBDebugService.compilationLogger;
   }
}
