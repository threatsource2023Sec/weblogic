@start rule: main
@packageDeclaration

import java.util.Locale;

import weblogic.i18n.Localizer;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.MessageLogger;
import weblogic.i18n.logging.MessageLoggerRegistry;
import weblogic.i18n.logging.MessageLoggerRegistryListener;
import weblogic.i18n.logging.MessageResetScheduler;
import weblogic.i18ntools.L10nLookup;
import weblogic.logging.Loggable;

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
    
    private static final Localizer LOCALIZER = L10nLookup.getLocalizer(
      Locale.getDefault(), LOCALIZER_CLASS, @className.class.getClassLoader());
    
    private MessageLogger messageLogger = findMessageLogger();
    
    private MessageLoggerInitializer() {
      MessageLoggerRegistry.addMessageLoggerRegistryListener(this);      
    }
            
    public void messageLoggerRegistryUpdated() {
      messageLogger = findMessageLogger();
    }
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
  public static String @logMethodName(@logArguments) @logExceptions {

    if (@logMessageResetPeriod > 0) {
      if (MessageResetScheduler.getInstance().isMessageLoggingDisabled("@logMessageId")) return "@logMessageId"; 
    }

    Object [] args = { @logArgumentClasses };
    CatalogMessage catalogMessage = new CatalogMessage("@logMessageId", @logMessageSeverityValue, args, MessageLoggerInitializer.LOCALIZER);
    catalogMessage.setStackTraceEnabled(@logStackTrace);
    catalogMessage.setDiagnosticVolume("@logDiagnosticVolume");
    catalogMessage.setExcludePartition(@logExcludePartition);     
    MessageLoggerInitializer.INSTANCE.messageLogger.log(catalogMessage);

    if (@logMessageResetPeriod > 0) {
      MessageResetScheduler.getInstance().scheduleMessageReset("@logMessageId", @logMessageResetPeriod);
    }

    return "@logMessageId";
  }

@end rule: logMessage

@start rule: logMessageLoggable
  public static Loggable @loggableMethodName(@logArguments)  {
    Object[] args = { @logArgumentClasses };
    Loggable l = new Loggable("@logMessageId", @logMessageSeverityValue, args, LOCALIZER_CLASS, MessageLoggerInitializer.INSTANCE.messageLogger, @className.class.getClassLoader());
    l.setStackTraceEnabled(@logStackTrace);
    l.setExcludePartition(@logExcludePartition);
    return l;
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
    Loggable l = (new Loggable("@logMessageId", @logMessageSeverityValue, args, LOCALIZER_CLASS, MessageLoggerInitializer.INSTANCE.messageLogger, @className.class.getClassLoader()));
    l.setStackTraceEnabled(@logStackTrace);
    l.setExcludePartition(@logExcludePartition);
    return l.getMessage();	
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
