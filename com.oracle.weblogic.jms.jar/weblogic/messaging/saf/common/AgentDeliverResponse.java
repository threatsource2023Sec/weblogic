package weblogic.messaging.saf.common;

import weblogic.messaging.saf.SAFResult;

public final class AgentDeliverResponse {
   private SAFResultImpl result;

   public AgentDeliverResponse(SAFResultImpl result) {
      this.result = result;
   }

   public SAFResult getResult() {
      return this.result;
   }
}
