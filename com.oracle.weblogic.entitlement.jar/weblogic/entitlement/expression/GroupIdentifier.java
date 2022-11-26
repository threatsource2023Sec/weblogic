package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public final class GroupIdentifier extends Identifier {
   public GroupIdentifier(String id) {
      super(id);
   }

   public final boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      return subject.isMemberOf((String)this.getId());
   }

   char getTypeId() {
      return 'g';
   }

   public String getIdTag() {
      return "Grp";
   }
}
