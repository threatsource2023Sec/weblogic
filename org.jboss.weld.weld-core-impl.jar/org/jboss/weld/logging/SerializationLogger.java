package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.InvalidObjectException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface SerializationLogger extends WeldLogger {
   SerializationLogger LOG = (SerializationLogger)Logger.getMessageLogger(SerializationLogger.class, Category.SERIALIZATION.getName());

   @Message(
      id = 1800,
      value = "Unable to get bean identifier at position {0} from {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToGetBeanIdentifier(int var1, Object var2);

   @Message(
      id = 1801,
      value = "Unable to deserialize {0}",
      format = Format.MESSAGE_FORMAT
   )
   InvalidObjectException unableToDeserialize(Object var1, @Cause Throwable var2);
}
