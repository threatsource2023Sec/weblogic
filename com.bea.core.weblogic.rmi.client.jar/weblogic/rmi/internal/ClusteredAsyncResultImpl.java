package weblogic.rmi.internal;

public class ClusteredAsyncResultImpl extends AsyncResultImpl {
   private Object replicaInfo;

   public Object getObject() throws Throwable {
      if (this.objectRetrieved) {
         return this.result;
      } else {
         try {
            this.result = this.getResults();
            this.replicaInfo = this.inboundResponse.getReplicaInfo();
            this.objectRetrieved = true;
         } finally {
            this.closeResponse();
         }

         return this.result;
      }
   }

   public Object getReplicaInfo() throws Throwable {
      this.getObject();
      return this.replicaInfo;
   }
}
