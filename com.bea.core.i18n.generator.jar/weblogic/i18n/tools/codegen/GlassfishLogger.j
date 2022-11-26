@start rule: main
@packageDeclaration

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;

import java.text.MessageFormat;

import org.glassfish.api.logging.LogLevel;
import org.glassfish.internal.logging.Loggable;
import org.glassfish.logging.annotation.LogMessageInfo;
import org.glassfish.logging.annotation.LogMessagesResourceBundle;
import org.glassfish.logging.annotation.LoggerInfo;

/** 
 * Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved.
 * @exclude
 */
public class @className
{
  // FIXME - The resource bundle name will need to change when the class is moved to Glassfish src line
  @@LogMessagesResourceBundle
  private static final String RESOURCE_BUNDLE_NAME = "@localizerClass";

  // FIXME - The logger name will need to be changed to the actual name used in Glassfish 
  // and update the description of the LoggerInfo annotation.
  @@LoggerInfo(subsystem="@messageCatalogSubsystem", 
    description="@messageCatalogSubsystem Logger",publish=true)
  private static Logger LOGGER = Logger.getLogger("@messageCatalogSubsystem", RESOURCE_BUNDLE_NAME);
  
  /**
   * Gets the underlying <code>java.util.logging.Logger</code> instance.
   */
  public static Logger getLogger() {
    return LOGGER;
  }
  
  // FIXME - Update the msg id prefix with the appropriate subsystem code
  private static final String MSG_ID_PREFIX = "AS-XXX-";  

@logMessages
}
@end rule: main

@start rule: logMessage
@logMessageInfo
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * level:   @logMessageLevelName
   */
  public static String @logMethodName(@logArguments) @logExceptions {
    Object [] args = { @logArgumentClasses };
    String rawMessage = LOGGER.getResourceBundle().getString(MSG_ID_@logMessageId);
    String formattedMessage = MessageFormat.format(rawMessage, args); 
    LogRecord rec = new LogRecord(@logMessageLevel, formattedMessage);
    @logSetLogRecordThrown    
    LOGGER.log(rec); 
    return MSG_ID_@logMessageId;
  }

@logMessageLoggableMethod
@end rule: logMessage

@start rule: logMessageLoggable
  public static Loggable @loggableMethodName(@logArguments)  {
    Level level = @logMessageLevel;
    Object[] args = { @logArgumentClasses };
    return new Loggable(level, MSG_ID_@logMessageId, args, LOGGER);
  }
@end rule: logMessageLoggable

@start rule: getMessage
@logMessageInfo
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * level:   @logMessageLevelName
   */
  public static String @logMethodName(@logArguments) @logExceptions {
    Level level = @logMessageLevel;
    Object[] args = { @logArgumentClasses };
    return (new Loggable(level, MSG_ID_@logMessageId, args, LOGGER)).getMessage();
  }

@logMessageLoggableMethod
@end rule: getMessage

@start rule: logMessageInfo
  /**
   * @logMessageDescription
   *
   * messageid:  @logMessageId
   * level:   @logMessageLevelName
   */
  @@LogMessageInfo(
    message = "@logMessageBodyString",
    comment = "@logMessageDetailString",
    level   = "@logMessageLevelName",
    cause   = "@logMessageCauseString",
    action  = "@logMessageActionString"
  )
  public static final String MSG_ID_@logMessageId = MSG_ID_PREFIX + "@logMessageId";
@end rule: logMessageInfo

@start rule: resetLogMessage
  /**
   * FIXME - Remove this method
   */
  public static void reset@logMethodName() {
    // NOP
  }
@end rule: resetLogMessage
