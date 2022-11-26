package com.bea.httppubsub;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public interface ObjectMessageBuilder {
   ObjectMessage build(Session var1, String var2, String var3) throws JMSException;
}
