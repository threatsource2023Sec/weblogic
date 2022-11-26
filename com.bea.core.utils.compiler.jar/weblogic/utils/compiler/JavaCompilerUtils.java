package weblogic.utils.compiler;

import com.bea.core.repackaged.jdt.internal.compiler.batch.FileSystem;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.impl.DoubleConstant;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.utils.PlatformConstants;
import weblogic.utils.compiler.jdt.GeneratedClassesDirectory;
import weblogic.utils.compiler.jdt.JDTJavaCompilerFactory;
import weblogic.utils.compiler.jdt.JSPFileSystem;

public class JavaCompilerUtils {
   private static final String SUFFIX_STRING_jar = ".jar";
   private static final String SUFFIX_STRING_zip = ".zip";
   private static final String SUFFIX_STRING_rar = ".rar";

   public static String[] splitClassPath(String classPath) {
      StringTokenizer token = new StringTokenizer(classPath, PlatformConstants.PATH_SEP);
      Set classpathes = new LinkedHashSet();

      while(true) {
         while(true) {
            String fileUnit;
            File file;
            do {
               if (!token.hasMoreTokens()) {
                  String[] results = (String[])classpathes.toArray(new String[0]);
                  return results;
               }

               fileUnit = token.nextToken();
               file = new File(fileUnit);
            } while(!file.exists());

            if (file.isDirectory()) {
               classpathes.add(fileUnit);
            } else {
               String lowercaseClasspathName = fileUnit.toLowerCase();
               if (lowercaseClasspathName.endsWith(".jar") || lowercaseClasspathName.endsWith(".zip") || lowercaseClasspathName.endsWith(".rar")) {
                  classpathes.add(fileUnit);
               }
            }
         }
      }
   }

   public static INameEnvironment createJSPFileSystem(String classpath, String outputRoot, String packagePrefix) {
      return new JSPFileSystem(new GeneratedClassesDirectory(new File(outputRoot), packagePrefix), new FileSystem(splitClassPath(classpath), new String[0], (String)null));
   }

   public static JavaCompilerDiagnostics compile(boolean useJDT, String classpath, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, boolean reportWarning) {
      JavaCompilerFactory factory = JDTJavaCompilerFactory.getInstance();
      JavaCompiler compiler = factory.createCompiler();
      JavaCompilationContext context = factory.createCompliationContext(classpath, outputRoot, fileNames, classNames, contents, reportWarning);
      JavaCompilerOptions options = factory.createOptions();
      return compiler.compile(context, options);
   }

   public static JavaCompilerDiagnostics compile(boolean useJDT, INameEnvironment fileSystem, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, String smap, boolean reportWarning) {
      JavaCompilerFactory factory = JDTJavaCompilerFactory.getInstance();
      JavaCompiler compiler = factory.createCompiler();
      JavaCompilationContext context = factory.createCompliationContext(fileSystem, outputRoot, fileNames, classNames, contents, smap, reportWarning);
      JavaCompilerOptions options = factory.createOptions();
      return compiler.compile(context, options);
   }

   static {
      DoubleConstant.fromValue(0.0);
   }
}
