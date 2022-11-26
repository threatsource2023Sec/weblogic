@start rule: main
@packageDeclaration

import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18n.logging.Severities;
import weblogic.logging.Loggable;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;

/** 
 * Copyright (c) 2003,2014, Oracle and/or its affiliates. All rights reserved.
 * @exclude
 */
public class @className
{
  private static final String LOCALIZER_CLASS = "@localizerClass";

  private static MessageLogger findMessageLogger() {
    return MessageLoggerRegistry.findMessageLogger(@className.class.getName());
  }

  private static final class MessageLoggerInitializer implements MessageLoggerRegistryListener {

    private static final MessageLoggerInitializer INSTANCE = new MessageLoggerInitializer();
    
    private MessageLogger messageLogger = findMessageLogger();
    
    private MessageLoggerInitializer() {
      MessageLoggerRegistry.addMessageLoggerRegistryListener(this);      
    }
            
    public void messageLoggerRegistryUpdated() {
      messageLogger = findMessageLogger();
    }
  }

  private static class LoggableMessageSpiImpl extends CatalogMessage implements LoggableMessageSpi
  {
    public LoggableMessageSpiImpl(String messageId, int severity, Object[] args, String resourceName, ClassLoader resourceClassLoader)
    {
      super(messageId, severity, args, resourceName, resourceClassLoader);
    }

    public String getPrefix() { return getMessageIdPrefix(); }
    public String getFormattedMessageBody() { return getMessage(); }
  }

  private static void logMessageUsingLoggerSpi(LoggerSpi logger, LoggableMessageSpiImpl message)
  {
    int severity = message.getSeverity();

    // Message not logged if severity is OFF, DEBUG, TRACE, or DYNAMIC.
    if (severity == Severities.INFO)
      logger.info(message);
    else if (severity == Severities.NOTICE || severity == Severities.WARNING)
      logger.warn(message);
    else if (severity == Severities.ERROR)
      logger.error(message);
    else if (severity == Severities.ALERT || severity == Severities.CRITICAL || severity == Severities.EMERGENCY)
      logger.severe(message);
  }


@logMessages
}
@end rule: main

@start rule: logMessage
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * severity:   @logMessageSeverity
   */
  public static String @logMethodName(@logLoggerSpi @logArguments) @logExceptions {

    if (@logMessageResetPeriod > 0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("@logMessageId")) return "@logMessageId"; 
    }

    Object [] args = { @logArgumentClasses };
    LoggableMessageSpiImpl catalogMessage = new LoggableMessageSpiImpl("@logMessageId", @logMessageSeverityValue, args, LOCALIZER_CLASS, @className.class.getClassLoader());
    catalogMessage.setStackTraceEnabled(@logStackTrace);
    catalogMessage.setDiagnosticVolume("@logDiagnosticVolume");    
    logMessageUsingLoggerSpi(logger, catalogMessage);

    if (@logMessageResetPeriod > 0) {
      MessageResetScheduler.getInstance().scheduleMessageReset("@logMessageId", @logMessageResetPeriod);
    }

    return "@logMessageId";
  }

@end rule: logMessage

@start rule: logMessageLoggable
  public static LoggableMessageSpi @loggableMethodName(@logArguments)  {
    Object[] args = { @logArgumentClasses };
    return new LoggableMessageSpiImpl("@logMessageId", @logMessageSeverityValue, args, LOCALIZER_CLASS, @className.class.getClassLoader());
  }

@end rule: logMessageLoggable

@start rule: getMessage
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * severity:   @logMessageSeverity
   */
  public static String @logMethodName(@logArguments) @logExceptions {
    Object [] args = { @logArgumentClasses };
    return (new Loggable("@logMessageId", @logMessageSeverityValue, args, LOCALIZER_CLASS, MessageLoggerInitializer.INSTANCE.messageLogger, @className.class.getClassLoader())).getMessage();	
  }

@end rule: getMessage

@start rule: resetLogMessage
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * severity:   @logMessageSeverity
   */
  public static void reset@logMethodName() {
    MessageResetScheduler.getInstance().resetLogMessage("@logMessageId");
  }
@end rule: resetLogMessage
