package weblogic.ejb20.cmp.rdbms.finders;

import java.io.Serializable;

public final class ExpressionParserException extends Exception implements Serializable {
   private static final long serialVersionUID = 4906284560152265516L;

   public ExpressionParserException(String reason) {
      super(reason);
   }
}
