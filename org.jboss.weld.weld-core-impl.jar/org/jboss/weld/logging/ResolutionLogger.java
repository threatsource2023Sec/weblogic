package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.WeldException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ResolutionLogger extends WeldLogger {
   ResolutionLogger LOG = (ResolutionLogger)Logger.getMessageLogger(ResolutionLogger.class, Category.RESOLUTION.getName());

   @Message(
      id = 1601,
      value = "Cannot extract rawType from {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException cannotExtractRawType(Object var1);

   @Message(
      id = 1602,
      value = "Cannot create qualifier instance model for {0}\n\tat {1}\n  StackTrace:",
      format = Format.MESSAGE_FORMAT
   )
   WeldException cannotCreateQualifierInstanceValues(Object var1, Object var2, @Cause Exception var3);
}
