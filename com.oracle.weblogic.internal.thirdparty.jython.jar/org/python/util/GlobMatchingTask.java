package org.python.util;

import java.io.File;
import java.util.Set;
import org.python.apache.tools.ant.BuildException;
import org.python.apache.tools.ant.taskdefs.MatchingTask;
import org.python.apache.tools.ant.types.Path;
import org.python.apache.tools.ant.util.GlobPatternMapper;
import org.python.apache.tools.ant.util.SourceFileScanner;

public abstract class GlobMatchingTask extends MatchingTask {
   private Path src;
   protected File destDir;
   private Set toExpose = Generic.set();

   public void setSrcdir(Path srcDir) {
      if (this.src == null) {
         this.src = srcDir;
      } else {
         this.src.append(srcDir);
      }

   }

   public Path getSrcdir() {
      return this.src;
   }

   public void setDestdir(File destDir) {
      this.destDir = destDir;
   }

   public File getDestdir() {
      return this.destDir;
   }

   public void execute() throws BuildException {
      this.checkParameters();
      this.toExpose.clear();
      String[] var1 = this.src.list();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String srcEntry = var1[var3];
         File srcDir = this.getProject().resolveFile(srcEntry);
         if (!srcDir.exists()) {
            throw new BuildException("srcdir '" + srcDir.getPath() + "' does not exist!", this.getLocation());
         }

         String[] files = this.getDirectoryScanner(srcDir).getIncludedFiles();
         this.scanDir(srcDir, this.destDir != null ? this.destDir : srcDir, files);
      }

      this.process(this.toExpose);
   }

   protected abstract void process(Set var1);

   protected abstract String getFrom();

   protected abstract String getTo();

   protected void scanDir(File srcDir, File destDir, String[] files) {
      GlobPatternMapper m = new GlobPatternMapper();
      m.setFrom(this.getFrom());
      m.setTo(this.getTo());
      SourceFileScanner sfs = new SourceFileScanner(this);
      File[] var6 = sfs.restrictAsFiles(files, srcDir, destDir, m);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         File file = var6[var8];
         this.toExpose.add(file);
      }

   }

   protected void checkParameters() throws BuildException {
      if (this.src != null && this.src.size() != 0) {
         if (this.destDir != null && !this.destDir.isDirectory()) {
            throw new BuildException("destination directory '" + this.destDir + "' does not exist " + "or is not a directory", this.getLocation());
         }
      } else {
         throw new BuildException("srcdir attribute must be set!", this.getLocation());
      }
   }
}
