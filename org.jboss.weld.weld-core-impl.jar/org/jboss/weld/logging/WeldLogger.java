package org.jboss.weld.logging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

@MessageLogger(
   projectCode = "WELD-"
)
public interface WeldLogger extends BasicLogger {
   String CATCHING_MARKER = "Catching";
   String WELD_PROJECT_CODE = "WELD-";

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 0,
      value = "Catching"
   )
   void catchingDebug(@Cause Throwable var1);
}
