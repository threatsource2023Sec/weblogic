package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public class RoleIdentifier extends Identifier {
   public RoleIdentifier(String roleName) {
      super(roleName);
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      return subject.isInRole((String)this.getId());
   }

   protected int getDependsOnInternal() {
      return 2;
   }

   char getTypeId() {
      return 'r';
   }

   public String getIdTag() {
      return "Rol";
   }
}
