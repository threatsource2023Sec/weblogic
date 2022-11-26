package kodo.conf.descriptor;

public interface TCPTransportBean extends PersistenceServerBean {
   int getSoTimeout();

   void setSoTimeout(int var1);

   String getHost();

   void setHost(String var1);

   int getPort();

   void setPort(int var1);
}
