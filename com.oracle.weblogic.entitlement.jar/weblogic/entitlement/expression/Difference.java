package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public final class Difference extends Function {
   public Difference(EExprRep arg0, EExprRep arg1) {
      super(arg0, arg1);
   }

   public Difference(EExprRep[] args) {
      super(args);
      if (args.length < 2) {
         throw new IllegalArgumentException("Difference: at least two arguments are expected");
      }
   }

   public boolean evaluate(ESubject subject, ResourceNode resource, ContextHandler context, PredicateRegistry registry) {
      if (!this.mArgs[0].evaluate(subject, resource, context, registry)) {
         return false;
      } else {
         int i = 1;

         for(int total = this.mArgs.length; i < total; ++i) {
            if (this.mArgs[i].evaluate(subject, resource, context, registry)) {
               return false;
            }
         }

         return true;
      }
   }

   char getTypeId() {
      return '-';
   }
}
