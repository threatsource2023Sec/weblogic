package weblogic.ejb20.cmp.rdbms.finders;

import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.j2ee.validation.IDescriptorError;
import weblogic.j2ee.validation.IDescriptorErrorInfo;
import weblogic.utils.PlatformConstants;

public final class InvalidFinderException extends Exception implements PlatformConstants, IDescriptorError {
   private static final long serialVersionUID = 110338118255488893L;
   public static final int NULL_NAME = 1;
   public static final int EMPTY_NAME = 2;
   public static final int INVALID_NAME_PREFIX = 3;
   public static final int NULL_QUERY = 4;
   public static final int INVALID_EXPRESSION_NUMBER = 5;
   public static final int EMPTY_EXPRESSION_TEXT = 6;
   public static final int EMPTY_EXPRESSION_TYPE = 7;
   public static final int INVALID_QUERY = 8;
   private int errorType;
   private String failedString;
   private IDescriptorErrorInfo errorInfo;

   public InvalidFinderException(int type, String invalidString) {
      this.errorType = 0;
      this.failedString = null;
      this.errorType = type;
      this.failedString = invalidString;
   }

   public InvalidFinderException(int type, String invalidString, IDescriptorErrorInfo errorInfo) {
      this(type, invalidString);
      this.errorInfo = errorInfo;
   }

   public int getErrorType() {
      return this.errorType;
   }

   public String getFailedString() {
      return this.failedString;
   }

   public String getMessage() {
      StringBuilder sb = new StringBuilder();
      EJBTextTextFormatter fmt = new EJBTextTextFormatter();
      switch (this.errorType) {
         case 1:
            sb.append(fmt.nullName(this.failedString));
            break;
         case 2:
            sb.append(fmt.emptyName(this.failedString));
            break;
         case 3:
            sb.append(fmt.invalidNamePrefix(this.failedString));
            break;
         case 4:
            sb.append(fmt.nullQuery(this.failedString));
            break;
         case 5:
            sb.append(fmt.invalidExpressionNumber(this.failedString));
            break;
         case 6:
            sb.append(fmt.emptyExpressionText(this.failedString));
            break;
         case 7:
            sb.append(fmt.emptyExpressionType(this.failedString));
            break;
         case 8:
            sb.append(fmt.invalidQuery(this.failedString));
      }

      sb.append(EOL);
      return sb.toString();
   }

   public boolean hasErrorInfo() {
      return this.errorInfo != null;
   }

   public IDescriptorErrorInfo getErrorInfo() {
      return this.errorInfo;
   }

   public void setDescriptorErrorInfo(IDescriptorErrorInfo errorInfo) {
      this.errorInfo = errorInfo;
   }
}
