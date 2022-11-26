package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.JMSDispatcher;

public final class JMSPushEntry implements Externalizable {
   static final long serialVersionUID = -632448292622511345L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int NEXT_MASK = 256;
   private static final int REDELIVERED_MASK = 512;
   private static final int SEQUENCER_ID_MASK = 1024;
   private static final int BACK_END_MASK = 2048;
   private static final int FRONT_END_MASK = 4096;
   private static final int CLIENTRESPONSIBLEFORACKNOWLEDGE = 8192;
   private static final int REDELIVERED_COUNT_FLAG = 16384;
   public static final int PIPELINE_GENERATION_MASK = 15728640;
   public static final int NO_PIPELINE_GENERATION = 0;
   public static final int EXPIRED_GENERATION = 1048576;
   public static final int CLIENT_ACK_GENERATION = 2097152;
   public static final int LEAST_PIPELINE_GENERATION = 4194304;
   public static final int FIRST_PIPELINE_GENERATION = 15728640;
   private static final int DEBUG_PIPELINE_GENERATION_SHIFT = 20;
   private transient JMSPushEntry next;
   private transient JMSPushEntry nextUnacked;
   private transient JMSPushEntry prevUnacked;
   private transient JMSDispatcher dispatcher;
   private transient long messageSize;
   private JMSID sequencerId;
   private JMSID consumerId;
   private long backEndSequenceNumber;
   private long frontEndSequenceNumber;
   private int pipelineGeneration;
   private int deliveryCount;
   private boolean clientResponsibleForAcknowledge;
   private transient boolean isTransactional = false;

   public JMSPushEntry(JMSID sequencerId, JMSID consumerId, long backEndSequenceNumber, long frontEndSequenceNumber, int deliveryCount, int pipelineGeneration) {
      this.sequencerId = sequencerId;
      this.consumerId = consumerId;
      this.backEndSequenceNumber = backEndSequenceNumber;
      this.frontEndSequenceNumber = frontEndSequenceNumber;
      this.deliveryCount = deliveryCount;
      this.pipelineGeneration = pipelineGeneration;
   }

   public final JMSID getSequencerId() {
      return this.sequencerId;
   }

   public final JMSID getConsumerId() {
      return this.consumerId;
   }

   public boolean isTransactional() {
      return this.isTransactional;
   }

   public void setTransactional() {
      this.isTransactional = true;
   }

   public final long getFrontEndSequenceNumber() {
      return this.frontEndSequenceNumber;
   }

   public final void setFrontEndSequenceNumber(long frontEndSequenceNumber) {
      this.frontEndSequenceNumber = frontEndSequenceNumber;
   }

   public final long getBackEndSequenceNumber() {
      return this.backEndSequenceNumber;
   }

   public final void setBackEndSequenceNumber(long backEndSequenceNumber) {
      this.backEndSequenceNumber = backEndSequenceNumber;
   }

   public int getPipelineGeneration() {
      return this.pipelineGeneration;
   }

   public void setPipelineGeneration(int pipelineGeneration) {
      this.pipelineGeneration = pipelineGeneration;
   }

   public static int nextRecoverGeneration(int generation) {
      if (generation < 4194304) {
         return generation;
      } else {
         generation += 1048576;
         if (generation > 15728640) {
            generation = 4194304;
         }

         return generation;
      }
   }

   public static int displayRecoverGeneration(int generation) {
      return generation >> 20;
   }

   public final long getMessageSize() {
      return this.messageSize;
   }

   public int getDeliveryCount() {
      return this.deliveryCount;
   }

   public final void setMessageSize(long messageSize) {
      this.messageSize = messageSize;
   }

   public final boolean getClientResponsibleForAcknowledge() {
      return this.clientResponsibleForAcknowledge;
   }

   public final void setClientResponsibleForAcknowledge(boolean clientResponsibleForAcknowledge) {
      this.clientResponsibleForAcknowledge = clientResponsibleForAcknowledge;
   }

   public final JMSPushEntry getNext() {
      return this.next;
   }

   public final void setNext(JMSPushEntry pushEntry) {
      this.next = pushEntry;
   }

   public final JMSPushEntry getNextUnacked() {
      return this.nextUnacked;
   }

   public final void setNextUnacked(JMSPushEntry pushEntry) {
      this.nextUnacked = pushEntry;
   }

   public final JMSPushEntry getPrevUnacked() {
      return this.prevUnacked;
   }

   public final void setPrevUnacked(JMSPushEntry pushEntry) {
      this.prevUnacked = pushEntry;
   }

   public final void setDispatcher(JMSDispatcher dispatcher) {
      this.dispatcher = dispatcher;
   }

   public final JMSDispatcher getDispatcher() {
      return this.dispatcher;
   }

