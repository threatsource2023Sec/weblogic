package weblogic.ant.taskdefs.antline;

import java.io.PrintStream;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildLogger;

public class ToolLogger implements BuildLogger {
   private PrintStream out;
   private PrintStream err;
   private int msgOutputLevel;

   public ToolLogger() {
      this.out = System.out;
      this.err = System.err;
      this.msgOutputLevel = 2;
   }

   public void buildStarted(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("build-start: " + event);
      }

   }

   public void buildFinished(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("build-finished: " + event);
      }

      Throwable error = event.getException();
      if (error != null) {
         if (this.msgOutputLevel < 3 && error instanceof BuildException) {
            if (error instanceof BuildException) {
               this.err.println(error.toString());
            } else {
               this.err.println(error.getMessage());
            }
         } else {
            error.printStackTrace(this.err);
         }
      }

   }

   public void targetStarted(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("target-started: " + event);
      }

   }

   public void targetFinished(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("target-finished: " + event);
      }

   }

   public void taskStarted(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("task-started: " + event);
      }

   }

   public void taskFinished(BuildEvent event) {
      if (AntTool.debug) {
         System.out.println("task-finished: " + event);
      }

   }

   public void messageLogged(BuildEvent event) {
      PrintStream logTo = event.getPriority() == 0 ? this.err : this.out;
      if (event.getPriority() <= this.msgOutputLevel && event.getTask() != null) {
         logTo.println(event.getMessage());
      }

   }

   public void setMessageOutputLevel(int level) {
      if (AntTool.debug) {
         System.out.println("set-message-output-level: " + level);
      }

      if (level >= 2) {
         this.msgOutputLevel = level;
      }

   }

   public void setOutputPrintStream(PrintStream output) {
      if (AntTool.debug) {
         System.out.println("set-output-print-stream: " + output.getClass().getName());
      }

      this.out = output;
   }

   public void setErrorPrintStream(PrintStream err) {
      if (AntTool.debug) {
         System.out.println("set-error-print-stream: " + err.getClass().getName());
      }

      this.err = err;
   }

   public void setEmacsMode(boolean emacsMode) {
      if (AntTool.debug) {
         System.out.println("set-emacs-mode: " + emacsMode);
      }

   }
}
