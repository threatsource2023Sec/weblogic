package org.jboss.weld.context.unbound;

import javax.enterprise.util.AnnotationLiteral;

public class UnboundLiteral extends AnnotationLiteral implements Unbound {
   public static final Unbound INSTANCE = new UnboundLiteral();

   private UnboundLiteral() {
   }
}
