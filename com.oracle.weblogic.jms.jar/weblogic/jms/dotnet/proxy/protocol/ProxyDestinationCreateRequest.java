package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyDestinationCreateRequest extends ProxyRequest {
   private String destinationName;
   private int destinationType;
   private boolean temporary;
   private static final int EXTVERSION = 1;
   private static final int _HAS_DESTINATION_TYPE = 1;
   private static final int _IS_TEMPORARY = 2;
   public static final int TYPE_QUEUE = 1;
   public static final int TYPE_TOPIC = 2;

   public ProxyDestinationCreateRequest(String destinationName, int destinationType, boolean temporary) {
      this.destinationName = destinationName;
      this.destinationType = destinationType;
      this.temporary = temporary;
   }

   public final String getDestinationName() {
      return this.destinationName;
   }

   public final void setDestinationName(String destinationName) {
      this.destinationName = destinationName;
   }

   public final int getDestinationType() {
      return this.destinationType;
   }

   public final boolean isTemporary() {
      return this.temporary;
   }

   public ProxyDestinationCreateRequest() {
   }

   public int getMarshalTypeCode() {
      return 20;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.destinationType != -1) {
         this.versionFlags.setBit(1);
      }

      if (this.temporary) {
         this.versionFlags.setBit(2);
      }

      this.versionFlags.marshal(mw);
      mw.writeString(this.destinationName);
      if (this.destinationType != -1) {
         mw.writeInt(this.destinationType);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      this.destinationName = mr.readString();
      if (versionFlags.isSet(1)) {
         this.destinationType = mr.readInt();
      }

      this.temporary = versionFlags.isSet(2);
   }
}
