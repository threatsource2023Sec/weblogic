package org.jboss.weld.literal;

import javax.enterprise.inject.Stereotype;
import javax.enterprise.util.AnnotationLiteral;

public class StereotypeLiteral extends AnnotationLiteral implements Stereotype {
   private static final long serialVersionUID = -974277187448157814L;
   public static final Stereotype INSTANCE = new StereotypeLiteral();

   private StereotypeLiteral() {
   }
}
