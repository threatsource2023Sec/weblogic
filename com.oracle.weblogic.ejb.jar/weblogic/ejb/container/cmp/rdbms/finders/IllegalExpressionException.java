package weblogic.ejb.container.cmp.rdbms.finders;

import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.j2ee.validation.IDescriptorError;
import weblogic.j2ee.validation.IDescriptorErrorInfo;
import weblogic.utils.PlatformConstants;

public final class IllegalExpressionException extends Exception implements PlatformConstants, IDescriptorError {
   private static final long serialVersionUID = -4466917394467675048L;
   public static final int PATH_EXPRESSION_OUTSIDE_OF_QUERY_TREE = 5;
   public static final int WARNING = 6;
   public static final int ERROR_ENCOUNTERED = 7;
   private int errorCode;
   private String identifier;
   private IDescriptorErrorInfo errorInfo;

   public IllegalExpressionException(int exceptionCode, String id) {
      this.errorCode = 0;
      this.identifier = null;
      this.errorCode = exceptionCode;
      this.identifier = id;
   }

   public IllegalExpressionException(int exceptionCode, String id, IDescriptorErrorInfo errorInfo) {
      this(exceptionCode, id);
      this.errorInfo = errorInfo;
   }

   public String getMessage() {
      StringBuffer sb = new StringBuffer();
      EJBTextTextFormatter fmt = new EJBTextTextFormatter();
      switch (this.errorCode) {
         case 5:
         case 7:
         default:
            sb.append(fmt.errorEncountered(this.identifier));
            break;
         case 6:
            sb.append(fmt.warning(this.identifier));
      }

      sb.append(EOL);
      return sb.toString();
   }

   public int getErrorCode() {
      return this.errorCode;
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
