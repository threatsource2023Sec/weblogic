package weblogic.cluster.replication;

public class QuerySessionResponse {
   private QuerySessionResponseMessage response = null;
   protected String id;

   QuerySessionResponse(String id) {
      this.id = id;
   }

   void setResponse(QuerySessionResponseMessage msg) {
      this.response = msg;
   }

   QuerySessionResponseMessage getResponse() {
      return this.response;
   }
}
