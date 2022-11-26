package org.jboss.weld.literal;

import javax.enterprise.inject.Decorated;
import javax.enterprise.util.AnnotationLiteral;

public class DecoratedLiteral extends AnnotationLiteral implements Decorated {
   private static final long serialVersionUID = -2787762993635349364L;
   public static final DecoratedLiteral INSTANCE = new DecoratedLiteral();

   private DecoratedLiteral() {
   }
}
