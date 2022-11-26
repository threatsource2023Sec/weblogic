package com.oracle.buzzmessagebus.impl;

import com.oracle.buzzmessagebus.api.BuzzLogger;
import com.oracle.buzzmessagebus.api.BuzzMessageToken;
import com.oracle.buzzmessagebus.api.BuzzSubprotocolReceiver;
import com.oracle.buzzmessagebus.api.BuzzTypes;
import com.oracle.buzzmessagebus.impl.internalapi.BuzzMessageTokenInternalApi;
import com.oracle.common.io.BufferSequence;
import com.oracle.common.net.exabus.EndPoint;
import java.nio.ByteBuffer;

public class BuzzMessageTokenImpl implements BuzzMessageToken, BuzzMessageTokenInternalApi {
   private final BuzzLogger l;
   private long messageId;
   private BuzzTypes.BuzzMessageState buzzMessageState;
   private final byte subprotocol;
   private final BuzzSubprotocolReceiver subprotocolReceiver;
   private Object cookie;
   private final EndPoint endPoint;
   private boolean isInMap;
   private BufferSequence data;
   private ConnectionInfo connectionInfo;

   public EndPoint getEndPoint() {
      return this.endPoint;
   }

   public long getMessageId() {
      return this.messageId;
   }

   public byte getSubprotocol() {
      return this.subprotocolReceiver != null ? this.subprotocolReceiver.getSubprotocolId() : this.subprotocol;
   }

   public Object setSubprotocolCookie(Object x) {
      Object existing = this.cookie;
      this.cookie = x;
      return existing;
   }

   public Object getSubprotocolCookie() {
      return this.cookie;
   }

   public BuzzTypes.BuzzMessageState getMessageState() {
      return this.buzzMessageState;
   }

   public BufferSequence getBufferSequence() {
      return this.data;
   }

   public boolean containsSubprotocolHeaderLength(BufferSequence bufferSequence) {
      return this.isFlagSet((short)Short.MIN_VALUE, bufferSequence);
   }

   public void setMessageId(long x) {
      this.messageId = x;
   }

   BuzzMessageTokenImpl(BuzzLogger l, EndPoint endPoint, ConnectionInfo connectionInfo, long messageId, byte subprotocol, BuzzSubprotocolReceiver subprotocolReceiver) {
      this.buzzMessageState = BuzzTypes.BuzzMessageState.OPEN;
      this.isInMap = false;
      this.l = l;
      this.endPoint = endPoint;
      this.messageId = messageId;
      this.subprotocol = subprotocol;
      this.subprotocolReceiver = subprotocolReceiver;
      this.connectionInfo = connectionInfo;
      this.data = null;
   }

   public BuzzMessageTokenImpl(BuzzLogger l, EndPoint endPoint, short flags, long id, BuzzSubprotocolReceiver subprotocolReceiver) {
      this.buzzMessageState = BuzzTypes.BuzzMessageState.OPEN;
      this.isInMap = false;
      this.l = l;
      this.endPoint = endPoint;
      this.messageId = id;
      this.subprotocol = subprotocolReceiver != null ? subprotocolReceiver.getSubprotocolId() : 0;
      this.subprotocolReceiver = subprotocolReceiver;
      this.connectionInfo = null;
      this.data = null;
      this.updateState(flags);
   }

   BuzzMessageTokenImpl(BuzzLogger l, EndPoint endPoint, ConnectionInfo connectionInfo, BufferSequence bufferSequence, short flags, long id, BuzzSubprotocolReceiver subprotocolReceiver) {
      this.buzzMessageState = BuzzTypes.BuzzMessageState.OPEN;
      this.isInMap = false;
      this.l = l;
      this.endPoint = endPoint;
      this.messageId = id;
      this.subprotocol = subprotocolReceiver.getSubprotocolId();
      this.subprotocolReceiver = subprotocolReceiver;
      this.connectionInfo = connectionInfo;
      this.addDataUpdateState(bufferSequence, flags);
   }

   BuzzMessageTokenImpl getNext() {
      this.messageId = this.connectionInfo.getNextId();
      this.buzzMessageState = BuzzTypes.BuzzMessageState.OPEN;
      this.isInMap = false;
      return this;
   }

   ConnectionInfo getConnectionInfo() {
      return this.connectionInfo;
   }

   void setConnectionInfo(ConnectionInfo x) {
      this.connectionInfo = x;
   }

   void isInMap(boolean x) {
      this.isInMap = x;
   }

   boolean isInMap() {
      return this.isInMap;
   }

   BuzzSubprotocolReceiver getSubprotocolReceiver() {
      return this.subprotocolReceiver;
   }

   void addDataUpdateState(BufferSequence buf, short flags) {
      this.data = buf;
      this.updateState(flags);
   }

   void addData(BufferSequence buf) {
      this.data = buf;
   }

   void close() {
      this.buzzMessageState = BuzzTypes.BuzzMessageState.CLOSED;
   }

   void updateState(short flags) {
      boolean isEndMessage = isFlagSet((short)1, flags);
      boolean isOneWayMsg = isFlagSet((short)16384, flags);
      switch (this.buzzMessageState) {
         case OPEN:
            this.buzzMessageState = !isEndMessage ? BuzzTypes.BuzzMessageState.OPEN : (isOneWayMsg ? BuzzTypes.BuzzMessageState.CLOSED : BuzzTypes.BuzzMessageState.HALF_CLOSED);
            break;
         case HALF_CLOSED:
            this.buzzMessageState = isEndMessage ? BuzzTypes.BuzzMessageState.CLOSED : BuzzTypes.BuzzMessageState.HALF_CLOSED;
            break;
         case CLOSED:
            throw new RuntimeException("updateState on CLOSED message");
         default:
            throw new RuntimeException("updateState in illegal state: " + this.buzzMessageState + " when updateState called");
      }

   }

   private boolean isFlagSet(short flagToCheck, BufferSequence buf) {
      ByteBuffer bbUnsafe = buf.getUnsafeBuffer(0);
      int p = buf.getBufferPosition(0);
      BSIS is = buf.getBufferLength(0) >= 3 ? null : new BSIS(this.l, this.endPoint, buf);
      if (is != null) {
         is.skipBytes(1);
      }

      short v = is == null ? bbUnsafe.getShort(p + 1) : is.readShort();
      return isFlagSet(flagToCheck, v);
   }

   static boolean isFlagSet(short flagToCheck, short value) {
      return (value & flagToCheck) == flagToCheck;
   }

   public String toString() {
      return this.getClass().getSimpleName().replaceAll("Impl", "") + "{Id: " + this.messageId + " size: " + (this.data == null ? 0L : this.data.getLength()) + " State: " + this.buzzMessageState.toString() + "}";
   }
}
