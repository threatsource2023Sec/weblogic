package weblogic.ant.taskdefs.build.module;

import java.io.File;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import weblogic.ant.taskdefs.build.BuildCtx;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.servlet.internal.StaleProber;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WarDefinition;
import weblogic.servlet.utils.WebAppLibraryUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class WebModule extends Module {
   private static final String WEBINF_CLASSES;
   private static final String WEBINF_SRC;
   private static final File tmpDir;
   private War war = null;

   public WebModule(BuildCtx ctx, File srcDir, File destDir) {
      super(ctx, srcDir, destDir);
   }

   public void addToClasspath(Path classpath) {
      try {
         VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(new File[]{this.srcDir, this.destDir});
         WarDefinition warDef = new WarDefinition();
         warDef.setUri(this.srcDir.getName());
         warDef.setVirtualJarFile(vjf);
         this.war = warDef.extract(tmpDir, (StaleProber)null);
         this.addLibraries(this.war);
         this.addToClasspath(classpath, this.war.getClassFinder().getClassPath());
      } catch (IOException var4) {
         throw new BuildException(var4);
      }
   }

   private void addLibraries(War war) throws IOException {
      LibraryManager mgr = WebAppLibraryUtils.getEmptyWebAppLibraryManager();
      mgr.lookup(LibraryReferenceFactory.getWebAppLibReference("DOMAIN"));
      WebAppLibraryUtils.addWebAppLibraries(mgr, war);
   }

   private void addToClasspath(Path classpath, String path) {
      Path.PathElement pe = classpath.createPathElement();
      pe.setPath(path);
   }

   public void build(Path classpath) {
      try {
         this.log("Compiling module: " + this.getClass().getName() + ": " + this.srcDir);
         this.destDir.mkdir();
         File webSrc = new File(this.srcDir, WEBINF_SRC);
         if (webSrc.exists() && webSrc.isDirectory()) {
            this.javac(classpath, webSrc, new File(this.destDir, WEBINF_CLASSES));
         }
      } finally {
         if (this.war != null) {
            this.war.getClassFinder().close();
            this.war.remove();
         }

      }

   }

   static {
      WEBINF_CLASSES = File.separatorChar + "WEB-INF" + File.separatorChar + "classes";
      WEBINF_SRC = File.separatorChar + "WEB-INF" + File.separatorChar + "src";
      tmpDir = new File(System.getProperty("java.io.tmpdir"), "wlcmp");
   }
}
