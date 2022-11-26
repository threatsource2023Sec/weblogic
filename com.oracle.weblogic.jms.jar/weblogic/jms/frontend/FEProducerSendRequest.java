package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.client.AsyncSendCallback;
import weblogic.jms.common.Destination;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.messaging.dispatcher.Response;

public final class FEProducerSendRequest extends Request implements CompletionListener, Externalizable {
   static final long serialVersionUID = -6258361113844425358L;
   private static final int EXTVERSION61 = 1;
   private static final int EXTVERSION81 = 2;
   private static final int EXTVERSION902 = 4;
   private static final int EXTVERSION1221 = 8;
   private static final int EXTVERSION = 8;
   private static final int VERSION_MASK = 255;
   private static final int MESSAGE_MASK = 65280;
   private static final int DESTINATION_MASK = 983040;
   private static final int TIMEOUT_MASK = 1048576;
   private static final int NORESPONSE_MASK = 2097152;
   public static final int CHECK_UNIT_OF_ORDER = 2097152;
   public static final int JMS_ASYNC_SEND_MASK = 4194304;
   public static final int USE_SESSION_ID_MASK = 8388608;
   private static final int MESSAGE_MASK_SHIFT = 8;
   private static final int DESTINATION_MASK_SHIFT = 16;
   static final int START = 0;
   static final int CONTINUE = 1;
   static final int TRY = 2;
   static final int RETRY = 3;
   static final int AFTER_START_IP = 4;
   static final int AFTER_POST_AUTH_IP = 5;
   static final int RETURN_FROM_START_IP = 6;
   static final int RETURN_FROM_POST_AUTH_IP = 7;
   static final int RELEASE_FANOUT = 8;
   private transient boolean infected;
   private MessageImpl message;
   private DestinationImpl destination;
   private long sendTimeout;
   private transient JMSDispatcher dispatcher;
   private transient Object failover;
   private transient int checkUnitOfOrder;
   private transient Serializable uooKey;
   private transient boolean uooNoFailover;
   private transient int numberOfRetries;
   private transient FEProducerSendRequest[] subRequest;
   private transient String unitForRouting;
   private transient String pathServiceJndiName;
   private transient int dataLen;
   private transient Request backendRequest;
   private transient boolean noResponse;
   private transient Object feDDHandler;
   private transient Object serverInfo;
   private int compressionThreshold;
   private transient Object authenticatedSubject;
   private transient AsyncSendCallback asyncCallback;
   private boolean jmsAsyncSend;
   private boolean useSessionId;
   private boolean pushPopSubject;

   public FEProducerSendRequest(JMSID producerId, MessageImpl message, DestinationImpl destination, long sendTimeout, int compressionThreshold, boolean useSessionId, AsyncSendCallback asyncCallback) {
      this(producerId, message, destination, sendTimeout, compressionThreshold, useSessionId);
      if (asyncCallback != null) {
         this.asyncCallback = asyncCallback;
         this.jmsAsyncSend = asyncCallback.isJMSAsyncSend();
         this.setListener(this);
      }

   }

   public FEProducerSendRequest(JMSID producerId, MessageImpl message, DestinationImpl destination, long sendTimeout, int compressionThreshold) {
      this(producerId, message, destination, sendTimeout, compressionThreshold, false);
   }

   public FEProducerSendRequest(JMSID producerId, MessageImpl message, DestinationImpl destination, long sendTimeout, int compressionThreshold, boolean useSessionId) {
      super(producerId, 5129);
      this.dataLen = 0;
      this.noResponse = false;
      this.compressionThreshold = Integer.MAX_VALUE;
      this.asyncCallback = null;
      this.jmsAsyncSend = false;
      this.useSessionId = false;
      this.pushPopSubject = false;
      this.message = message;
      this.destination = destination;
      this.sendTimeout = sendTimeout;
      this.compressionThreshold = compressionThreshold;
      this.useSessionId = useSessionId;
   }

   public final boolean isJMSAsyncSend() {
      return this.jmsAsyncSend;
   }

   public final boolean useSessionId() {
      return this.useSessionId;
   }

   public final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   public void rememberOneWayState() {
   }

   public void setNoResponse(boolean noResponse) {
      this.noResponse = noResponse;
   }

   void setFEDDHandler(Object feDDHandler) {
      this.feDDHandler = feDDHandler;
   }

   Object getFEDDHandler() {
      return this.feDDHandler;
   }

   void setServerInfo(Object serverInfo) {
      this.serverInfo = serverInfo;
   }

   Object getServerInfo() {
      return this.serverInfo;
   }

   public boolean isNoResponse() {
      return this.noResponse;
   }

   public MessageImpl getMessage() {
      return this.message;
   }

   DestinationImpl getDestination() {
      return this.destination;
   }

   void setDestination(DestinationImpl destination) {
      this.destination = destination;
   }

   public int getDataLen() {
      return this.dataLen;
   }

   JMSDispatcher getDispatcher() {
      return this.dispatcher;
   }

