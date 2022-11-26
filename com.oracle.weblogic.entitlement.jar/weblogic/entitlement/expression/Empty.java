package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public final class Empty extends EExprRep {
   public static final Empty EMPTY = new Empty();

   protected int getDependsOnInternal() {
      return 0;
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      return false;
   }

   char getTypeId() {
      return 'e';
   }

   void outForPersist(StringBuffer buf) {
      buf.append(this.getTypeId());
   }

   protected void writeExternalForm(StringBuffer buf) {
      buf.append('{');
      buf.append('}');
   }
}
