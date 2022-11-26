package weblogic.wtc.jatmi;

public interface Objinfo {
   ObjectId getObjectId();

   void setObjectId(ObjectId var1);

   int getKeyType();

   void setKeyType(int var1);

   String getDomainId();

   void setOrigDomain(String var1);

   void setDomainId(String var1);

   String getIntfId();

   void setIntfId(String var1);

   int getGroupId();

   void setGroupId(int var1);

   short getIsMyDomain();

   void setIsMyDomain(short var1);

   ClientInfo getRecvSrcCltinfo();

   ClientInfo getSendSrcCltinfo();

   void setRecvSrcCltinfo(ClientInfo var1);

   void setSendSrcCltinfo(ClientInfo var1);

   void setCltinfo(ClientInfo var1);

   ClientInfo getCltinfo();

   int getIsACallout();

   void setIsACallout(int var1);

   int getScaIntfBkt();

   void setScaIntfBkt(int var1);

   int getConnGen();

   void setConnGen(int var1);

   int getAppKey();

   void setAppKey(int var1);

   int getConnId();

   void setConnId(int var1);

   void setSvcTmid(int[] var1);

   void setAOMHandle(int var1);
}
