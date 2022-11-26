package weblogic.ant.taskdefs.j2ee;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import weblogic.ant.taskdefs.utils.AntLibraryUtils;
import weblogic.ant.taskdefs.utils.LibraryElement;
import weblogic.application.compiler.AppMerge;

public final class AppMergeTask extends MatchingTask {
   private String source = null;
   private String output = null;
   private String libdir = null;
   private String plan = null;
   private boolean verbose = false;
   private Collection libraries = new ArrayList();

   public void setSource(String source) {
      this.source = source;
   }

   public void setOutput(String output) {
      this.output = output;
   }

   public void setlibraryDir(String libdir) {
      this.libdir = libdir;
   }

   public void setPlan(String plan) {
      this.plan = plan;
   }

   public void addConfiguredLibrary(LibraryElement library) {
      this.libraries.add(library);
   }

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   public void execute() {
      List args = new ArrayList();
      File outputFile = null;
      if (this.output != null) {
         outputFile = this.project.resolveFile(this.output);
         args.add("-output");
         args.add(outputFile.getAbsolutePath());
      }

      File sourceFile = this.project.resolveFile(this.source);
      if (sourceFile == null) {
         throw new BuildException("Source must be specified");
      } else if (!sourceFile.exists()) {
         throw new BuildException("Source not found: " + sourceFile);
      } else if (outputFile != null && outputFile.isFile() && sourceFile.isFile() && outputFile.lastModified() > sourceFile.lastModified()) {
         this.log(outputFile + " is up to date", 3);
      } else {
         if (this.plan != null) {
            args.add("-plan");
            args.add(this.project.resolveFile(this.plan).getAbsolutePath());
         }

         if (this.verbose) {
            args.add("-verbose");
         }

         args.add("-noexit");
         File libdirFile = null;
         if (this.libdir != null) {
            libdirFile = this.project.resolveFile(this.libdir);
         }

         args.addAll(AntLibraryUtils.getLibraryFlags(libdirFile, this.libraries));
         args.add(sourceFile.getAbsolutePath());
         this.runAppMerge((String[])((String[])args.toArray(new String[args.size()])));
      }
   }

   private void runAppMerge(String[] args) {
      try {
         AppMerge.main(args);
      } catch (Exception var3) {
         throw new BuildException(var3);
      }
   }
}
