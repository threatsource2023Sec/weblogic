package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JMSSAFMessageLogFileMBean extends LogFileMBean {
   String DEFAULT_MESSAGE_LOG_FILE_NAME = "jms.messages.log";
   String DEFAULT_MESSAGE_LOG_DIR_NAME = "safagents";

   String getFileName();

   void setFileName(String var1) throws InvalidAttributeValueException;
}
