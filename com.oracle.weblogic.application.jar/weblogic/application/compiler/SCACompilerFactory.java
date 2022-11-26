package weblogic.application.compiler;

import java.io.IOException;
import java.util.List;
import weblogic.application.utils.IOUtils;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

final class SCACompilerFactory implements Factory {
   private String SCA_CONTRIBUTION_URI = "META-INF/sca-contribution.xml";

   public Boolean claim(CompilerCtx m) {
      if (m.getSourceFile().isFile() && !JarFileUtils.isJar(m.getSourceFile())) {
         return false;
      } else {
         VirtualJarFile composite = null;

         try {
            composite = VirtualJarFactory.createVirtualJar(m.getSourceFile());
            if (composite.getEntry(this.SCA_CONTRIBUTION_URI) != null) {
               Boolean var3 = true;
               return var3;
            }
         } catch (IOException var8) {
            Object var4 = null;
            return (Boolean)var4;
         } finally {
            IOUtils.forceClose(composite);
         }

         return null;
      }
   }

   public Boolean claim(CompilerCtx m, List allClaimants) {
      return this.claim(m);
   }

   public Merger create(CompilerCtx m) {
      return new SCAMerger(m);
   }
}
