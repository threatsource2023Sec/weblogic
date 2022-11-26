package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.compiler.JavaCompilationContext;
import weblogic.utils.compiler.JavaCompiler;
import weblogic.utils.compiler.JavaCompilerFactory;
import weblogic.utils.compiler.JavaCompilerOptions;
import weblogic.utils.compiler.JavaCompilerUtils;

public class JDTJavaCompilerFactory implements JavaCompilerFactory {
   private final Map hashMap = new HashMap();
   private static JDTJavaCompilerFactory factoryInstance = new JDTJavaCompilerFactory();

   private JDTJavaCompilerFactory() {
   }

   public static JDTJavaCompilerFactory getInstance() {
      return factoryInstance;
   }

   public JavaCompiler createCompiler() {
      return new JDTJavaCompiler();
   }

   public INameEnvironment createFileSystem(String classpathNames, String[] initialFileNames, String encoding) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      synchronized(this.hashMap) {
         INameEnvironment nameEnviron = (INameEnvironment)this.hashMap.get(cl);
         if (nameEnviron == null) {
            nameEnviron = new EJBFileSystem(JavaCompilerUtils.splitClassPath(classpathNames), initialFileNames, (String)null);
            this.hashMap.put(cl, nameEnviron);
         }

         return (INameEnvironment)nameEnviron;
      }
   }

   public void resetCache(ClassLoader cl) {
      INameEnvironment nameEnviron;
      synchronized(this.hashMap) {
         nameEnviron = (INameEnvironment)this.hashMap.remove(cl);
      }

      if (nameEnviron != null) {
         nameEnviron.cleanup();
      }

   }

   public JavaCompilationContext createCompliationContext() {
      return new JDTCompilationContext();
   }

   public JavaCompilationContext createCompliationContext(String classpath, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, boolean reportWarning) {
      return new JDTCompilationContext(classpath, outputRoot, fileNames, classNames, contents, reportWarning);
   }

   public JavaCompilationContext createCompliationContext(INameEnvironment fileSystem, String outputRoot, String[] fileNames, String[] classNames, char[][] contents, String smap, boolean reportWarning) {
      return new JDTCompilationContext(fileSystem, outputRoot, fileNames, classNames, contents, smap, reportWarning);
   }

   public JavaCompilerOptions createOptions() {
      return new JDTCompilerOptions();
   }

   public JavaCompilerOptions createOptions(boolean withDebug) {
      return new JDTCompilerOptions(withDebug);
   }

   public JavaCompilerOptions createOptions(Map customizedOptions) {
      return new JDTCompilerOptions(customizedOptions);
   }
}
