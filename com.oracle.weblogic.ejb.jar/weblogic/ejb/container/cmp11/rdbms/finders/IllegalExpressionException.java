package weblogic.ejb.container.cmp11.rdbms.finders;

import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.utils.PlatformConstants;

public final class IllegalExpressionException extends Exception implements PlatformConstants {
   private static final long serialVersionUID = -3707529156302719304L;
   public static final int INVALID_IDENTIFIER = 1;
   public static final int INVALID_OPERATION = 2;
   public static final int INVALID_EXPRESSION_TYPE = 3;
   public static final int COULD_NOT_PARSE = 4;
   private int errorCode;
   private String identifier;
   private Finder finder;
   private Finder.FinderExpression expression;
   private String message;

   public IllegalExpressionException(int exceptionCode, String id, Finder.FinderExpression expression) {
      this.errorCode = 0;
      this.identifier = null;
      this.finder = null;
      this.expression = null;
      this.message = null;
      this.errorCode = exceptionCode;
      this.identifier = id;
      this.expression = expression;
   }

   public IllegalExpressionException(int exceptionCode, String id) {
      this(exceptionCode, id, (Finder.FinderExpression)null);
   }

   public IllegalExpressionException(int exceptionCode, String id, String msg) {
      this(exceptionCode, id);
      this.message = msg;
   }

   public void setFinder(Finder finder) {
      this.finder = finder;
   }

   public String getMessage() {
      StringBuffer sb = new StringBuffer();
      EJBTextTextFormatter fmt = new EJBTextTextFormatter();
      sb.append(this.getClass().getName() + ":" + EOL);
      sb.append(fmt.whileTryingToProcess(this.finder == null ? null : this.finder.toUserLevelString(true)) + EOL);
      String wlql = "WLQL";
      switch (this.errorCode) {
         case 1:
            sb.append(fmt.invalidIdInExpression(wlql, this.identifier));
            break;
         case 2:
            sb.append(fmt.invalidOp(wlql, this.identifier));
            break;
         case 3:
            sb.append("Finder Expression: " + this.expression + " is invalid." + EOL);
            sb.append("It has type " + this.identifier + ", which is not a valid Finder Expression type.");
            break;
         case 4:
            sb.append(fmt.couldNotParse(wlql, this.identifier));
      }

      sb.append("  ").append(this.message);
      sb.append(EOL);
      return sb.toString();
   }
}
