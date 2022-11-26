package org.jboss.weld.literal;

import javax.enterprise.inject.Intercepted;
import javax.enterprise.util.AnnotationLiteral;

public class InterceptedLiteral extends AnnotationLiteral implements Intercepted {
   private static final long serialVersionUID = -5006473476809796434L;
   public static final InterceptedLiteral INSTANCE = new InterceptedLiteral();

   private InterceptedLiteral() {
   }
}
