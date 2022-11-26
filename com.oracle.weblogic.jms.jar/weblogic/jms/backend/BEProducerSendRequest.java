package weblogic.jms.backend;

import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;
import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.path.helper.KeyString;
import weblogic.work.InheritableThreadContext;

public final class BEProducerSendRequest extends Request implements Externalizable, CompletionListener {
   static final long serialVersionUID = -3794283731017311093L;
   private static final int VERSION61 = 1;
   private static final int VERSION81 = 2;
   private static final int VERSION902 = 4;
   private static final int VERSION1221 = 8;
   private static final int EXTVERSION = 8;
   private static final int VERSION_MASK = 255;
   private static final int MESSAGE_TYPE_MASK = 65280;
   private static final int CONNECTION_ID_MASK = 65536;
   private static final int TIMEOUT_MASK = 131072;
   private static final int PRODUCER_ID_MASK = 262144;
   private static final int JMS_ASYNC_SEND_MASK = 524288;
   private static final int SESSION_ID_MASK = 1048576;
   public static final int CHECK_UNIT_OF_ORDER = 2097152;
   private static final int MESSAGE_TYPE_SHIFT = 8;
   private long sendTimeout;
   static final int SEND_RUNNING_WITHOUT_BLOCKING = 500;
   static final int SEND_BLOCKED_WAITING_FOR_QUOTA = 501;
   static final int SEND_QUOTA_GRANTED = 502;
   static final int SEND_TIMED_OUT_WAITING_FOR_QUOTA = 503;
   static final int SEND_UNIT_OF_ORDER_PATH_SERVICE = 504;
   private MessageImpl message;
   private JMSID connectionId;
   private JMSID sessionId;
   private int checkUnitOfOrder;
   private JMSID producerId;
   private boolean jmsAsyncSend;
   private transient KeyString uooKey;
   private transient BEUOOMember uooMember;
   private transient CompletionRequest completionRequest;
   private transient KernelRequest kernelSendRequest;
   private transient BEUOOState.State uooState;
   private transient InheritableThreadContext context;
   private transient Sequence sequence;

   public BEProducerSendRequest(JMSID destinationId, MessageImpl message, JMSID connectionId, long sendTimeout, JMSID producerId) {
      this(destinationId, message, connectionId, sendTimeout, producerId, false);
   }

   public BEProducerSendRequest(JMSID destinationId, MessageImpl message, JMSID connectionId, long sendTimeout, JMSID producerId, boolean jmsAsyncSend) {
      this(destinationId, message, connectionId, (JMSID)null, sendTimeout, producerId, jmsAsyncSend);
   }

   public BEProducerSendRequest(JMSID destinationId, MessageImpl message, JMSID connectionId, JMSID sessionId, long sendTimeout, JMSID producerId, boolean jmsAsyncSend) {
      super(destinationId, 12052);
      this.sessionId = null;
      this.producerId = null;
      this.jmsAsyncSend = false;
      this.producerId = producerId;
      this.message = message;
      this.connectionId = connectionId;
      this.sessionId = sessionId;
      this.sendTimeout = sendTimeout;
      this.jmsAsyncSend = jmsAsyncSend;
   }

   MessageImpl getMessage() {
      return this.message;
   }

   JMSID getConnectionId() {
      return this.connectionId;
   }

   JMSID getSessionId() {
      return this.sessionId;
   }

   JMSID getProducerId() {
      return this.producerId;
   }

   public boolean isJMSAsyncSend() {
      return this.jmsAsyncSend;
   }

   public int remoteSignature() {
      return 35;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSProducerSendResponse();
   }

   public void setCheckUOO(int c) {
      this.checkUnitOfOrder = c;
   }

   int getCheckUOO() {
      return this.checkUnitOfOrder;
   }

   public void setUOOInfo(KeyString uooKey, BEUOOMember uooMember, CompletionRequest completionRequest) {
      this.uooKey = uooKey;
      this.uooMember = uooMember;
      this.completionRequest = completionRequest;
   }

   public KeyString getUOOKey() {
      return this.uooKey;
   }

   public BEUOOMember getUOOMember() {
      return this.uooMember;
   }

   public CompletionRequest getCompletionRequest() {
      return this.completionRequest;
   }

   public void onCompletion(CompletionRequest cr, Object result) {
      this.resumeExecution(false);
   }

   public void onException(CompletionRequest cr, Throwable throwable) {
      this.resumeRequest(throwable, false);
   }

   Sequence getSequence() {
      return this.sequence;
   }

   void setSequence(Sequence sequence) {
      this.sequence = sequence;
   }

