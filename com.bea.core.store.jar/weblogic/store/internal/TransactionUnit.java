package weblogic.store.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TransactionUnit {
   private Set links = new HashSet();
   private List requests;
   private StoreRequest oneRequest;
   FlushUnit flushUnit = null;
   private boolean added = false;
   private boolean topicUnit = false;

   TransactionUnit(List requests) {
      this.requests = requests;
   }

   TransactionUnit(StoreRequest oneRequest) {
      this.oneRequest = oneRequest;
   }

   TransactionUnit() {
      this.topicUnit = true;
   }

   boolean isTopicUnit() {
      return this.topicUnit;
   }

   List getRequests() {
      return this.requests;
   }

   StoreRequest getOneRequest() {
      return this.oneRequest;
   }

   List getRequestsForAdding() {
      if (this.added) {
         System.out.println("getRequestsForAdding: add twice!!! this unit:" + this.requests);
         Thread.dumpStack();
      } else {
         this.added = true;
      }

      return this.requests;
   }

   StoreRequest getOneRequestForAdding() {
      if (this.added) {
         System.out.println("getOneRequestForAdding: add twice!!! this unit:" + this.oneRequest);
         Thread.dumpStack();
      } else {
         this.added = true;
      }

      return this.oneRequest;
   }

   void addLink(TransactionUnit link) {
      if (this.links == null) {
         this.links = new HashSet();
      }

      this.links.add(link);
   }

   Set getLinks() {
      return this.links;
   }

   void setFlushUnit(FlushUnit flushUnit) {
      this.flushUnit = flushUnit;
   }

   FlushUnit getFlushUnit() {
      return this.flushUnit;
   }
}
