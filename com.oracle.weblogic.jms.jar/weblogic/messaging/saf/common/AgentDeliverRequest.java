package weblogic.messaging.saf.common;

import java.util.ArrayList;
import java.util.List;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.internal.AgentImpl;
import weblogic.messaging.saf.internal.ConversationReassembler;
import weblogic.messaging.saf.internal.ReceivingAgentImpl;

public final class AgentDeliverRequest {
   private final SAFRequest request;
   private final SAFConversationInfo conversationInfo;
   private final boolean syncAck;
   private Object result;
   private Throwable throwableResponse;
   private int numWaiting;

   public AgentDeliverRequest(SAFConversationInfo conversationInfo, SAFRequest request, boolean syncAck) {
      this.conversationInfo = conversationInfo;
      this.request = request;
      this.syncAck = syncAck;
   }

   public SAFRequest getRequest() {
      return this.request;
   }

   public final boolean isSyncAck() {
      return this.syncAck;
   }

   public SAFConversationInfo getConversationInfo() {
      return this.conversationInfo;
   }

   public AgentDeliverResponse finishDeliver(AgentImpl agent) throws SAFException {
      ConversationReassembler conversation = ((ReceivingAgentImpl)agent).getConversation(this.getConversationInfo());
      if (conversation == null) {
         return this.processDeliverFailure(Result.UNKNOWNCONVERSATION);
      } else if (conversation.isClosed()) {
         return this.processDeliverFailure(Result.CONVERSATIONTERMINATED);
      } else {
         ((SAFRequestImpl)this.request).setAgentRequest(this);
         conversation.addMessage(this.request);
         return !this.syncAck ? new AgentDeliverResponse((SAFResultImpl)null) : this.getResult();
      }
   }

   private AgentDeliverResponse processDeliverFailure(SAFResult.Result fault) throws SAFException {
      if (this.syncAck) {
         List list = new ArrayList();
         list.add(new Long(0L));
         list.add(new Long(0L));
         return new AgentDeliverResponse(new SAFResultImpl(this.getConversationInfo(), list, fault, "Cannot synchronously deliver message to Endpoint: " + fault.getDescription()));
      } else {
         throw new SAFException("Cannot asynchronously deliver message to Endpoint for async : " + fault.getDescription());
      }
   }

   public final synchronized void notifyResult(AgentDeliverResponse result) {
      this.result = result;
      if (this.numWaiting > 0) {
         this.notify();
      }

   }

   private synchronized AgentDeliverResponse getResult() throws SAFException {
      while(this.result == null && this.throwableResponse == null) {
         this.sleepTillNotified();
      }

      if (this.throwableResponse != null) {
         if (this.throwableResponse instanceof RuntimeException) {
            throw (RuntimeException)this.throwableResponse;
         } else if (this.throwableResponse instanceof SAFException) {
            throw (SAFException)this.throwableResponse;
         } else {
            throw new SAFException(this.throwableResponse.getMessage(), this.throwableResponse);
         }
      } else {
         return (AgentDeliverResponse)this.result;
      }
   }

   private void sleepTillNotified() {
      if (SAFDebug.SAFVerbose.isDebugEnabled()) {
         SAFDebug.SAFVerbose.debug(this + " is in sleepTillNotify()");
      }

      boolean var10 = false;

      Object savedError;
      Object savedRuntimeException;
      boolean test;
      label151: {
         try {
            var10 = true;
            ++this.numWaiting;
            this.wait();
            if (SAFDebug.SAFVerbose.isDebugEnabled()) {
               SAFDebug.SAFVerbose.debug(this + " is waken up from sleepTillNotify()");
               var10 = false;
            } else {
               var10 = false;
            }
            break label151;
         } catch (InterruptedException var11) {
            var10 = false;
         } finally {
            if (var10) {
               --this.numWaiting;
               if (this.numWaiting > 0) {
                  this.notify();
               }

               Error savedError = null;
               RuntimeException savedRuntimeException = null;
               boolean test = false;
               if (test) {
                  if (savedError != null) {
                     throw savedError;
                  }

                  if (savedRuntimeException != null) {
                     throw savedRuntimeException;
                  }
               }

            }
         }

         --this.numWaiting;
         if (this.numWaiting > 0) {
            this.notify();
         }

         savedError = null;
         savedRuntimeException = null;
         test = false;
         if (test) {
            if (savedError != null) {
               throw savedError;
            }

            if (savedRuntimeException != null) {
               throw savedRuntimeException;
            }
         }

         return;
      }

      --this.numWaiting;
      if (this.numWaiting > 0) {
         this.notify();
      }

      savedError = null;
      savedRuntimeException = null;
      test = false;
      if (test) {
         if (savedError != null) {
            throw savedError;
         }

         if (savedRuntimeException != null) {
            throw savedRuntimeException;
         }
      }

   }
}
