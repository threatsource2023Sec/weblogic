package org.apache.log.output.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.log.LogEvent;

public interface MessageBuilder {
   Message buildMessage(Session var1, LogEvent var2) throws JMSException;
}
