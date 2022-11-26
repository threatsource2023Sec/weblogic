package weblogic.store.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class FlushUnit {
   private final List requests = new LinkedList();
   private boolean containsTopicLoad = false;

   boolean hasTopicLoad() {
      return this.containsTopicLoad;
   }

   void markTopicLoad() {
      this.containsTopicLoad = true;
   }

   void addTransactionUnit(TransactionUnit tu) {
      if (tu.getOneRequest() != null) {
         this.requests.add(tu.getOneRequestForAdding());
      } else {
         this.requests.addAll(tu.getRequestsForAdding());
      }

   }

   int size() {
      return this.requests.size();
   }

   List getRequests() {
      return this.requests;
   }

   void sortRequests() {
      Collections.sort(this.requests, new Comparator() {
         public int compare(StoreRequest o1, StoreRequest o2) {
            long groupDifference = o1.getFlushGroup() - o2.getFlushGroup();
            return groupDifference == 0L ? Long.signum(o1.getLiveSequence() - o2.getLiveSequence()) : Long.signum(groupDifference);
         }
      });
   }
}
