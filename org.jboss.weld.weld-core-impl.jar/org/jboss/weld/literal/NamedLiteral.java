package org.jboss.weld.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

public class NamedLiteral extends AnnotationLiteral implements Named {
   private static final long serialVersionUID = 5089199348756765779L;
   private final String value;
   public static final Named DEFAULT = new NamedLiteral("");

   public String value() {
      return this.value;
   }

   public NamedLiteral(String value) {
      this.value = value;
   }
}
