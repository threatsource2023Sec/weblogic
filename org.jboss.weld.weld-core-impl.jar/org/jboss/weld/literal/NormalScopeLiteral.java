package org.jboss.weld.literal;

import javax.enterprise.context.NormalScope;
import javax.enterprise.util.AnnotationLiteral;

public class NormalScopeLiteral extends AnnotationLiteral implements NormalScope {
   private static final long serialVersionUID = -411873333591249571L;
   private final boolean passivating;

   public NormalScopeLiteral(boolean passivating) {
      this.passivating = passivating;
   }

   public boolean passivating() {
      return this.passivating;
   }
}
