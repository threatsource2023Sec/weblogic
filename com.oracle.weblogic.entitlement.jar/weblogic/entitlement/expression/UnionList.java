package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public abstract class UnionList extends Function {
   public UnionList(EExprRep[] elements) {
      super(elements);
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      int i = 0;

      for(int total = this.mArgs.length; i < total; ++i) {
         if (this.mArgs[i].evaluate(subject, resource, context, registry)) {
            return true;
         }
      }

      return false;
   }

   protected void writeExternalForm(StringBuffer buf) {
      if (this.Enclosed) {
         buf.append('{');
      }

      buf.append(this.getListTag());
      buf.append('(');
      if (this.mArgs.length > 0) {
         for(int i = 0; i < this.mArgs.length; ++i) {
            if (i > 0) {
               buf.append(',');
            }

            ((Identifier)this.mArgs[i]).writeExternalIdentifier(buf);
         }
      }

      buf.append(')');
      if (this.Enclosed) {
         buf.append('}');
      }

   }

   public abstract String getListTag();
}
