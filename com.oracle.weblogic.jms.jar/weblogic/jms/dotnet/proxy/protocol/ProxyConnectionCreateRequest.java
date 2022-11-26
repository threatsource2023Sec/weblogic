package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyConnectionCreateRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _IS_XA = 1;
   private static final int _HAS_USER_NAME = 2;
   private static final int _HAS_PASSWORD = 3;
   private long listenerServiceId;
   private String userName;
   private String password;
   private boolean createXAConnection;

   public ProxyConnectionCreateRequest() {
   }

   public ProxyConnectionCreateRequest(long listenerServiceId, String userName, String password, boolean createXAConnection) {
      this.listenerServiceId = listenerServiceId;
      this.userName = userName;
      this.password = password;
      this.createXAConnection = createXAConnection;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getPassword() {
      return this.password;
   }

   public boolean isCreateXAConnection() {
      return this.createXAConnection;
   }

   public long getListenerServiceId() {
      return this.listenerServiceId;
   }

   public int getMarshalTypeCode() {
      return 9;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.userName != null) {
         this.versionFlags.setBit(2);
      }

      if (this.password != null) {
         this.versionFlags.setBit(3);
      }

      if (this.createXAConnection) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      mw.writeLong(this.listenerServiceId);
      if (this.userName != null) {
         mw.writeString(this.userName);
      }

      if (this.password != null) {
         mw.writeString(this.password);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.listenerServiceId = mr.readLong();
      if (this.versionFlags.isSet(2)) {
         this.userName = mr.readString();
      }

      if (this.versionFlags.isSet(3)) {
         this.password = mr.readString();
      }

      if (this.versionFlags.isSet(1)) {
         this.createXAConnection = true;
      }

   }
}
