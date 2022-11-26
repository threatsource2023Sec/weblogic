package weblogic.jms.common;

import java.util.List;
import weblogic.logging.jms.JMSMessageLogger;

public interface JMSMessageEventLogListener {
   JMSMessageLogger getJMSMessageLogger();

   List getMessageLoggingJMSHeaders();

   List getMessageLoggingUserProperties();

   String getListenerName();
}
