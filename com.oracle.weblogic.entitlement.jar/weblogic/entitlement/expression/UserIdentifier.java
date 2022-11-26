package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public final class UserIdentifier extends Identifier {
   public UserIdentifier(String id) {
      super(id);
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      return subject.isUser((String)this.getId());
   }

   char getTypeId() {
      return 'u';
   }

   public String getIdTag() {
      return "Usr";
   }
}
