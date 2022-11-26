package com.bea.httppubsub.jms.provider;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

public interface JmsProvider {
   ConnectionFactory getConnectionFactory();

   Topic getTopic();
}
