package weblogic.utils.expressions;

import java.io.Serializable;

public class ExpressionParserException extends Exception implements Serializable {
   private static final long serialVersionUID = 4712773711138878642L;

   public ExpressionParserException(String reason) {
      super(reason);
   }

   public ExpressionParserException(String reason, Throwable cause) {
      super(reason, cause);
   }
}
