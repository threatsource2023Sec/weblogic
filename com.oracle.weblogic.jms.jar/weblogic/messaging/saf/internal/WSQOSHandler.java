package weblogic.messaging.saf.internal;

import java.util.List;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFTransport;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.common.AgentDeliverRequest;
import weblogic.messaging.saf.common.AgentDeliverResponse;
import weblogic.messaging.saf.common.SAFRequestImpl;
import weblogic.messaging.saf.common.SAFResultImpl;

public class WSQOSHandler extends QOSHandler {
   private long lastAsyncAckNumHigh;

   public WSQOSHandler(SAFConversationInfo conversationInfo, SAFTransport transport, long raWindowSize) {
      super(conversationInfo, transport);
   }

   private static boolean isDeliverSync(SAFRequest safReq) {
      AgentDeliverRequest agentDeliverRequest = ((SAFRequestImpl)safReq).getAgentRequest();
      return agentDeliverRequest != null && agentDeliverRequest.isSyncAck();
   }

   private final void sendSyncNack(SAFRequest safReq) {
      this.setResult();
      AgentDeliverRequest agentDeliverRequest = ((SAFRequestImpl)safReq).getAgentRequest();
      agentDeliverRequest.notifyResult(new AgentDeliverResponse(this.result));
   }

   protected void sendNack() {
      if (isDeliverSync(this.currentSAFRequest)) {
         this.sendSyncNack(this.currentSAFRequest);
      } else {
         this.sendResult();
      }
   }

   protected void sendAck() {
      MessageReference mref = this.sequenceNumberLowMRef;

      long currSyncSeqNumber;
      for(currSyncSeqNumber = -1L; mref != null; mref = mref.getNext()) {
         if (isDeliverSync(mref.getMessage())) {
            currSyncSeqNumber = this.sendSyncAck(mref);
         }
      }

      if (currSyncSeqNumber < this.lastAsyncAckNumHigh && this.lastAsyncAckNumHigh != 0L) {
         this.sendAsyncAck();
      }

      this.sequenceNumberLowMRef = this.sequenceNumberHighMRef = null;
      this.lastAsyncAckNumHigh = 0L;
   }

   private long sendSyncAck(MessageReference mref) {
      SAFRequestImpl safReq = (SAFRequestImpl)mref.getMessage();
      AgentDeliverRequest agentDeliverRequest = safReq.getAgentRequest();
      long currSyncSeqNumber = safReq.getSequenceNumber();
      List seqNumbers = this.sequence.getAllSequenceNumberRanges();
      AgentDeliverResponse response = new AgentDeliverResponse(new SAFResultImpl(agentDeliverRequest.getConversationInfo(), seqNumbers, Result.SUCCESSFUL, (String)null));
      agentDeliverRequest.notifyResult(response);
      return currSyncSeqNumber;
   }

   private void sendAsyncAck() {
      this.sequenceNumberLow = this.sequenceNumberLow < this.lastAsyncAckNumHigh ? this.sequenceNumberLow : this.lastAsyncAckNumHigh;
      this.sendAckInternal();
   }

   protected final void update(MessageReference mref, SAFResult.Result resultCode) {
      long sequenceNumber = mref.getSequenceNumber();
      this.result.setResultCode(resultCode);
      if (resultCode == Result.SUCCESSFUL) {
         if (this.sequenceNumberLowMRef == null) {
            this.sequenceNumberLowMRef = mref;
            this.sequenceNumberHighMRef = mref;
         } else {
            this.sequenceNumberHighMRef.setNext(mref);
            this.sequenceNumberHighMRef = mref;
         }

         if (sequenceNumber >= this.sequenceNumberHigh) {
            this.sequenceNumberHigh = sequenceNumber;
         }

         if (sequenceNumber <= this.sequenceNumberLow) {
            this.sequenceNumberLow = sequenceNumber;
         }

         if (!isDeliverSync(mref.getMessage()) && sequenceNumber >= this.lastAsyncAckNumHigh) {
            this.lastAsyncAckNumHigh = sequenceNumber;
         }

      }
   }
}
