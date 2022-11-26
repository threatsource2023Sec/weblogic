package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class JMSPushRequest extends Request implements Externalizable {
   static final long serialVersionUID = -7576284721569682185L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int MESSAGE_MASK = 65280;
   private static final int BACK_END_MASK = 65536;
   private static final int NEXT_MASK = 131072;
   private static final int MESSAGE_MASK_SHIFT = 8;
   private MessageImpl message;
   private JMSPushEntry firstPushEntry;
   private JMSPushEntry lastPushEntry;
   private int compressionThreshold = Integer.MAX_VALUE;

   public JMSPushRequest(int invocableType, JMSID invocableId, MessageImpl message) {
      super(invocableId, 15616 | invocableType);
      this.message = message;
   }

   public JMSPushRequest(int invocableType, JMSID invocableId, MessageImpl message, JMSPushEntry pushEntry) {
      super(invocableId, 15616 | invocableType);
      this.message = message;
      this.firstPushEntry = pushEntry;
      if (pushEntry != null) {
         do {
            this.lastPushEntry = pushEntry;
            pushEntry = pushEntry.getNext();
         } while(pushEntry != null);
      }

   }

   public JMSPushRequest(JMSPushRequest pushRequest) {
      super((JMSID)pushRequest.invocableId, pushRequest.methodId);
      this.message = pushRequest.message;
      this.firstPushEntry = pushRequest.firstPushEntry;
      this.lastPushEntry = pushRequest.lastPushEntry;
      this.next = pushRequest.next;
   }

   public final int getCompressionThreshold() {
      return this.compressionThreshold;
   }

   public final void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
   }

   public MessageImpl getMessage() {
      return this.message;
   }

   public void setMessage(MessageImpl message) {
      this.message = message;
   }

   public void setInvocableType(int invocableType) {
      this.methodId = this.methodId & 16776960 | invocableType;
   }

   public void addPushEntry(JMSPushEntry pushEntry) {
      pushEntry.setNext((JMSPushEntry)null);
      if (this.firstPushEntry == null) {
         this.firstPushEntry = pushEntry;
      } else {
         this.lastPushEntry.setNext(pushEntry);
      }

      this.lastPushEntry = pushEntry;
   }

   public JMSPushEntry removePushEntry() {
      JMSPushEntry pushEntry = this.firstPushEntry;
      if (pushEntry != null) {
         this.firstPushEntry = pushEntry.getNext();
         if (this.firstPushEntry == null) {
            this.lastPushEntry = null;
         }
      }

      return pushEntry;
   }

   public long getBackEndSequenceNumber() {
      return this.firstPushEntry.getBackEndSequenceNumber();
   }

   public long getFrontEndSequenceNumber() {
      return this.firstPushEntry.getFrontEndSequenceNumber();
   }

   public void setFirstPushEntry(JMSPushEntry firstPushEntry) {
      this.firstPushEntry = firstPushEntry;
   }

   public JMSPushEntry getFirstPushEntry() {
      return this.firstPushEntry;
   }

   public void setLastPushEntry(JMSPushEntry lastPushEntry) {
      this.lastPushEntry = lastPushEntry;
   }

   public JMSPushEntry getLastPushEntry() {
      return this.lastPushEntry;
   }

   public final JMSPushEntry getPushEntries() {
      return this.firstPushEntry;
   }

   public final void setPushEntries(JMSPushEntry firstPushEntry) {
      if ((this.firstPushEntry = firstPushEntry) == null) {
         this.lastPushEntry = null;
      } else {
         for(this.lastPushEntry = firstPushEntry; this.lastPushEntry.getNext() != null; this.lastPushEntry = this.lastPushEntry.getNext()) {
         }
      }

   }

   public int remoteSignature() {
      return 64;
   }

   public boolean isServerOneWay() {
      return true;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public JMSPushRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      JMSPushRequest pushRequest = this;

      do {
         int mask = pushRequest.message.getType() << 8;
         if (pushRequest.getNext() != null) {
            mask |= 131072;
         }

         out.writeInt(mask | 1);
         if (MessageImpl.debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireInfo("JMSPushEntry.write", mask, (Throwable)null, ", msgType 0x" + Integer.toHexString(pushRequest.message.getType()).toUpperCase() + (this.compressionThreshold == Integer.MAX_VALUE ? ", compressionThreshold==MAX_VALUE" : ", compressionThreshold=" + this.compressionThreshold));
         }

         super.writeExternal(out);
         if (this.compressionThreshold == Integer.MAX_VALUE) {
            pushRequest.message.writeExternal(out);
         } else {
            pushRequest.message.writeExternal(MessageImpl.createJMSObjectOutputWrapper(out, this.compressionThreshold, true));
         }

         pushRequest.firstPushEntry.writeExternal(out, this);
         pushRequest = (JMSPushRequest)pushRequest.getNext();
      } while(pushRequest != null);

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      JMSPushRequest pushRequest = this;

      do {
         int mask = in.readInt();
         int version = mask & 255;
         if (version != 1) {
            StreamCorruptedException sce = JMSUtilities.versionIOException(version, 1, 1);
            debugWireInfo("JMSPushEntry.read ", mask, sce, " threw");
            throw sce;
         }

         try {
            super.readExternal(in);
         } catch (IOException var6) {
            debugWireInfo("JMSPushEntry.read ", mask, var6, " threw");
            throw var6;
         } catch (ClassNotFoundException var7) {
            debugWireInfo("JMSPushEntry.read ", mask, var7, " threw");
            throw var7;
         }

         byte messageType = (byte)((mask & '\uff00') >>> 8);
         if (MessageImpl.debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireInfo("JMSPushEntry.read ", mask, (Throwable)null, ", msgType 0x" + Integer.toHexString(messageType).toUpperCase());
         }

         pushRequest.message = MessageImpl.createMessageImpl(messageType);
         pushRequest.message.readExternal(in);
         pushRequest.firstPushEntry = new JMSPushEntry();
         pushRequest.lastPushEntry = pushRequest.firstPushEntry.readExternal(in, pushRequest);
         if ((mask & 131072) != 0) {
            pushRequest.setNext(new JMSPushRequest());
         }
      } while((pushRequest = (JMSPushRequest)pushRequest.getNext()) != null);

   }

   private static void debugWireInfo(String prefix, int mask, Throwable exception, String suffix) {
      if (MessageImpl.debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
         if (exception != null) {
            JMSDebug.JMSDispatcher.debug(prefix + " versionInt 0x" + Integer.toHexString(mask).toUpperCase() + suffix, exception);
         } else {
            JMSDebug.JMSDispatcher.debug(prefix + " versionInt 0x" + Integer.toHexString(mask).toUpperCase() + suffix);
         }
      }

   }
}
