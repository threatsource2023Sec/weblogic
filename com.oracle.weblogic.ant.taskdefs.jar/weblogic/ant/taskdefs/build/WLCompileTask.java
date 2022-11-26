package weblogic.ant.taskdefs.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.selectors.FileSelector;
import weblogic.ant.taskdefs.utils.AntLibraryUtils;
import weblogic.ant.taskdefs.utils.LibraryElement;
import weblogic.application.SplitDirectoryUtils;
import weblogic.application.library.LibraryInitializer;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryReferenceFactory;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryUtils;

public final class WLCompileTask extends MatchingTask {
   public static final File libraryTmpDir = new File(System.getProperty("java.io.tmpdir"), "wlcmp_libraries");
   private File srcdir;
   private File destdir;
   private File libdir;
   private Path compileClasspath;
   private Javac javacTask;
   private Collection libraries;
   private LibraryInitializer libraryInitializer;

   public WLCompileTask() {
      this.compileClasspath = (Path)Path.systemClasspath.clone();
      this.libraries = new ArrayList();
      this.libraryInitializer = null;
   }

   public void addConfiguredJavac(Javac javacTask) {
      this.javacTask = javacTask;
   }

   public void setClasspath(Path classpath) {
      this.compileClasspath.append(classpath);
   }

   public Path createClasspath() {
      return this.compileClasspath.createPath();
   }

   public void setSrcdir(File srcdir) {
      this.srcdir = srcdir;
   }

   public void setDestdir(File destdir) {
      this.destdir = destdir;
   }

   public void setlibraryDir(File libdir) {
      this.libdir = libdir;
   }

   public void addConfiguredLibrary(LibraryElement library) {
      this.libraries.add(library);
   }

   private void checkSrcdir() throws BuildException {
      if (this.srcdir == null) {
         throw new BuildException("srcdir must be set");
      } else if (!this.srcdir.exists()) {
         throw new BuildException("srcdir: " + this.srcdir.getAbsolutePath() + " does not exist or cannot be read.");
      } else if (!this.srcdir.isDirectory()) {
         throw new BuildException("srcdir: " + this.srcdir.getAbsolutePath() + " is not a directory.");
      }
   }

   private void checkDestdir() throws BuildException {
      if (this.destdir == null) {
         throw new BuildException("destdir must be set");
      } else {
         if (this.destdir.exists()) {
            if (!this.destdir.isDirectory()) {
               throw new BuildException("destdir: " + this.destdir.getAbsolutePath() + " is not a directory.");
            }
         } else if (!this.destdir.mkdirs()) {
            throw new BuildException("destdir " + this.destdir.getAbsolutePath() + " does not exist, and we were unable to create it.");
         }

      }
   }

   private void checkParameters() throws BuildException {
      this.checkSrcdir();
      this.checkDestdir();
      AntLibraryUtils.validateLibraries(this.libdir, this.libraries);
   }

   public void execute() throws BuildException {
      Thread th = Thread.currentThread();
      ClassLoader cl = th.getContextClassLoader();
      ClassLoader antLoader = this.getClass().getClassLoader();
      if (cl != this.getClass().getClassLoader() && antLoader instanceof AntClassLoader) {
         this.setClasspath(new Path(this.project, ((AntClassLoader)antLoader).getClasspath()));
      }

      try {
         th.setContextClassLoader(antLoader);
         this.privateExecute();
      } finally {
         th.setContextClassLoader(cl);
         if (this.libraryInitializer != null) {
            this.libraryInitializer.cleanup();
         }

      }

   }

   private void privateExecute() throws BuildException {
      this.log("Executing WLCompileTask", 3);
      this.fileset.setDir(this.srcdir);
      this.fileset.appendSelector(new BaseDirSelector());
      this.checkParameters();
      this.log("srcdir: " + this.srcdir.getAbsolutePath(), 3);
      this.log("destdir: " + this.destdir.getAbsolutePath(), 3);
      BuildCtx ctx = new BuildCtx();
      ctx.setProject(this.project);
      ctx.setSrcDir(this.srcdir);
      ctx.setDestDir(this.destdir);
      ctx.setJavacTask(this.javacTask);
      this.handleLibraries(ctx);
      Application app = ApplicationFactory.newApplication(ctx);
      app.build(this.compileClasspath, this.buildDirSet(this.fileset));

      try {
         SplitDirectoryUtils.generatePropFile(this.srcdir, this.destdir);
      } catch (IOException var4) {
         var4.printStackTrace();
         throw new BuildException("Error generating build properties file", var4);
      }
   }

   private Set buildDirSet(FileSet fileset) throws BuildException {
      DirectoryScanner ds = fileset.getDirectoryScanner(this.project);
      String[] s = ds.getIncludedDirectories();
      if (s != null && s.length != 0) {
         HashSet includeDirs = new HashSet();

         for(int i = 0; i < s.length; ++i) {
            includeDirs.add(new File(this.srcdir, s[i]));
         }

         return includeDirs;
      } else {
         throw new BuildException("No modules were found to build.  Please ensure that your srcdir: " + this.srcdir.getAbsolutePath() + " is not empty, and your excludes or includes parameters do not eliminate all directories.");
      }
   }

   private void handleLibraries(BuildCtx ctx) {
      if (this.libdir != null || !this.libraries.isEmpty()) {
         try {
            this.libraryInitializer = new LibraryInitializer(libraryTmpDir, LibClasspathTask.getDumbLibraryApplicationFactoryManager());
            File[] libdirs = null;
            if (this.libdir != null) {
               libdirs = new File[]{this.libdir};
            }

            AntLibraryUtils.registerLibraries(this.libraryInitializer, libdirs, (LibraryElement[])((LibraryElement[])this.libraries.toArray(new LibraryElement[this.libraries.size()])), false);
            this.libraryInitializer.initRegisteredLibraries();
            LibraryManager libraryManager = new LibraryManager(LibraryUtils.initAppReferencer(), "DOMAIN", LibraryReferenceFactory.getAppLibReference((String)null));
            LibraryUtils.importAppLibraries(libraryManager, ctx, ctx);
            this.addLibsToClasspath(ctx);
         } catch (LoggableLibraryProcessingException var4) {
            throw new BuildException(var4.getLoggable().getMessage());
         }
      }
   }

   private void addLibsToClasspath(BuildCtx ctx) {
      String cp = AntLibraryUtils.getClassPath(ctx.getLibraryFiles(), ctx.getLibraryClassFinder());
      ctx.getLibraryClassFinder().close();
      this.setClasspath(new Path(this.project, cp));
   }

   private static class BaseDirSelector implements FileSelector {
      private BaseDirSelector() {
      }

      public boolean isSelected(File basedir, String filename, File file) {
         return file.isDirectory() && file.getParentFile().equals(basedir);
      }

      public boolean isSelected(Resource resource) {
         return resource.isDirectory();
      }

      // $FF: synthetic method
      BaseDirSelector(Object x0) {
         this();
      }
   }
}