   public JMSPushEntry() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.writeExternal(out, (JMSPushRequest)null);
   }

   public void writeExternal(ObjectOutput out, JMSPushRequest pushRequest) throws IOException {
      ObjectOutput piStream;
      if (out instanceof MessageImpl.JMSObjectOutputWrapper) {
         piStream = ((MessageImpl.JMSObjectOutputWrapper)out).getInnerObjectOutput();
      } else {
         piStream = out;
      }

      boolean atLeastDiabloIOStream;
      if (piStream instanceof PeerInfoable) {
         atLeastDiabloIOStream = PeerInfo.VERSION_DIABLO.compareTo(((PeerInfoable)piStream).getPeerInfo()) <= 0;
      } else {
         atLeastDiabloIOStream = false;
      }

      assert atLeastDiabloIOStream || this.pipelineGeneration == 0;

      int mask = 1;
      JMSPushEntry pushEntry = this;
      JMSID lastSequencerId = null;
      if (pushRequest == null) {
         mask |= 6144;
      } else if (pushRequest.getMethodId() == 15620) {
         mask |= 4096;
      } else {
         lastSequencerId = (JMSID)pushRequest.getInvocableId();
         mask |= 2048;
      }

      do {
         mask &= -15746817;
         mask |= this.pipelineGeneration;
         if (pushEntry.next != null) {
            mask |= 256;
         }

         if (pushEntry.deliveryCount > 1) {
            if (atLeastDiabloIOStream) {
               mask |= 16384;
            } else {
               mask |= 512;
            }
         }

         if (pushEntry.getClientResponsibleForAcknowledge()) {
            mask |= 8192;
         }

         if ((mask & 2048) != 0 && pushEntry.sequencerId != lastSequencerId) {
            mask |= 1024;
            lastSequencerId = this.sequencerId;
         }

         out.writeInt(mask);
         if (MessageImpl.debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            this.debugWire("JMSPushEntry.write", mask, ", atLeastDiabloIOStream=" + atLeastDiabloIOStream);
         }

         if ((mask & 1024) != 0) {
            pushEntry.sequencerId.writeExternal(out);
         }

         if ((mask & 2048) != 0) {
            out.writeLong(pushEntry.backEndSequenceNumber);
         }

         if ((mask & 4096) != 0) {
            out.writeLong(pushEntry.frontEndSequenceNumber);
         }

         pushEntry.consumerId.writeExternal(out);
         if ((mask & 16384) != 0) {
            out.writeInt(pushEntry.deliveryCount);
         }

         pushEntry = pushEntry.next;
      } while(pushEntry != null);

   }

   private void debugWire(String prefix, int mask, String suffix) {
      JMSDebug.JMSDispatcher.debug(prefix + " versionInt x" + Integer.toHexString(mask).toUpperCase() + suffix);
   }

   public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {
      this.readExternal(in, (JMSPushRequest)null);
   }

   JMSPushEntry readExternal(ObjectInput in, JMSPushRequest pushRequest) throws ClassNotFoundException, IOException {
      JMSPushEntry pushEntry = this;
      JMSID lastSequencerId = (JMSID)pushRequest.getInvocableId();

      JMSPushEntry prevPushEntry;
      do {
         int mask = in.readInt();
         int version = mask & 255;
         if (MessageImpl.debugWire && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            this.debugWire("JMSPushEntry.read ", mask, ", version=" + version);
         }

         if (version != 1) {
            throw JMSUtilities.versionIOException(version, 1, 1);
         }

         if ((mask & 1024) == 0) {
            pushEntry.sequencerId = lastSequencerId;
         } else {
            pushEntry.sequencerId = new JMSID();
            pushEntry.sequencerId.readExternal(in);
         }

         if ((mask & 2048) != 0) {
            pushEntry.backEndSequenceNumber = in.readLong();
         }

         if ((mask & 4096) != 0) {
            pushEntry.frontEndSequenceNumber = in.readLong();
         }

         pushEntry.consumerId = new JMSID();
         pushEntry.consumerId.readExternal(in);
         pushEntry.setClientResponsibleForAcknowledge((mask & 8192) != 0);
         this.pipelineGeneration = mask & 15728640;
         if ((mask & 16384) != 0) {
            pushEntry.deliveryCount = in.readInt();
         } else if ((mask & 512) != 0) {
            pushEntry.deliveryCount = 2;
         } else {
            pushEntry.deliveryCount = 1;
         }

         prevPushEntry = pushEntry;
         if ((mask & 256) != 0) {
            pushEntry.next = new JMSPushEntry();
         }
      } while((pushEntry = pushEntry.next) != null);

      return prevPushEntry;
   }
}
