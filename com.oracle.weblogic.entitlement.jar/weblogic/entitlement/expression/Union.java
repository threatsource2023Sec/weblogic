package weblogic.entitlement.expression;

import weblogic.entitlement.engine.ESubject;
import weblogic.entitlement.engine.PredicateRegistry;
import weblogic.entitlement.engine.ResourceNode;
import weblogic.security.service.ContextHandler;

public class Union extends Function {
   public Union(EExprRep arg0, EExprRep arg1) {
      super(arg0, arg1);
   }

   public Union(EExprRep[] args) {
      super(args);
      if (args.length < 2) {
         throw new IllegalArgumentException("Union: at least two arguments are expected");
      }
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

   char getTypeId() {
      return '|';
   }
}
