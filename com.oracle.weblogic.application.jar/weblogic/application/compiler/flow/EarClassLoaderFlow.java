package weblogic.application.compiler.flow;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.ApplicationFileManager;
import weblogic.application.SplitDirectoryInfo;
import weblogic.application.compiler.AppcUtils;
import weblogic.application.compiler.CompilerCtx;
import weblogic.application.io.Ear;
import weblogic.application.io.JarCopyFilter;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.FileUtils;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class EarClassLoaderFlow extends CompilerFlow {
   private Ear ear = null;
   private static final File dummyExtractDir = new File(System.getProperty("java.io.tmpdir"), "_appc_tmp");

   public EarClassLoaderFlow(CompilerCtx ctx) {
      super(ctx);
   }

   public void compile() throws ToolFailureException {
      this.init();
   }

   private void init() throws ToolFailureException {
      File sourceFile = this.ctx.getSourceFile();
      File outputDir = this.ctx.getOutputDir();
      VirtualJarFile vjf = null;
      boolean srcSameAsOutput = sourceFile.equals(outputDir);
      File splitDirProps = EarUtils.getSplitDirProperties(sourceFile);
      boolean isSplitDir = splitDirProps.exists();
      SplitDirectoryInfo splitDirInfo = null;

      try {
         if (isSplitDir) {
            splitDirInfo = new SplitDirectoryInfo(sourceFile, splitDirProps);
         }

         if (isSplitDir && !srcSameAsOutput) {
            File[] roots = splitDirInfo.getRootDirectories();

            for(int i = 0; i < roots.length; ++i) {
               AppcUtils.expandJarFileIntoDirectory(roots[i], outputDir);
            }

            Map otherUris = splitDirInfo.getUriLinks();
            Iterator iter = otherUris.keySet().iterator();

            while(true) {
               if (!iter.hasNext()) {
                  EarUtils.getSplitDirProperties(outputDir).delete();
                  isSplitDir = false;
                  srcSameAsOutput = true;
                  break;
               }

               String uri = (String)iter.next();
               List actualFiles = (List)otherUris.get(uri);

               for(int count = actualFiles.size() - 1; count >= 0; --count) {
                  FileUtils.copyPreservePermissions((File)actualFiles.get(count), outputDir);
               }
            }
         }

         if (isSplitDir) {
            this.ctx.setSplitDir();
            vjf = VirtualJarFactory.createVirtualJar(splitDirInfo.getSrcDir(), splitDirInfo.getDestDir());
            this.ear = new Ear(sourceFile.getName(), dummyExtractDir, splitDirInfo, JarCopyFilter.NOCOPY_FILTER);
            this.ctx.setApplicationFileManager(ApplicationFileManager.newInstance(splitDirInfo));
            this.ctx.setSplitDirectoryInfo(splitDirInfo);
         } else {
            if (!srcSameAsOutput) {
               AppcUtils.expandJarFileIntoDirectory(sourceFile, outputDir);
            }

            vjf = VirtualJarFactory.createVirtualJar(outputDir);
            this.ear = new Ear(sourceFile.getName(), dummyExtractDir, new File[]{outputDir}, JarCopyFilter.NOCOPY_FILTER);
            this.ctx.setupApplicationFileManager(outputDir);
         }

         this.ctx.setVSource(this.ctx.getApplicationFileManager().getVirtualJarFile());
      } catch (IOException var14) {
         throw new ToolFailureException(J2EELogger.logAppcSourceFileNotAccessibleLoggable(sourceFile.getAbsolutePath(), var14.toString()).getMessage(), var14);
      }

      this.ctx.addAppAnnotationScanningClassPathFirst(this.ear.getClassFinder().getClassPath());
      this.ctx.setEar(this.ear);
   }

   public void cleanup() {
      this.ear.getClassFinder().close();
      IOUtils.forceClose(this.ctx.getVSource());
   }
}