   public BEProducerSendRequest() {
      this.sessionId = null;
      this.producerId = null;
      this.jmsAsyncSend = false;
   }

   private byte getVersionForWrite(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_61) < 0) {
            throw JMSUtilities.versionIOException(0, 1, 2);
         } else if (pi.compareTo(PeerInfo.VERSION_81) < 0) {
            return 1;
         } else if (pi.compareTo(PeerInfo.VERSION_901) < 0) {
            return 2;
         } else {
            return (byte)(pi.compareTo(PeerInfo.VERSION_1221) < 0 ? 4 : 8);
         }
      } else {
         return 4;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getVersionForWrite(out) & 255;
      boolean jmsasync = this.isJMSAsyncSend();
      if (jmsasync && version < 8) {
         throw new IOException("JMS asynchnorous send can be supported by back-end server version [" + (out instanceof PeerInfoable ? ((PeerInfoable)out).getPeerInfo() : version) + "]");
      } else {
         int mask = version;
         int type = this.message.getType();
         if (type != 0) {
            mask = version | type << 8;
         }

         if (this.connectionId != null) {
            mask |= 65536;
         }

         if (this.producerId != null) {
            mask |= 262144;
         }

         if (version >= 2 && this.sendTimeout != 10L) {
            mask |= 131072;
         }

         if (version >= 8 && this.sessionId != null) {
            mask |= 1048576;
         }

         mask |= this.checkUnitOfOrder;
         if (version >= 8 && jmsasync) {
            mask |= 524288;
         }

         out.writeInt(mask);
         super.writeExternal(out);
         this.message.writeExternal(out);
         if (this.connectionId != null) {
            this.connectionId.writeExternal(out);
         }

         if ((mask & 131072) != 0) {
            out.writeLong(this.sendTimeout);
         }

         if ((mask & 262144) != 0) {
            this.producerId.writeExternal(out);
         }

         if (version >= 8 && this.sessionId != null) {
            this.sessionId.writeExternal(out);
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 8 && version != 4) {
         throw JMSUtilities.versionIOException(version, 4, 8);
      } else {
         super.readExternal(in);
         byte type = (byte)((mask & '\uff00') >> 8);
         this.message = MessageImpl.createMessageImpl(type);
         this.message.readExternal(in);
         if ((mask & 65536) != 0) {
            this.connectionId = new JMSID();
            this.connectionId.readExternal(in);
         }

         if ((mask & 131072) != 0) {
            this.sendTimeout = in.readLong();
         } else {
            this.sendTimeout = 10L;
         }

         this.checkUnitOfOrder = mask & 2097152;
         if ((mask & 262144) != 0) {
            this.producerId = new JMSID();
            this.producerId.readExternal(in);
         }

         this.jmsAsyncSend = (mask & 524288) != 0;
         if ((mask & 1048576) != 0) {
            this.sessionId = new JMSID();
            this.sessionId.readExternal(in);
         }

      }
   }

   long getSendTimeout() {
      return this.sendTimeout;
   }

   void setKernelRequest(KernelRequest kernelRequest) {
      this.kernelSendRequest = kernelRequest;
   }

   KernelRequest getKernelRequest() {
      return this.kernelSendRequest;
   }

   BEUOOState.State getUooState() {
      return this.uooState;
   }

   void setUooState(BEUOOState.State arg) {
      this.uooState = arg;
   }

   void restoreResources(boolean success) {
      BEUOOState.State stableUOOState = this.uooState;
      if (stableUOOState != null) {
         synchronized(this) {
            if (!this.hasResults()) {
               return;
            }
         }

         stableUOOState.removeReference(this, success);
      }
   }

   void rememberThreadContext() {
      if (this.context == null) {
         this.context = InheritableThreadContext.getContext();
      }

   }

   public void run() {
      boolean contextPushed = false;

      try {
         try {
            if (this.context != null) {
               this.context.push();
               contextPushed = true;
            }
         } catch (Throwable var7) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               ByteArrayOutputStream ostr = new ByteArrayOutputStream();
               var7.printStackTrace(new PrintStream(ostr));
               JMSDebug.JMSBackEnd.debug("<" + Thread.currentThread().getName() + "> Failed to push the InheritableThreadContext " + this.context + "\n" + ostr);
            }
         }

         super.run();
      } finally {
         if (this.context != null && contextPushed) {
            this.context.pop();
         }

      }

   }

   JMSProducerSendResponse setupSendResponse() {
      JMSProducerSendResponse sendResponse = new JMSProducerSendResponse(this.message.getId());
      this.setResult(sendResponse);
      return sendResponse;
   }
}
