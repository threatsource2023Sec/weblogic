package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyPushMessageRequest extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _SUGGEST_WINDOW_TURN = 1;
   private long consumerId;
   private ProxyMessageImpl message;
   private boolean suggestWindowTurn;
   private ProxyPushMessageRequest next;

   public ProxyPushMessageRequest(long consumerId, ProxyMessageImpl message, boolean suggestWindowTurn) {
      this.consumerId = consumerId;
      this.message = message;
      this.suggestWindowTurn = suggestWindowTurn;
   }

   void setNext(ProxyPushMessageRequest p) {
      this.next = p;
   }

   ProxyPushMessageRequest getNext() {
      return this.next;
   }

   public ProxyMessageImpl getMessage() {
      return this.message;
   }

   public ProxyPushMessageRequest() {
   }

   public int getMarshalTypeCode() {
      return 44;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.suggestWindowTurn) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      mw.writeLong(this.consumerId);
      mw.writeByte(this.message.getType());
      this.message.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.consumerId = mr.readLong();
      byte type = mr.readByte();
      this.message = ProxyMessageImpl.createMessageImpl(type);
      this.message.unmarshal(mr);
      this.suggestWindowTurn = this.versionFlags.isSet(1);
   }
}
