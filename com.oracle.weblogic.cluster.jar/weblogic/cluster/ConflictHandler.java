package weblogic.cluster;

public interface ConflictHandler {
   void conflictStart(ServiceOffer var1);

   void conflictStop(ServiceOffer var1);
}
