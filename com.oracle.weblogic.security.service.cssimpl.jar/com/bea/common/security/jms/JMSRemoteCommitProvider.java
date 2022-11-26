package com.bea.common.security.jms;

import java.io.Serializable;
import java.util.Properties;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.openjpa.event.AbstractRemoteCommitProvider;
import org.apache.openjpa.event.RemoteCommitEvent;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.GenericConfigurable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class JMSRemoteCommitProvider extends AbstractRemoteCommitProvider implements Configurable, GenericConfigurable, ExceptionListener {
   private static Localizer s_loc = Localizer.forPackage(JMSRemoteCommitProvider.class);
   private String _topicName = "topic/OpenJPACommitProviderTopic";
   private String _tcfName = "java:/ConnectionFactory";
   private Properties _ctxProps = null;
   private int _reconnectAttempts = 0;
   private TopicConnection _connection;
   private TopicSession _session;
   private TopicPublisher _publisher;

   public void setTopic(String name) {
      this._topicName = name;
   }

   public void setTopicConnectionFactory(String name) {
      this._tcfName = name;
   }

   public void setExceptionReconnectAttempts(int attempts) {
      this._reconnectAttempts = attempts;
   }

   public void setInto(Options opts) {
      if (opts != null && !opts.isEmpty()) {
         this._ctxProps = new Properties();
         this._ctxProps.putAll(opts);
         opts.clear();
      } else {
         this._ctxProps = null;
      }

   }

   protected Context newContext() throws NamingException {
      return this._ctxProps == null ? new InitialContext() : new InitialContext(this._ctxProps);
   }

   public void broadcast(RemoteCommitEvent event) {
      try {
         this._publisher.publish(this.createMessage(event));
         if (this.log.isTraceEnabled()) {
            this.log.trace(s_loc.get("jms-sent-update", this._topicName));
         }
      } catch (JMSException var3) {
         if (this.log.isWarnEnabled()) {
            this.log.warn(s_loc.get("jms-send-error", this._topicName), var3);
         }
      }

   }

   public void close() {
      try {
         if (this._connection != null) {
            this._connection.close();
            if (this.log.isInfoEnabled()) {
               this.log.info(s_loc.get("jms-close-listener", this._topicName));
            }
         }
      } catch (JMSException var5) {
         if (this.log.isWarnEnabled()) {
            this.log.warn(s_loc.get("jms-close-error", this._topicName), var5);
         }
      } finally {
         this._connection = null;
      }

   }

   public void endConfiguration() {
      super.endConfiguration();
      this.connect();
   }

   protected void connect() {
      try {
         Context ctx = this.newContext();
         TopicConnectionFactory tcf = (TopicConnectionFactory)ctx.lookup(this._tcfName);
         Topic topic = (Topic)ctx.lookup(this._topicName);
         ctx.close();
         this._connection = tcf.createTopicConnection();
         this._session = this._connection.createTopicSession(false, 1);
         this._publisher = this._session.createPublisher(topic);
         TopicSubscriber s = this._session.createSubscriber(topic, (String)null, true);
         MessageListener l = this.getMessageListener();
         s.setMessageListener(l);
         this._connection.start();
         this._connection.setExceptionListener(this);
         if (this.log.isInfoEnabled()) {
            this.log.info(s_loc.get("jms-start-listener", this._topicName));
         }

      } catch (OpenJPAException var6) {
         throw var6;
      } catch (Exception var7) {
         throw (new UserException(s_loc.get("jms-provider-config", this._topicName, this._tcfName), var7)).setFatal(true);
      }
   }

   protected MessageListener getMessageListener() {
      return new MessageListener() {
         public void onMessage(Message m) {
            if (!(m instanceof ObjectMessage)) {
               if (JMSRemoteCommitProvider.this.log.isWarnEnabled()) {
                  JMSRemoteCommitProvider.this.log.warn(JMSRemoteCommitProvider.s_loc.get("jms-receive-error-3", JMSRemoteCommitProvider.this._topicName, m.getClass().getName()));
               }

            } else {
               ObjectMessage om = (ObjectMessage)m;

               Serializable o;
               try {
                  o = om.getObject();
               } catch (JMSException var5) {
                  if (JMSRemoteCommitProvider.this.log.isWarnEnabled()) {
                     JMSRemoteCommitProvider.this.log.warn(JMSRemoteCommitProvider.s_loc.get("jms-receive-error-1"), var5);
                  }

                  return;
               }

               if (o instanceof RemoteCommitEvent) {
                  if (JMSRemoteCommitProvider.this.log.isTraceEnabled()) {
                     JMSRemoteCommitProvider.this.log.trace(JMSRemoteCommitProvider.s_loc.get("jms-received-update", JMSRemoteCommitProvider.this._topicName));
                  }

                  RemoteCommitEvent rce = (RemoteCommitEvent)o;
                  JMSRemoteCommitProvider.this.fireEvent(rce);
               } else if (JMSRemoteCommitProvider.this.log.isWarnEnabled()) {
                  JMSRemoteCommitProvider.this.log.warn(JMSRemoteCommitProvider.s_loc.get("jms-receive-error-2", o.getClass().getName(), JMSRemoteCommitProvider.this._topicName));
               }

            }
         }
      };
   }

   protected Message createMessage(RemoteCommitEvent event) throws JMSException {
      return this._session.createObjectMessage(event);
   }

   public void onException(JMSException ex) {
      if (this.log.isWarnEnabled()) {
         this.log.warn(s_loc.get("jms-listener-error", this._topicName), ex);
      }

      if (this._reconnectAttempts > 0) {
         this.close();
         boolean connected = false;

         for(int i = 0; !connected && i < this._reconnectAttempts; ++i) {
            try {
               if (this.log.isInfoEnabled()) {
                  this.log.info(s_loc.get("jms-reconnect-attempt", this._topicName, String.valueOf(i + 1)));
               }

               this.connect();
               connected = true;
            } catch (Exception var7) {
               if (this.log.isInfoEnabled()) {
                  this.log.info(s_loc.get("jms-reconnect-fail", this._topicName), var7);
               }

               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var6) {
                  break;
               }
            }
         }

         if (!connected && this.log.isErrorEnabled()) {
            this.log.error(s_loc.get("jms-cant-reconnect", this._topicName, String.valueOf(this._reconnectAttempts)));
         } else if (connected && this.log.isInfoEnabled()) {
            this.log.info(s_loc.get("jms-reconnected", this._topicName));
         }

      }
   }

   public TopicPublisher getPublisher() {
      return this._publisher;
   }

   public String getTopic() {
      return this._topicName;
   }

   public int getReconnectAttempts() {
      return this._reconnectAttempts;
   }
}
