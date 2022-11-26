package weblogic.messaging.saf.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFConversationNotAvailException;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFTransport;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.messaging.saf.common.SAFResultImpl;

public abstract class QOSHandler {
   protected long sequenceNumberLow = 1L;
   protected long sequenceNumberHigh = 1L;
   protected MessageReference sequenceNumberLowMRef;
   protected MessageReference sequenceNumberHighMRef;
   protected SAFResultImpl result = new SAFResultImpl();
   protected SAFRequest currentSAFRequest;
   protected SAFTransport transport;
   protected SAFConversationInfo conversationInfo;
   protected Sequence sequence;

   public QOSHandler(SAFConversationInfo info, SAFTransport transport) {
      this.conversationInfo = info;
      this.transport = transport;
   }

   void preProcess(MessageReference msgRef) {
      this.result = new SAFResultImpl();
      this.currentSAFRequest = msgRef.getMessage();
      this.sequenceNumberHigh = msgRef.getSequenceNumber();
      this.sequenceNumberLow = msgRef.getSequenceNumber();
   }

   void setCurrentSAFRequest(SAFRequest request) {
      this.currentSAFRequest = request;
   }

   void setSAFException(SAFException se) {
      this.result.setSAFException(se);
   }

   static QOSHandler getQOSHandler(SAFConversationInfo conversationInfo, SAFTransport transport, long raWindowSize) {
      switch (conversationInfo.getDestinationType()) {
         case 2:
         default:
            return new WSQOSHandler(conversationInfo, transport, raWindowSize);
      }
   }

   void setSequence(Sequence sequence) {
      this.sequence = sequence;
   }

   protected abstract void update(MessageReference var1, SAFResult.Result var2);

   protected abstract void sendAck();

   protected abstract void sendNack();

   protected final void sendAckInternal() {
      this.sendResult();
      if (SAFDebug.SAFVerbose.isDebugEnabled()) {
         List numbers = this.result.getSequenceNumbers();
         String str = null;

         long start;
         long end;
         for(Iterator i = numbers.iterator(); i.hasNext(); str = str + "< sequenceNumberLow=" + start + " sequenceNumberHigh=" + end + "> ") {
            start = (Long)i.next();
            end = (Long)i.next();
         }

         SAFDebug.SAFVerbose.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
         SAFDebug.SAFVerbose.debug(" == execute() sendResult: Successfully sent  ack conversationInfo" + this.conversationInfo + str);
         SAFDebug.SAFVerbose.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      }

   }

   protected void sendResult() {
      this.setResult();
      this.transport.sendResult(this.result);
   }

   protected final SAFResult getResult() {
      return this.result;
   }

   protected void setResult() {
      if (this.result.getResultCode() == Result.SUCCESSFUL) {
         this.result.setSequenceNumbers(this.sequence.getAllSequenceNumberRanges());
      } else {
         long currentSAFRequestSeqNum = this.currentSAFRequest.getSequenceNumber();
         List seqNumbers = new ArrayList();
         Long num = new Long(currentSAFRequestSeqNum);
         seqNumbers.add(num);
         seqNumbers.add(num);
         this.result.setSequenceNumbers(seqNumbers);
      }

      this.result.setConversationInfo(this.conversationInfo);
   }

   public SAFResult.Result handleEndpointDeliveryFailure(Throwable e, SAFRequest request) {
      SAFException safDeliverException = null;
      if (e instanceof SAFException) {
         safDeliverException = (SAFException)e;
         if (e instanceof SAFConversationNotAvailException) {
            this.result.setResultCode(Result.CONVERSATIONTERMINATED);
         } else {
            this.result.setResultCode(Result.ENDPOITNNOTAVAIL);
         }
      } else {
         this.result.setResultCode(Result.SAFINTERNALERROR);
         safDeliverException = new SAFException(e.getMessage(), e);
      }

      this.result.setSAFException(safDeliverException);
      if (SAFDebug.SAFVerbose.isDebugEnabled()) {
         e.printStackTrace();
      }

      return this.result.getResultCode();
   }
}
