package com.bea.httppubsub.jms.internal;

import com.bea.httppubsub.descriptor.JmsHandlerBean;
import com.bea.httppubsub.jms.PubSubJmsLogger;
import com.bea.httppubsub.jms.provider.JmsProvider;
import java.util.Hashtable;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JmsProviderImpl implements JmsProvider {
   private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private ConnectionFactory connectionFactory;
   private Topic topic;

   public JmsProviderImpl(JmsHandlerBean config) {
      if (config == null) {
         throw new IllegalArgumentException("JmsHandlerBean cannot be null.");
      } else {
         this.initialize(config);
      }
   }

   public ConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   public Topic getTopic() {
      return this.topic;
   }

   private void initialize(JmsHandlerBean config) {
      String jmsProviderUrl = config.getJmsProviderUrl();
      String connectionFactoryJndiName = config.getConnectionFactoryJndiName();
      String topicJndiName = config.getTopicJndiName();
      PubSubJmsLogger.logJmsConfigurations(jmsProviderUrl, connectionFactoryJndiName, topicJndiName);

      try {
         if (jmsProviderUrl != null && !"".equals(jmsProviderUrl)) {
            Hashtable env = new Hashtable();
            env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            env.put("java.naming.provider.url", jmsProviderUrl);
            Context ctx = new InitialContext(env);
            this.connectionFactory = (ConnectionFactory)ctx.lookup(connectionFactoryJndiName);
            this.topic = (Topic)ctx.lookup(topicJndiName);
         } else {
            Context ctx = new InitialContext();
            this.connectionFactory = (ConnectionFactory)ctx.lookup("java:comp/env/" + connectionFactoryJndiName);
            this.topic = (Topic)ctx.lookup("java:comp/env/" + topicJndiName);
         }

      } catch (NamingException var7) {
         throw new RuntimeException(PubSubJmsLogger.logCannotRetrieveJmsResourcesLoggable(var7).getMessage());
      }
   }
}
