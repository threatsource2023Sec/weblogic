package com.bea.core.repackaged.springframework.cglib.transform;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

public abstract class AbstractProcessTask extends Task {
   private Vector filesets = new Vector();

   public void addFileset(FileSet set) {
      this.filesets.addElement(set);
   }

   protected Collection getFiles() {
      Map fileMap = new HashMap();
      Project p = this.getProject();

      for(int i = 0; i < this.filesets.size(); ++i) {
         FileSet fs = (FileSet)this.filesets.elementAt(i);
         DirectoryScanner ds = fs.getDirectoryScanner(p);
         String[] srcFiles = ds.getIncludedFiles();
         File dir = fs.getDir(p);

         for(int j = 0; j < srcFiles.length; ++j) {
            File src = new File(dir, srcFiles[j]);
            fileMap.put(src.getAbsolutePath(), src);
         }
      }

      return fileMap.values();
   }

   public void execute() throws BuildException {
      this.beforeExecute();
      Iterator it = this.getFiles().iterator();

      while(it.hasNext()) {
         try {
            this.processFile((File)it.next());
         } catch (Exception var3) {
            throw new BuildException(var3);
         }
      }

   }

   protected void beforeExecute() throws BuildException {
   }

   protected abstract void processFile(File var1) throws Exception;
}
