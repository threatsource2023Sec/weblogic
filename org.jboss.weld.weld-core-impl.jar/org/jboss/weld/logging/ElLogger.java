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
public interface ElLogger extends WeldLogger {
   ElLogger LOG = (ElLogger)Logger.getMessageLogger(ElLogger.class, Category.EL.getName());

   @Message(
      id = 1001,
      value = "Cannot pass null expressionFactory"
   )
   IllegalArgumentException nullExpressionFactory();

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1002,
      value = "Looking for EL property {0}",
      format = Format.MESSAGE_FORMAT
   )
   void propertyLookup(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 1003,
      value = "EL property {0} resolved to {1}",
      format = Format.MESSAGE_FORMAT
   )
   void propertyResolved(Object var1, Object var2);
}
