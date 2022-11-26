package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.Cause;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalStateException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface XmlLogger extends WeldLogger {
   XmlLogger LOG = (XmlLogger)Logger.getMessageLogger(XmlLogger.class, Category.BOOTSTRAP.getName());

   @Message(
      id = 1200,
      value = "Error configuring XML parser"
   )
   IllegalStateException configurationError(@Cause Throwable var1);

   @Message(
      id = 1201,
      value = "Error loading beans.xml {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException loadError(Object var1, @Cause Throwable var2);

   @Message(
      id = 1202,
      value = "Error parsing {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException parsingError(Object var1, @Cause Throwable var2);

   @Message(
      id = 1203,
      value = "<alternatives> can only be specified once, but appears multiple times:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleAlternatives(Object var1);

   @Message(
      id = 1204,
      value = "<decorators> can only be specified once, but is specified multiple times:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleDecorators(Object var1);

   @Message(
      id = 1205,
      value = "<interceptors> can only be specified once, but it is specified multiple times:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleInterceptors(Object var1);

   @Message(
      id = 1207,
      value = "<scan> can only be specified once, but it is specified multiple times:  {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException multipleScanning(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1208,
      value = "Error when validating {0}@{1} against xsd. {2}",
      format = Format.MESSAGE_FORMAT
   )
   void xsdValidationError(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 1210,
      value = "Warning when validating {0}@{1} against xsd. {2}",
      format = Format.MESSAGE_FORMAT
   )
   void xsdValidationWarning(Object var1, Object var2, Object var3);
}
