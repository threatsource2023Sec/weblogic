package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public final class Inverse extends Function {
   public Inverse(EExprRep arg0) {
      super(arg0);
      if (arg0 == null) {
         throw new IllegalArgumentException("Inverse: non-null argument is expected");
      }
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      return !this.mArgs[0].evaluate(subject, resource, context, registry);
   }

   char getTypeId() {
      return '~';
   }

   protected void writeExternalForm(StringBuffer buf) {
      if (this.Enclosed) {
         buf.append('{');
      }

      buf.append(this.getTypeId());
      this.mArgs[0].writeExternalForm(buf);
      if (this.Enclosed) {
         buf.append('}');
      }

   }
}
