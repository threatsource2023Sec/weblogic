package weblogic.messaging.saf.internal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFResult;
import weblogic.messaging.saf.store.SAFStore;

abstract class Conversation {
   protected boolean running;
   protected boolean poisoned;
   protected boolean ordered;
   protected SAFConversationInfo info;
   protected MessageReference firstMessage;
   protected MessageReference lastMessage;
   protected long timeToLive;
   private boolean seenLastMsg;
   private int msgCount;
   protected SAFStore store;
   protected SAFManagerImpl safManager;

   Conversation(SAFConversationInfo info, SAFStore store, SAFManagerImpl safManager) {
      this.info = info;
      this.store = store;
      this.safManager = safManager;
   }

   abstract void close() throws SAFException;

   protected void addMessageToList(MessageReference msgRef) {
      if (msgRef != null) {
         if (this.firstMessage == null) {
            this.firstMessage = msgRef;
            this.lastMessage = msgRef;
         } else {
            this.lastMessage.setNext(msgRef);
            msgRef.setPrev(this.lastMessage);
            this.lastMessage = msgRef;
         }

         ++this.msgCount;
      }
   }

   protected void addMessageToListInorder(MessageReference msgRef) {
      if (msgRef != null) {
         ++this.msgCount;
         if (this.firstMessage == null) {
            this.firstMessage = msgRef;
            this.lastMessage = msgRef;
         } else {
            MessageReference p;
            for(p = this.firstMessage; p != null && p.getSequenceNumber() <= msgRef.getSequenceNumber(); p = p.getNext()) {
               if (p.getSequenceNumber() == msgRef.getSequenceNumber()) {
                  return;
               }
            }

            if (p == null) {
               this.lastMessage.setNext(msgRef);
               msgRef.setPrev(this.lastMessage);
               this.lastMessage = msgRef;
            } else {
               msgRef.setNext(p);
               msgRef.setPrev(p.getPrev());
               if (p.getPrev() != null) {
                  p.getPrev().setNext(msgRef);
               }

               p.setPrev(msgRef);
               if (p == this.firstMessage) {
                  this.firstMessage = msgRef;
               }

            }
         }
      }
   }

   protected void restoreMessages(MessageReference msgRef) {
      MessageReference nextMsg;
      do {
         nextMsg = msgRef.getNext();
         msgRef.setNext((MessageReference)null);
         msgRef.setPrev((MessageReference)null);
         this.addMessageToListInorder(msgRef);
         msgRef = nextMsg;
      } while(nextMsg != null);

   }

   protected void removeMessageFromList(MessageReference msgRef) {
      if (msgRef != null) {
         if (msgRef == this.firstMessage) {
            this.firstMessage = msgRef.getNext();
            if (this.firstMessage != null) {
               this.firstMessage.setPrev((MessageReference)null);
            }

            if (msgRef == this.lastMessage) {
               this.lastMessage = this.firstMessage;
            }
         } else {
            if (msgRef.getNext() != null) {
               msgRef.getNext().setPrev(msgRef.getPrev());
            }

            if (msgRef.getPrev() != null) {
               msgRef.getPrev().setNext(msgRef.getNext());
            }
         }

         --this.msgCount;
      }
   }

   final boolean isDone() {
      return this.firstMessage == null && this.seenLastMsg;
   }

   public SAFConversationInfo getInfo() {
      return this.info;
   }

   public String getName() {
      return this.info.getConversationName();
   }

   synchronized void setSeenLastMsg(boolean seen) {
      this.seenLastMsg = seen;
   }

   synchronized boolean hasSeenLastMsg() {
      return this.seenLastMsg;
   }

   public String getConversationName() {
      return this.info.getConversationName();
   }

   protected static boolean isPoisoned(SAFResult.Result resultCode) {
      switch (resultCode) {
         case CONVERSATIONPOISENED:
         case SAFHRWRITEFAILURE:
         case SAFNOCURRENTTX:
         case SAFTXNOTSTARTED:
         case UNKNOWNCONVERSATION:
         case SAFINTERNALERROR:
         case EXPIRED:
         case CONVERSATIONTIMEOUT:
            return true;
         default:
            return false;
      }
   }

   public void dumpAttributes(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeAttribute("running", String.valueOf(this.running));
      xsw.writeAttribute("poisoned", String.valueOf(this.poisoned));
      xsw.writeAttribute("ordered", String.valueOf(this.ordered));
      xsw.writeAttribute("timeToLive", String.valueOf(this.timeToLive));
      xsw.writeAttribute("seenLastMessage", String.valueOf(this.seenLastMsg));
      xsw.writeAttribute("msgCount", String.valueOf(this.msgCount));
      xsw.writeAttribute("storeName", this.store != null ? (this.store.getStoreName() != null ? this.store.getStoreName() : "") : "");
   }
}
