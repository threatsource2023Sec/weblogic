package kodo.conf.descriptor;

public interface TCPRemoteCommitProviderBean extends RemoteCommitProviderBean {
   int getMaxIdle();

   void setMaxIdle(int var1);

   int getNumBroadcastThreads();

   void setNumBroadcastThreads(int var1);

   int getRecoveryTimeMillis();

   void setRecoveryTimeMillis(int var1);

   int getMaxActive();

   void setMaxActive(int var1);

   int getPort();

   void setPort(int var1);

   String getAddresses();

   void setAddresses(String var1);
}
