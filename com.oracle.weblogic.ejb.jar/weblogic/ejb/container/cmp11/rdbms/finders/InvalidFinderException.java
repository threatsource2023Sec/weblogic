package weblogic.ejb.container.cmp11.rdbms.finders;

public final class InvalidFinderException extends Exception {
   private static final long serialVersionUID = -1148840916163106141L;
   public static final int NULL_NAME = 1;
   public static final int EMPTY_NAME = 2;
   public static final int INVALID_NAME_PREFIX = 3;
   public static final int NULL_QUERY = 4;
   public static final int INVALID_EXPRESSION_NUMBER = 5;
   public static final int EMPTY_EXPRESSION_TEXT = 6;
   public static final int EMPTY_EXPRESSION_TYPE = 7;
   public static final int INVALID_QUERY = 8;
   private int errorType = 0;
   private String failedString = null;

   public InvalidFinderException(int type, String invalidString) {
      this.errorType = type;
      this.failedString = invalidString;
   }

   public int getErrorType() {
      return this.errorType;
   }

   public String getFailedString() {
      return this.failedString;
   }
}
