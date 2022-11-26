package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.Response;

public final class BESessionCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 1492850028849803175L;
   private static final int EXTVERSION62 = 1;
   private static final int EXTVERSION70 = 2;
   private static final int EXTVERSION90 = 3;
   private static final int EXTVERSION1221 = 4;
   private static final int EXTVERSION = 4;
   private static final int VERSION_MASK = 255;
   private static final int TRANSACTED_MASK = 256;
   private static final int STOPPED_MASK = 1024;
   private static final int XA_SESSION_MASK = 2048;
   private static final int ACKNOWLEDGE_MODE_MASK = 267386880;
   private static final int ACKNOWLEDGE_MODE_SHIFT = 20;
   private DispatcherId feDispatcherId;
   private JMSID connectionId;
   private JMSID sessionId;
   private JMSID sequencerId;
   private boolean transacted;
   private boolean xaSession;
   private int acknowledgeMode;
   private boolean isStopped;
   private long startStopSequenceNumber;
   private String connectionAddress = null;
   private transient String pushWorkManager;
   private byte clientVersion = 1;
   private DispatcherWrapper feDispatcherWrapper;

   public BESessionCreateRequest(DispatcherId feDispatcherId, DispatcherWrapper feDispatcherWrapper, JMSID connectionId, JMSID sessionId, JMSID sequencerId, boolean transacted, boolean xaSession, int acknowledgeMode, boolean isStopped, long startStopSequenceNumber, byte clientVersion, String connectionAddress, String workManager) {
      super((JMSID)null, 13570);
      this.feDispatcherId = feDispatcherId;
      this.feDispatcherWrapper = feDispatcherWrapper;
      this.connectionId = connectionId;
      this.sessionId = sessionId;
      this.sequencerId = sequencerId;
      this.transacted = transacted;
      this.xaSession = xaSession;
      this.acknowledgeMode = acknowledgeMode;
      this.isStopped = isStopped;
      this.startStopSequenceNumber = startStopSequenceNumber;
      this.clientVersion = clientVersion;
      this.connectionAddress = connectionAddress;
      this.pushWorkManager = workManager;
   }

   public final DispatcherId getFEDispatcherId() {
      return this.feDispatcherId;
   }

   public final DispatcherWrapper getFEDispatcherWrapper() {
      return this.feDispatcherWrapper;
   }

   public final JMSID getConnectionId() {
      return this.connectionId;
   }

   public final JMSID getSessionId() {
      return this.sessionId;
   }

   public final String getConnectionAddress() {
      return this.connectionAddress;
   }

   public final JMSID getSequencerId() {
      return this.sequencerId;
   }

   public final boolean getTransacted() {
      return this.transacted;
   }

   public final boolean getXASession() {
      return this.xaSession;
   }

   public final int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public final boolean getIsStopped() {
      return this.isStopped;
   }

   public final long getStartStopSequenceNumber() {
      return this.startStopSequenceNumber;
   }

   public String getPushWorkManager() {
      return this.pushWorkManager;
   }

   public final byte getClientVersion() {
      return this.clientVersion;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BESessionCreateRequest() {
   }

   private byte getVersion(Object oo) {
      if (oo instanceof PeerInfoable) {
         PeerInfo peerInfo = ((PeerInfoable)oo).getPeerInfo();
         if (peerInfo.compareTo(PeerInfo.VERSION_70) < 0) {
            return 1;
         }
      }

      return 4;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getVersion(out) & 255;
      int mask = version | this.acknowledgeMode << 20;
      if (this.transacted) {
         mask |= 256;
      }

      if (this.xaSession) {
         mask |= 2048;
      }

      if (this.isStopped) {
         mask |= 1024;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.feDispatcherId.writeExternal(out);
      this.connectionId.writeExternal(out);
      this.sessionId.writeExternal(out);
      this.sequencerId.writeExternal(out);
      out.writeLong(this.startStopSequenceNumber);
      if (version >= 2) {
         out.writeByte(this.clientVersion);
      }

      if (version >= 3) {
         out.writeUTF(this.connectionAddress);
      }

      if (version >= 4) {
         this.feDispatcherWrapper.writeExternal(out);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1 && version != 2 && version != 3 && version != 4) {
         throw JMSUtilities.versionIOException(version, 1, 4);
      } else {
         super.readExternal(in);
         this.feDispatcherId = new DispatcherId();
         this.feDispatcherId.readExternal(in);
         this.connectionId = new JMSID();
         this.connectionId.readExternal(in);
         this.sessionId = new JMSID();
         this.sessionId.readExternal(in);
         this.sequencerId = new JMSID();
         this.sequencerId.readExternal(in);
         this.startStopSequenceNumber = in.readLong();
         if (version >= 2) {
            this.clientVersion = in.readByte();
         }

         if (version >= 3) {
            this.connectionAddress = in.readUTF();
         }

         if (version >= 4) {
            this.feDispatcherWrapper = new DispatcherWrapper();
            this.feDispatcherWrapper.readExternal(in);
         }

         this.acknowledgeMode = mask >> 20;
         this.transacted = (mask & 256) != 0;
         this.xaSession = (mask & 2048) != 0;
         this.isStopped = (mask & 1024) != 0;
      }
   }
}
