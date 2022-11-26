package com.bea.core.repackaged.jdt.core;

import com.bea.core.repackaged.jdt.core.index.JavaIndexer;
import com.bea.core.repackaged.jdt.internal.antadapter.AntAdapterMessages;
import java.io.IOException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class BuildJarIndex extends Task {
   private String jarPath;
   private String indexPath;

   public void execute() throws BuildException {
      if (this.jarPath == null) {
         throw new BuildException(AntAdapterMessages.getString("buildJarIndex.jarFile.cannot.be.null"));
      } else if (this.indexPath == null) {
         throw new BuildException(AntAdapterMessages.getString("buildJarIndex.indexFile.cannot.be.null"));
      } else {
         try {
            JavaIndexer.generateIndexForJar(this.jarPath, this.indexPath);
         } catch (IOException var2) {
            throw new BuildException(AntAdapterMessages.getString("buildJarIndex.ioexception.occured", var2.getLocalizedMessage()), var2);
         }
      }
   }

   public void setJarPath(String path) {
      this.jarPath = path;
   }

   public void setIndexPath(String path) {
      this.indexPath = path;
   }
}
