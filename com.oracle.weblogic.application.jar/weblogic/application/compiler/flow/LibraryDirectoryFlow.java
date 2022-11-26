package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.utils.PersistenceUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.compiler.ToolFailureException;

public final class LibraryDirectoryFlow extends CompilerFlow {
   public LibraryDirectoryFlow(CompilerCtx appCtx) {
      super(appCtx);
   }

   public void compile() throws ToolFailureException {
      ApplicationBean appBean = this.ctx.getApplicationDD();
      String applib = appBean.getLibraryDirectory();
      if (this.processAppLib(applib)) {
         MultiClassFinder allJarFinders = new MultiClassFinder();

         try {
            File[] rootFiles = PersistenceUtils.getApplicationRoots(this.ctx.getAppClassLoader(), this.ctx.getApplicationId(), false);

            for(int i = rootFiles.length - 1; i >= 0; --i) {
               File libraryDirectory = new File(rootFiles[i], applib);
               if (libraryDirectory.isDirectory()) {
                  File[] files = libraryDirectory.listFiles();

                  for(int count = files.length - 1; count >= 0; --count) {
                     if (files[count].getName().endsWith(".jar")) {
                        allJarFinders.addFinderFirst(new JarClassFinder(files[count]));
                     }
                  }
               }
            }

            this.ctx.getAppClassLoader().addClassFinderFirst(allJarFinders);
            this.ctx.addAppAnnotationScanningClassPathFirst(allJarFinders.getClassPath());
         } catch (IOException var9) {
            throw new ToolFailureException("Unable process <library-directory> in application.xml", var9);
         }
      }

   }

   private boolean processAppLib(String applib) {
      return applib != null && !applib.trim().isEmpty();
   }

   public void cleanup() {
   }
}
