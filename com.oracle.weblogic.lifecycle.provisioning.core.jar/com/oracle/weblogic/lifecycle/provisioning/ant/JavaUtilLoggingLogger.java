package com.oracle.weblogic.lifecycle.provisioning.ant;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class JavaUtilLoggingLogger implements BuildListener {
   private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
   protected final Logger logger;

   public JavaUtilLoggingLogger(Logger logger) {
      Objects.requireNonNull(logger);
      this.logger = logger;
   }

   public void buildStarted(BuildEvent event) {
   }

   public void buildFinished(BuildEvent event) {
      if (event != null) {
         Level level = this.getLevel(event);
         if (level != null && this.logger.isLoggable(level)) {
            Throwable error = event.getException();
            if (error == null) {
               this.log(level, event.getTarget(), event.getProject(), event.getTask(), "BUILD SUCCEEDED", (Throwable)null);
            } else {
               this.log(level, event.getTarget(), event.getProject(), event.getTask(), "BUILD FAILED", error);
            }
         }
      }

   }

   public void targetStarted(BuildEvent event) {
      if (event != null && this.logger.isLoggable(Level.INFO)) {
         Target target = event.getTarget();
         if (target != null) {
            String targetName = target.getName();
            if (targetName != null && !targetName.isEmpty()) {
               this.log(Level.INFO, event.getTarget(), event.getProject(), event.getTask(), "Executing target \"{0}\"", event.getException(), targetName);
            }
         }
      }

   }

   public void targetFinished(BuildEvent event) {
      if (event != null && this.logger.isLoggable(Level.INFO)) {
         Target target = event.getTarget();
         if (target != null) {
            String targetName = target.getName();
            if (targetName != null && !targetName.isEmpty()) {
               this.log(Level.INFO, event.getTarget(), event.getProject(), event.getTask(), "Executed target \"{0}\"", event.getException(), targetName);
            }
         }
      }

   }

   public void taskStarted(BuildEvent event) {
   }

   public void taskFinished(BuildEvent event) {
   }

   protected Level getLevel(BuildEvent event) {
      Level returnValue;
      if (event == null) {
         returnValue = Level.OFF;
      } else {
         int eventLevel = event.getPriority();
         switch (eventLevel) {
            case 0:
               returnValue = Level.SEVERE;
               break;
            case 1:
               returnValue = Level.WARNING;
               break;
            case 2:
               returnValue = Level.INFO;
               break;
            case 3:
               returnValue = Level.FINE;
               break;
            case 4:
               returnValue = Level.FINER;
               break;
            default:
               if (eventLevel < 0) {
                  returnValue = Level.OFF;
               } else {
                  returnValue = Level.FINEST;
               }
         }
      }

      return returnValue;
   }

   public void messageLogged(BuildEvent event) {
      if (event != null) {
         this.log(this.getLevel(event), event.getTarget(), event.getProject(), event.getTask(), event.getMessage(), event.getException());
      }

   }

   private final void log(Level level, Target target, Project project, Task task, String message, Throwable throwable, Object... parameters) {
      if (level != null && message != null && this.logger.isLoggable(level)) {
         LogRecord logRecord = this.createLogRecord(level, target, project, task, message, throwable, parameters);
         if (logRecord != null) {
            this.logger.log(logRecord);
         }
      }

   }

   protected LogRecord createLogRecord(Level level, Target target, Project project, Task task, String message, Throwable throwable, Object... parameters) {
      LogRecord returnValue = null;
      if (level != null && this.logger.isLoggable(level)) {
         returnValue = new LogRecord(level, message);
         returnValue.setLoggerName(this.logger.getName());
         String className;
         String methodName;
         if (target == null) {
            if (project == null) {
               className = "(no project):(no target)";
               if (task == null) {
                  methodName = "(no task)";
               } else {
                  methodName = String.valueOf(task.getLocation());
               }
            } else {
               String projectName = project.getName();
               StringBuilder sb;
               if (projectName == null) {
                  sb = new StringBuilder("(unnamed project)");
               } else {
                  sb = new StringBuilder(projectName);
               }

               className = sb.append(":(no target)").toString();
               if (task == null) {
                  methodName = "(no task)";
               } else {
                  methodName = String.valueOf(task.getLocation());
               }
            }
         } else if (project == null) {
            className = "(no project):" + target.getName();
            if (task == null) {
               methodName = "(no task)";
            } else {
               methodName = String.valueOf(task.getLocation());
            }
         } else {
            className = project.getName() + ":" + target.getName();
            if (task == null) {
               methodName = "(no task)";
            } else {
               methodName = String.valueOf(task.getLocation());
            }
         }

         returnValue.setSourceClassName(className);
         returnValue.setSourceMethodName(methodName);
         returnValue.setParameters(parameters);
         returnValue.setThrown(throwable);
      }

      return returnValue;
   }
}
