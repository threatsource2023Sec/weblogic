package weblogic.management.scripting.jsr88;

import javax.enterprise.deploy.spi.status.ProgressObject;

public interface WLSTProgress {
   String getState();

   boolean isRunning();

   boolean isFailed();

   boolean isCompleted();

   String getMessage();

   String getCommandType();

   void printStatus();

   ProgressObject getProgressObject();
}
