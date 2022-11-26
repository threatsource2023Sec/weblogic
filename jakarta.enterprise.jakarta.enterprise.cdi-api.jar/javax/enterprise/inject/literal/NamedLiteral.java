package javax.enterprise.inject.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Named;

public final class NamedLiteral extends AnnotationLiteral implements Named {
   public static final Named INSTANCE = of("");
   private static final long serialVersionUID = 1L;
   private final String value;

   public static NamedLiteral of(String value) {
      return new NamedLiteral(value);
   }

   public String value() {
      return this.value;
   }

   private NamedLiteral(String value) {
      this.value = value;
   }
}