   void setDispatcher(JMSDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   void setFailover(Object failover) {
      this.failover = failover;
   }

   Object getFailover() {
      return this.failover;
   }

   public long getSendTimeout() {
      return this.sendTimeout;
   }

   void setInfected(boolean infected) {
      this.infected = infected;
   }

   boolean isInfected() {
      return this.infected;
   }

   void setUooNoFailover(boolean nfo) {
      this.uooNoFailover = nfo;
   }

   boolean getUOONoFailover() {
      return this.uooNoFailover;
   }

   void setUnitForRouting(String unitForRouting) {
      this.unitForRouting = unitForRouting;
   }

   String getUnitForRouting() {
      return this.unitForRouting;
   }

   void setPathServiceJndiName(String pathServiceJndiName) {
      this.pathServiceJndiName = pathServiceJndiName;
   }

   String getPathServiceJndiName() {
      return this.pathServiceJndiName;
   }

   public void setCheckUOO(int c) {
      this.checkUnitOfOrder = c;
   }

   int getCheckUOO() {
      return this.checkUnitOfOrder;
   }

   int getNumberOfRetries() {
      return this.numberOfRetries;
   }

   public void setNumberOfRetries(int nor) {
      this.numberOfRetries = nor;
   }

   FEProducerSendRequest[] getSubRequest() {
      return this.subRequest;
   }

   void setSubRequest(FEProducerSendRequest[] arg) {
      this.subRequest = arg;
   }

   public Request getBackendRequest() {
      return this.backendRequest;
   }

   public void setBackendRequest(Request bepsr) {
      this.backendRequest = bepsr;
   }

   public void setUOOInfo(Serializable uooKey) {
      this.uooKey = uooKey;
   }

   public Object getUOOKey() {
      return this.uooKey;
   }

   public int remoteSignature() {
      return this.noResponse ? 64 : 19;
   }

   public boolean isServerOneWay() {
      return this.remoteSignature() == 64;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return new JMSProducerSendResponse();
   }

   public void onCompletion(Object result) {
      if (this.asyncCallback != null) {
         this.asyncCallback.processCompletion(result, this, this.destination, this.message);
      }
   }

   public void onException(Throwable throwable) {
      if (this.asyncCallback != null) {
         this.asyncCallback.processException(throwable);
      }
   }

   public FEProducerSendRequest() {
      this.dataLen = 0;
      this.noResponse = false;
      this.compressionThreshold = Integer.MAX_VALUE;
      this.asyncCallback = null;
      this.jmsAsyncSend = false;
      this.useSessionId = false;
      this.pushPopSubject = false;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int startPos = 0;
      out = this.getVersionedStream(out);
      PeerInfo peerInfo = ((PeerInfoable)out).getPeerInfo();
      if (peerInfo.compareTo(PeerInfo.VERSION_61) < 0) {
         throw JMSUtilities.versionIOException(0, 1, 2);
      } else {
         byte version;
         if (peerInfo.compareTo(PeerInfo.VERSION_81) < 0) {
            version = 1;
         } else if (peerInfo.compareTo(PeerInfo.VERSION_901) < 0) {
            version = 2;
         } else if (peerInfo.compareTo(PeerInfo.VERSION_1221) < 0) {
            version = 4;
         } else {
            version = 8;
         }

         boolean jmsasync = this.isJMSAsyncSend();
         if (jmsasync && version < 8) {
            throw new IOException("JMS asynchnorous send can not be supported by front-end server version [" + peerInfo + "]");
         } else {
            int mask = version | this.message.getType() << 8;
            mask |= Destination.getDestinationType(this.destination, 16);
            if (version >= 2 && this.sendTimeout != 10L) {
               mask |= 1048576;
            }

            if (version >= 4 && this.noResponse) {
               mask |= 2097152;
            }

            if (version >= 8 && jmsasync) {
               mask |= 4194304;
            }

            if (version >= 8 && this.useSessionId) {
               mask |= 8388608;
            }

            out.writeInt(mask);
            super.writeExternal(out);
            if (this.noResponse || jmsasync) {
               startPos = MessageImpl.getPosition(out);
            }

            this.message.writeExternal(MessageImpl.createJMSObjectOutputWrapper(out, this.compressionThreshold, false));
            if (this.noResponse || jmsasync) {
               this.dataLen = MessageImpl.getPosition(out) - startPos;
            }

            if (this.destination != null) {
               this.destination.writeExternal(out);
            }

            if ((mask & 1048576) != 0) {
               out.writeLong(this.sendTimeout);
            }

         }
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version >= 1 && version <= 8) {
         super.readExternal(in);
         int type = (mask & '\uff00') >>> 8;
         this.message = MessageImpl.createMessageImpl((byte)type);
         this.message.readExternal(in);
         int dtype = (mask & 983040) >>> 16;
         this.destination = Destination.createDestination((byte)dtype, in);
         if ((mask & 1048576) != 0) {
            this.sendTimeout = in.readLong();
         }

         this.noResponse = (mask & 2097152) != 0;
         this.jmsAsyncSend = (mask & 4194304) != 0;
         this.useSessionId = (mask & 8388608) != 0;
      } else {
         throw JMSUtilities.versionIOException(version, 1, 8);
      }
   }

   void setUpPushPopSubject(boolean val) {
      this.pushPopSubject = val;
   }

   boolean getPushPopSubject() {
      return this.pushPopSubject;
   }

   void setAuthenticatedSubject(Object currentSubject) {
      this.authenticatedSubject = currentSubject;
   }

   Object getAuthenticatedSubject() {
      return this.authenticatedSubject;
   }
}
