package org.jboss.weld.context.bound;

import javax.enterprise.util.AnnotationLiteral;

public class BoundLiteral extends AnnotationLiteral implements Bound {
   public static final Bound INSTANCE = new BoundLiteral();

   private BoundLiteral() {
   }
}
