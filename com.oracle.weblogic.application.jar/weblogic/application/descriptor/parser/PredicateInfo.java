package weblogic.application.descriptor.parser;

import java.util.ArrayList;
import java.util.Collection;

public class PredicateInfo {
   private ArrayList predicates = new ArrayList();

   public void addExpression(PredicateExpression expr) {
      this.predicates.add(expr);
   }

   public Collection getExpressions() {
      return this.predicates;
   }
}
