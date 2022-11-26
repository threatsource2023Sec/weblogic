package weblogic.messaging.saf.internal;

import java.util.ArrayList;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.saf.SAFRequest;
import weblogic.messaging.saf.common.SAFRequestImpl;

public final class MessageReference {
   private final SAFRequest message;
   private MessageElement element;
   private final ArrayList faultCodes = new ArrayList();
   private RetryController retryController;
   private MessageReference prev;
   private MessageReference next;

   MessageReference(MessageElement element, double retryDelayMultiplier, long retryDelayBase, long retryDelayMaximum) {
      this.element = element;
      this.message = (SAFRequestImpl)element.getMessage();
      this.retryController = new RetryController(retryDelayBase, retryDelayMaximum, retryDelayMultiplier);
   }

   MessageReference(SAFRequest request) {
      this.message = request;
   }

   SAFRequest getMessage() {
      return this.message;
   }

   MessageElement getElement() {
      return this.element;
   }

   long getSequenceNumber() {
      return this.message.getSequenceNumber();
   }

   long getNextRetryDelay() {
      return this.retryController.getNextRetry();
   }

   void setFaultCode(int code) {
      this.faultCodes.add(new Integer(code));
   }

   ArrayList getFaultCodes() {
      return this.faultCodes;
   }

   boolean isExpired() {
      return ((SAFRequestImpl)this.message).isExpired();
   }

   synchronized boolean hasBeenHandled() {
      return this.message.getPayload() == null;
   }

   void setPrev(MessageReference prev) {
      this.prev = prev;
   }

   MessageReference getPrev() {
      return this.prev;
   }

   void setNext(MessageReference next) {
      this.next = next;
   }

   MessageReference getNext() {
      return this.next;
   }
}
