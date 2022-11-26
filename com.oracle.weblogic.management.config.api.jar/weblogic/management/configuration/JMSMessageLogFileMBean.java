package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface JMSMessageLogFileMBean extends LogFileMBean {
   String DEFAULT_MESSAGE_LOG_FILE_NAME = "jms.messages.log";
   String DEFAULT_MESSAGE_LOG_DIR_NAME = "jmsservers";

   String getFileName();

   void setFileName(String var1) throws InvalidAttributeValueException;
}
