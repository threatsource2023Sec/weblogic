package kodo.conf.descriptor;

public interface ClusterRemoteCommitProviderBean extends RemoteCommitProviderBean {
   int getBufferSize();

   void setBufferSize(int var1);

   String getCacheTopics();

   void setCacheTopics(String var1);

   String getRecoverAction();

   void setRecoverAction(String var1);
}
