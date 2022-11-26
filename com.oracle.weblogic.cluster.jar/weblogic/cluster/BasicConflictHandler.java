package weblogic.cluster;

final class BasicConflictHandler implements ConflictHandler {
   public BasicConflictHandler() {
   }

   public void conflictStart(ServiceOffer offer) {
      if (offer.isClusterable()) {
         ClusterLogger.logConflictStartInCompatibleClusterableObject(offer.name(), offer.serviceName());
      } else {
         ClusterLogger.logConflictStartNonClusterableObject(offer.name(), offer.serviceName());
      }

   }

   public void conflictStop(ServiceOffer offer) {
      ClusterLogger.logConflictStop(offer.name(), offer.serviceName());
   }
}
