package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;

@MessageLogger(
   projectCode = "WELD-"
)
public interface VersionLogger extends WeldLogger {
   VersionLogger LOG = (VersionLogger)Logger.getMessageLogger(VersionLogger.class, Category.VERSION.getName());

   @LogMessage(
      level = Level.INFO
   )
   @Message(
      id = 900,
      value = "{0}",
      format = Format.MESSAGE_FORMAT
   )
   void version(Object var1);
}
