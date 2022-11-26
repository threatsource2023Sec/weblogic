package weblogic.ant.taskdefs.build;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

abstract class BaseTask extends Task {
   protected abstract void privateExecute() throws BuildException;

   public final void execute() throws BuildException {
      Thread th = Thread.currentThread();
      ClassLoader cl = th.getContextClassLoader();
      ClassLoader antLoader = this.getClass().getClassLoader();

      try {
         th.setContextClassLoader(antLoader);
         this.privateExecute();
      } finally {
         th.setContextClassLoader(cl);
      }

   }
}
