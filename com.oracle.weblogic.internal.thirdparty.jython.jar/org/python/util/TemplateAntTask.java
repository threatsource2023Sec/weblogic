package org.python.util;

import java.io.File;
import java.io.IOException;
import org.python.apache.tools.ant.BuildException;
import org.python.apache.tools.ant.taskdefs.Execute;
import org.python.apache.tools.ant.taskdefs.MatchingTask;

public class TemplateAntTask extends MatchingTask {
   protected String python;
   private File srcDir;
   protected boolean verbose = false;
   protected boolean lazy = false;

   public void setPython(String aPE) {
      this.python = aPE;
   }

   public void setSrcdir(String in) {
      this.srcDir = new File(this.getProject().replaceProperties(in));
   }

   public void setVerbose(String in) {
      this.verbose = new Boolean(this.getProject().replaceProperties(in));
   }

   public void setLazy(String in) {
      this.lazy = new Boolean(this.getProject().replaceProperties(in));
   }

   public void execute() {
      if (null == this.srcDir) {
         throw new BuildException("no srcdir specified");
      } else if (!this.srcDir.exists()) {
         throw new BuildException("srcdir '" + this.srcDir + "' doesn't exist");
      } else {
         File gexposeScript = new File(this.srcDir.getAbsolutePath() + File.separator + "gexpose.py");
         File gderiveScript = new File(this.srcDir.getAbsolutePath() + File.separator + "gderived.py");
         if (!gexposeScript.exists()) {
            throw new BuildException("no gexpose.py script found at: " + gexposeScript);
         } else if (!gderiveScript.exists()) {
            throw new BuildException("no gderive.py script found at: " + gderiveScript);
         } else {
            this.runPythonScript(gexposeScript.getAbsolutePath());
            this.runPythonScript(gderiveScript.getAbsolutePath());
         }
      }
   }

   private void runPythonScript(String script) throws BuildException {
      if (null == this.python) {
         this.python = "python";
      }

      Execute e = new Execute();
      e.setWorkingDirectory(this.srcDir);
      String[] command;
      if (this.lazy) {
         command = new String[]{this.python, script, "--lazy"};
      } else {
         command = new String[]{this.python, script};
      }

      e.setCommandline(command);
      if (this.verbose) {
         String out = "";

         for(int k = 0; k < e.getCommandline().length; ++k) {
            out = out + e.getCommandline()[k] + " ";
         }

         this.log("executing: " + out);
      }

      try {
         e.execute();
      } catch (IOException var6) {
         throw new BuildException(var6.toString(), var6);
      }
   }
}
