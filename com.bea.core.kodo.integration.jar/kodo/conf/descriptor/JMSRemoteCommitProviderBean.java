package kodo.conf.descriptor;

public interface JMSRemoteCommitProviderBean extends RemoteCommitProviderBean {
   String getTopic();

   void setTopic(String var1);

   int getExceptionReconnectAttempts();

   void setExceptionReconnectAttempts(int var1);

   String getTopicConnectionFactory();

   void setTopicConnectionFactory(String var1);
}
